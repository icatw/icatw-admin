<template>
  <el-container>
    <el-aside width="160px">
      <SideMenu></SideMenu>
    </el-aside>
    <el-container>

      <el-header>
        <strong>icatwの后台管理系统</strong>
        <div class="header-avatar">
          <el-avatar size="medium"
                     :src="userInfo.avatar"></el-avatar>
          <el-dropdown>
            <span class="el-dropdown-link">
            {{ userInfo.username }}<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <router-link to="/userCenter">个人中心</router-link>
              </el-dropdown-item>
              <el-dropdown-item @click.native="logout">退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-link href="http://www.icatw.top" target="_blank">icatwの博客</el-link>
          <el-link href="https://gitee.com/icatw" target="_blank">Gitee</el-link>
        </div>
      </el-header>

      <el-main>
        <!--        面包屑导航-->
        <el-breadcrumb
            separator-class="el-icon-arrow-right"
            v-if="this.$router.currentRoute.path != '/index'">
          <el-breadcrumb-item :to="{ path: '/index' }">首页
          </el-breadcrumb-item>
          <el-breadcrumb-item>
            {{ this.$router.currentRoute.meta.title }}
          </el-breadcrumb-item>
        </el-breadcrumb>
        <!--        面包屑导航-->
        <Tabs></Tabs>
        <div style="margin: 0 15px;">
          <router-view></router-view>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
// @ is an alias to /src
import SideMenu from "@/components/SideMenu";
import avatar from "element-ui/packages/avatar";
import Tabs from "@/components/Tabs"

export default {
  name: "Home",
  components: {
    SideMenu, Tabs
  },
  data() {
    return {
      userInfo: {
        id: "",
        username: "icatw",
        avatar: ""
      }
    }
  },
  created() {
    this.getUserInfo()
  },
  methods: {
    getUserInfo() {
      this.$axios.get("/sys/userInfo").then(res => {
        console.log(res)
        this.userInfo = res.data.data;
      })
    },
    logout() {
      this.$axios.post("/logout").then(res => {
        localStorage.clear()
        sessionStorage.clear()
        this.$store.commit("resetState")
        this.$router.push('/login')
      })
    }
  }
}
</script>
<style scoped>
.el-menu-vertical-demo {
  height: 100%;
}

.header-avatar {
  float: right;
  width: 250px;
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.el-container {
  padding: 0;
  margin: 0;
  height: 100%;
}

.el-header {
  background-color: #64c5ee;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  line-height: 200px;
}

.el-main {
  /*background-color: #E9EEF3;*/
  color: #333;
  padding: 0 !important;
}


.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}

.el-icon-arrow-down {
  font-size: 12px;
}

a {
  text-decoration: none;
}

.el-breadcrumb {
  margin-top:20px;
  margin-bottom:20px;
  margin-left: 20px;
}

</style>
