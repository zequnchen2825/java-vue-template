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
          with-credentials>
          <el-button slot="trigger" size="small" type="primary">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">
            <template v-if="size">{{`图片尺寸${sizeVerify ? '要求' : '建议'}：${size}`}}</template>
            图片大小小于{{maxKb}}kb
          </div>
      </el-upload>
  </div>
</template>
<script>
import { getOssInfo } from "@/api/sys";
import { randomFileName } from "@/utils";
import { validateURL, verifyPicSize } from "@/utils/validate";
import OSS from "ali-oss";


export default {
  name: "ql-upload-v2",
  props: { 
    supportGif:Boolean, 
    accept: String, 
    url: String, 
    maxKb: Number, 
    duration: Number, 
    size: String,
    sizeVerify:{
      default:false,
      type:Boolean,
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
    }
  },
  methods: {
    async handleBefore(file) {
      if(this.sizeVerify){
        let res = await verifyPicSize(file, (width, height) => {
            let arr = [...this.size.matchAll(/\d+/g)]
            let  [sw, sh] = arr.map(it => it[0]);
            if(width > sw || sh > sh){
              return false
            }else{
              return true;
            }
        })
        if(!res){
          this.$message.warning(`上传尺寸不符合要求!`);
          throw e;
        }
      }
      let mx = Number.parseInt(this.maxKb);
      if (!Number.isInteger(mx) || mx <= 0) {
        mx = 500;
      }
      // console.log(123, file.type)
      // if (!this.supportGif) {
      //   if (!(file.type == "image/jpeg" || file.type == "image/jpg" || file.type == "image/png" || file.type == "image/gif")) {
      //     this.$message.warning(`只能上传jpeg,jpg,png格式的图片`);
      //     throw false;
      //   }
      // }
      if (file.size > mx * 1024) {
        this.$message.warning(`文件大小超过了 ${mx}kb`);
        throw false;
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
        if (self.accept == "audio/mp3") {
          client = new OSS.Wrapper({
            accessKeyId: resp.data.credentials.accessKeyId,
            accessKeySecret: resp.data.credentials.accessKeySecret,
            secure: true,
            region: "oss-cn-hangzhou",
            stsToken: resp.data.credentials.securityToken,
            bucket: "qianliao-doc-download-402-301"
          });
        } else {
          client = new OSS.Wrapper({
            accessKeyId: resp.data.ossAccessId,
            accessKeySecret: resp.data.ossAccessKey,
            region: "oss-cn-hangzhou",
            bucket: "ql-res"
          });
        }
        console.log("file=" + JSON.stringify(req.file));
        console.log(req.file.type);

        const fileKey = randomFileName();
        var fileName = req.file.name;
        var index1=fileName.lastIndexOf(".");
        var index2 =fileName.length;
        let postfix = fileName.substring(index1,index2);
        console.log(postfix);
        if (typeof postfix == "undefined") {
          postfix = ".jpg";
        }
        return client.multipartUpload(
          "qlLive/parental/" + fileKey + postfix,
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
      // console.log("file="+JSON.stringify(file));
      // console.log("fileList="+JSON.stringify(fileList));
      if (self.accept == "audio/mp3") {
        let url = "https://docs.qianliaowang.com/" + response.name;
        self.$emit("update:url", url);
        url =
          "https://qianliao-doc-download-402-301.oss-cn-hangzhou.aliyuncs.com/" +
          response.name;
        let audio = document.createElement("AUDIO");
        audio.setAttribute("src", url);
        audio.setAttribute("controls", "controls");
        audio.addEventListener("loadedmetadata", e => {
          console.log("audio.duration=" + audio.duration);
          self.$emit("update:duration", Math.floor(audio.duration));
        });
      } else {
        self.$emit("update:url", "https://media.nicebooker.com/" + response.name);
      }
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

