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
        :before-upload="handleBefore">
          <el-button slot="trigger" size="small" type="primary">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">只能上传图片文件，且不超过{{maxKb||500}}kb</div>
      </el-upload>
  </div>
</template>
<script>
import { getOssInfo } from "@/api/sys";
import { randomFileName } from "@/utils";
import OSS from "ali-oss";

export default {
  name: "ql-upload-max-img",
  props: {
    maxKb: Number
  },
  data() {
    return {
      fileList: []
    };
  },
  methods: {
    handleBefore(file) {
      let mx = Number.parseInt(this.maxKb);
      if (!Number.isInteger(mx) || mx <= 0) {
        mx = 500;
      }
      if (file.size > mx * 1024) {
        this.$message.warning(`文件大小超过了 ${mx}kb`);
        return false;
      }
      this.$emit("beforeUpload", file);
      return true;
    },
    ossUpload(req) {
      const self = this;

      return getOssInfo({}).then(resp => {
        const extMap = {
          "audio/mp3": ".mp3",
          "image/jpeg": ".jpg"
        };
        // console.log(
        //   resp.data.credentials.accessKeyId +
        //     ":==>" +
        //     resp.data.credentials.accessKeySecret +
        //     ":==>" +
        //     resp.data.credentials.securityToken
        // );
        let client;
        client = new OSS.Wrapper({
          accessKeyId: resp.data.ossAccessId,
          accessKeySecret: resp.data.ossAccessKey,
          region: "oss-cn-hangzhou",
          bucket: "ql-res"
        });
        // console.log("file=" + JSON.stringify(req.file));
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
    hanldeFileSuccess (response, file, fileList) {
      const self = this;
      console.log("response=" + JSON.stringify(response));
      self.$emit("update", "https://media.nicebooker.com/" + response.name);
    },
    handlePictureCardPreview(file) {
      console.log('handlePictureCardPreview: ', file)
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

