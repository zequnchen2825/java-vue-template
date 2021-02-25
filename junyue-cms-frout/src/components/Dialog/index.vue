<template>
     <div  style="border: 1px dashed #eee; padding: 30px; margin-bottom: 50px; border-radius: 8px;">
          <div v-for="(item,itemIndex) in items" :key="itemIndex">
          <el-input v-model="item.id" type="hidden"></el-input>
            <el-form-item  label="子标题:">
              <el-input style="max-width:200px" v-model="item.title"></el-input>
            </el-form-item>
          <div v-for="(module, index) in item.profileList" :key="index">
            <el-form-item v-if="module.type === 'image'" label="图片:">
              <ql-upload accept="image/*" :url.sync="module.content" :max-kb="40000"></ql-upload> 
            </el-form-item>
            <el-form-item v-else-if="module.type === 'text'" label="文字:">
              <el-input type="textarea" v-model="module.content"></el-input>
            </el-form-item>
          </div>
          
          <div style="text-align: right">
            <el-button type="primary" @click="addTextModule(itemIndex)">添加文字</el-button>
            <el-button type="primary" @click="addImageModule(itemIndex)">添加图片</el-button>
          </div>
          </div>
          <div slot="footer">
          <el-button type="primary" @click="addModule">新增子模块</el-button>
          <el-button type="primary" @click="saveData">保存</el-button>
          <el-button @click="subCancelDialog">取消</el-button>
        </div>
     </div>
</template>

<script>
import { getOssInfo } from "@/api/sys";
import { noblankValidator, numberValidator } from "@/utils/formValidator";
import {
  responseNotify,
  statusFilter,
  statusMap,
  displayStatusFilter,
  displayStatusMap,
  formatTimeFilter,
  dpOpts,
  clearObj,
  businessStatusMap
} from "@/utils";
import picUpload from "@/components/PicUpload/index.vue";
import midEvent from "@/components/Dialog/midEvent.vue";

export default {
  name: "desc-dialog",
  props: {
    accept: String,
    url: String,
    maxKb: Number,
    duration: Number
    // moduleId: undefined,
    // moduleTitle: undefined,
    // profileList: []
  },
  data() {
    return {
      items: [
        {
          id: undefined,
          title: undefined,
          profileList: [
            {
              type: "image",
              content: ""
            },
            {
              type: "text",
              content: ""
            }
          ]
        }
      ],
      descDialogFormVisible: false
    };
  },

  created() {
    midEvent.$on("modifyDesc", val => {
      console.log("修改简介,从父组件传值过来");
      console.log(val);
      this.items = [
        {
          id: undefined,
          title: undefined,
          profileList: [
            {
              type: "image",
              content: ""
            },
            {
              type: "text",
              content: ""
            }
          ]
        }
      ];
      this.items = val;
    });
  },

  components: {
    picUpload
  },

  methods: {
    saveData() {
      this.$emit("saveDesc", this.items);
    },
    subCancelDialog() {
      this.items = [
        {
          id: undefined,
          title: undefined,
          profileList: [
            {
              type: "image",
              content: ""
            },
            {
              type: "text",
              content: ""
            }
          ]
        }
      ];
      this.$emit("cancelDialog");
    },
    addModule() {
      const self = this;
      this.items.push({
        moduleId: "",
        moduleTitle: "",
        profileList: [
          {
            type: "image",
            content: ""
          },
          {
            type: "text",
            content: ""
          }
        ]
      });
    },
    saveDescData() {},
    resetTemp() {
      const self = this;
      clearObj(self.modules);
      self.fileList = [];
    },
    addTextModule(itemIndex) {
      this.items[itemIndex].profileList.push({
        type: "text",
        content: ""
      });
    },
    addImageModule(itemIndex) {
      this.items[itemIndex].profileList.push({
        type: "image",
        content: ""
      });
    }
  }
};
</script>
