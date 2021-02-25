<template>
  <span>
    <el-select
      v-model="listQuery.userId"
      filterable
      clearable
      remote
      reserve-keyword
      placeholder="输入学员ID/微信昵称"
      loading-text="加载中..."
      :remote-method="remoteLoadUser"
      :loading="remoteLoadUserLoading"
    >
      <el-option
        v-for="item in userOptions"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      ></el-option>
    </el-select>
    <el-input
      v-if="includeMobile"
      class="filter-item"
      style="width: 200px; margin-bottom: 10px"
      v-model="listQuery.userMobile"
      placeholder="输入学员手机号"
    ></el-input>
  </span>
</template>

<script>
import { findUserByKeyword } from "@/api/user";
export default {
  props: {
    listQuery: Object,
    includeMobile: false,
  },
  data() {
    return {
      userOptions: [],
      remoteLoadUserLoading: false,
      remoteLoadUserKeyword: "",
    };
  },
  created() {},
  methods: {
    remoteLoadUser(keyword) {
      if (!keyword || keyword.trim() === "") {
        return;
      }

      this.remoteLoadUserKeyword = keyword;

      if (this.remoteLoadUserLoading) {
        // 防抖处理
        return;
      }

      this.remoteLoadUserLoading = true;
      this.userOptions = [];

      setTimeout(() => {
        this.remoteLoadUserLoading = false;
        findUserByKeyword(this.remoteLoadUserKeyword).then((response) => {
          this.userOptions = response.data.list || [];
        });
      }, 1000);
    },
  },
};
</script>
