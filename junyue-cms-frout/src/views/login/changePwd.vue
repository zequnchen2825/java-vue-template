<template>
  <div class="app-container">
    <el-form :model="userForm" :rules="rules" ref="userForm">
      <el-form-item label="用户名" prop="pass" :label-width="formLabelWidth">
        <el-input v-model="userName" readonly="readonly"></el-input>
      </el-form-item>
      <el-form-item label="原密码" prop="pass" :label-width="formLabelWidth">
        <el-input v-model="userForm.passWord" placeholder="请输入原密码" :type="passWordType"></el-input>
        <span class="show-pwd" @click="showPwd(1)">
          <svg-icon icon-class="eye" />
        </span>
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword" :label-width="formLabelWidth">
        <el-input
          v-model="userForm.newPassword"
          placeholder="请输入新密码"
          id="newkey"
          :type="newPasswordType"
        ></el-input>
        <span class="show-pwd" @click="showPwd(2)">
          <svg-icon icon-class="eye" />
        </span>
      </el-form-item>
      <el-form-item label="重复新密码" prop="checkNewPassword" :label-width="formLabelWidth">
        <el-input
          v-model="userForm.checkNewPassword"
          placeholder="请再次输入新密码"
          id="newkey1"
          :type="checkNewPasswordType"
        ></el-input>
        <span class="show-pwd" @click="showPwd(3)">
          <svg-icon icon-class="eye" />
        </span>
      </el-form-item>
    </el-form>
    <div class="grid-content bg-purple">
      <el-button type="primary" @click="submitForm()">保存</el-button>
    </div>
  </div>
</template>

<script>
import { changePwd } from "@/api/together/sysUser";
import { mapGetters } from "vuex";
export default {
  data() {
    /*****检验两次密码是否一致***/
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.userForm.checkNewPassword !== "") {
          this.$refs.userForm.validateField("checkNewPassword");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.userForm.newPassword) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      passWordType: "password",
      newPasswordType: "password",
      checkNewPasswordType: "password",
      userForm: {
        id: undefined,        passWord: undefined,        newPassword: undefined,        checkNewPassword: undefined
      },
      rules: {
        passWord: [
          { required: true, message: "请输入密码！", trigger: "blur" }
        ],
        newPassword: [{ validator: validatePass, trigger: "blur" }],
        checkNewPassword: [{ validator: validatePass2, trigger: "blur" }]
      }
    };
  },
  computed: {
    ...mapGetters(["currentUserId", "userName"])
  },
  created() {},
  methods: {
    showPwd(value) {
      if (value === 1) {
        if (this.passWordType === "password") {
          this.passWordType = "";
        } else {
          this.passWordType = "password";
        }
      }
      if (value === 2) {
        if (this.newPasswordType === "password") {
          this.newPasswordType = "";
        } else {
          this.newPasswordType = "password";
        }
      }
      if (value === 3) {
        if (this.checkNewPasswordType === "password") {
          this.checkNewPasswordType = "";
        } else {
          this.checkNewPasswordType = "password";
        }
      }
    },
    //修改密码
    submitForm() {
      this.userForm.id = this.currentUserId;
      this.$refs["userForm"].validate(valid => {
        if (valid) {
          changePwd(this.userForm).then(response => {
            //根据状态判断是否成功
            if (response.code === 200) {
              this.$notify({
                title: "成功",
                message: "操作成功",
                type: "success",
                duration: 2000
              });
              this.logout();
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
