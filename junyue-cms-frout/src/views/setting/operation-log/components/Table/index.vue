<!--列表-->
<template>
  <section>
    <table-renderer
      :data-source="data"
      :columns="tableColumns"
      border
      size="mini"
      :dropdown="false"
      element-loading-text="加载中"
      fit
      highlight-current-row
      v-on="$listeners"
      @operation-click="onTableOperationClick"
      :action-bar="tableActionBar"
    />

    <el-dialog :visible.sync="isShow.detail" title="日志详情" width="960px">
      <table-renderer
        :data-source="currentRowData.contentList"
        :columns="contentTableColumns"
        border
        size="mini"
      />
    </el-dialog>
  </section>
</template>

<script type="text/jsx">
  // components
  import TableRenderer from "@/components/TableRenderer";
  // mixins
  import NavTabMixin from "@/mixin/nav-tab";
  // utils
export default {
  name: "Table",
  mixins: [NavTabMixin],
  components: { TableRenderer },
  props: {
    data: {
      type: Array,
      require: true,
    },
  },
  data() {
    return {
      currentRowData: {
        contentList: [],
      },
      isShow: {
        detail: false,
      },
      // 表格columns配置
      tableColumns: [
        { label: "表名", prop: "tableName" },
        { label: "业务名称", prop: "businessName" },
        { label: "操作人", prop: "creator" },
        { label: "操作类型", prop: "type" },
        { label: "操作时间", prop: "createTime" },
        { label: "业务主键值", prop: "primaryId" },
      ],
      contentTableColumns: [
        { label: "字段", prop: "name" },
        { label: "名称", prop: "note" },
        { label: "旧值", prop: "oldVal" },
        { label: "新值", prop: "newVal" },
      ],
      // 生成右侧操作栏配置
      tableActionBar: [
        {
          label: "详情",
          key: "detail",
        },
      ],
    };
  },
  methods: {
    /**
     * 监听表格右侧操作栏
     * */
    async onTableOperationClick(key, { row }) {
      this.currentRowData = row;
      if (key === "detail") {
        this.isShow.detail = true;
        return;
      }
    },
  },
};
</script>


