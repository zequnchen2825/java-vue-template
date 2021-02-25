<!--
操作日志
-->
<template>
  <section class="content">
    <!--AppHeader-->
    <app-header @filter-change="handleChangeFilterParams"></app-header>
    <!--table表格展示-->
    <app-table v-loading="isLoading" :data="tableData" />
    <!--pagination分页-->
    <el-pagination
      class="pagination-container"
      color
      :current-page="requestParams.page"
      :page-sizes="[10, 20, 30,  50]"
      :page-size="requestParams.size"
      :total="requestParams.total"
      @size-change="handleSizeChange"
      layout="total,  sizes,  prev,  pager,  next,  jumper"
      @current-change="handleCurrentChange"
    />
  </section>
</template>

<script>
  import AppHeader from "./components/Header";
  import AppTable from "./components/Table";
  import {queryList} from "@/api/operationLog";

  export default {
  name: "OperationLog",
  components: {
    AppHeader,
    AppTable,
  },
  data() {
    return {
      requestParams: {
        page: 1,
        size: 20,
      },
      tableData: [], // 表格数据
      isLoading: false,
    };
  },
  created() {
    this.getTableData(this.requestParams);
  },
  methods: {
    getTableData(params) {
      this.isLoading = true;
      queryList(params)
        .then(({ state = {}, data = {} }) => {
          const { code } = state;
          const { list = [], total = 0 } = data;
          if (code === 200) {
            this.tableData = Object.freeze(list);
            this.requestParams.total = total;
          }
        })
        .finally(() => {
          this.isLoading = false;
        });
    },
    /**
     * 处理Filter参数修改
     * @event
     * @param params {object} 响应头部筛选修改后的参数 */
    handleChangeFilterParams(params) {
      this.getTableData(
        (this.requestParams = {
          ...this.requestParams,
          ...params,
          page: 1,
        })
      );
    },
    // 监听分页size切换
    handleSizeChange(value) {
      this.getTableData(
        (this.requestParams = {
          ...this.requestParams,
          size: value,
          page: 1,
        })
      );
    },
    // 监听分页page切换
    handleCurrentChange(value) {
      this.getTableData(
        (this.requestParams = {
          ...this.requestParams,
          page: value,
        })
      );
    },
  },
};
</script>
<style lang="scss" scoped>
.content {
  padding: 10px;
}
</style>
