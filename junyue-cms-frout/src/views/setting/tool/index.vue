<template>
  <div class="app-container">
      <div class="filter-container">
        <el-input
            type="textarea"
            :rows="10"
            placeholder="请输入内容"
            v-model="modeData">
        </el-input>
      </div>
      <div class="filter-container">
          <el-button type="primary" @click="changeData">转化</el-button>
      </div>
      <div class="filter-container">
        <el-input
            type="textarea"
            :rows="10"
            placeholder="请输入内容"
            v-model="tempData">
        </el-input>
      </div>
  </div>
</template>

<script>
import { whiteList, addWhite, cancelWhite } from "@/api/user";
import { responseNotify, formatTimeFilter, clearObj } from "@/utils";
export default {
  data() {
    return {
      modeData: '',
      tempData: ''
    };
  },
  filters: { formatTimeFilter },
  created() {
  },
  methods: {
      changeData() {
        if (this.modeData) {
          // 判断第一个是不是 标点符号
          this.tempData = this.modeData.match(/[\u4e00-\u9fa5]([\u3002|\uff1f|\uff01|\uff0c|\u3001|\uff1b|\uff1a|\u201c|\u201d|\u2018|\u2019|\uff08|\uff09|\u300a|\u300b|\u3008|\u3009|\u3010|\u3011|\u300e|\u300f|\u300c|\u300d|\ufe43|\ufe44|\u3014|\u3015|\u2026|\u2014|\uff5e|\ufe4f|\uffe5]+)?/ig)
            .map(item => {
              return (`${item}\n`)
            }).join('')

          let reg = /[\u3002|\uff1f|\uff01|\uff0c|\u3001|\uff1b|\uff1a|\u201c|\u201d|\u2018|\u2019|\uff08|\uff09|\u300a|\u300b|\u3008|\u3009|\u3010|\u3011|\u300e|\u300f|\u300c|\u300d|\ufe43|\ufe44|\u3014|\u3015|\u2026|\u2014|\uff5e|\ufe4f|\uffe5]/;
          if (reg.test(this.modeData[0])) {
            this.tempData = `${this.modeData[0]}${this.tempData}`
          }
        }
      }
  }
};
</script>
