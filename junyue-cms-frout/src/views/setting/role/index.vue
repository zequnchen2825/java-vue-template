<template>
  <imp-panel>
    <h3 class="box-title" slot="header" style="width: 100%;">
      <el-button type="primary" icon="plus" @click="newAdd">新增</el-button>
      <el-button type="danger" icon="delete" @click="batchDelete">删除</el-button>
    </h3>
    <el-row slot="body" style="margin-bottom: 20px;" :gutter="24">
      <el-col :span="6" :xs="24" :sm="24" :md="6" :lg="6" style="margin-bottom: 20px;">
        <el-tree v-if="roleTree" ref="roleTree" :data="roleTree" show-checkbox default-expand-all highlight-current :render-content="renderContent" @node-click="handleNodeClick" clearable node-key="id" :props="defaultProps"></el-tree>
      </el-col>
      <el-col :span="18" :xs="24" :sm="24" :md="18" :lg="18">
        <el-card class="box-card">
          <div class="text item">
            <el-form :model="form" ref="form" :rules="rules">
              <!-- <el-form-item label="父级" :label-width="formLabelWidth">
                <el-select-tree v-model="form.parentId" :treeData="roleTree" :propNames="defaultProps" clearable placeholder="请选择父级">
                </el-select-tree>
              </el-form-item> -->
              <el-form-item label="角色名称" :label-width="formLabelWidth" prop="roleName">
                <el-input v-model="form.roleName" auto-complete="off"></el-input>
              </el-form-item>
              <el-form-item label="英文编码" :label-width="formLabelWidth" prop="roleCode">
                <el-input v-model="form.roleCode" auto-complete="off"></el-input>
              </el-form-item>
              <el-form-item label="排序" :label-width="formLabelWidth">
                <el-input v-model="form.sort" type="number"></el-input>
              </el-form-item>
              <el-form-item label="" :label-width="formLabelWidth">
                <el-button type="primary" @click="onSubmit" v-text="form.id?'修改':'新增'"></el-button>
                <el-button type="info" @click="settingResource($event,form.id)" icon="setting" v-show="form.id && form.id!=null">配置资源</el-button>
                <el-button type="danger" @click="deleteSelected" icon="delete" v-show="form.id && form.id!=null">删除
                </el-button>

                <el-button type="danger" @click="roleUser(form)" icon="el-icon-user" v-show="form.id && form.id!=null">已分配用户
                </el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-card>

        <el-dialog :close-on-click-modal=false title="配置资源" :visible.sync="dialogVisible" size="tiny">
          <div class="select-tree">
            <el-scrollbar tag="div" class='is-empty' wrap-class="el-select-dropdown__wrap" view-class="el-select-dropdown__list">
              <el-tree :data="resourceTree" ref="resourceTree" show-checkbox default-expand-all node-key="id" :check-strictly="true" v-loading="dialogLoading" :props="menuDefaultProps" @check="clickCheckBox">
              </el-tree>
            </el-scrollbar>
          </div>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="configRoleResources">确 定</el-button>
          </span>
        </el-dialog>

        <el-dialog title="已分配用户" :visible.sync="userRoleDialog" width="40%">
          <el-table :data="roleUserList" v-loading.body="listLoading" element-loading-text="加载中" border fit highlight-current-row>
            <el-table-column label="id" align="center">
              <template slot-scope="scope">{{scope.row.id}}</template>
            </el-table-column>
            <el-table-column label="登录名" align="center">
              <template slot-scope="scope">{{scope.row.userName}}</template>
            </el-table-column>
            <el-table-column label="真实姓名" align="center">
              <template slot-scope="scope">{{scope.row.realName}}</template>
            </el-table-column>
            <!--                  <el-table-column label="邮箱" align="center">-->
            <!--                      <template slot-scope="scope">{{scope.row.mail}}</template>-->
            <!--                  </el-table-column>-->
            <el-table-column label="是否有效" align="center">
              <template slot-scope="scope">{{scope.row.userState==0?'否':'是'}}</template>
            </el-table-column>
          </el-table>
          <div class="pagination-container">
            <el-pagination background @current-change="pageRoleUser" :current-page="listQuery.page" :page-size="listQuery.limit" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="userTotal"></el-pagination>
          </div>
        </el-dialog>

      </el-col>
    </el-row>
  </imp-panel>
</template>

