<template>
  <div>
    <el-upload
      ref="upload"
      action="http://oss-cn-hangzhou.aliyuncs.com"
      :http-request="ossUpload"
      :before-upload="handleBefore"
      accept="video/*"
      :limit="1"
      :file-list="fileList"
      :on-remove="handleRemove"
      with-credentials
    >
      <el-button slot="trigger" size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">只能上传视频文件，且不超过{{maxKb||500}}kb</div>
    </el-upload>
    <el-progress
      v-show="showProgress"
      :text-inside="true"
      :stroke-width="15"
      :percentage="uploadPercent"
    ></el-progress>
  </div>
</template>

<script>
import { getOssInfo } from "@/api/sys";
import { randomFileName } from "@/utils";
import { validateURL } from "@/utils/validate";
import OSS from "ali-oss";
import { uploadVideo } from "@/utils/videoUploader";

export default {
  data() {
    return {
      showProgress: undefined,
      uploadPercent: undefined
    };
  },
  name: "ql-video-upload",
  props: {
    url: String,
    maxKb: Number,
    duration: Number,
    resourceName: String,
    extraData: Object,
    progressByEvent: {
      type: Boolean,
      default: false
    },
    subject: String
  },
  computed: {
    fileList: {
      get: function() {
        if (this.url) {
          return [{ name: this.resourceName, url: this.url }];
        } else {
          return [];
        }
      }
      // set:function(val){

      // }
    }
  },
  mounted: function() {
    console.log('mounted----', this.uploadPercent)
    if (this.progressByEvent) {
      window.uploadProcess = new EventTarget();
      uploadProcess.addEventListener("process", e => {
        this.showProgress = true;
        //console.log(e);
        this.uploadPercent = e.percent;
      });
    }
  },
  created() {
    console.log('创建----', this.uploadPercent)
  },
  destroyed() {
    console.log('销毁------', this.uploadPercent)
  },
  methods: {
    sendPath(ret) {
      // console.log("发送消息中" + ret);
      this.$emit("handlePath", ret, this.extraData || null);
    },
    handleBefore(file) {
      let mx = Number.parseInt(this.maxKb * 1024);
      if (!Number.isInteger(mx) || mx <= 0) {
        mx = 500;
      }
      if (file.size > mx) {
        this.$message.warning(`文件大小超过了 ${mx}kb`);
        return false;
      }
      return true;
    },
    ossUpload(req) {
      //var data = success;
      console.log("请求对象" + JSON.stringify(req), this.subject);
      getOssInfo({}).then(resp => {
        uploadVideo(
          req.file,
          resp.data.credentials.accessKeySecret,
          resp.data.credentials.securityToken,
          resp.data.credentials.securityToken,
          {
            handleProgress: this.handleProgress
          },
          this.subject || 'chinese'
        ).then(response => {
          console.log(JSON.stringify(response));
          this.sendPath(response);
        });
      });
    },
    handleProgress(e) {
      this.showProgress = true;
      this.uploadPercent = e.percent;
      console.info("handleProgress", e);
    },
    handleRemove(file, fileList) {
      if(this.showProgress) {
        this.showProgress = false
      }
      this.sendPath({
        uploadInfo: {
          videoId: ''
        }
      });
    }
  }
};
</script>

