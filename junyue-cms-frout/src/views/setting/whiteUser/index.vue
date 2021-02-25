<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        class="filter-item"
        style="width: 200px;"
        v-model="listQuery.userId"
        placeholder="userId"
      ></el-input>
      <el-input
        class="filter-item"
        style="width: 200px;"
        v-model="listQuery.userName"
        placeholder="用户昵称"
      ></el-input>
      <el-button
        class="filter-item"
        style="width: 100px;"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >查询</el-button>
      <el-button
        class="filter-item"
        style="width: 100px;"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >新增</el-button>
      <el-button
        class="filter-item"
        style="width: 250px;"
        type="primary"
        icon="el-icon-edit"
        @click="handleOutsideReset"
      >清除站外订单/销售</el-button>
    </div>
    <el-table
      :data="list"
      v-loading.body="listLoading"
      element-loading-text="加载中"
      border=""
      fit
      highlight-current-row
    >
      <el-table-column label="用户昵称" align="center">
        <template slot-scope="scope">{{scope.row.userName}}</template>
      </el-table-column>
      <el-table-column label="用户userId" align="center">
        <template slot-scope="scope">{{scope.row.userId}}</template>
      </el-table-column>
      <el-table-column label="真实姓名" align="center">
        <template slot-scope="scope">{{scope.row.realName}}</template>
      </el-table-column>
      <el-table-column label="创建人" align="center">
        <template slot-scope="scope">{{scope.row.createBy}}</template>
      </el-table-column>
      <el-table-column label="状态" align="center">
        <template slot-scope="scope">{{scope.row.status == 'Y'? "有效":"无效"}}</template>
      </el-table-column>
      <el-table-column label="创建时间" align="center">
        <template slot-scope="scope">{{scope.row.createTime | formatTimeFilter}}</template>
      </el-table-column>
      <el-table-column label="修改人" align="center">
        <template slot-scope="scope">{{scope.row.updateBy}}</template>
      </el-table-column>
      <el-table-column label="修改时间" align="center">
        <template slot-scope="scope">{{scope.row.updateTime | formatTimeFilter}}</template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="250">
        <template slot-scope="scope">
          <el-button type="danger" size="mini" @click="handleDel(scope.row)">删除</el-button>
          <p></p>
          <el-button type="danger" size="mini" @click="resetInsideSell(scope.row)">清除销售</el-button>
          <p></p>
          <el-button  size="mini" @click="handleUpdate(scope.row)">修改</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination-container">
      <el-pagination
        background=""
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="listQuery.page"
        :page-sizes="[10, 20, 30,  50]"
        :page-size="listQuery.size"
        layout="total,  sizes,  prev,  pager,  next,  jumper"
        :total="total"
      ></el-pagination>
    </div>
    <el-dialog :close-on-click-modal=false :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="40%">
      <el-form ref="dataForm" :model="temp" :rules="rules"  label-position="right" label-width="100px">
        <el-form-item label="真实姓名:" prop="realName">
          <el-input v-model="temp.realName" ></el-input>
        </el-form-item>
        <el-form-item label="后台登录名:"  prop="accountName">
          <el-input v-model="temp.accountName"></el-input>
        </el-form-item>
        <el-form-item label="用户id:"  prop="userId">
          <el-input v-model="temp.userId"></el-input>
        </el-form-item>
        <el-form-item label="状态:">
          <el-radio-group v-model="temp.status">
            <el-radio :label='"Y"'>有效</el-radio>
            <el-radio :label='"N"'>无效</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button v-if="dialogStatus == 'create'" type="primary" @click="saveData">保存</el-button>
        <el-button v-else type="primary" @click="updateData">更新</el-button>
        <el-button @click="dialogFormVisible = false">取消</el-button>
      </div>
    </el-dialog>
    <el-dialog :close-on-click-modal=false title="清除站外订单/销售" :visible.sync="outsideResetDialogFormVisible" width="25%">
      <el-form ref="dataForm" :model="outsideResetTemp" label-position="right" label-width="100px">
        <el-form-item label="手机号:">
          <el-input v-model="outsideResetTemp.phone"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <!--<el-button type="primary" @click="resetOutsideSell">清除销售</el-button>-->
        <el-button type="primary" @click="resetOutsideOrder">清除订单</el-button>
        <el-button @click="outsideResetDialogFormVisible = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {addWhite, cancelWhite, resetOrder, resetSellRelationship, whiteList, updateWhite} from "@/api/user";
  import {clearObj, formatTimeFilter, responseNotify} from "@/utils";

  export default {
  data() {
    return {
      list: [],
      listLoading: true,
      total: 0,
      listQuery: {
        page: 1,
        size: 20,
        userId: undefined,
        userName: undefined,
        type:'course'
      },
      dialogFormVisible: false,
      outsideResetDialogFormVisible: false,
      dialogLoading: false,
      dialogStatus: '',
      textMap: {
        update: '修改',
        create: '新建'
      },
      temp: {
        userId: undefined,
        id: undefined,
        userName: undefined,
        realName: undefined,
        accountName: undefined,
        status: 'N',
        type:'course'
      },
      outsideResetTemp: {
        phone: undefined
      },
      rules: {
        userId: [
            { required: true, message: '请输入用户id', trigger: 'blur' }
        ]
      }
    
    };
  },
  filters: { formatTimeFilter },
  created() {
    this.resetTemp();
    this.fetchData();
  },
  methods: {
    fetchData() {
      const self = this;
      self.listLoading = true;
      whiteList(self.listQuery).then(response => {
        self.list = response.data.items || [];
        self.listLoading = false;
        self.total = response.data.total || 0;
      });
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
      this.temp = {
        userId: undefined,
        id: undefined,
        userName: undefined,
        realName: undefined,
        accountName: undefined,
        status: 'N',
        type:'course'
      }
    },
    resetOutsideResetTemp() {
        const self = this;
        clearObj(self.outsideResetTemp);
    },

    handleCreate() {
      const self = this;
      self.resetTemp();
      self.dialogFormVisible = true;
      this.dialogStatus = 'create';
    },
    handleOutsideReset() {
        const self = this;
        self.resetOutsideResetTemp();
        self.outsideResetDialogFormVisible = true;
    },
    saveData() {
      addWhite(this.temp).then(response => {
        //根据状态判断是否成功
        if (response.code === 200) {
            this.$notify({
                title: "成功",
                message: "操作成功",
                type: "success",
                duration: 2000
            });
            this.dialogFormVisible = false;
            this.fetchData();
        } else {
            this.listLoading = false;
            this.$notify({
                title: "失败",
                message: "操作失败",
                type: "error",
                duration: 2000
            });
        }
        
      });
      
    },
      resetOutsideOrder() {
          resetOrder({ mobile: this.outsideResetTemp.phone, type:"outside" }).then(response => {
              //根据状态判断是否成功
              responseNotify(response, this);
          });
          this.outsideResetDialogFormVisible = false;
      },
      /*resetOutsideSell() {
          resetSellRelationship({ id: this.outsideResetTemp.phone, type:"outside" }).then(response => {
              //根据状态判断是否成功
              responseNotify(response, this);
          });
          this.outsideResetDialogFormVisible = false;
      },*/
    handleDel(row) {
      const self = this;
      self
        .$confirm(`确定删除记录,userId= ${row.userId}?`, "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
        .then(() => {
          cancelWhite({ userId: row.userId ,type: 'course' }).then(response => {
            //根据状态判断是否成功
            responseNotify(response, self);
            self.fetchData();
          });
        })
        .catch(() => {});
    },
    resetInsideOrder(row) {
        const self = this;
        self
            .$confirm(`确定清除订单记录,userId= ${row.userId}?`, "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            })
            .then(() => {
                resetOrder({ id: row.userId, type:"inside" }).then(response => {
                    //根据状态判断是否成功
                    responseNotify(response, self);
                });
            })
            .catch(() => {});
    },
    resetInsideSell(row) {
        const self = this;
        self
            .$confirm(`确定清除销售关系记录,userId= ${row.userId}?`, "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            })
            .then(() => {
                resetSellRelationship({ id: row.userId, type:"inside" }).then(response => {
                    //根据状态判断是否成功
                    responseNotify(response, self);
                });
            })
            .catch(() => {});
    },
    handleUpdate(row){
      this.temp = Object.assign({}, row);
      this.dialogStatus = 'update';
      this.dialogFormVisible = true;
    },
    updateData(){
      updateWhite(this.temp).then(response => {
        //根据状态判断是否成功
        if (response.code === 200) {
            this.$notify({
                title: "成功",
                message: "操作成功",
                type: "success",
                duration: 2000
            });
            this.dialogFormVisible = false;
            this.fetchData();
        } else {
            this.listLoading = false;
            this.$notify({
                title: "失败",
                message: "操作失败",
                type: "error",
                duration: 2000
            });
        }
        
      });
    }
  }
};
</script>
