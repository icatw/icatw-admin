<template>
  <el-card class="main-card">
    <el-tabs v-model="activeName">
      <!-- 修改信息 -->
      <el-tab-pane label="修改信息" name="info">
        <div class="info-container">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:8081/upload"
              :show-file-list="false"
              :on-success="updateAvatar"
          >
            <img v-if="infoForm.avatar" :src="infoForm.avatar" class="avatar"/>
            <i v-else class="el-icon-plus avatar-uploader-icon"/>
          </el-upload>
          <el-form
              label-width="70px"
              :model="infoForm"
              style="width:320px;margin-left:3rem"
          >
            <el-form-item label="用户名">
              <el-input v-model="infoForm.username" size="small"/>
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="infoForm.email" size="small"/>
            </el-form-item>
            <el-form-item label="手机号">
              <el-input v-model="infoForm.phone" size="small"/>
            </el-form-item>
            <el-button
                @click="updateInfo"
                type="primary"
                size="medium"
                style="margin-left:4.375rem"
            >
              修改
            </el-button>
          </el-form>
        </div>
      </el-tab-pane>
      <!-- 修改密码 -->
      <el-tab-pane label="修改密码" name="password">
        <el-form label-width="70px" :model="passwordForm" style="width:320px">
          <el-form-item label="旧密码">
            <el-input
                @keyup.enter.native="updatePassword"
                v-model="passwordForm.oldPassword"
                size="small"
                show-password
            />
          </el-form-item>
          <el-form-item label="新密码">
            <el-input
                @keyup.enter.native="updatePassword"
                v-model="passwordForm.newPassword"
                size="small"
                show-password
            />
          </el-form-item>
          <el-form-item label="确认密码">
            <el-input
                @keyup.enter.native="updatePassword"
                v-model="passwordForm.confirmPassword"
                size="small"
                show-password
            />
          </el-form-item>
          <el-button
              type="primary"
              size="medium"
              style="margin-left:4.4rem"
              @click="updatePassword"
          >
            修改
          </el-button>
        </el-form>
      </el-tab-pane>
    </el-tabs>
  </el-card>
</template>

<script>
export default {
  data: function () {
    return {
      infoForm: {
        username: 'icatw',
        email: '762188827@qq.com',
        phone: '17374630398',
        avatar: ''
      },
      passwordForm: {
        oldPassword: "",
        newPassword: "",
        confirmPassword: ""
      },
      activeName: "info"
    };
  },
  created() {
    this.getUserInfo()
  },
  methods: {
    getUserInfo() {
      this.$axios.get('/sys/user/getCurrentUser').then(res => {
        console.log(res)
        this.infoForm = res.data.data
      })
    },
    updateAvatar(response) {
      console.log(response)
      if (response.code == 200) {
        this.$message.success("头像上传成功！");
        this.infoForm.avatar = response.data
      } else {
        this.$message.error(response.msg);
      }
    },
    updateInfo() {
      if (this.infoForm.username.trim() == "") {
        this.$message.error("用户名不能为空");
        return false;
      }
      this.$axios.post('/sys/user/update', JSON.stringify(this.infoForm))
          .then(res => {
            console.log(res.data)
            this.$message({
              showClose: true,
              message: '恭喜你，修改成功',
              type: 'success',
              onClose: () => {
                this.getUserInfo()
              }
            });
          })
    },
    updatePassword() {
      if (this.passwordForm.oldPassword.trim() == "") {
        this.$message.error("旧密码不能为空");
        return false;
      }
      if (this.passwordForm.newPassword.trim() == "") {
        this.$message.error("新密码不能为空");
        return false;
      }
      if (this.passwordForm.newPassword.length < 6) {
        this.$message.error("新密码不能少于6位");
        return false;
      }
      if (this.passwordForm.newPassword != this.passwordForm.confirmPassword) {
        this.$message.error("两次密码输入不一致");
        return false;
      }
      this.axios
          .put("/api/admin/users/password", this.passwordForm)
          .then(({data}) => {
            if (data.flag) {
              this.passwordForm.oldPassword = "";
              this.passwordForm.newPassword = "";
              this.passwordForm.confirmPassword = "";
              this.$message.success(data.message);
            } else {
              this.$message.error(data.message);
            }
          });
    }
  }
}
;
</script>

<style scoped>
.avatar-container {
  text-align: center;
}

.el-icon-message-solid {
  color: #f56c6c;
  margin-right: 0.3rem;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}

.avatar {
  width: 120px;
  height: 120px;
  display: block;
}

.info-container {
  display: flex;
  align-items: center;
  margin-left: 20%;
  margin-top: 5rem;
}
</style>
