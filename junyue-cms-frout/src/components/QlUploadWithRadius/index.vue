<template>
  <div class="container">
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
      :auto-upload="true"
      list-type="picture"
      class="upload"
      with-credentials
    >
      <el-button slot="trigger" size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="upload-tip">
        <template v-if="size">{{`图片尺寸${sizeVerify ? '要求' : '建议'}：${size}`}}</template>
        图片大小小于{{maxKb}}kb
        <div class="upload-tip tip-red">如果需要给图片自定义圆角请在右侧设置好圆角后再点击上传图片，默认圆角为32,单位px</div>
      </div>
    </el-upload>
    <div class="input-box">
      <div class="input">
        <div :span="12">
          左上角圆角：
          <el-input-number :min="0" v-model="leftTopRadius" size="small" />
        </div>
        <div :span="12">
          右上角圆角：
          <el-input-number :min="0" v-model="rightTopRadius" size="small" />
        </div>
      </div>
      <div class="input">
        <div :span="12">
          左下角圆角：
          <el-input-number :min="0" v-model="leftBottomRadius" size="small" />
        </div>
        <div :span="12">
          右下角圆角：
          <el-input-number :min="0" v-model="rightBottomRadius" size="small" />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { getOssInfo } from "@/api/sys";
import { randomFileName } from "@/utils";
import { validateURL, verifyPicSize } from "@/utils/validate";
import OSS from "ali-oss";
import ClipImg from "@/utils/clipImg";

export default {
  name: "ql-upload-with-radius",
  props: {
    supportGif: Boolean,
    accept: String,
    url: String,
    maxKb: Number,
    duration: Number,
    size: String,
    sizeVerify: {
      default: false,
      type: Boolean,
    },
  },
  data() {
    return {
      leftTopRadius: 32,
      rightTopRadius: 32,
      leftBottomRadius: 32,
      rightBottomRadius: 32,
    };
  },
  computed: {
    fileList: {
      get: function () {
        if (this.url && validateURL(this.url)) {
          return [{ name: "file", url: this.url }];
        } else {
          return [];
        }
      },
    },
  },
  methods: {
    async handleBefore(file) {
      if (this.sizeVerify) {
        let res = await verifyPicSize(file, (width, height) => {
          let arr = [...this.size.matchAll(/\d+/g)];
          let [sw, sh] = arr.map((it) => it[0]);
          if (width > sw) {
            return false;
          } else {
            return true;
          }
        });
        if (!res) {
          this.$message.warning(`上传尺寸不符合要求!`);
          throw e;
        }
      }
      let mx = Number.parseInt(this.maxKb);
      if (!Number.isInteger(mx) || mx <= 0) {
        mx = 500;
      }
      if (file.size > mx * 1024) {
        this.$message.warning(`文件大小超过了 ${mx}kb`);
        throw false;
      }
      return true;
    },
    async ossUpload(req) {
      const {
        leftTopRadius,
        leftBottomRadius,
        rightTopRadius,
        rightBottomRadius,
      } = this;
      const self = this;
      // 如果有设置圆角则替换原始的文件为圆角图片
      if (
        leftTopRadius ||
        leftBottomRadius ||
        rightTopRadius ||
        rightTopRadius
      ) {
        let arr = [...this.size.matchAll(/\d+/g)];
        const clipImg = new ClipImg(
          arr[0],
          arr[1],
          0,
          leftTopRadius,
          leftBottomRadius,
          rightTopRadius,
          rightBottomRadius
        );
        // 处理图片成圆角
        const file = await clipImg.formatImg(req.file);
        const { uid } = req.file;
        file.uid = uid;
        req.file = file;
      }
      return getOssInfo({}).then((resp) => {
        const extMap = {
          "audio/mp3": ".mp3",
          "image/jpeg": ".jpg",
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
            bucket: "qianliao-doc-download-402-301",
          });
        } else {
          client = new OSS.Wrapper({
            accessKeyId: resp.data.ossAccessId,
            accessKeySecret: resp.data.ossAccessKey,
            region: "oss-cn-hangzhou",
            bucket: "ql-res",
          });
        }
        // console.log("file=" + JSON.stringify(req.file));
        // console.log(req.file.type);

        const fileKey = randomFileName();
        var fileName = req.file.name;
        var index1 = fileName.lastIndexOf(".");
        var index2 = fileName.length;
        let postfix = fileName.substring(index1, index2);
        console.log(postfix);
        if (typeof postfix == "undefined") {
          postfix = ".jpg";
        }
        return client.multipartUpload(
          "qlLive/parental/" + fileKey + postfix,
          req.file,
          {
            progress: function (p) {
              return function (done) {
                self.$refs["upload"].handleProgress(
                  { percent: p * 100 },
                  req.file
                );
                console.log(p);
                done();
              };
            },
          }
        );
      });
    },
    hanldeFileSuccess(response, file, fileList) {
      const self = this;
      // console.log("response=" + JSON.stringify(response));
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
        audio.addEventListener("loadedmetadata", (e) => {
          console.log("audio.duration=" + audio.duration);
          self.$emit("update:duration", Math.floor(audio.duration));
        });
      } else {
        self.$emit(
          "update:url",
          "https://media.nicebooker.com/" + response.name
        );
      }
    },
    handleFileExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择1个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      );
    },
    handleFileRemove(file, fileList) {
      const self = this;
      self.$emit("update:url", "");
      self.$emit("update:duration", 0);
    },
  },
};
</script>

<style lang="scss" scoped>
.container {
  display: flex;
  .upload {
    width: 400px;
    margin-right: 50px;
    white-space: normal;
    .upload-tip {
      margin-top: 6px;
      font-size: 12px;
      line-height: initial;
      color: #666;
      &.tip-red {
        color: red;
      }
    }
  }
  .input-box {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    .input {
      color: #666;
      display: flex;
      font-weight: normal;
      & ~ .input {
        margin-top: 14px;
      }
      & > div:last-child {
        margin-left: 20px;
      }
    }
    
  }
}
</style>

