<template>
    <div class="app-container">
        <div class="filter-container">
            <el-input class="filter-item" style="width: 200px;" v-model="listQuery.userName" placeholder="管理用户名"></el-input>
            <el-input class="filter-item" style="width: 200px;" v-model="listQuery.id" placeholder="管理用户ID"></el-input>
            <el-input class="filter-item" style="width: 200px;" v-model="listQuery.realName" placeholder="管理用户真实姓名"></el-input>
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
            >分配</el-button>
            <el-button
                class="filter-item"
                :style="'width: 120px;display:'+buttonController[0].display"
                type="primary"
                icon="el-icon-plus"
                @click="handleBetchCreate"
            >批量新建</el-button>
            <el-button
                class="filter-item"
                :style="'width: 120px;display:'+buttonController[1].display"
                type="primary"
                icon="el-icon-close"
                @click="handleBetchDel"
            >批量禁用</el-button>
        </div>

        <el-table
            :data="list"
            v-loading.body="listLoading"
            element-loading-text="加载中"
            border
            fit
            highlight-current-row
        >
            <el-table-column label="用户ID" align="center">
                <template slot-scope="scope">{{scope.row.id}}</template>
            </el-table-column>
            <el-table-column label="登录名" align="center">
                <template slot-scope="scope">{{scope.row.name}}</template>
            </el-table-column>
            <el-table-column label="真实名字" align="center">
                <template slot-scope="scope">{{scope.row.displayName}}</template>
            </el-table-column>
            <el-table-column label="邮箱" align="center">
                <template slot-scope="scope">{{scope.row.emailAddress}}</template>
            </el-table-column>
            <el-table-column label="状态" align="center">
                <template slot-scope="scope"> {{scope.row.active ? '启用' : '禁用'}}</template>
            </el-table-column>
            <el-table-column label="创建时间" align="center" width="160px">
                <template slot-scope="scope">{{scope.row.createTime | dateFormat }}</template>
            </el-table-column>

            <el-table-column label="修改时间" align="center" width="160px">
                <template slot-scope="scope">{{scope.row.updateTime | dateFormat }}</template>
            </el-table-column>

            <el-table-column label="操作" align="center" width="350px">
                <template slot-scope="scope">
                    <el-button type="success" size="mini" @click="checkMenu(scope.row)">菜单查看</el-button>

                    <el-button type="info" size="mini" @click="handleSysRole(scope.row)">角色配置</el-button>

                    <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div class="pagination-container">
            <el-pagination
                background
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="listQuery.page"
                :page-sizes="[10,20,30,50]"
                :page-size="listQuery.limit"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total"
            ></el-pagination>
        </div>

        <el-dialog title='添加'  :visible.sync="sysUserFormDialog" :close-on-click-modal=false :close-on-press-escape=false  width="30%">
            <el-form
                ref="dataForm"
                :model="entity"
                :rules="rules"
                label-position="right"
                label-width="100px"
            >
                <el-form-item label="登录名:" prop="userName">
                    <el-select v-model="value" filterable placeholder="请选择">
                        <el-option
                            v-for="item in users"
                            :key="item.name"
                            :label="item.displayName+(item.name)"
                            :value="item.name"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>

            </el-form>
            <div slot="footer">
                <el-button v-if="entity.id == undefined" type="primary" @click="createData">添加</el-button>
                <el-button @click="sysUserFormDialog=false">取消</el-button>
            </div>
        </el-dialog>
        <el-dialog title='批量添加'  :visible.sync="batchsysUserFormDialog" :close-on-click-modal=false :close-on-press-escape=false  width="30%">

            <el-form
                ref="dataForm"
                :model="dataForm"
                :rules="rules"
                label-position="right"
                label-width="60px"

            >
                <p style="color: red ;text-align:center">注意:批量新增账户初始密码为:qlchat-加上用户名;如 账户:admin 密码:qlchat-admin</p>
                <p style="color: red ;text-align:center">用户名格式：名拼音.姓拼音 如张三 san.zhang</p>
                <el-form-item label="角色:" prop="sysRoleIds">
                    <el-select v-model="dataForm.sysRoleIds" multiple placeholder="请选择">
                        <el-option
                            v-for="role in allRole"
                            :label="role.label"
                            :key="role.key"
                            :value="role.key"
                        >{{role.label}}
                        </el-option>
                    </el-select>
                </el-form-item>




                <el-row
                    v-for="(num, index) in userNum"
                    :key="num">
                    <el-col :span="12">
                        <el-form-item label="账号:" :prop="`userNames[${index}]`" :rules="[
              { validator: checkName, trigger: 'blur' }
            ]">
                            <el-input v-model="dataForm.userNames[num-1]" placeholder="请输入账号"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="姓名:" :prop="`userRealNames[${index}]`" :rules="[
              { validator: checkRealName, trigger: 'blur' }
            ]">
                            <el-input v-model="dataForm.userRealNames[num-1]" placeholder="请输入姓名"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-form-item >
                    <el-button type="primary" icon="el-icon-plus" size="mini" @click="userNum++"></el-button>
                    <el-button type="primary" icon="el-icon-minus" size="mini" @click="userNum >1?userNum--:userNum ;dataForm.userNames[userNum]='';dataForm.userRealNames[userNum]=''"></el-button>
                </el-form-item>



            </el-form>
            <div slot="footer">
                <el-button v-if="entity.id == undefined" type="primary" @click="createBetchData">添加</el-button>
                <el-button @click="batchsysUserFormDialog=false">取消</el-button>
            </div>
        </el-dialog>
        <el-dialog title="批量禁用" :visible.sync="batchDelsysUserFormDialog" :close-on-click-modal=false :close-on-press-escape=false  width="50%">
            <el-form
                ref="dataForm"
                :model="dataForm"
                :rules="rules"
                label-position="right"
                label-width="60px"

            >
                <p style="color: red ;text-align:center">注意:禁用用户非简单删除本地用户 是将远程corwd用户禁用后禁用本地用户！！慎用</p>

                <el-input
                    type="textarea"
                    :rows="5"
                    placeholder="请输入禁用用户名,逗号隔开;如test1,test2"
                    v-model="dataForm.delBetchUserNames">
                </el-input>
            </el-form>
            <div slot="footer">
                <el-button v-if="entity.id == undefined" type="primary" @click="delBetchData">禁用</el-button>
                <el-button @click="batchDelsysUserFormDialog=false">取消</el-button>
            </div>

        </el-dialog>
        <el-dialog title="角色配置" :visible.sync="userRoleDialog" :close-on-click-modal=false :close-on-press-escape=false  width="50%">

            <!-- <el-checkbox-group v-model="sysRoleIds">
              <div style="margin: 15px 0;"></div>
              <el-checkbox
                v-for="role in allRole"
                :label="role.key"
                :key="role.key"
              >{{role.label}}</el-checkbox>
            </el-checkbox-group> -->
            <div class="select-tree">
                <el-scrollbar
                    tag="div"
                    class='is-empty'
                    wrap-class="el-select-dropdown__wrap"
                    view-class="el-select-dropdown__list">
                    <el-tree
                        :data="allRole"
                        ref="allRole"
                        show-checkbox
                        node-key="key"
                        :default-checked-keys="sysRoleIds"
                        :props="roleProps">
                    </el-tree>
                </el-scrollbar>
            </div>


            <div slot="footer">
                <el-button v-model="checkAll" @change="handleCheckAll">全选</el-button>
                <el-button type="primary" @click="handleSysUserRole">保存</el-button>
                <el-button @click="userRoleDialog=false">取消</el-button>
            </div>
        </el-dialog>
        <el-dialog title="结果" :visible.sync="resultDialog" width="20%">
            <dl v-html="result">
                {{result}}
            </dl>
        </el-dialog>
        <el-dialog title="菜单" :visible.sync="menuDialog" width="20%">
            <el-tree
                :data="userMenu"
                :props ="defaultProps"
                accordion>
            </el-tree>
        </el-dialog>
    </div>
