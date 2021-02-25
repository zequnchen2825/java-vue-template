<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input class="filter-item" style="width: 200px;" type="number" v-model.number="listQuery.id"
                placeholder="ID"></el-input>
      <el-button class="filter-item" style="width: 100px;" type="primary" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>
      <el-button class="filter-item" style="width: 100px;" type="primary" icon="el-icon-edit" @click="handleCreate">新建
      </el-button>
    </div>

    <el-table :data="list" v-loading.body="listLoading" element-loading-text="加载中" border fit highlight-current-row>
      <el-table-column label='序号' type="index" align="center" width="50px">
      </el-table-column>
      <el-table-column label='主键id' align="center">
        <template slot-scope="scope">
          {{scope.row.id}}
        </template>
      </el-table-column>
      <el-table-column label='渠道名称' align="center">
        <template slot-scope="scope">
          {{scope.row.channelName}}
        </template>
      </el-table-column>
      <el-table-column label='渠道tag' align="center">
        <template slot-scope="scope">
          {{scope.row.channelTag}}
        </template>
      </el-table-column>
      <el-table-column label='状态' align="center" width="160px">
        <template slot-scope="scope">
          {{scope.row.status==1?'有效':'无效'}}
        </template>
      </el-table-column>
      <el-table-column label='bucket' align="center">
        <template slot-scope="scope">
          {{scope.row.channelType == 1?'官网':''}}
          {{scope.row.channelType == 2?'微信公众号':''}}
          {{scope.row.channelType == 3?'微信小程序':''}}
        </template>
      </el-table-column>
      <el-table-column label='appId' align="center" width="160px">
        <template slot-scope="scope">
          {{scope.row.appid}}
        </template>
      </el-table-column>
      <el-table-column label='appKey' align="center" width="160px">
        <template slot-scope="scope">
          {{scope.row.appKey}}
        </template>
      </el-table-column>
      <el-table-column label='商户id' align="center" width="160px">
        <template slot-scope="scope">
          {{scope.row.mchId}}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200px">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button type="primary" size="mini" @click="goChannel(scope.row)">业务管理</el-button>
          <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="listQuery.page" :page-sizes="[10,20,30,50]" :page-size="listQuery.limit"
                     layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="50%"
               :before-close="handleDialogClose">
      <el-form ref="dataForm" :model="entity" :rules="rules" label-position="right" label-width="100px">
        <el-form-item label="渠道名称:" prop="channelName">
          <el-input v-model="entity.channelName"></el-input>
        </el-form-item>
        <el-form-item label="渠道tag:" prop="channelTag">
          <el-input v-model="entity.channelTag"></el-input>
        </el-form-item>
        <el-form-item label="渠道类型:" prop="channelType">
          <el-radio-group v-model="entity.channelType">
            <el-radio :label=Number(1)>官网</el-radio>
            <el-radio :label=Number(2)>微信公众号</el-radio>
            <el-radio :label=Number(3)>微信小程序</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="状态:" prop="status">
          <el-radio-group v-model="entity.status">
            <el-radio :label="1">有效</el-radio>
            <el-radio :label="0">无效</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="appSecret:" prop="appSecret">
          <el-input v-model="entity.appSecret"></el-input>
        </el-form-item>
        <el-form-item label="appKey:" prop="appKey">
          <el-input v-model="entity.appKey"></el-input>
        </el-form-item>
        <el-form-item label="重定向url:" prop="redirectUrl">
          <el-input v-model="entity.redirectUrl"></el-input>
        </el-form-item>
        <el-form-item label="商户id:" prop="mchId">
          <el-input v-model="entity.mchId"></el-input>
        </el-form-item>
        <el-form-item label="商户key:" prop="mchKey">
          <el-input v-model="entity.mchKey"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">新建</el-button>
        <el-button v-else type="primary" @click="updateData">修改</el-button>
        <el-button @click="dialogFormVisible=false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {add, getList, update} from '@/api/together/channel'
  import moment from "moment";

  export default {
    data() {
      return {
        list: [],
        listLoading: true,
        total: null,
        listQuery: {
          page: 1,
          limit: 20,
          id: undefined,
          platformId: undefined
        },
        dialogFormVisible: false,
        dialogStatus: '',
        textMap: {
          update: '修改',
          create: '新建'
        },
        entity: {
          id: undefined,

          channelName: undefined,

          channelTag: undefined,

          channelType: undefined,

          status: 1,

          appId: undefined,

          appSecret: undefined,

          appKey: undefined,

          platformId: undefined,

          isDelete: 0,

          redirectUrl: undefined,

          schoolId: undefined,

          mchId: undefined,

          mchKey: undefined,

          terminal: undefined
        },
        loadingOptions: {
          text: 'Loading',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        },
        rules: {}
      }
    },
    filters: {
      statusFilter(status) {
        const statusMap = {
          Y: 'success',
          N: 'danger'
        };
        return statusMap[status]
      },
      dateFormat(date) {
        if (date) {
          return moment(date).format("YYYY-MM-DD HH:mm:ss");
        }
      }
    },
    computed: {},
    created() {
      this.fetchData();
    },
    methods: {
      fetchData() {
        var platformId = this.$route.params.platformId;
        var platformName = this.$route.params.platformName;
        this.listQuery.platformId = platformId;


        this.listLoading = true;
        getList(this.listQuery).then(response => {
          this.list = response.data;
          this.listLoading = false;
          this.total = response.count;
        });
      },
      handleFilter() {
        this.listQuery.page = 1;
        this.fetchData();
      },
      handleSizeChange(val) {
        this.listQuery.limit = val;
        this.fetchData();
      },
      handleCurrentChange(val) {
        this.listQuery.page = val;
        this.fetchData();
      },
      resetEntity() {
        this.entity = {
          id: undefined,

          channelName: undefined,

          channelTag: undefined,

          channelType: undefined,

          status: 1,

          appId: undefined,

          appSecret: undefined,

          appKey: undefined,

          platformId: undefined,

          isDelete: 0,

          redirectUrl: undefined,

          schoolId: undefined,

          mchId: undefined,

          mchKey: undefined,

          terminal: undefined
        }
      },
      handleCreate() {
        this.resetEntity();
        this.dialogStatus = 'create';
        this.dialogFormVisible = true;
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate();
        });
      },
      createData() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            const loading = this.$loading(this.loadingOptions);
            add(this.entity).then(response => {
              //根据状态判断是否成功
              if (response.code === 200) {
                this.fetchData();
                this.$notify({
                  title: '成功',
                  message: '操作成功',
                  type: 'success',
                  duration: 2000
                });
              } else {
                this.$notify({
                  title: '失败',
                  message: '操作失败',
                  type: 'error',
                  duration: 2000
                });
              }
              loading.close();
            }).catch(e => {
              loading.close();
              console.log('操作失败', e);
            });
            this.dialogFormVisible = false;
          }
        });
      },
      handleUpdate(row) {
        this.entity = Object.assign({}, row);
        this.dialogStatus = 'update';
        this.dialogFormVisible = true;
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate();
        });
      },
      updateData() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            const loading = this.$loading(this.loadingOptions);
            update(this.entity).then(response => {
              //根据状态判断是否成功
              if (response.code === 200) {
                this.fetchData();
                this.$notify({
                  title: '成功',
                  message: '操作成功',
                  type: 'success',
                  duration: 2000
                });
              } else {
                this.$notify({
                  title: '失败',
                  message: '操作失败',
                  type: 'error',
                  duration: 2000
                });
              }
              loading.close();
            }).catch(e => {
              loading.close();
              console.log('操作失败', e);
            });
            this.dialogFormVisible = false;
          }
        });
      },
      handleDelete(row) {
        this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const loading = this.$loading(this.loadingOptions);
          row.isDelete = 1;
          update(row).then(response => {
            //根据状态判断是否成功
            if (response.code === 200) {
              var delIndex = '';
              this.list.map((item, index) => {
                if (item.id == row.id) {
                  delIndex = index;
                }
              });
              this.list.splice(delIndex, 1);
              this.$notify({
                title: '成功',
                message: '操作成功',
                type: 'success',
                duration: 2000
              });
            } else {
              this.$notify({
                title: '失败',
                message: '操作失败',
                type: 'error',
                duration: 2000
              });
            }
            loading.close();
          }).catch(e => {
            loading.close();
            console.log('操作失败', e);
          });
        });
      },
      handleDialogClose() {
        this.$confirm('确认关闭？')
          .then(_ => {
            this.dialogFormVisible = false;
          })
          .catch(_ => {
          });
      },
      goChannel(row){
        this.$router.push(`/setting/channel/${row.id}/${row.platformName}`)
      },
    }
  }
</script>
