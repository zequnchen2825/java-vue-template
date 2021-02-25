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
      <el-table-column label='平台名称' align="center">
        <template slot-scope="scope">
          {{scope.row.platformName}}
        </template>
      </el-table-column>
      <el-table-column label='平台tag' align="center">
        <template slot-scope="scope">
          {{scope.row.platformTag}}
        </template>
      </el-table-column>
      <el-table-column label='状态' align="center" width="160px">
        <template slot-scope="scope">
          {{scope.row.status==1?'有效':'无效'}}
        </template>
      </el-table-column>
      <el-table-column label='bucket' align="center">
        <template slot-scope="scope">
          {{scope.row.bucket}}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200px">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button type="primary" size="mini" @click="goChannel(scope.row)">渠道管理</el-button>
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
        <el-form-item label="平台名称:" prop="platformName">
          <el-input v-model="entity.platformName"></el-input>
        </el-form-item>
        <el-form-item label="平台tag:" prop="platformTag">
          <el-input v-model="entity.platformTag"></el-input>
        </el-form-item>
        <el-form-item label="状态:" prop="status">
          <el-radio-group v-model="entity.status">
            <el-radio :label="1">有效</el-radio>
            <el-radio :label="0">无效</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机:" prop="mobile">
          <el-input v-model="entity.mobile"></el-input>
        </el-form-item>
        <el-form-item label="公司地址:" prop="address">
          <el-input v-model="entity.address"></el-input>
        </el-form-item>
        <el-form-item label="qq:" prop="qq">
          <el-input v-model="entity.qq"></el-input>
        </el-form-item>
        <el-form-item label="维度:" prop="latitude">
          <el-input v-model="entity.latitude"></el-input>
        </el-form-item>
        <el-form-item label="经度:" prop="longitude">
          <el-input v-model="entity.longitude"></el-input>
        </el-form-item>
        <el-form-item label="公众号名称:" prop="subscribeName">
          <el-input v-model="entity.subscribeName"></el-input>
        </el-form-item>
        <el-form-item label="email:" prop="email">
          <el-input v-model="entity.email"></el-input>
        </el-form-item>
        <el-form-item label="bucket:" prop="bucket">
          <el-input v-model="entity.bucket"></el-input>
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
  import {add, getList, update} from '@/api/together/platform'
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
          id: undefined
        },
        dialogFormVisible: false,
        dialogStatus: '',
        textMap: {
          update: '修改',
          create: '新建'
        },
        entity: {
          id: undefined,

          platformName: undefined,

          platformTag: undefined,

          status: 1,

          mobile: undefined,

          address: undefined,

          qq: undefined,

          isDelete: 0,

          latitude: undefined,

          longitude: undefined,

          subscribeName: undefined,

          email: undefined,

          bucket: undefined
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

          platformName: undefined,

          platformTag: undefined,

          status: 1,

          mobile: undefined,

          address: undefined,

          qq: undefined,

          isDelete: 0,

          latitude: undefined,

          longitude: undefined,

          subscribeName: undefined,

          email: undefined,

          bucket: undefined
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