<script>
import {
  getList,
  updateSysRole as updateRole,
  addSysRole as addRole,
  deleteSysRole as deleteRole,
  addResource,
  getResource,
  findUserByRole
} from "@/api/together/sysRole";
import { getList as getResourceList, getListByRoleId} from "@/api/together/menu";
import panel from "@/components/menu/panel.vue";
import selectTree from "@/components/menu/selectTree.vue";
import treeter from "@/components/menu/treeter";
import merge from "element-ui/src/utils/merge";
import qs from "qs";

export default {
  mixins: [treeter],
  components: {
    "imp-panel": panel,
    "el-select-tree": selectTree
  },
  data() {
    return {
      formLabelWidth: "100px",
      dialogVisible: false,
      dialogLoading: false,
      userRoleDialog: false,
      rules: {
        roleName: [
          { required: true, message: "请输入角色名称", trigger: "blur" },
          { min: 2, max: 20, message: "长度在 2 到 20 个字符", trigger: "blur" }
        ],
        roleCode: [
          { required: true, message: "请输入角色名称", trigger: "blur" },
          { min: 2, max: 20, message: "长度在 2 到 20 个字符", trigger: "blur" }
        ]
      },
      form: {
        id: null,
        roleName: "",
        roleCode: "",
        sort: 0,
        isDelete: 0
      },
      defaultProps: {
        children: "children",
        label: "roleName",
        id: "id"
      },
      menuDefaultProps: {
        children: "subWocMenu",
        label: "menuName",
        id: "id"
      },
      roleTree: [],
      listLoading: true,
      resourceTree: [],
      roleUserList: [],
      listQuery: {
        page: 1,
        limit: 10,
        roleId: undefined
      },
      userTotal: 0,
      roleTotal: 0
    };
  },
  created() {
    this.fetchData();
  },
  methods: {
    fetchData() {
      getList({ page: 1, limit: 1000 }).then(response => {
        this.roleTree = [];
        this.roleTree.push(...response.data);
      });
    },
    configRoleResources() {
      let checkedKeys = this.$refs.resourceTree.getCheckedKeys();
      addResource(this.form.id, checkedKeys.join(","))
        .then(response => {
          if (response.code === 200) {
            this.dialogVisible = false;
            this.$notify({
              title: "成功",
              message: "操作成功",
              type: "success",
              duration: 2000
            });
          } else {
            this.$notify({
              title: "失败",
              message: "操作失败" + response.state.msg,
              type: "warning",
              duration: 2000
            });
          }
        })
        .catch(e => {
          console.log("操作失败", e);
        });
    },
    newAdd() {
      this.form = {
        id: null,
        roleName: "",
        roleCode: "",
        sort: 0,
        isDelete: 0
      };
    },
    renderContent(h, { node, data, store }) {
      return (
        <span>
          <span>
            <span>{node.label}</span>
          </span>
          <span class="render-content">
            <i
              class="fa fa-wrench"
              title="配置资源"
              on-click={e => this.settingResource(e, data.id)}
            />
            <i
              class="fa fa-trash"
              on-click={() => this.deleteSelected(data.id)}
            />
          </span>
        </span>
      );
    },
    settingResource(event, id) {
      event.stopPropagation();
      this.dialogVisible = true;
      if (this.resourceTree == null || this.resourceTree.length <= 0) {
        this.dialogLoading = true;
        getResourceList().then(response => {
          this.dialogLoading = false;
          this.resourceTree = [];
          this.resourceTree.push(...response.data);
        });
      }
      getListByRoleId({roleId:id})
        .then(response => {
          this.$refs.resourceTree.setCheckedKeys(
            response.data.map(item => item.id)
          );
        })
        .catch(err => {
          console.log("错误", err);
        });
    },
    roleUser(form) {
      this.userRoleDialog = true;
      this.listQuery.roleId = form.id;
      findUserByRole(this.listQuery).then(response => {
        if (response.code == 0) {
          this.roleUserList = response.data.list;
          console.log(this.roleUserList);
          this.userRoleDialog = true;
          this.userTotal = response.data.count;
        }
      });
    },
    deleteSelected() {
      deleteRole(this.form.id)
        .then(response => {
          if (response.code === 200) {
            this.deleteFromTree(this.roleTree, this.form.id);
            this.newAdd();
            this.$notify({
              title: "成功",
              message: "操作成功",
              type: "success",
              duration: 2000
            });
          } else {
            this.$notify({
              title: "失败",
              message: "操作失败" + response.state.msg,
              type: "warning",
              duration: 2000
            });
          }
        })
        .catch(e => {
          console.log("操作失败", e);
        });
    },
    batchDelete() {
      var checkKeys = this.$refs.roleTree.getCheckedKeys();
      if (checkKeys == null || checkKeys.length <= 0) {
        this.$message.warning("请选择要删除的资源");
        return;
      }
      this.$confirm("确定删除?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        deleteRole(checkKeys.join(","))
          .then(response => {
            if (response.code === 200) {
              this.fetchData();
              this.$notify({
                title: "成功",
                message: "操作成功",
                type: "success",
                duration: 2000
              });
            } else {
              this.$notify({
                title: "失败",
                message: "操作失败" + response.state.msg,
                type: "warning",
                duration: 2000
              });
            }
          })
          .catch(e => {
            this.$message("操作失败");
            console.log(checkKeys, e);
          });
      });
    },
    handleNodeClick(data) {
      this.form = merge({}, data);
    },
    onSubmit() {
      if (this.form.id == null) {
        addRole(qs.stringify(this.form))
          .then(response => {
            if (response.code === 200) {
              this.form.id = response.data.id;
              this.appendTreeNode(this.roleTree, response.data);
              this.$notify({
                title: "成功",
                message: "操作成功",
                type: "success",
                duration: 2000
              });
            } else {
              this.$notify({
                title: "失败",
                message: "操作失败" + response.msg,
                type: "warning",
                duration: 2000
              });
            }
          })
          .catch(e => {
            console.log("新增失败", e);
          });
      } else {
        updateRole(qs.stringify(this.form))
          .then(response => {
            if (response.code === 200) {
              this.form.id = response.data.id;
              this.updateTreeNode(this.roleTree, response.data);
              this.$notify({
                title: "成功",
                message: "操作成功",
                type: "success",
                duration: 2000
              });
            } else {
              this.$notify({
                title: "失败",
                message: "操作失败" + response.state.msg,
                type: "warning",
                duration: 2000
              });
            }
          })
          .catch(e => {
            console.log("更新失败", e);
          });
      }
    },
    clickCheckBox(data) {
      if (data.parentId != 0) {
        let checked = this.$refs.resourceTree.getCheckedKeys();
        if (checked.indexOf(data.parentId) == -1) {
          checked.push(data.parentId);
          this.$refs.resourceTree.setCheckedKeys(checked);
        }
      }
      let checked = this.$refs.resourceTree.getCheckedKeys();
      if (checked.indexOf(data.id) == -1) {
        data.subWocMenu.map(children => {
          this.deleteCheckBox(checked, children);
        });
      } else {
        data.subWocMenu.map(children => {
          this.addcheckBox(checked, children);
        });
      }

      this.$refs.resourceTree.setCheckedKeys(checked);

      console.log(this.$refs.resourceTree.getCheckedKeys());
    },
    deleteCheckBox(arr, children) {
      let index = arr.indexOf(children.id);
      if (index != -1) {
        arr.splice(index, 1);
        if (children.subWocMenu.length > 0) {
          children.subWocMenu.map(children => {
            this.deleteCheckBox(arr, children);
          });
        }
      }
    },
    addcheckBox(arr, children) {
      arr.push(children.id);
      if (children.subWocMenu.length > 0) {
        children.subWocMenu.map(children => {
          this.addcheckBox(arr, children);
        });
      }
    },
    pageRoleUser(val) {
      this.listQuery.page = val;
      findUserByRole(this.listQuery).then(response => {
        if (response.code == 0) {
          this.roleUserList = response.data.list;
          console.log(this.roleUserList);
          this.userRoleDialog = true;
          this.userTotal = response.data.count;
        }
      });
    }
  }
};
</script>
<style>
.render-content {
  float: right;
  margin-right: 20px;
}

.render-content i.fa {
  margin-left: 10px;
}

.render-content i.fa:hover {
  color: #e6000f;
}

.select-tree .el-scrollbar.is-empty .el-select-dropdown__list {
  padding: 0;
}

.select-tree .el-scrollbar {
  border: 1px solid #d1dbe5;
}

.select-tree .el-tree {
  border: 0;
}
</style>
