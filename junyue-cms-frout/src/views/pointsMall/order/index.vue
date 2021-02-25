<template>
  <div class="app-container">
    <div class="filter-container" style="margin:20px">
      <el-input
        placeholder="商品id"
        v-model="listQuery.goodsId"
        style="width: 200px; margin-top: 10px"
        @input="numberInput('goodsId')"
        clearable
      ></el-input>
      <el-input
        placeholder="商品名称"
        v-model="listQuery.goodsTitle"
        style="width: 200px; margin-top: 10px"
        clearable
      ></el-input>
      <el-select
        class="filter-item"
        v-model="listQuery.goodsType"
        style="margin-top: 10px"
        placeholder="商品类型"
        clearable
      >
        <el-option
          v-for="item in productTypes"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
      <el-select
        class="filter-item"
        v-model="listQuery.orderStatus"
        style="margin-top: 10px"
        placeholder="商品订单状态"
        clearable
      >
        <el-option
          v-for="item in orderStatuses"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
      <el-date-picker
        type="datetime"
        v-model="listQuery.startTime"
        style="margin-top: 10px"
        value-format="yyyy-MM-dd HH:mm:ss"
        default-time="00:00:00"
        placeholder="下单开始时间"
      ></el-date-picker>
      <el-date-picker
        type="datetime"
        v-model="listQuery.endTime"
        style="margin-top: 10px"
        value-format="yyyy-MM-dd HH:mm:ss"
        default-time="23:59:59"
        placeholder="下单结束时间"
      ></el-date-picker>
      <el-input
        placeholder="用户id"
        v-model="listQuery.userId"
        style="width: 200px; margin-top: 10px"
        @input="numberInput('userId')"
        clearable
      ></el-input>
      <el-input
        placeholder="订单id"
        v-model="listQuery.id"
        style="width: 200px; margin-top: 10px"
        @input="numberInput('id')"
        clearable
      ></el-input>
      <!-- <el-select
        class="filter-item"
        v-model="listQuery.userType"
        style="margin-top: 10px"
        placeholder="用户类型"
        clearable
      >
        <el-option
          v-for="item in userTypes"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>-->
      <el-select
        class="filter-item"
        v-model="listQuery.payType"
        style="margin-top: 10px"
        placeholder="支付类型"
        clearable
      >
        <el-option
          v-for="item in payTypes"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
      <el-select
        class="filter-item"
        style="margin-top: 10px"
        v-model="listQuery.expressStatus"
        placeholder="是否有物流单号"
        clearable
      >
        <el-option
          v-for="item in isHasShipmentNumbers"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
      <el-button
        class="filter-item"
        style="width: 100px; margin-top:10px;"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
        :loading="listLoading"
      >查询</el-button>
      <el-button
        class="filter-item"
        style="width: 100px; margin-top:10px;"
        type="primary"
        icon="el-icon-download"
        @click="export2Excel"
      >导出</el-button>
      <el-button
        class="filter-item"
        style="width: 120px; margin-top:10px;"
        type="primary"
        icon="el-icon-refresh"
        @click="syncExpress"
      >同步物流
      </el-button>
    </div>

    <div style="margin: 20px">共搜到{{total}}条数据</div>

    <el-table
      :data="list"
      v-loading.body="listLoading"
      element-loading-text="加载中"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="商品ID" align="center">
        <template slot-scope="scope">{{scope.row.goodsId}}</template>
      </el-table-column>
      <el-table-column label="商品名称" align="center">
        <template slot-scope="scope">{{scope.row.goodsTitle}}</template>
      </el-table-column>
      <el-table-column label="商品类型" align="center">
        <template slot-scope="scope">{{productTypeMap[scope.row.goodsType]}}</template>
      </el-table-column>
      <el-table-column label="商品数量" align="center">
        <template slot-scope="scope">{{scope.row.goodsNum}}</template>
      </el-table-column>
      <el-table-column label="单价" align="center">
        <template
          slot-scope="scope"
        >{{scope.row.price/scope.row.goodsNum}}{{productPriceTypeMap[scope.row.payType]}}</template>
      </el-table-column>
      <el-table-column label="总价" align="center">
        <template slot-scope="scope">{{scope.row.price}}{{productPriceTypeMap[scope.row.payType]}}</template>
      </el-table-column>
      <el-table-column label="用户ID" align="center">
        <template slot-scope="scope">{{scope.row.userId}}</template>
      </el-table-column>
      <el-table-column label="用户昵称" align="center">
        <template slot-scope="scope">{{scope.row.username}}</template>
      </el-table-column>
      <el-table-column label="订单ID" align="center">
        <template slot-scope="scope">{{scope.row.id}}</template>
      </el-table-column>
      <el-table-column label="下单时间" align="center">
        <template slot-scope="scope">{{scope.row.createTime | normalFormaTime}}</template>
      </el-table-column>
      <el-table-column label="收货人" align="center">
        <template slot-scope="scope">{{scope.row.receiverName}}</template>
      </el-table-column>
      <el-table-column label="收货手机" align="center">
        <template slot-scope="scope">{{scope.row.receivePhone}}</template>
      </el-table-column>
      <el-table-column label="收货地区" align="center">
        <template slot-scope="scope">{{scope.row.area}}</template>
      </el-table-column>
      <el-table-column label="收货地址" align="center">
        <template slot-scope="scope">{{scope.row.address}}</template>
      </el-table-column>
      <el-table-column label="物流单号" align="center">
        <template slot-scope="scope">{{scope.row.expressNo}}</template>
      </el-table-column>
      <el-table-column label="物流公司" align="center">
        <template slot-scope="scope">{{scope.row.expressCompany | expressCompanyFilter}}</template>
      </el-table-column>
      <el-table-column label="物流备注" align="center">
        <template slot-scope="scope">{{scope.row.remark}}</template>
      </el-table-column>
      <el-table-column label="订单备注" align="center">
        <template slot-scope="scope">{{scope.row.memo}}</template>
      </el-table-column>
      <el-table-column label="状态" align="center">
        <template slot-scope="scope">
          <span
            :style="{'color': scope.row.orderStatus === orderStatus2 ? 'red' : 'green'}"
          >{{orderStatusMap[scope.row.orderStatus]}}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            :disabled="scope.row.goodsType === productType2"
            @click="editOrderInfo(scope.row)"
          >修改</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="listQuery.page"
        :page-sizes="[10, 20, 30, 40, 50]"
        :page-size="listQuery.size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </div>

    <el-dialog
      :close-on-click-modal="false"
      title="修改订单收货信息"
      :visible.sync="dialogFormVisible"
      width="560px"
      @close="closeDialog"
    >
      <el-form
        ref="orderForm"
        :model="dialogData"
        label-position="right"
        label-width="120px"
        :rules="rules"
      >
        <el-form-item label="用户昵称">{{dialogData.username}}</el-form-item>
        <!-- <el-form-item label="项目">{{dialogData.project}}</el-form-item> -->
        <!-- <el-form-item label="业务类型">{{dialogData.businessType}}</el-form-item> -->
        <el-form-item label="收货人姓名" prop="receiverName">
          <el-input v-model="dialogData.receiverName" placeholder="请输入收货人姓名" clearable></el-input>
        </el-form-item>
        <el-form-item label="收货手机号" prop="receivePhone">
          <el-input v-model="dialogData.receivePhone" placeholder="请输入收货手机号" clearable></el-input>
        </el-form-item>
        <el-form-item label="收货地区" prop="area">
          <el-input v-model="dialogData.area" placeholder="请输入收货地区" clearable></el-input>
        </el-form-item>
        <el-form-item label="收货地址" prop="address">
          <el-input v-model="dialogData.address" placeholder="请输入收货地址" clearable></el-input>
        </el-form-item>
        <el-form-item label="物流单号">
          <el-input v-model="dialogData.expressNo" clearable></el-input>
        </el-form-item>
        <el-form-item label="物流公司">
          <el-select
            class="filter-item"
            v-model="dialogData.expressCompany"
            placeholder="请选择"
            clearable
          >
            <el-option
              v-for="item in expressCompanyList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="物流备注">
          <el-input v-model="dialogData.remark" clearable></el-input>
        </el-form-item>
        <el-form-item label="订单备注">
          <el-input v-model="dialogData.memo" clearable></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button
          type="primary"
          @click="dialogConfirm('orderForm')"
          :loading="confirmBtnLoading"
        >保存</el-button>
        <el-button @click="closeDialog">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {exportList, orderList, orderUpdate, syncExpress} from "@/api/pointMall";
  import {expressCompanyFilter, normalFormaTime} from "@/utils";
  import {
    EXPRESS_STATUS_1,
    EXPRESS_STATUS_2,
    ORDER_STATUS_1,
    ORDER_STATUS_2,
    PAY_TYPE_1,
    PAY_TYPE_2,
    PRODUCT_PRICE_TYPE_1,
    PRODUCT_PRICE_TYPE_2,
    PRODUCT_TYPE_1,
    PRODUCT_TYPE_2
  } from "../config";

  export default {
  data() {
    return {
      productType2: PRODUCT_TYPE_2,
      orderStatus2: ORDER_STATUS_2,
      list: [],
      listLoading: true,
      total: 0,
      dialogFormVisible: false,
      listQuery: {
        //查询条件
        page: 1,
        size: 20,
        id: null,
        goodsId: undefined,
        goodsTitle: undefined,
        goodsType: undefined,
        orderStatus: undefined,
        startTime: undefined,
        endTime: undefined,
        userId: undefined,
        // userType: undefined,
        payType: undefined,
        expressStatus: undefined
      },
      productTypes: [
        { value: PRODUCT_TYPE_1, label: "实物商品" },
        { value: PRODUCT_TYPE_2, label: "虚拟商品" }
      ],
      // userTypes: [
      //   { value: USER_TYPE_1, label: "xx" },
      //   { value: USER_TYPE_2, label: "xxx" }
      // ],
      payTypes: [
        { value: PAY_TYPE_1, label: "钻石" },
        { value: PAY_TYPE_2, label: "鱼豆豆" }
      ],
      orderStatuses: [
        { value: ORDER_STATUS_1, label: "有效" },
        { value: ORDER_STATUS_2, label: "无效" }
      ],
      isHasShipmentNumbers: [
        { value: EXPRESS_STATUS_1, label: "有物流单号" },
        { value: EXPRESS_STATUS_2, label: "无物流单号" }
      ],
      expressCompanyList: [
        { value: "SF", label: "顺丰速运" },
        { value: "ZTO", label: "中通快递" },
        { value: "HTKY", label: "百世快递" },
        { value: "HHTT", label: "天天快递" },
        { value: "DBL", label: "德邦快递" },
        { value: "UC", label: "优速快递" },
        { value: "JD", label: "京东快递" },
        { value: "STO", label: "申通快递" },
        { value: "YTO", label: "圆通速递" },
        { value: "YD", label: "韵达速递" },
        { value: "YZPY", label: "邮政快递包裹" },
        { value: "EMS", label: "EMS" }
      ],
      productTypeMap: {
        [PRODUCT_TYPE_1]: "实物",
        [PRODUCT_TYPE_2]: "虚拟"
      },
      productPriceTypeMap: {
        [PRODUCT_PRICE_TYPE_1]: "钻石",
        [PRODUCT_PRICE_TYPE_2]: "鱼豆豆"
      },
      orderStatusMap: {
        [ORDER_STATUS_1]: "有效",
        [ORDER_STATUS_2]: "无效"
      },
      dialogData: {},
      confirmBtnLoading: false,
      rules: {
        receiverName: [
          { required: true, message: "请完善必填数据", trigger: "blur" }
        ],
        receivePhone: [
          { required: true, message: "请完善必填数据", trigger: "blur" }
        ],
        area: [{ required: true, message: "请完善必填数据", trigger: "blur" }],
        address: [
          { required: true, message: "请完善必填数据", trigger: "blur" }
        ]
      }
    };
  },
  filters: { normalFormaTime, expressCompanyFilter },
  created() {
    this.fetchData();
  },
  methods: {
    fetchData() {
      this.listLoading = true;
      Object.keys(this.listQuery).map(key => {
        if (this.listQuery[key] === "") {
          this.listQuery[key] = undefined;
        }
      });
      orderList(this.listQuery).then(response => {
        this.list = response.data.list || [];
        this.total = response.data.count || 0;
        this.listLoading = false;
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
    export2Excel() {
      exportList(this.listQuery).then(response => {
        if (response.code === 200) {
          this.$notify({
            title: "导出提示",
            message: response.state.msg,
            type: "success",
            duration: 5000
          });
        } else {
          this.$notify({
            title: "导出提示",
            message: response.state.msg,
            type: "error",
            duration: 5000
          });
        }
      });
    },
    syncExpress() {
      syncExpress({}).then(response => {
        if (response.code === 200) {
          this.$notify({
            title: "系统提示",
            message: response.state.msg,
            type: "success",
            duration: 5000
          });
        } else {
          this.$notify({
            title: "系统提示",
            message: response.state.msg,
            type: "error",
            duration: 5000
          });
        }
      });
    },
    editOrderInfo(row) {
      this.dialogFormVisible = true;
      this.dialogData = Object.assign({}, row);
      this.$nextTick(() => {
        this.$refs["orderForm"].clearValidate();
      });
    },
    closeDialog() {
      this.dialogFormVisible = false;
    },
    dialogConfirm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.confirmBtnLoading = true;
          orderUpdate(this.dialogData).then(() => {
            this.confirmBtnLoading = false;
            this.dialogFormVisible = false;
            this.$message.success("保存成功");
            this.fetchData();
          });
        } else {
          this.$message.error("请完善必填数据");
          return false;
        }
      });
    },
    numberInput(key) {
      this.listQuery[key] = this.listQuery[key].replace(/[^\d]/g, "");
    }
  }
};
</script>
