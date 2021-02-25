<template>
  <div class="app-container">

    <el-form :model="userForm" :rules="rules" ref="userForm">

        <el-form-item label="用户ID" prop="id" :label-width="formLabelWidth">
            <el-input v-model="userForm.id" readonly="readonly"></el-input>
        </el-form-item>

      <el-form-item label="用户名" prop="userName" :label-width="formLabelWidth">
        <el-input v-model="userName" readonly="readonly"></el-input>
      </el-form-item>

        <el-form-item label="用户真实姓名" prop="realName" :label-width="formLabelWidth">
            <el-input v-model="userForm.realName" readonly="readonly"></el-input>
        </el-form-item>


        <el-form-item label="用户昵称" prop="nickName" :label-width="formLabelWidth">
            <el-input v-model="userForm.nickName"></el-input>
        </el-form-item>
    </el-form>
    <div class="grid-content bg-purple">
      <el-button type="primary" @click="submitForm()">保存</el-button>
    </div>
  </div>
</template>

<script>
import { updateUser,getUserInfo } from "@/api/together/sysUser";
import { mapGetters } from "vuex";
export default {
  data() {
    return {
      userForm: {
        id: undefined,        userName: undefined,        realName: undefined,        nickName: undefined
      },
      rules: {
          nickName: [
          { required: true, message: "请输入用户呢称！", trigger: "blur" }
        ]
      }
    };
  },
  computed: {
    ...mapGetters(["currentUserId", "userName"])
  },
  created() {
      this.userForm.id = this.currentUserId;
      getUserInfo({"userId":this.userForm.id}).then(response => {
          this.userForm = response.data;
          console.info(response.data)
      });
      
  },
  methods: {
    //修改密码
    submitForm() {
      this.userForm.id = this.currentUserId;
      this.$refs["userForm"].validate(valid => {
        if (valid) {
            updateUser(this.userForm).then(response => {
            //根据状态判断是否成功
            if (response.code === 200) {
              this.$notify({
                title: "成功",
                message: "操作成功",
                type: "success",
                duration: 2000
              });
              // this.logout();
            } else {
              this.$notify({
                title: "失败",
                message: "操作失败",
                type: "success",
                duration: 2000
              });
            }
          });
        } else {
          this.$message.error("-_- 验证失败！");
        }
      });
    },
    logout() {
      this.$store.dispatch("LogOut").then(() => {
        location.reload(); // 为了重新实例化vue-router对象 避免bug
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;
.show-pwd {
  position: absolute;
  right: 10px;
  top: 7px;
  font-size: 16px;
  color: $dark_gray;
  cursor: pointer;
  user-select: none;
}
</style>
