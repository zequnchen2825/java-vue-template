<template>
  <div class="text-item-container">
    <el-row type="flex" justify="space-between" align="middle" class="row-content">
      <el-col :span="1" style="width:20px;text-align:center;">{{rowIndex}}</el-col>
      <el-divider direction="vertical"></el-divider>
      <el-col :span="18" :lg="18" :xl="18">{{textContent}}</el-col>
      <el-col :span="4" :lg="6" :xl="4" style="text-align:right;">
        <el-button
          size="mini"
          type="primary"
          icon="el-icon-edit"
          circle
          @click="editTextItem()"
          v-if="!simple"
        ></el-button>
        <el-divider direction="vertical"></el-divider>
        <el-button
          size="mini"
          type="danger"
          icon="el-icon-delete"
          circle
          @click="delTextItem()"
          v-if="!simple"
        ></el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  props: {
    content: String,
    index: Number,
    simple: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      textContent: this.content,
      rowIndex: this.index,
    };
  },
  components: {},
  watch: {
    index(val) {
      this.rowIndex = val;
    },
  },
  methods: {
    editTextItem() {
      this.$emit("text-handle", "edit");
    },
    delTextItem() {
      this.$emit("text-handle", "del");
    },
  },
};
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.text-item-container {
  .row-content {
    padding: 10px 10px;
    background-color: #ecf5ff;
  }
}
</style>