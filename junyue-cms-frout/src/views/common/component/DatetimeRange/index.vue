<template>
  <span>
    <el-date-picker
      type="datetime"
      class="filter-item"
      default-time="00:00:00"
      v-model="timeRange.start"
      value-format="yyyy-MM-dd HH:mm:ss"
      :placeholder="placeholder + '开始时间'"
      style="margin-bottom: 5px"
      clearable
    ></el-date-picker>
    <el-date-picker
      type="datetime"
      class="filter-item"
      default-time="23:59:59"
      v-model="timeRange.end"
      value-format="yyyy-MM-dd HH:mm:ss"
      :placeholder="placeholder + '结束时间'"
      style="margin-bottom: 5px"
      clearable
    ></el-date-picker>
  </span>
</template>

<script>
import { findUserByKeyword } from "@/api/user";
export default {
  props: {
    timeRange: Object,
    placeholder: String,
  },
  data() {
    return {};
  },
  created() {},
  methods: {},
};
</script>
