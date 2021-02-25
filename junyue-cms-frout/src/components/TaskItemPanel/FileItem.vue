<template>
  <el-row type="flex" align="middle" class="row-content">
    <el-col :span="1" style="width:20px;text-align:center;">
      {{ index }}
    </el-col>
    <el-divider direction="vertical"></el-divider>
    <el-col :span="18" :xl="18" :lg="18">
      <el-row type="flex" :gutter="10">
        <el-col :span="4"  style="text-align:center;">
          <img :src="content" style="width:50px;height:50px;" v-if="checkImgExt(ext)" />
          <i class="el-icon-document" style="font-size:30px;" v-else></i>
        </el-col>
        <el-col :span="22" :lg="16" :xl="18">
          <el-row>
            <el-col :span="24">{{ fileName }}</el-col>
          </el-row>
          <el-row style="margin-top:10px;">
            <el-col :span="24">{{ bytesToSize(size) }}</el-col>
          </el-row>
        </el-col>
      </el-row>
    </el-col>
    <el-col v-if="!simple" :span="4" :xl="4" :lg="4" style="text-align:right;">
      <el-button
        size="mini"
        type="danger"
        icon="el-icon-delete"
        circle
        @click="delFileItem()"
      ></el-button>
    </el-col>
  </el-row>
</template>

<script>
export default {
  props: {
    index: Number,
    simple: { type: Boolean, default: false },
    content: {
      type: String,
      require: true
    },
    fileName: {
      type: String,
      require: true
    },
    ext: {
      type: [Number, String],
      require: true
    },
    size: {
      type: [Number, String],
      require: true
    },
  },
  data() {
    return {};
  },
  methods: {
    bytesToSize(bytes) {
      bytes = parseInt(bytes);
      if (bytes === 200) return "0 B";
      const k = 1024, // or 1024
        sizes = ["B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"],
        i = Math.floor(Math.log(bytes) / Math.log(k));

      return (bytes / Math.pow(k, i)).toPrecision(3) + " " + sizes[i];
    },
    checkImgExt(ext) {
      console.log(ext);
      return ext === "png" || ext === "jpg";

    },
    editFileItem() {
      this.$emit("file-handle", "edit");
    },
    delFileItem() {
      this.$emit("file-handle", "del");
    }
  }
};
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
  .row-content {
    padding: 10px 10px;
  }
</style>
