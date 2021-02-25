<template>
    <div class="app-container">
        <div class="filter-container">
            <el-button class="filter-item" style="width: 100px;" type="primary" icon="el-icon-search"
                       @click="handleFilter">刷新
            </el-button>
        </div>

        <el-table :data="list"
                  v-loading.body="listLoading"
                  element-loading-text="加载中"
                  border fit highlight-current-row
        >
            <el-table-column label='编号' align="center">
                <template slot-scope="scope">
                    {{(listQuery.page - 1) * (listQuery.size) + scope.$index + 1}}
                </template>
            </el-table-column>
            <el-table-column label='任务ID' align="center">
                <template slot-scope="scope">
                    {{scope.row.id}}
                </template>
            </el-table-column>

            <el-table-column label='文件名' align="center">
                <template slot-scope="scope">
                    {{scope.row.fileName}}
                </template>
            </el-table-column>

            <el-table-column label='创建时间' align="center">
                <template slot-scope="scope">
                    {{scope.row.createTime | normalFormaTime}}
                </template>
            </el-table-column>

            <el-table-column label='状态' align="center">
                <template slot-scope="scope">
                    {{scope.row.status | exportTaskStatus}}
                </template>
            </el-table-column>

            <el-table-column label='备注' align="center">
                <template slot-scope="scope">
                    {{scope.row.remark === '' || scope.row.remark === null ? '-' : scope.row.remark}}
                </template>
            </el-table-column>

            <el-table-column label='操作' align="center">
                <template slot-scope="scope">
                    <el-link :href="scope.row.downloadUrl" target="_blank">下载</el-link>
                </template>
            </el-table-column>
        </el-table>

        <div class="pagination-container">
            <el-pagination color @size-change="handleSizeChange" @current-change="handleCurrentChange"
                           :current-page.sync="listQuery.page" :page-sizes="[10, 20, 30,  50]"
                           :page-size="listQuery.size" layout="total,  sizes,  prev,  pager,  next,  jumper"
                           :total="total">
            </el-pagination>
        </div>

    </div>
</template>

<script>
    import {
        queryForList
    } from "@/api/export/dataExport";
    import {getOssInfo} from "@/api/sys";
    import {
        responseNotify,
        statusFilter,
        statusMap,
        userStateMap,
        subjectMap,
        formatTimeFilter,
        dpOpts,
        clearObj,
        normalFormaTime
    } from "@/utils";
    import {noblankValidator, numberValidator} from "@/utils/formValidator";

    function exportTaskStatus(status) {
        if (status === 'wait') {
            return "等待中";
        }
        if (status === 'ing') {
            return "正在导出";
        }
        if (status === 'success') {
            return "导出成功";
        }
        if (status === 'fail') {
            return "导出失败";
        }
        return "未知";
    }

    export default {
        data() {
            return {
                list: [],
                dayList: [],
                baseInfo: {},
                listLoading: true,
                total: 0,
                listQuery: {
                    page: 1,
                    size: 20
                },
                temp: {}
            };
        },
        filters: {
            statusFilter,
            statusMap,
            formatTimeFilter,
            userStateMap,
            subjectMap,
            normalFormaTime,
            exportTaskStatus
        },
        created() {
            this.fetchData();
            this.initList();
        },
        methods: {
            fetchData() {
                const self = this;
                self.listLoading = true;
                queryForList(self.listQuery).then(response => {
                    self.list = response.data.list || [];
                    self.total = response.data.total || 0;
                    self.listLoading = false;
                }).catch(e => {
                    self.listLoading = false;
                });
            },
            initList() {

            },
            handleFilter() {
                this.listQuery.page = 1;
                this.fetchData();
            },
            handleSizeChange(val) {
                this.listQuery.size = val;
                this.fetchData();
            },
            handleCurrentChange(val) {
                this.listQuery.page = val;
                this.fetchData();
            },
            resetTemp() {
                const self = this;
                clearObj(self.temp);
                self.fileList = [];
            },
        }
    };
</script>
