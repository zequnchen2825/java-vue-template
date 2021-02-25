<template>
  <div>
        <el-upload
        ref="upload"
        :http-request="ossUpload"
        class="avatar-uploader"
        action="http://oss-cn-hangzhou.aliyuncs.com"
        :accept="accept"
        list-type="picture-card"
        :on-preview="handlePictureCardPreview"
        :on-remove="handleRemove"
        :on-success="hanldeFileSuccess"
        :before-upload="handleBefore"
        :limit="limit"
        >
        <i class="el-icon-plus"></i>
      </el-upload>
      <el-dialog :close-on-click-modal=false :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>
<script>
import { getOssInfo } from "@/api/sys";
import { randomFileName } from "@/utils";
import { validateURL } from "@/utils/validate";
import OSS from "ali-oss";

export default {
  name: "pic-upload",
  props: { limit:Number, accept: String, url: String, maxKb: Number },
  data() {
    return {
      dialogImageUrl: "",
      dialogVisible: false
    };
  },
  methods: {
    handleBefore(file) {
      let mx = Number.parseInt(this.maxKb);
      if (!Number.isInteger(mx) || mx <= 0) {
        mx = 500;
      }
      console.log(file.type);
      if (file.type != "image/jpeg") {
        this.$message.warning(`只能上传jpeg和jpg格式的图片`);
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
      console.log("response=" + JSON.stringify(response));
      self.$emit("update:url", "https://media.nicebooker.com/" + response.name);
      console.log('hhhhhhhhhhhhhhhhh')
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
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

