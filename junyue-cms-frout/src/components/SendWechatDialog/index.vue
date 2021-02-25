<template>
  <el-dialog
    title="发送微信消息"
    :close-on-click-modal="false"
    width="640px"
    v-bind="$attrs"
    v-on="$listeners"
    class="wx-message-dialog"
    @close="handleDialogClose"
  >
    <el-alert
      title="注意：发送时请确保微信已登录wehub，发送顺序为依次发送，选择过多时会导致发送时间过长，请合理选择发送数量。"
      type="warning"
      :closable="false"
    ></el-alert>

    <el-tag style="margin-top: 10px">可发送数：{{leadId.length}}</el-tag>

    <el-row type="flex" style="width:230px;justify-content:space-between;margin-top: 16px">
      <el-button size="mini" type="primary" @click="onTextButtonClick" icon="el-icon-plus">文本</el-button>
      <FileUpload :accept="fileAccept" :fileItem.sync="fileItem" @file-handle="addFileItem($event)"></FileUpload>
      <el-button
        size="mini"
        type="primary"
        :disabled="sendContentList.length===0"
        @click="sendContentList=[]"
      >清空</el-button>
    </el-row>
    <el-divider></el-divider>
    <el-card shadow="never">
      <div slot="header">发送内容</div>
      <div v-if="sendContentList">
        <draggable
          v-model="sendContentList"
          element="div"
          :options="dragOptions"
          @start="dragging = true"
          @end="dragging = false"
        >
          <div
            v-for="(item, index) in sendContentList"
            :key="item.content.path || item.content"
            class="dragBox"
          >
            <template v-if="item.type === 'text'">
              <text-item
                class="content-item"
                :content="item.content"
                :index="index + 1"
                @text-handle="textItemHandle($event, item, index)"
              ></text-item>
            </template>
            <template v-else-if="item.type === 'file'">
              <file-item
                class="content-item"
                :props="item.content"
                :index="index + 1"
                @file-handle="fileItemHandle($event, item, index)"
              ></file-item>
            </template>
          </div>
        </draggable>
      </div>
    </el-card>

    <div slot="footer">
      <el-button
        type="primary"
        :disabled="sendContentList.length===0"
        @click="handleSubmit"
        :loading="isLoading"
      >{{sendButtonName}}</el-button>
      <el-button @click="$emit('update:visible',false)">取消</el-button>
    </div>

    <!--新建/编辑 文本-->
    <add-text-dialog
      :visible.sync="isShow.sendText"
      :type="textDialogType"
      append-to-body
      :text-content="defaultEditTextValue"
      @get-content="handleGetTextContent"
    />
  </el-dialog>
</template>

<script>
import draggable from "vuedraggable";
import FileUpload from "@/views/crm/sop/templates/components/fileUpload";
import FileItem from "@/views/common/component/fileItem";
import TextItem from "@/views/common/component/textItem";
import AddTextDialog from "./components/AddTextDialog";
import { sendWXMessage } from "@/api/crm/saleList";

export default {
  name: "SendWechatDialog",
  components: {
    draggable,
    FileUpload,
    FileItem,
    TextItem,
    AddTextDialog
  },
  props: {
    leadId: {
      type: [Number, Array],
      require: true
    },
    customRequest: {
      type: Function,
      require: false
    }
  },
  data() {
    return {
      /*添加/编辑文字弹窗状态*/
      textDialogType: "NEW",
      textDialogIndex: -1,
      defaultEditTextValue: "",
      /**************************************/
      fileItem: null,
      fileAccept:
        "image/png, image/jpeg, application/msword, application/vnd.ms-powerpoint,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,video/mp4",
      sendContentList: [],
      isShow: {
        sendText: false,
        sendFile: false
      },
      dragOptions: {
        draggable: ".dragBox",
        filter: "input,textarea",
        preventOnFilter: false,
        scroll: true
      },
      sendButtonName: "发送",
      isLoading: false
    };
  },
  methods: {
    handleDialogClose() {
      this.sendContentList = [];
    },
    onTextButtonClick() {
      this.textDialogType = "NEW";
      this.isShow.sendText = true;
    },
    handleDeleteContentItem(id, index) {
      this.$confirm("此操作将删除选中项, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.sendContentList.splice(index, 1);
        })
        .catch(() => {
          // 取消清除
        });
    },
    fileItemHandle(type, item, index) {
      if (type === "del") {
        this.handleDeleteContentItem(item.id, index);
      }
    },
    textItemHandle(type, item, index) {
      if (type === "edit") {
        this.textDialogType = "EDIT";
        this.textDialogIndex = index;
        this.defaultEditTextValue = item.content;
        this.isShow.sendText = true;
      }
      if (type === "del") {
        this.textDialogIndex = index;
        this.handleDeleteContentItem(item.id, index);
      }
    },
    handleGetTextContent({type = "", content = ""}) {
      if (type === "NEW") {
        this.sendContentList.push({
          type: "text",
          content: content
        });
      }
      if (type === "EDIT") {
        this.sendContentList[this.textDialogIndex].content = content;
      }
    },
    handleSubmit() {
      if (this.isLoading) {
        return;
      }

      this.sendButtonName = "发送中...";
      this.isLoading = true;

      const tmpSendContent = this.sendContentList.map(item => {
        if (item.type === "file") {
          if (item.content.ext === "png" || item.content.ext === "jpg") {
            item.type = "image";
          }
          item.content = JSON.stringify(item.content);
        }

        return {...item, type: item.type.toUpperCase()};
      });

      if (typeof this.customRequest === 'function') {
        this.customRequest(tmpSendContent)
          .then(() => {
            this.sendContentList = [];
          })
          .finally(() => {
            this.sendButtonName = "发送";
            this.isLoading = false;
          });
      }else{
        if (this.leadId.length === 200) {
          this.$message.error("请至少选择一个已添加好友的线索");
          return;
        }
        sendWXMessage({
          leadsIdList: this.leadId,
          messageList: tmpSendContent
        })
          .then(response => {
            if (response.code === 200) {
              this.$message({
                showClose: true,
                message: "发送成功",
                type: "success"
              });
              this.sendContentList = [];

              this.$emit("update:visible", false);
            }

            this.sendButtonName = "发送";
            this.isLoading = false;
          })
          .catch(e => {
            this.$message({
              showClose: true,
              message: "发送失败",
              type: "error"
            });

            this.sendButtonName = "发送";
            this.isLoading = false;
            console.log(e);
          });
      }
    },
    addFileItem(file) {
      if (file.size > 1024 * 1024 * 25) {
        this.$message({
          showClose: true,
          message: "文件大小最多不超过25M",
          type: "error"
        });
        return;
      }

      file.content = {
        path: file.content,
        ext: file.ext,
        name: file.fileName,
        size: file.size
      };

      this.sendContentList.push(file);
    }
  }
};
</script>
<style lang="scss">
.content-item {
  margin-bottom: 20px;
}
.wx-message-dialog .el-dialog__body {
  padding: 20px;
}
</style>