</template>

<script>
    import {
        getList,
        updateSysUser,
        addSysUser,
        deleteSysUser,
        findAllUser,
        batchAddUser,
        batchDeleteUser
    } from "@/api/together/sysUser"
    import {
        getList as sysRoleList
    } from "@/api/together/sysRole"
    import {
        getList as sysUserRoleList,
        addSysUserRole
    } from "@/api/together/sysUserRole"
    import {
        getList as menuList
    } from "@/api/together/menu"
    import moment from "moment"
    import {mapGetters} from 'vuex'
    import store from '@/store'
    export default {
        data() {


            return {
                 checkName: (rule, value, callback) => {
                    if (!value) {
                        return callback(new Error('请输入用户名'));
                    }
                    var reg = /^[a-z]{2,13}\.[a-z]{2,10}$/;
                    if (!reg.test(value)) {
                        return callback(new Error('用户名格式有误'));
                    }
                },
                checkRealName: (rule, value, callback) => {
                    if (!value) {
                        return callback(new Error('请输入姓名'));
                    }
                },
                buttonController:[
                    {
                        code: '/setting/sysUser/batchAddButton',
                        display : "none"
                    },
                    {
                        code: '/setting/sysUser/batchDeleteButton',
                        display : "none"
                    }

                ],
                list: [],
                listLoading: true,
                total: null,
                listQuery: {
                    page: 1,
                    limit: 20,
                    userName: undefined,
                    id: undefined,
                    realName: undefined
                },
                sysUserFormDialog: false,
                batchsysUserFormDialog: false,
                batchDelsysUserFormDialog: false,
                menuDialog: false,
                resultDialog: false,
                entity: {
                    id: undefined,        userName: undefined,        password: undefined,        realName: undefined,        mail: undefined,        status: undefined,        createTime: undefined,        createBy: undefined,        updateTime: undefined,        updateBy: undefined
                },
                loadingOptions: {
                    text: "Loading",
                    spinner: "el-icon-loading",
                    background: "rgba(0, 0, 0, 0.7)"
                },
                rules: {
                    sysRoleIds: [
                        { type: 'array',required: true, message: '至少选择一个角色', trigger: 'change' }
                    ]
                },
                checkAll: false,
                userRoleDialog: false,
                userId: undefined,      sysRoleIds: [],
                allRole: [],
                users:[],
                value:null,
                userName :null,
                userNum: 1,

                dataForm:{
                    sysRoleIds: [],
                    userNames:[],
                    userRealNames:[],
                    delBetchUserNames:undefined
                },
                result:undefined,
                userMenu:[],
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                roleProps: {
                    children: 'children',
                    label: 'label'
                },

            };
        },
        filters: {
            statusFilter(status) {
                const statusMap = {
                    Y: "success",
                    N: "danger"
                };
                return statusMap[status];
            },
            dateFormat(date) {
                if (date) {
                    return moment(date).format("YYYY-MM-DD HH:mm:ss");
                }
            }
        },
        computed: {
            ...mapGetters([
                "getButton"
            ])
        },
        created() {
            this.fetchData()
            this.fetchSysRole()
        },
        methods: {
            fetchData() {
                let button = this.getButton;

                for(var i = 0 ; i < this.buttonController.length ; i++){
                    for(var j =0 ; j <button.length ; j++){
                        if(this.buttonController[i].code ==  button[j].code){
                            this.buttonController[i].display ="";
                            break;
                        }
                    }
                }

                this.listLoading = true;
                getList(this.listQuery).then(response => {
                    if (response.data == null) {
                        this.listLoading = false;
                    } else {
                        this.list = response.data.list;
                        this.listLoading = false;
                        this.total = response.data.count;
                    }
                });
            },
            fetchSysRole() {
                sysRoleList({page: 1, limit: 1000}).then(response => {
                    this.allRole = response.data.list.map(item => ({label: item.name, key: item.id}))
                });
            },
            handleCheckAll() {
                if (this.checkAll) {
                    this.sysRoleIds = [];
                    this.sysRoleIds = this.sysRoleIds.concat(
                        this.allRole.map(i => i.key)
                    )
                } else {
                    this.sysRoleIds = [];
                }
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
                    id: undefined,        userName: undefined,        password: undefined,        realName: undefined,        mail: undefined,        status: undefined,        createTime: undefined,        createBy: undefined,        updateTime: undefined,        updateBy: undefined
                };
            },
            handleCreate() {
                findAllUser().then(response =>{
                    this.value = null;
                    if(response.code == 0){

                        this.users = response.data;
                        this.resetEntity();
                        this.sysUserFormDialog = true;
                        this.$nextTick(() => {
                            this.$refs["dataForm"].clearValidate();
                        })
                    }


                });

            },
            handleBetchCreate(){
                this.userNum = 1;
                this.dataForm.sysRoleIds= [];
                this.dataForm.userNames = [];
                this.dataForm.userRealNames =[];
                this.batchsysUserFormDialog = true;
            },
            handleBetchDel(){
                this.dataForm.delBetchUserNames = undefined;
                this.batchDelsysUserFormDialog = true;
            },
            createBetchData(){
                debugger
                let roleIds = this.dataForm.sysRoleIds.join(",");
                let userNames = this.dataForm.userNames.join(",");
                let userRealNames = this.dataForm.userRealNames.join(",");

                batchAddUser({userNames:userNames,realnames:userRealNames,roleIds:roleIds}).then(response => {
                    //根据状态判断是否成功
                    if (response.code === 200) {
                        this.fetchData();
                        let result = response.data;
                        let msg = "<p>成功名单:</p>";
                        for(var i = 0 ; i <result.success.length ; i++){
                            msg+="<p>"+result.success[i]+"</p>";
                        }
                        msg+="<p>失败名单：</p>"
                        for(var i = 0 ; i <result.fail.length ; i++){
                            msg+="<p>"+result.fail[i].userName+",原因："+result.fail[i].reason+"</p>";
                        }
                        // this.$alert(msg,"结果", {
                        //   dangerouslyUseHTMLString: true
                        // });
                        this.result = msg;
                        this.resultDialog = true;
                        this.$notify({
                            title: "成功",
                            message: "操作成功",
                            type: "success",
                            duration: 2000
                        });
                    } else {
                        this.$notify({
                            title: "失败",
                            message: "操作失败",
                            type: "error",
                            duration: 2000
                        });
                    }
                    loading.close();
                    this.batchDelsysUserFormDialog = false
                }).catch(e => {
                    loading.close();
                    console.log("操作失败", e);
                });



            },
            delBetchData(){
                this.$refs["dataForm"].validate(valid => {
                    if (valid) {

                        const loading = this.$loading(this.loadingOptions);
                        batchDeleteUser({userNames:this.dataForm.delBetchUserNames}).then(response => {
                            //根据状态判断是否成功
                            if (response.code === 200) {
                                this.fetchData();
                                let result = response.data;
                                let msg = "<p>成功名单:</p>";
                                for(var i = 0 ; i <result.success.length ; i++){
                                    msg+="<p>"+result.success[i]+"</p>";
                                }
                                msg+="<p>失败名单：</p>"
                                for(var i = 0 ; i <result.fail.length ; i++){
                                    msg+="<p>"+result.fail[i].userName+",原因："+result.fail[i].reason+"</p>";
                                }
                                // this.$alert(msg,"结果", {
                                //   dangerouslyUseHTMLString: true,
                                //   lockScroll:false
                                // });
                                this.result = msg;
                                this.resultDialog = true;
                                this.$notify({
                                    title: "成功",
                                    message: "操作成功",
                                    type: "success",
                                    duration: 2000
                                });
                            } else {
                                this.$notify({
                                    title: "失败",
                                    message: "操作失败",
                                    type: "error",
                                    duration: 2000
                                });
                            }
                            loading.close();
                            this.batchDelsysUserFormDialog = false
                        }).catch(e => {
                            loading.close();
                            console.log("操作失败", e);
                        });
                    }
                })

            },
            createData() {
                this.$refs["dataForm"].validate(valid => {
                    if (valid) {
                        const loading = this.$loading(this.loadingOptions);
                        addSysUser(this.value)
                            .then(response => {
                                //根据状态判断是否成功
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
                                        message: "操作失败",
                                        type: "error",
                                        duration: 2000
                                    });
                                }
                                loading.close();
                            })
                            .catch(e => {
                                loading.close();
                                console.log("操作失败", e);
                            });
                        this.sysUserFormDialog = false;
                    }
                });
            },
            handleUpdate(row) {
                this.entity = Object.assign({}, row);
                this.sysUserFormDialog = true;
                this.$nextTick(() => {
                    this.$refs["dataForm"].clearValidate();
                });
            },
            updateData() {
                this.$refs["dataForm"].validate(valid => {
                    if (valid) {
                        const loading = this.$loading(this.loadingOptions);
                        updateSysUser(this.entity)
                            .then(response => {
                                //根据状态判断是否成功
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
                                        message: "操作失败",
                                        type: "error",
                                        duration: 2000
                                    });
                                }
                                loading.close();
                            })
                            .catch(e => {
                                loading.close();
                                console.log("操作失败", e);
                            });
                        this.sysUserFormDialog = false;
                    }
                });
            },
            handleDelete(row) {
                this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(() => {
                    const loading = this.$loading(this.loadingOptions);
                    deleteSysUser(row.name)
                        .then(response => {
                            //根据状态判断是否成功
                            if (response.code === 200) {
                                var delIndex = "";
                                this.list.map((item, index) => {
                                    if (item.name == row.name) {
                                        delIndex = index;
                                    }
                                });
                                this.list.splice(delIndex, 1);
                                this.$notify({
                                    title: "成功",
                                    message: "操作成功",
                                    type: "success",
                                    duration: 2000
                                });
                            } else {
                                this.$notify({
                                    title: "失败",
                                    message: "操作失败",
                                    type: "error",
                                    duration: 2000
                                });
                            }
                            loading.close();
                        })
                        .catch(e => {
                            loading.close();
                            console.log("操作失败", e);
                        });
                });
            },
            handleSysRole(row) {
                this.checkAll = false;
                this.userRoleDialog = true;
                this.userId = row.id;
                this.userName = row.name;
                this.$refs.allRole&&this.$refs.allRole.setCheckedKeys([]);
                //this.$refs.allRole.setCheckedKeys([]);
                sysUserRoleList({userName: row.name }).then(
                    response => {
                        this.sysRoleIds = []
                        for (var idx in response.data.list) {
                            let item = response.data.list[idx];
                            this.sysRoleIds.push(item.roleId);
                        }
                    }
                );
            },
            handleSysUserRole() {
                let checkedKeys = this.$refs.allRole.getCheckedKeys();
                addSysUserRole({
                    userName: this.userName,
                    roleIds: checkedKeys.join(',')
                })
                    .then(response => {
                        //根据状态判断是否成功
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
                                message: "操作失败",
                                type: "error",
                                duration: 2000
                            });
                        }
                    })
                    .catch(e => {
                        console.log("操作失败", e);
                    });
                this.userRoleDialog = false;
            },
            checkMenu(row){
                const loading = this.$loading(this.loadingOptions);
                const menus = store.state.user.menus;
                debugger;
                menuList({userName:row.name}).then(response =>{
                    this.menuDialog = true
                    if(response.code == 0){
                        this.userMenu = response.data.list;
                    }
                    loading.close();
                }).catch(e => {
                    loading.close();
                    console.log("操作失败", e);
                });
            }
        }
    };
</script>
<style>
    .render-content {
        float: right;
        margin-right: 20px
    }

    .render-content i.fa {
        margin-left: 10px;
    }

    .render-content i.fa:hover{
        color: #e6000f;
    }

    .select-tree .el-scrollbar.is-empty .el-select-dropdown__list {
        padding: 0;
    }

    .select-tree .el-scrollbar {
        border: 1px solid #d1dbe5;
    }

    .select-tree .el-tree{
        border:0;
    }
</style>
