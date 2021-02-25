<template>
  <el-form class="el-form" label-position="left" size="mini" inline>
    <el-form-item  class="el-form-item">
      <el-date-picker type="datetime" placeholder="开始操作时间" v-model="formData.startCreateTime"></el-date-picker>
      -
      <el-date-picker type="datetime" placeholder="结束操作时间" v-model="formData.endCreateTime"></el-date-picker>
    </el-form-item>

    <el-form-item class="el-form-item">
      <el-select class="filter-item" v-model="formData.table" placeholder="选择日志业务表" clearable>
        <el-option
          v-for="item in listableOptions"
          :key="item.table"
          :label="item.name"
          :value="item.table"
        ></el-option>
      </el-select>
    </el-form-item>

    <el-form-item class="el-form-item">
      <el-select class="filter-item" v-model="formData.type" placeholder="选择操作类型" clearable>
        <el-option label="添加" value="INSERT" />
        <el-option label="删除" value="DELETE" />
        <el-option label="更新" value="UPDATE" />
      </el-select>
    </el-form-item>

    <el-button
      class="filter-item"
      style="width: 100px;"
      type="primary"
      icon="el-icon-search"
      @click="onSubmitFilter"
      size="mini"
    >查询</el-button>
  </el-form>
</template>

<script>
  import {queryListable} from "@/api/operationLog";

  export default {
  name: "Header",
  components: {},
  data() {
    return {
      isShow: {
        mapping: false,
      },
      formData: {},
      listableOptions: [],
    };
  },
  created() {
    queryListable().then((response) => {
      this.listableOptions = response.data.listable;
    });
  },
  methods: {
    // 点击查询
    onSubmitFilter() {
      this.$emit("filter-change", this.formData);
    },
  },
};
</script>
