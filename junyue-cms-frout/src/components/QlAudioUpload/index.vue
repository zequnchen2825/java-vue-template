<template>
  <div>
    <el-upload
      ref="upload"
      action="http://oss-cn-hangzhou.aliyuncs.com"
      :http-request="ossUpload"
      :on-success="hanldeFileSuccess"
      :on-exceed="handleFileExceed"
      :on-remove="handleFileRemove"
      :before-upload="handleBefore"
      :file-list="fileList"
      :accept="accept"
      :limit="1"
      with-credentials
    >
      <el-button slot="trigger" size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">只能上传音频短视频文件，且不超过{{maxKb||500}}kb</div>
    </el-upload>
  </div>
</template>
<script>
import { getOssInfo } from "@/api/sys";
import { randomFileName } from "@/utils";
import { validateURL } from "@/utils/validate";
import { extname } from "path";
import OSS from "ali-oss";

export default {
  name: "ql-audio-upload",
  props: {
    accept: String,
    url: String,
    maxKb: Number,
    duration: Number,
    encrypt: {
      default: true,
      type: Boolean
    }
  },
  computed: {
    fileList: {
      get: function() {
        if (this.url && validateURL(this.url)) {
          return [{ name: "file", url: this.url }];
        } else {
          return [];
        }
      }
      // set:function(val){

      // }
    },
    listType: {
      get: function() {
        if (this.accept && this.accept.startsWith("image/")) {
          return "picture";
        }
        return "";
      }
    }
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
      return true;
    },
    ossUpload(req) {
      const self = this;

      return getOssInfo({})
        .then(resp => {
          const extMap = {
            "audio/*": ".mp3",
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
          if ( (self.accept.startsWith("audio/") || self.accept.startsWith("video/")) && this.encrypt ) {
            client = new OSS.Wrapper({
              accessKeyId: resp.data.credentials.accessKeyId,
              accessKeySecret: resp.data.credentials.accessKeySecret,
              secure: true,
              region: resp.data.region,
              stsToken: resp.data.credentials.securityToken,
              bucket: resp.data.stsBucket
            });
          } else {
            client = new OSS.Wrapper({
              accessKeyId: resp.data.ossAccessId,
              accessKeySecret: resp.data.ossAccessKey,
              region: resp.data.region,
              bucket: resp.data.bucket
            });
          }
          const fileKey = randomFileName();
          let subfix = extMap[self.accept];
          if (typeof subfix == "undefined") {
            subfix = "";
          }
          let fileType = ".jpg";
          if (req.file.name) {
            fileType = extname(req.file.name);
          }
          return client.multipartUpload(
            resp.data.ossPath + fileKey + fileType,
            req.file,
            {
              progress: function(p) {
                return function(done) {
                  self.$refs["upload"].handleProgress(
                    { percent: p * 100 },
                    req.file
                  );
                  // console.log(p);
                  done();
                };
              }
            }
          );
        })
        .catch(error => {
          console.log("error---", error);
        });
    },
    hanldeFileSuccess(response, file, fileList) {
      if(response.res.statusCode!=200){
          this.$message.warning(`上传失败`);
          return ;
      }
      const self = this;
      getOssInfo({}).then(resp => {
        if (self.accept.startsWith("audio/")) {
          let prefix = this.encrypt ? resp.data.stsPrefix : 'https://media.nicebooker.com/' //resp.data.prefix;
          let url = prefix + response.name;
          self.$emit("update:url", url);

          let reurl = this.encrypt
            ? "https://" +
              resp.data.stsBucket +
              "." +
              resp.data.region +
              ".aliyuncs.com/" +
              response.name
            : url;

          let audio = document.createElement("AUDIO");
          audio.setAttribute("src", reurl);
          audio.setAttribute("controls", "controls");
          audio.addEventListener("loadedmetadata", e => {
            self.$emit("update:duration", Math.ceil(audio.duration));
          });
        } else if (self.accept.startsWith("video/")) {
          let url = resp.data.stsPrefix + response.name;
          self.$emit("update:url", url);
        } else {
          self.$emit("update:url", resp.data.prefix + response.name);
        }
      });
    },
    handleFileExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择1个文件，本次选择了 ${
          files.length
        } 个文件，共选择了 ${files.length + fileList.length} 个文件`
      );
    },
    handleFileRemove(file, fileList) {
      const self = this;
      self.$emit("update:url", "");
      self.$emit("update:duration", 0);
    }
  }
};
</script>

