<template>
  <el-button class="filter-item" style="width: 100px;" type="primary" @click="exportExcel">导出</el-button>
</template>

<script>
import request from "@/utils/request";

export default {
  props: {
    listQuery: Object,
    baseUrl: String,
  },
  data() {
    return {};
  },
  created() {},
  methods: {
    exportExcel() {
      if (!this.baseUrl) {
        return;
      }
      
      let self = this;
      new request({
        url: this.baseUrl + "/export",
        method: "post",
        data: this.listQuery,
      }).then((response) => {
        if (response.code === 200) {
          self.$notify({
            title: "导出提示",
            message: "请到【用户管理->导出列表】下载导出结果",
            type: "success",
            duration: 5000,
          });
        }
      });
    },
  },
};
</script>
