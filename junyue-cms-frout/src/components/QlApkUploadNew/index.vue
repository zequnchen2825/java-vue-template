<template>
  <el-upload
    ref="upload"
    :http-request="ossUpload"
    class="avatar-uploader"
    action="http://oss-cn-hangzhou.aliyuncs.com"
    :accept="accept"
    list-type="picture-card"
    :on-success="hanldeFileSuccess"
    :before-upload="handleBefore"
    :limit="limit"
    :show-file-list="false"
    >
    <slot name="btn"></slot>
    <img v-if="url" :src="url" class="avatar11">
  </el-upload>
</template>
<script>
import { getOssInfo } from "@/api/sys";
import { randomFileName } from "@/utils";
import { validateURL } from "@/utils/validate";
import OSS from "ali-oss";

export default {
  name: "pic-upload",
  props: { limit:Number, accept: String, url: String, maxKb: Number, tips: String, index: Number },
  methods: {
    handleBefore(file, e) {
      let mx = Number.parseInt(this.maxKb);
      let arrowType = ['image/gif','image/jpeg','image/jpg','image/png','image/svg']
      if (!Number.isInteger(mx) || mx <= 0) {
        mx = 500;
      }
      console.log(file.type);
      if (!arrowType.includes(file.type)) {
        this.$message.warning(`只能上传图片`);
        return false;
      }
      if (file.size > mx * 1024) {
        this.$message.warning(`文件大小超过了 ${mx}kb`);
        return false;
      }
      return true;
    },
    ossUpload(req) {
      const self = this;

      return getOssInfo({}).then(resp => {
        const extMap = {
          "audio/mp3": ".mp3",
          "image/jpeg": ".jpg"
        };
        console.log(
          resp.data.credentials.accessKeyId +
            ":==>" +
            resp.data.credentials.accessKeySecret +
            ":==>" +
            resp.data.credentials.securityToken
        );
        let client;
        client = new OSS.Wrapper({
          accessKeyId: resp.data.ossAccessId,
          accessKeySecret: resp.data.ossAccessKey,
          region: "oss-cn-hangzhou",
          bucket: "ql-res"
        });
        console.log("file=" + JSON.stringify(req.file));
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
                console.log(p);
                done();
              };
            }
          }
        );
      });
    },
    hanldeFileSuccess(response, file, fileList) {
      const self = this;
      const imageUrl = "https://media.nicebooker.com/" + response.name
      console.log("response=" + JSON.stringify(response));
      this.imageUrl = imageUrl
      self.$emit("update:url", "https://media.nicebooker.com/" + response.name);
      self.$emit("success", {
        index: this.index,
        url: imageUrl
      });
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
.avatar11 {
  width: 100%;
  display: block;
  z-index: 2;
  position: relative;
  top: -100%;
}
</style>

