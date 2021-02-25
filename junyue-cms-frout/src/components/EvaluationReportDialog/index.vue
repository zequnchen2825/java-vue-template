<template>
  <el-dialog title="报告详情" width="600px"  v-bind="$attrs" v-on="$listeners" @open="handleDialogOpen">
    <el-table :data="tableData" v-loading.body="isLoading" element-loading-text="加载中" border fit size="mini" height="400">
      <el-table-column label='序号' type="index" align="center" width="50px"/>

      <el-table-column label='分数' prop="score" align="center"/>

      <el-table-column label='状态' align="center" prop="abandon">
        <template slot-scope="{row}">
          {{row.status === 'abandon'?'未完成':'已完成'}}
        </template>
      </el-table-column>

      <el-table-column label='详细报告' align="center" >
        <template slot-scope="scope">
          <el-button v-if="isUrlView" type="text" @click="copyEntryUrl(scope.row.id,scope.row.userReportUrl)">复制链接</el-button><br>
          <el-link v-if="isUrlView" :href="scope.row.userPcReportUrl" target="_blank">点击查看</el-link>
          <span v-else>旧版测评无法查看</span>
        </template>
      </el-table-column>
    </el-table>

    <span slot="footer">
      <el-button size="mini" @click="$emit('update:visible',false)">关闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
import {getEvaluationReportDetail} from '@/api/crm/student';

import Clipboard from "clipboard";

export default {
name: "EvaluationReportDialog",
  props: {
    userId: {
      type: [Number, String],
      default: 0
    },
    paperId: {
      type: [Number, String],
      default: 0
    },
    isUrlView: {
      type: Boolean,
      default: true
    },
  },
  data() {
    return {
      isLoading: false,
      tableData: []
    };
  },
  methods:{
    handleDialogOpen() {
      this.getTableData({"userId":this.userId,"paperId":this.paperId});
    },
    async getTableData(params) {
      const {state, data} = await getEvaluationReportDetail(params)

      if (state.code === 200) {
        this.tableData=Object.freeze(data.items)
      }else {
        this.tableData = [];
      }
    },
    copyEntryUrl(data,entryUrl) {
      const clipboard = new Clipboard('.el-button', {
        text: (trigger)=> {
          return entryUrl;
        }
      })
      clipboard.on('success', e => {
        this.$message({
          type: "success",
          message: '复制成功'
        });
        clipboard.destroy();
      })
      clipboard.on('error', e => {
        clipboard.destroy();
      })
    },
  }
}
</script>
