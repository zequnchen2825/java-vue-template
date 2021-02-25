<template>
  <div class="file-item-container">
    <el-row type="flex" justify="space-between" align="middle" class="row-content">
      <el-col :span="1" style="width:20px;text-align:center;">
        {{
        rowIndex
        }}
      </el-col>
      <el-divider direction="vertical"></el-divider>
      <el-col :span="18" :xl="18" :lg="18">
        <el-row type="flex" :gutter="10">
          <el-col :span="2" :lg="4" :xl="2" style="text-align:center;">
            <img :src="file.path" style="width:50px;height:50px;" v-if="checkImgExt(file.ext)" />
            <i class="el-icon-document" style="font-size:30px;" v-else></i>
          </el-col>
          <el-col :span="21" :lg="16" :xl="18" :offset="1">
            <el-row>
              <el-col :span="24">{{ file.name }}</el-col>
            </el-row>
            <el-row style="margin-top:10px;">
              <el-col :span="24">{{ bytesToSize(file.size) }}</el-col>
            </el-row>
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="4" :xl="4" :lg="4" style="text-align:right;">
        <el-button
          size="mini"
          type="danger"
          icon="el-icon-delete"
          circle
          @click="delFileItem()"
          v-if="!simple"
        ></el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  props: {
    simple: { type: Boolean, default: false },
    props: {
      type: Object,
      default: null,
    },
    index: Number,
  },
  data() {
    return {
      file: this.props,
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
    bytesToSize(bytes) {
      bytes = parseInt(bytes);
      if (bytes === 200) return "0 B";
      var k = 1024, // or 1024
        sizes = ["B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"],
        i = Math.floor(Math.log(bytes) / Math.log(k));

      return (bytes / Math.pow(k, i)).toPrecision(3) + " " + sizes[i];
    },
    checkImgExt(ext) {
      if (ext === "png" || ext === "jpg") {
        return true;
      }
      return false;
    },
    editFileItem() {
      this.$emit("file-handle", "edit");
    },
    delFileItem() {
      this.$emit("file-handle", "del");
    },
  },
};
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.file-item-container {
  .row-content {
    padding: 10px 10px;
  }
}
</style>