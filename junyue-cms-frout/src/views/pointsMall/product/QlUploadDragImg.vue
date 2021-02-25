<style scoped lang="scss">
.dragBox.item {
  position: relative;

  .hover-tip {
    display: none;
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    height: auto;
    color: #fff;
    text-align: center;
    line-height: 1.5;
    padding: 5px 0;
    pointer-events: none;
  }

  &:hover {
    .hover-tip {
      display: block;
    }
  }
}
</style>
<template>
  <div class="drag-img">
    <qlUploadMaxImg
      @update="handleGetContentImg"
      :handleBeforeLoadImg="handleBeforeLoadImg"
      :tip="tip"
    ></qlUploadMaxImg>
    <div class="draggable-image-list">
      <draggable element="div" v-model="imageLists" :options="dragOptions" @change="handleChange">
        <div class="dragBox item" v-for="(item, index) in imageLists" :key="index">
          <img :src="item" />
          <i class="el-icon-error" @click="handleRemoveImage(index)"></i>
        </div>
      </draggable>
    </div>
  </div>
</template>
<script>
import qlUploadMaxImg from "./QlUploadMaxImg.vue";
import draggable from "vuedraggable";
import { max } from "moment";

export default {
  name: "custom-max-pic-upload-dragger",
  props: {
    imageList: {
      default: () => [],
      type: Array
    },
    tip: {
      type: String,
      default: ""
    },
    maxNumber: {
      type: Number,
      dafault: 0
    }
  },
  components: {
    qlUploadMaxImg,
    draggable
  },
  data() {
    return {
      dragOptions: {
        draggable: ".dragBox",
        filter: "input,textarea",
        preventOnFilter: false,
        scroll: true
      },
      imageLists: [],
      canMessage: true // 错误提示防抖，防止多次提示
    };
  },
  watch: {
    imageList: {
      handler(val, oldVal) {
        this.imageLists = val;
      },
      immediate: true
    }
  },
  methods: {
    handleGetContentImg(data) {
      this.imageLists.push(data);
      // 多个图片一次上传时 超过最大个数，只保留最大值的个数图片
      if (this.maxNumber && this.imageLists.length > this.maxNumber) {
        this.imageLists = this.imageLists.slice(0, this.maxNumber);
        this.showErrorMessage();
      }
      this.$emit("update:imageList", this.imageLists);
    },
    handleRemoveImage(index) {
      this.imageLists.splice(index, 1);
    },
    handleChange() {
      this.$emit("update:imageList", this.imageLists);
    },
    handleBeforeLoadImg() {
      if (this.maxNumber && this.imageLists.length >= this.maxNumber) {
        this.showErrorMessage();
        return false;
      }
      return true;
    },
    showErrorMessage() {
      if (this.canMessage) {
        this.$message.error("图片上传数目已达上限");
        this.canMessage = false;
        setTimeout(() => {
          this.canMessage = true;
        }, 3000);
      }
    }
  }
};
</script>