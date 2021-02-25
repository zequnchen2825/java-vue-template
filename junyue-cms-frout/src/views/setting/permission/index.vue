<template>
  <div class="app-container">
    <imp-panel>
      <h3 class="box-title" slot="header" style="width: 100%;">
        <el-button type="primary" icon="plus" @click="newAdd">新增</el-button>
        <el-button type="danger" icon="delete" @click="batchDelete">删除</el-button>
        <el-input style="width: 200px;" v-model="filterText" placeholder="按权限名称搜索"></el-input>
      </h3>
      <el-row slot="body" style="margin-bottom: 20px;" :gutter="24">
        <el-col :span="6" :xs="24" :sm="24" :md="6" :lg="6" style="margin-bottom: 20px;">
          <el-tree v-if="resourceTree"
                   ref="resourceTree"
                   :data="resourceTree"
                   show-checkbox
                   highlight-current
                   :render-content="renderContent"
                   :filter-node-method="filterNode"
                   @node-click="handleNodeClick" clearable node-key="id" :props="defaultProps"></el-tree>
        </el-col>
        <el-col :span="18" :xs="24" :sm="24" :md="18" :lg="18">
          <el-card class="box-card">
            <div class="text item">
              <el-form :model="form" ref="form">
                <el-form-item label="父级" :label-width="formLabelWidth">
                  <el-select-tree v-model="form.pId" :treeData="resourceTree" :propNames="defaultProps" clearable
                                  placeholder="请选择父级">
                  </el-select-tree>
                </el-form-item>
                <el-form-item label="资源名称" :label-width="formLabelWidth">
                  <el-input v-model="form.menuName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="权限编码" :label-width="formLabelWidth">
                  <el-input v-model="form.permissionCode" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="跳转地址" :label-width="formLabelWidth">
                  <el-input v-model="form.menuUrl" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="类型" :label-width="formLabelWidth">
                  <el-radio-group v-model="form.level">
                    <el-radio class="radio" :label="1">父菜单</el-radio>
                    <el-radio class="radio" :label="2">子菜单</el-radio>
                    <el-radio class="radio" :label="3">按钮</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="是否生效" :label-width="formLabelWidth">
                  <el-radio-group v-model="form.status">
                    <el-radio class="radio" :label="1">是</el-radio>
                    <el-radio class="radio" :label="0">否</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="排序" :label-width="formLabelWidth">
                  <el-input v-model="form.orderLevel" type="number"></el-input>
                </el-form-item>
                <el-form-item label="备注" :label-width="formLabelWidth">
                  <el-input v-model="form.remark" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="" :label-width="formLabelWidth">
                  <el-button type="primary" @click="onSubmit" v-text="form.id?'修改':'新增'"></el-button>
                  <el-button type="danger" @click="deleteSelected" icon="delete" v-show="form.id && form.id!=null">删除
                  </el-button>
                  <el-button type="info" @click="resourceRole(form)" icon="el-icon-user"
                             v-show="form.id && form.id!=null">已分配角色
                  </el-button>

                </el-form-item>
              </el-form>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </imp-panel>


    <el-dialog title="已分配角色" :visible.sync="roleResourceDialog" width="40%">
      <el-table
        :data="resourceRoleList"
        v-loading.body="listLoading"
        element-loading-text="加载中"
        border
        fit
        highlight-current-row
      >
        <el-table-column label="id" align="center">
          <template slot-scope="scope">{{scope.row.id}}</template>
        </el-table-column>
        <el-table-column label="角色名称" align="center">
          <template slot-scope="scope">{{scope.row.roleName}}</template>
        </el-table-column>
        <el-table-column label="角色编码" align="center">
          <template slot-scope="scope">{{scope.row.roleCode}}</template>
        </el-table-column>
      </el-table>
      <div class="pagination-container">
        <el-pagination
          background
          @current-change="pageResourceRole"
          :current-page="listRoleQuery.page"
          :page-size="listRoleQuery.limit"
          :page-sizes="[10]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="roleTotal"
        ></el-pagination>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {
    getList,
    updateResource,
    addResource,
    deleteResource,
    findByResource,
    findRoleByResource
  } from '@/api/sysResource'
  import panel from "@/components/menu/panel.vue"
  import selectTree from "@/components/menu/selectTree.vue"
  import treeter from "@/components/menu/treeter"
  import merge from 'element-ui/src/utils/merge';
  import qs from 'qs';

  export default {
    mixins: [treeter],
    components: {
      'imp-panel': panel,
      'el-select-tree': selectTree
    },
    watch: {
      filterText(val) {
        this.$refs.resourceTree.filter(val);
        if (val.trim().length === 200) {
          var nodes = this.$refs.resourceTree.store.nodesMap
          for (var i in nodes) {
            if (nodes[i].expanded === true) {
              nodes[i].expanded = false;
            }
          }
        }
      }
    },
    data() {
      return {
        formLabelWidth: '100px',
        listLoading: false,
        form: {
          id: null,
          menuName: '',
          level: 1,
          permissionCode: '',
          menuUrl: '',
          orderLevel: 0,
          pId: '',
          status: 1,
          remark: '',
          icon: 0
        },
        defaultProps: {
          children: 'subWocMenu',
          label: 'menuName',
          id: "id",
        },
        filterText: '',
        resourceTree: [],
        resourceRoleList: [],
        userResourceDialog: false,
        roleResourceDialog: false,
        listQuery: {
          page: 1,
          limit: 10,
          resourceId: undefined
        },
        listRoleQuery: {
          page: 1,
          limit: 10,
          resourceId: undefined
        },
        userTotal: 0,
        roleTotal: 0
      }
    },
    created() {
      this.fetchData();
    },
    methods: {
      fetchData() {
        getList().then(response => {
          this.resourceTree = [];
          this.resourceTree.push(...response.data)
        });
      },
      filterNode(value, data) {
        if (!value) {
          return true;
        }
        return data.name.indexOf(value) !== -1;
      },
      newAdd() {
        this.form = {
          id: null,
          menuName: '',
          level: 1,
          permissionCode: '',
          menuUrl: '',
          orderLevel: 0,
          pId: '',
          status: 1,
          remark: '',
          icon: 0
        };
      },
      renderContent(h, {node, data, store}) {
        return (
          < span >
          < span >
          < span > {node.label} < /span>
          < /span>
          < /span>);
      },
      deleteSelected() {
        deleteResource(this.form.id).then(response => {
          if (response.code === 200) {
            this.deleteFromTree(this.resourceTree, this.form.id);
            this.newAdd();
            this.$notify({
              title: '成功',
              message: '操作成功',
              type: 'success',
              duration: 2000
            })
          } else {
            this.$notify({
              title: '失败',
              message: '操作失败' + response.state.msg,
              type: 'warning',
              duration: 2000
            })
          }
        }).catch(e => {
          console.log('操作失败', e);
        })
        this.fetchData();
      },
      batchDelete() {
        var checkKeys = this.$refs.resourceTree.getCheckedKeys();
        if (checkKeys == null || checkKeys.length <= 0) {
          this.$message.warning('请选择要删除的资源');
          return;
        }
        this.$confirm('确定删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteResource(checkKeys.join(',')).then(response => {
            if (response.code === 200) {
              this.fetchData();
              this.$notify({
                title: '成功',
                message: '操作成功',
                type: 'success',
                duration: 2000
              })
            } else {
              this.$notify({
                title: '失败',
                message: '操作失败' + response.state.msg,
                type: 'warning',
                duration: 2000
              })
            }
          }).catch(e => {
            this.$message('操作失败');
            console.log(checkKeys, e);
          })
        });

      },
      handleNodeClick(data) {
        this.form = merge({}, data);
      },
      onSubmit() {
        if (this.form.id == null) {
          addResource(qs.stringify(this.form)).then(response => {
            if (response.code === 200) {
              this.form.id = response.data.id;
              this.appendTreeNode(this.resourceTree, response.data);
              this.$notify({
                title: '成功',
                message: '操作成功',
                type: 'success',
                duration: 2000
              })
            } else {
              this.$notify({
                title: '失败',
                message: '操作失败' + response.state.msg,
                type: 'warning',
                duration: 2000
              })
            }
          }).catch(e => {
            console.log('新增失败', e);
          })
        } else {
          updateResource(qs.stringify(this.form)).then(response => {
            if (response.code === 200) {
              this.form.id = response.data.id;
              this.updateTreeNode(this.resourceTree, response.data);
              this.$notify({
                title: '成功',
                message: '操作成功',
                type: 'success',
                duration: 2000
              })
            } else {
              this.$notify({
                title: '失败',
                message: '操作失败' + response.state.msg,
                type: 'warning',
                duration: 2000
              })
            }
          }).catch(e => {
            console.log('更新失败', e);
          })
        }
        this.fetchData();
      },
       resourceRole(form) {
        this.listRoleQuery.page=1;
        this.listRoleQuery.limit=10;
        this.listRoleQuery.resourceId = form.id;
        findRoleByResource(this.listRoleQuery).then(
          response => {
            if (response.code == 200) {
              this.resourceRoleList = response.data;
              console.log(this.resourceRoleList)
              this.roleResourceDialog = true;
              this.roleTotal = response.data.count;
            }
          }
        )
      },
       pageResourceRole(val) {
        this.listRoleQuery.page = val
        findRoleByResource(this.listRoleQuery).then(
          response => {
            if (response.code == 0) {
              this.resourceRoleList = response.data.list;
              console.log(this.resourceRoleList)
              this.roleResourceDialog = true;
              this.roleTotal = response.data.count;
            }
          }
        )
      }
    }
  }
</script>
