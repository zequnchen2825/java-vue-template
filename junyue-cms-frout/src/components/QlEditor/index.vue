<template>
  <div>
    <!-- 图片上传组件辅助-->
    <el-upload
      ref="upload"
      class="image-uploader-editor"
      action="http://oss-cn-hangzhou.aliyuncs.com"
      accept="image/*"
      :http-request="ossUpload"
      :on-success="uploadSuccess"
      :on-error="uploadError"
      :before-upload="beforeUpload"
      :show-file-list="false"
    >
    </el-upload>
    <!--富文本编辑器组件-->
    <el-row v-loading="quillUpdateImg">
      <quill-editor
        v-model="detailContent"
        ref="myQuillEditor"
        :options="editorOption"
        @change="onEditorChange($event)"
      >
      </quill-editor>
<!--      {{formatSave}}-->
    </el-row>
  </div>
</template>
<script>
  import 'quill/dist/quill.core.css'
  import 'quill/dist/quill.snow.css'
  import { quillEditor } from 'vue-quill-editor'
  import { getOssInfo } from "@/api/sys";
  import { randomFileName } from "@/utils";
  import OSS from "ali-oss";
  import * as Quill from 'quill'  //引入编辑器

  var fonts = ['', 'Arial'];
  var Font = Quill.import('formats/font');
  Font.whitelist = fonts; //将字体加入到白名单
  Quill.register(Font, true);

  export default {
    name: "ql-quill-editor",
    props: {
      content: {
        type: String,
        default: ''
      },
      maxKb: {
        type: Number,
        default: 500
      },
      size: {
        default: 'large',
        type: String
      },
      // 工具栏配置
      toolbarOptions: {
        default() {
        	return [
				['bold', 'italic', 'underline', 'strike'],  // toggled buttons
				['blockquote'], //'code-block'

				[{'header': 1}, {'header': 2}],  // custom button values
				[{'list': 'ordered'}, {'list': 'bullet'}],
				[{'script': 'sub'}, {'script': 'super'}],  // superscript/subscript
				[{'indent': '-1'}, {'indent': '+1'}],  // outdent/indent
				// [{'direction': 'rtl'}],  // text direction

				[{'size': ['small', false, 'large', 'huge']}],  // custom dropdown
				// [{'header': [1, 2, 3, 4, 5, 6, false]}],

				[{'color': []}, {'background': []}],  // dropdown with defaults from theme
				// [{'font': fonts}],
				[{'align': []}],
				['link', 'image', 'video'],
				['clean'],  // remove formatting button
                ['undo', 'redo', 'format'],
            ]
        },
        type: Array
      }
    },
    data() {
      return {
        quillUpdateImg: false,  // 根据图片上传状态来确定是否显示loading动画，刚开始是false,不显示
        formatSave: null,
        serverUrl: '',  // 这里写你要上传的图片服务器地址
        header: {token: ''},  // 有的图片服务器要求请求头需要有token之类的参数，写在这里
        detailContent: '',  // 富文本内容
        editorOption: {  // 富文本编辑器配置
          placeholder: '',
          theme: 'snow',
          modules: {
            toolbar: {
              container: this.toolbarOptions,  // 工具栏
              handlers: {
                'image': function (value) {
                  if (value) {
                    // 触发input框选择图片文件
                    document.querySelector('.image-uploader-editor input').click();
                  } else {
                    this.quill.format('image', false);
                  }
                },
                redo() {
                  console.log(this);

                  this.quill.history.redo();
                },
                undo() {
                  console.log(this);

                  this.quill.history.undo();
                },
                format() {
                  console.log(this.formatSave);
                  if(!this.formatSave)  {
                    this.formatSave = this.quill.getFormat()
                  } else {
                    let selection = this.quill.getSelection()
                    console.log(selection);
                    this.quill.formatText(selection.index, selection.length, this.formatSave)
                    this.formatSave = null
                  }
                }
              },
            }
          }
        }
      }
    },
    watch: {
      // 父组件值变化同步到子组件内
      content: {
        handler: function (newValue, oldValue) {
          this.detailContent = newValue;
        },
        deep: true,
        immediate: true
      }
    },
    components: {
      quillEditor
    },
    mounted() {
      // 编辑器高度
      let refQuill = this.$refs.myQuillEditor.quill
      refQuill.container.style.height = this.size == 'small' ? '150px' : '400px';

      // var Font = refQuill.import('formats/font');
      // Font.whitelist = ['Arial']; //将字体加入到白名单
      // refQuill.register(Font, true);
    },
    methods: {
      // 富文本图片上传前
      beforeUpload(file) {
        let mx = parseInt(this.maxKb);
        if (!Number.isInteger(mx) || mx <= 0) {
          mx = 500;
        }
        if (file.size > mx * 1024) {
          this.$message.warning(`文件大小超过了${mx}kb`);
         return false;
        }
        this.quillUpdateImg = true;
        return true;
      },
      ossUpload(req) {
        const self = this;

        return getOssInfo({}).then(res => {
          let client = new OSS.Wrapper({
            // accessKeyId: res.data.credentials.accessKeyId,
            // accessKeySecret: res.data.credentials.accessKeySecret,
            // region: res.data.region,
            // stsToken: res.data.credentials.securityToken,
            // bucket: res.data.bucket
            accessKeyId: res.data.ossAccessId,
            accessKeySecret: res.data.ossAccessKey,
            region: res.data.region,
            bucket: res.data.bucket
          });
          const fileKey = randomFileName();
          var fileName = req.file.name;
          var index1 = fileName.lastIndexOf(".");
          var index2 = fileName.length;
          let postfix = fileName.substring(index1,index2);
          if (typeof postfix == "undefined") {
            postfix = ".jpg";
          }
          return client.multipartUpload(
            "qlLive/english/" + fileKey + postfix,
            req.file
          );
        });
      },
      // 富文本图片上传成功，res为图片服务器返回的数据
      uploadSuccess(res) {
        // 获取富文本组件实例
        let quill = this.$refs.myQuillEditor.quill;
        let imageUrl = "https://media.nicebooker.com/" + res.name;
        // 获取光标所在位置
        let length = quill.getSelection().index;
        // 插入图片
        quill.insertEmbed(length, 'image', imageUrl);
        // 调整光标到最后
        quill.setSelection(length + 1);
        // loading动画消失
        this.quillUpdateImg = false;
      },
      // 富文本图片上传失败
      uploadError() {
        // loading动画消失
        this.quillUpdateImg = false;
        this.$message.error('图片插入失败');
      },
      onEditorChange(event) {
        this.$emit("update:content", this.detailContent);
      }
    }
  }
</script>
<style>
.image-uploader-editor {
  height: 1px;
}
</style>
