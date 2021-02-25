<!--
 * @Author: NikoZhang
 * @Date: 2019-09-03 11:43:46
 * @LastEditTime: 2020-07-20 14:19:25
 * @LastEditors: NikoZhang
 * @Description: 下拉框生成器，需要TableRenderer使用
 -->
<template>
  <el-dropdown v-on="$listeners" v-bind="$attrs">
    <el-button type="text" size="mini" class="el-dropdown-link">
      {{ label }}
      <i class="el-icon-arrow-down el-icon--right"></i>
    </el-button>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item
        divided
        v-for="(item, index) in dropdownList"
        :key="index"
        v-permission="item.permission"
        :command="item.key"
        v-bind="item.props"
      >
        {{ item.label }}
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script type="text/jsx">
export default {
  name: 'DropdownRenderer',
  props: {
    label: {
      type: String,
      require: false,
      default: '更多'
    },
    dropdownList: {
      type: Array,
      require: true
    }
  },
    computed: {}
};
</script>
