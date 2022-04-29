<template>
  <el-container>
    <el-aside width="200px">
      <SideMenu></SideMenu>
    </el-aside>
    <el-container>

      <el-header>
        <strong>VueAdmin后台管理系统</strong>
        <div class="header-avatar">
          <el-avatar size="medium"
                     src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
          <el-dropdown>
            <span class="el-dropdown-link">
            Admin<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>个人中心</el-dropdown-item>
              <el-dropdown-item>退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-link href="http://www.icatw.top" target="_blank">icatwの博客</el-link>
          <el-link href="https://gitee.com/icatw" target="_blank">Gitee</el-link>
        </div>
      </el-header>

      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
// @ is an alias to /src
import SideMenu from "@/components/SideMenu";

export default {
  name: "Home",
  components: {
    SideMenu
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
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  line-height: 160px;
}


.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}

.el-icon-arrow-down {
  font-size: 12px;
}

</style>
