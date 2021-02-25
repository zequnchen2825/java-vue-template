<template>
  <el-dialog
    width="500px"
    :show-close="false"
    :close-on-click-modal="false"
    v-bind="$attrs"
    v-on="$listeners"
    @open="handleDialogOpen"
    @close="handleDialogClose"
  >
    <div slot="title">
      {{type==='NEW'?'添加文本':'编辑文本'}}
      <el-popover
        placement="right"
        width="400"
        trigger="click"
        @show="handleEmojiPickerShow"
      >
        <emoji-picker
          class="emoji-picker"
          title="选择emoji表情"
          @select="handleSelectEmoji"
        />
<!--        <i slot="reference" class="el-icon-user"/>-->
        <svg slot="reference" style="width: 16px;height: 16px;cursor:pointer;" t="1596528066813" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1263" width="200" height="200"><path d="M346.6 432c30.6 0 55.2-25.2 55.2-56.2 0-31.2-24.8-56.4-55.2-56.4-30.6 0-55.2 25.2-55.2 56.4-0.2 31 24.6 56.2 55.2 56.2z m331.4 0c30.6 0 55.2-25.2 55.2-56.2 0-31.2-24.8-56.4-55.2-56.4s-55.2 25.2-55.2 56.4c0 31 24.8 56.2 55.2 56.2zM512.2 4C231.6 4 4 231.4 4 512s227.6 508 508.2 508 508.2-227.4 508.2-508C1020.6 231.4 793 4 512.2 4z m1.6 997.6c-268.8 0-486.8-218-486.8-486.8S245 28 513.8 28s486.8 218 486.8 486.8-217.8 486.8-486.8 486.8z m241.6-479.4c-12.2 0-22 10-22 22.6 0 124.4-99 225.4-221 225.4s-221-101-221-225.4c0-12.4-10-22.6-22-22.6-12.2 0-22 10-22 22.6 0 149.2 118.8 270.4 265.2 270.4S777.4 694 777.4 544.8c0-12.6-9.8-22.6-22-22.6z" fill="" p-id="1264"></path></svg>
      </el-popover>
    </div>
    <el-input
      ref="INPUT"
      type="textarea"
      placeholder="输入内容"
      v-model="content"
      maxlength="500"
      :autosize="{ minRows: 6, maxRows: 10}"
      show-word-limit
      @blur="handleInputBlur"
    />
    <div slot="footer" class="dialog-footer">
      <el-button size="mini" @click="$emit('update:visible',false)">取 消</el-button>
      <el-button size="mini" type="primary" @click="handleSubmit">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import { Picker} from 'emoji-mart-vue';

  export default {
    components: {
      EmojiPicker: Picker
    },
    props: {
      type: {
        require: true,
        default: 'EDIT'
      },
      textContent: {
        require: false
      }
    },
    data() {
      return {
        isShow: false,
        inputCurrentPositionIndex: 0,
        isInitForEmojiPicker: true,
        content: ""
      };
    },
    methods: {
      getInputCurrentPosition(element) {
        return element.selectionStart;
      },
      handleInputBlur(event) {
        const inputElement = event.target;
        this.inputCurrentPositionIndex = this.getInputCurrentPosition(inputElement);
      },
      handleEmojiPickerShow() {
        this.isInitForEmojiPicker = true;
      },
      handleSelectEmoji(emoji) {
        /*
        https://github.com/jm-david/emoji-mart-vue#i18n
        this.content += emoji.native;
        this.$refs.INPUT.$refs.textarea.
        */

        console.log(emoji);
        const inputCurrentPositionIndex = this.inputCurrentPositionIndex
        const value = this.content.slice(0, inputCurrentPositionIndex) +
          emoji.native +
          this.content.slice(inputCurrentPositionIndex, this.content.length);

        this.content = value;
      },
      handleDialogClose() {
        this.content = "";
      },
      handleDialogOpen() {
        if (this.type === 'EDIT') {
          this.content = this.textContent;
        }
      },
      handleSubmit() {
        if (this.content.length === 200) {
          return this.$message.error('请输入内容');
        }
        this.$emit('get-content', {
          type: this.type,
          content: this.content
        });
        this.$emit('update:visible', false)
        this.content = "";
      }
    }
  };
</script>
<style lang="scss" scoped>
  .emoji-picker{
    /*position: absolute;*/
  }
</style>
