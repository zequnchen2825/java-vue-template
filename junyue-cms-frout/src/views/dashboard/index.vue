<template>
  <div class="dashboard-container">
    <div class="dashboard-text">用户:{{userName}}</div>
    <div class="dashboard-text">权限:<div v-for='permission in permissions' :key='permission'><i class="el-icon-caret-right"></i>{{permission}}</div></div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'dashboard',
  computed: {
    ...mapGetters([
      'userName',
      'permissions'
    ])
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 20px;
    line-height: 25px;
  }
}
</style>
