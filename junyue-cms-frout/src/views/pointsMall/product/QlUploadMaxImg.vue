<template>
  <div>
    <el-upload
      ref="upload"
      :http-request="ossUpload"
      action="http://oss-cn-hangzhou.aliyuncs.com"
      accept="image/*"
      list-type="picture"
      :show-file-list="false"
      :file-list="fileList"
      :on-preview="handlePictureCardPreview"
      :on-success="hanldeFileSuccess"
      multiple
      :before-upload="handleBefore"
    >
      <el-button @click="upload" size="small" type="primary" :loading="loading">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">{{tip}}</div>
    </el-upload>
  </div>
</template>
<script>
import { getOssInfo } from "@/api/sys";
import { randomFileName } from "@/utils";
import OSS from "ali-oss";

export default {
  name: "custom-ql-upload-max-img",
  props: {
    tip: {
      type: String,
      dafault: ""
    },
    handleBeforeLoadImg: {
      type: Function,
      default: () => {
        return true;
      }
    }
  },
  data() {
    return {
      fileList: [],
      type: "",
      loading: false
    };
  },
  methods: {
    upload() {
      this.$refs.upload.submit();
    },
    handleBefore(file) {
      return this.handleBeforeLoadImg();
    },
    ossUpload(req) {
      const self = this;
      this.loading = true;
      return getOssInfo({}).then(resp => {
        const extMap = {
          "image/jpeg": ".jpg"
        };
        let client;
        client = new OSS.Wrapper({
          accessKeyId: resp.data.ossAccessId,
          accessKeySecret: resp.data.ossAccessKey,
          region: "oss-cn-hangzhou",
          bucket: "ql-res"
        });
        const fileKey = randomFileName();
        let subfix = extMap[self.accept];
        if (typeof subfix == "undefined") {
          subfix = ".jpg";
        }
        return client.multipartUpload(
          "qlLive/parental/" + fileKey + subfix,
          req.file,
          {
            progress: function(p) {
              return function(done) {
                self.$refs["upload"].handleProgress(
                  { percent: p * 100 },
                  req.file
                );
                done();
              };
            }
          }
        );
      });
    },
    hanldeFileSuccess(response, file, fileList) {
      const self = this;
      this.loading = false;
      self.$emit("update", "https://media.nicebooker.com/" + response.name);
    },
    handlePictureCardPreview(file) {
      console.log("handlePictureCardPreview: ", file);
    }
  }
};
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
