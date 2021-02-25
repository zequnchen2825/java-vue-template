<template>
  <div class="app-container">
    <div class="filter-container" style="margin:20px">
      <el-input
        placeholder="商品ID/名称"
        v-model="listQuery.searchContent"
        style="width: 200px;"
        clearable
      ></el-input>
      <el-select
        key="subject"
        class="filter-item"
        v-model="listQuery.type"
        placeholder="请选择商品类型"
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
        key="type"
        class="filter-item"
        v-model="listQuery.status"
        placeholder="请选择状态"
        clearable
      >
        <el-option
          v-for="item in productStatuses"
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
        icon="el-icon-plus"
        @click="addProduct"
      >新增</el-button>
    </div>

    <el-table
      :data="list"
      v-loading.body="listLoading"
      element-loading-text="加载中"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="序号" align="center">
        <template slot-scope="scope">{{scope.$index + 1}}</template>
      </el-table-column>
      <el-table-column label="商品ID" align="center">
        <template slot-scope="scope">{{scope.row.id}}</template>
      </el-table-column>
      <el-table-column label="商品名称" align="center">
        <template slot-scope="scope">{{scope.row.title}}</template>
      </el-table-column>
      <el-table-column label="商品类型" align="center">
        <template slot-scope="scope">{{productTypeMap[scope.row.type]}}</template>
      </el-table-column>
      <el-table-column label="价格" align="center">
        <template slot-scope="scope">{{scope.row.price}}{{productPriceTypeMap[scope.row.payType]}}</template>
      </el-table-column>
      <el-table-column label="库存" align="center">
        <template slot-scope="scope">{{scope.row.num || '已售罄'}}</template>
      </el-table-column>
      <el-table-column label="已兑换数量" align="center">
        <template slot-scope="scope">{{scope.row.exchangeNumber}}</template>
      </el-table-column>
      <el-table-column label="创建时间" align="center">
        <template slot-scope="scope">{{scope.row.createTime | normalFormaTime}}</template>
      </el-table-column>
      <el-table-column label="状态" align="center">
        <template slot-scope="scope">{{productStatusMap[scope.row.status]}}</template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="250">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="editProduct(scope.row)">编辑</el-button>
          <el-button
            type="warning"
            size="mini"
            @click="changeProductStatus(scope.row.id, scope.row.status)"
          >{{productStatusOppositeMap[scope.row.status]}}</el-button>
          <el-button
            type="danger"
            size="mini"
            :disabled="scope.row.status === productStatus1"
            @click="deleteProduct(scope.row.id)"
          >删除</el-button>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center">
        <template slot-scope="scope">{{scope.row.status === productStatus1 ? '上架状态，不可删除' : ''}}</template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="listQuery.page"
        :page-sizes="[10, 20, 30, 40, 50]"
        :page-size="listQuery.limit"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </div>

    <el-dialog
      :close-on-click-modal="false"
      :title="dialogTitle"
      :visible.sync="dialogFormVisible"
      width="560px"
      @close="closeDialog"
      destroy-on-close
    >
      <el-form
        ref="productForm"
        :model="dialogData"
        label-position="left"
        label-width="120px"
        :rules="rules"
      >
        <el-form-item label="商品名称" prop="title">
          <el-input v-model="dialogData.title" maxlength="50" placeholder="请输入商品名称" clearable></el-input>
        </el-form-item>
        <el-form-item label="商品类型" prop="type">
          <el-radio-group
            v-if="dialogType === 1"
            v-model="dialogData.type"
            @change="toggleProductType"
          >
            <el-radio :label="productType1">实物商品</el-radio>
            <el-radio :label="productType2">虚拟商品</el-radio>
          </el-radio-group>
          <span
            v-if="dialogType === 2"
          >{{dialogData.type === productType1 ? '实物商品' : dialogData.type === productType2 ? '虚拟商品' : ''}}</span>
          <el-input
            v-if="dialogData.type === productType2"
            v-model="dialogData.virtualUrl"
            placeholder="请输入课程领取链接"
            style="width: 180px"
            ref="courseLink"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item v-if="dialogData.type === productType1" label="关联仓库商品" prop="warehouseGoods">
          <el-select
            v-if="dialogType === 1"
            key="subject"
            class="filter-item"
            v-model="dialogData.relatedGoodsId"
            placeholder="请关联仓库中的商品"
            ref="warehouseGoods"
            clearable
          >
            <el-option
              v-for="item in warehouseGoodsData"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
          <div
            v-if="dialogType === 2"
          >{{relatedGoodsInfo && `${relatedGoodsInfo.title}（商品ID：${relatedGoodsInfo.id}）`}}</div>
        </el-form-item>
        <el-form-item label="仓库库存" v-if="dialogData.type === productType1">
          <div
            v-if="dialogType === 1"
          >{{warehouseGoodsData.find(item => item.value === dialogData.relatedGoodsId) && warehouseGoodsData.find(item => item.value === dialogData.relatedGoodsId).num}}</div>
          <div v-if="dialogType === 2">{{relatedGoodsInfo && relatedGoodsInfo.num}}</div>
        </el-form-item>
        <el-form-item label="商品描述" prop="desc" v-if="dialogData.type === productType2">
          <el-input v-model="dialogData.desc" maxlength="200" placeholder="请输入商品描述" clearable></el-input>
        </el-form-item>
        <el-form-item label="库存" prop="num" v-show="dialogData.type === productType2">
          <el-input
            v-model="dialogData.num"
            placeholder="请输入库存"
            ref="productStock"
            @input="numberInput(dialogData, 'num')"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input
            v-model="dialogData.price"
            placeholder="请输入商品价格"
            style="width: 200px"
            ref="productPrice"
            maxlength="8"
            @input="numberInput(dialogData, 'price')"
            clearable
          ></el-input>
          <el-select
            key="subject"
            class="filter-item"
            v-model="dialogData.payType"
            placeholder="请选择积分币"
            style="width: 150px"
            ref="priceType"
          >
            <el-option
              v-for="item in priceTypes"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品描述" prop="desc" v-if="dialogData.type === productType1">
          <el-input v-model="dialogData.desc" maxlength="200" placeholder="请输入商品描述" clearable></el-input>
        </el-form-item>
        <el-form-item label="商品头图" required>
          <qlUploadDragImg
            :imageList.sync="dialogData.headImageArray"
            :maxNumber="10"
            tip="提示:最多上传10张，已上传的图片可拖拽调整排序"
          />
        </el-form-item>
        <el-form-item label="商品详情" required>
          <qlUploadDragImg
            :imageList.sync="dialogData.infoImageArray"
            tip="提示:不限制上传张数，已上传的图片可拖拽调整排序"
          />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button
          type="primary"
          @click="dialogConfirm('productForm')"
          :loading="confirmBtnLoading"
        >保存</el-button>
        <el-button @click="closeDialog">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { cloneDeep } from "lodash";
