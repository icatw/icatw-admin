<template>
  <el-row type="flex" class="row-bg" justify="center">
    <el-col :xl="6" :lg="7">
      <h2>欢迎来到icatw后台管理系统</h2>
      <el-image :src="require('@/assets/icatw.jpg')" style="width: 200px;height: 200px"></el-image>
      <p>
        <el-link type="primary" href="https://gitee.com/icatw/icatw-admin">Gitee地址</el-link>
      </p>
      <p>联系方式：762188827@qq.com</p>
    </el-col>
    <el-col :span="1">
      <el-divider direction="vertical"></el-divider>
    </el-col>
    <el-col :xl="6" :lg="7">
      <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="100px" class="demo-loginForm">
        <el-form-item label="用户名" prop="username" style="width: 380px">
          <el-input v-model="loginForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" style="width: 380px">
          <el-input type="password" show-password v-model="loginForm.password"></el-input>
        </el-form-item>
        <el-form-item label="验证码" prop="code" style="width: 380px">
          <el-input v-model="loginForm.code" style="width:172px;float: left"></el-input>
          <el-image :src="captchaImg" class="captchaImg" @click="getCaptcha"></el-image>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('loginForm')">立即登录</el-button>
          <el-button @click="resetForm('loginForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<script>
import qs from "qs";

export default {
  name: "Login",
  data() {
    return {
      loginForm: {
        username: 'admin',
        password: '888888',
        code: '',
        token: ''
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        code: [
          {required: true, message: '请输入验证码', trigger: 'blur'},
          {min: 5, max: 5, message: '长度为5个字符', trigger: 'blur'}
        ],
      },
      captchaImg: ''
    };
  },
  created() {
    this.getCaptcha()
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post("/login?" + qs.stringify(this.loginForm)).then(res => {

            console.log('登陆接口=============>' + res.data)

            const jwt = res.headers['authorization']
            console.log(jwt)
            this.$store.commit('SET_TOKEN', jwt)
            this.$router.push("/index")
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    getCaptcha() {
      this.$axios.get('/captcha').then(res => {
        console.log(res)
        this.loginForm.token = res.data.data.token
        this.captchaImg = res.data.data.captchaImg
        this.loginForm.code = ''
      })
    }
  }
}
</script>

<style scoped>
.el-row {
  background-color: #fafafa;
  height: 100vh;
  display: flex;
  align-items: center;
  text-align: center;
}

.el-divider {
  height: 200px;
}

.captchaImg {
  width: 100px;
  height: 40px;
  float: left;
  margin-left: 8px;
  border-radius: 4px;
}
</style>