import {
  queryForList,
  saveOrUpdate,
  changeStatus,
  relatedGoodsList
} from "@/api/pointMall";
import { normalFormaTime } from "@/utils";
import qlUploadDragImg from "./QlUploadDragImg.vue";
import {
  PRODUCT_TYPE_1,
  PRODUCT_TYPE_2,
  PRODUCT_STATUS_1,
  PRODUCT_STATUS_2,
  PRODUCT_PRICE_TYPE_1,
  PRODUCT_PRICE_TYPE_2
} from "../config";

export default {
  data() {
    const checkProductType = (rule, value, callback) => {
      if (this.$refs.courseLink && !this.$refs.courseLink.value) {
        // 虚拟商品需要输入课程领取链接
        callback(new Error("请完善必填数据"));
        return;
      }
      callback();
    };
    const checkProductPrice = (rule, value, callback) => {
      const price = this.$refs.productPrice.value;
      if (!price && price !== 0) {
        callback(new Error("请完善必填数据"));
        return;
      }
      if (!this.$refs.priceType.value) {
        callback(new Error("请选择积分币"));
        return;
      }
      callback();
    };
    const checkProductStock = (rule, value, callback) => {
      if (this.$refs.productStock) {
        const stock = this.$refs.productStock.value;
        if (!stock && stock !== 0 && this.dialogData.type === PRODUCT_TYPE_2) {
          callback(new Error("请完善必填数据"));
          return;
        }
      }
      callback();
    };
    const checkWarehouseGoods = (rule, value, callback) => {
      if (this.$refs.warehouseGoods) {
        const warehouseGoods = this.$refs.warehouseGoods.value;
        if (!warehouseGoods) {
          callback(new Error("请完善必填数据"));
          return;
        }
      }
      callback();
    };
    return {
      productStatus1: PRODUCT_STATUS_1,
      productType1: PRODUCT_TYPE_1,
      productType2: PRODUCT_TYPE_2,
      productTypes: [
        { value: PRODUCT_TYPE_1, label: "实物商品" },
        { value: PRODUCT_TYPE_2, label: "虚拟商品" }
      ],
      productStatuses: [
        { value: PRODUCT_STATUS_1, label: "上架" },
        { value: PRODUCT_STATUS_2, label: "下架" }
      ],
      priceTypes: [
        { value: PRODUCT_PRICE_TYPE_1, label: "钻石" },
        { value: PRODUCT_PRICE_TYPE_2, label: "鱼豆豆" }
      ],
      productPriceTypeMap: {
        [PRODUCT_PRICE_TYPE_1]: "钻石",
        [PRODUCT_PRICE_TYPE_2]: "鱼豆豆"
      },
      productTypeMap: {
        [PRODUCT_TYPE_1]: "实物",
        [PRODUCT_TYPE_2]: "虚拟"
      },
      productStatusMap: {
        [PRODUCT_STATUS_1]: "上架",
        [PRODUCT_STATUS_2]: "下架"
      },
      productStatusOppositeMap: {
        [PRODUCT_STATUS_2]: "上架",
        [PRODUCT_STATUS_1]: "下架"
      },
      list: [],
      listLoading: false,
      total: 0,
      dialogFormVisible: false,
      listQuery: {
        goodTypes: "MARKETGOODSLIST",
        page: 1,
        limit: 20,
        searchContent: undefined,
        type: undefined,
        status: undefined
      },
      dialogType: undefined, // 1: 新增商品  2: 编辑商品
      dialogData: {},
      relatedGoodsInfo: {}, // 修改时，获取的关联仓库商品的信息
      warehouseGoodsData: [], // 关联仓库商品的数据
      confirmBtnLoading: false,
      rules: {
        title: [{ required: true, message: "请完善必填数据", trigger: "blur" }],
        type: [
          {
            required: true,
            message: "请完善必填数据",
            validator: checkProductType,
            trigger: "change"
          },
          {
            required: true,
            message: "请完善必填数据",
            trigger: "blur"
          }
        ],
        warehouseGoods: [
          {
            required: true,
            message: "请完善必填数据",
            validator: checkWarehouseGoods,
            trigger: "change"
          }
        ],
        desc: [{ required: true, message: "请完善必填数据", trigger: "blur" }],
        price: [
          {
            required: true,
            validator: checkProductPrice,
            trigger: "blur"
          },
          {
            validator: checkProductPrice,
            trigger: "change"
          }
        ],
        num: [{ required: true, validator: checkProductStock, trigger: "blur" }]
      }
    };
  },
  filters: { normalFormaTime },
  computed: {
    dialogTitle() {
      return this.dialogType === 1
        ? "新增商品"
        : this.dialogType === 2
        ? "编辑商品"
        : "";
    }
  },
  components: { qlUploadDragImg },
  created() {
    this.fetchData();
  },
  methods: {
    fetchData() {
      this.listLoading = true;
      queryForList(this.listQuery).then(response => {
        this.list = response.data.items || [];
        this.total = response.data.count || 0;
        this.listLoading = false;
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
    closeDialog() {
      this.dialogFormVisible = false;
    },
    addProduct() {
      this.dialogData = {
        title: "",
        type: undefined,
        virtualUrl: "",
        desc: "",
        price: undefined,
        payType: undefined,
        relatedGoodsId: undefined,
        num: undefined,
        headImageArray: [],
        infoImageArray: []
      };
      this.dialogType = 1;
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["productForm"].clearValidate();
      });
      relatedGoodsList().then(res => {
        if (res && res.data && res.data.items.length) {
          this.warehouseGoodsData = res.data.items.map((item, index) => ({
            value: item.id,
            label: `${item.title}（商品ID：${item.id}）`,
            num: item.num
          }));
        }
      });
    },
    editProduct(row) {
      this.dialogData = cloneDeep(row);
      this.dialogType = 2;
      if (row.type === PRODUCT_TYPE_1) {
        relatedGoodsList().then(res => {
          if (res && res.data && res.data.items.length) {
            this.relatedGoodsInfo = res.data.items.find(
              item => item.id === row.relatedGoodsId
            );
          }
        });
      }

      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["productForm"].clearValidate();
      });
    },
    dialogConfirm(formName) {
      if (this.confirmBtnLoading) reutrn;
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (!this.dialogData.headImageArray.length) {
            this.$message.error("商品头图不能为空");
            return;
          }
          if (!this.dialogData.infoImageArray.length) {
            this.$message.error("商品详情不能为空");
            return;
          }
          this.confirmBtnLoading = true;
          if (this.dialogType === 1) {
            // 新增商品
            saveOrUpdate(this.dialogData).then(() => {
              this.confirmBtnLoading = false;
              this.dialogFormVisible = false;
              this.$message.success("保存成功");
              this.fetchData();
            });
          } else if (this.dialogType == 2) {
            // 更新商品
            saveOrUpdate(this.dialogData).then(() => {
              this.confirmBtnLoading = false;
              this.dialogFormVisible = false;
              this.$message.success("保存成功");
              this.fetchData();
            });
          }
        } else {
          this.$message.error("请完善必填数据");
          return false;
        }
      });
    },
    changeProductStatus(id, currentStatus) {
      // 上架/下架
      let status;
      if (currentStatus === PRODUCT_STATUS_1) {
        status = PRODUCT_STATUS_2;
      }
      if (currentStatus === PRODUCT_STATUS_2) {
        status = PRODUCT_STATUS_1;
      }
      changeStatus({ id, status }).then(res => {
        if (res && res.state && res.state.code === 200) {
          this.$message.success("操作成功");
          this.fetchData();
        }
      });
    },
    deleteProduct(id) {
      this.$confirm("此操作将永久删除该商品, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          changeStatus({ id, status: "D" }).then(res => {
            if (res && res.state && res.state.code === 200) {
              this.$message.success("操作成功");
              this.fetchData();
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    toggleProductType(value) {
      this.$refs["productForm"].clearValidate();

      if (value === PRODUCT_TYPE_1) {
        this.dialogData.virtualUrl = "";
      }
      if (value === PRODUCT_TYPE_2) {
        this.dialogData.relatedGoodsId = undefined;
      }
    },
    numberInput(dialogData, key) {
      dialogData[key] = dialogData[key].replace(/[^\d]/g, "");
    }
  }
};
</script>