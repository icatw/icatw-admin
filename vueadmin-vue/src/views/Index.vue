<template>
  <div class="animate__animated animate__fadeIn">
    <el-card style="margin:  10px -10px;width: 100%;">
      <el-row style="margin-bottom: 10px">
        <el-collapse>
          <el-collapse-item title="后端学习" name="1">
            <div>平时学习做项目过程中一直在开发后端接口✍️，但是没有前台界面来展示出效果</div>
            <div>枯燥的后端开发让你决定自己开发界面🧐，你开始学习vue了～～</div>
          </el-collapse-item>
          <el-collapse-item title="vue学习" name="2">
            <div>vue 端学习一直顺风顺水🤗，直到你决定要自己去搭建后台...</div>
            <div>你会发现页面端布局、侧边菜单的布置、顶部历史菜单的设计 让你抱头痛哭😭，你开始转向了开源项目...</div>
          </el-collapse-item>
          <el-collapse-item title="开源项目学习" name="3">
            <div>看着开源项目的预览，真的棒，我要用！！！👍</div>
            <div>但是问题页随之而来 npm install Error是什么鬼👀？？ 经历了千辛万苦终于把依赖拉下来了！！😄</div>
            <div>这代码好多啊！这么多js！封装这么严重！看不懂啊🤔！！！ 最终你选择去寻找简洁版的后台管理界面！🥺</div>
          </el-collapse-item>
          <el-collapse-item title="icatwの后台管理" name="4">
            <div>是的，我想说的就是 我就是你要找的简洁版后台管理界面😎</div>
            <div>我已经搭建好了侧边栏以及顶部历史菜单标签。 剩下了由你来设计🤠</div>
            <div>如果我的项目可以帮到你，请点亮一个小小的star 🌟🌟</div>
            <el-link href="https://gitee.com/icatw/icatw-admin">项目源码</el-link>
          </el-collapse-item>
        </el-collapse>
      </el-row>
      <el-descriptions class="margin-top" title="当前用户信息" :column="3" size="medium" border>
        <el-descriptions-item>
          <template slot="label"><i class="el-icon-user"></i>头像</template>
          <el-image
              style="width: 48px; height: 48px;border-radius: 50%"
              :src="userInfo.avatar"
          >
          </el-image>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label"><i class="el-icon-user"></i>用户名</template>
          {{ userInfo.username }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>手机号
          </template>
          {{ userInfo.phone }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-message"></i>邮箱
          </template>
          {{ userInfo.email }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-place"></i>地区
          </template>
          {{ userInfo.city }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">个人简介</template>
          梦想是成为一个浪漫的程序员~
        </el-descriptions-item>
      </el-descriptions>
      <el-row style="margin-top: 10px" :gutter="20">
        <el-col :span="8">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>后端技术</span>
            </div>
            <div>· SpringBoot</div>
            <div>· SpringSecurity</div>
            <div>· MybatisPlus</div>
            <div>· JWT</div>
            <div>· Redis缓存</div>
            <div>· 阿里云oss</div>
            <div>· hutool工具类</div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>前端技术</span>
            </div>
            <div>· HTML、CSS、JS三件套</div>
            <div>· vue 2.*</div>
            <div>· Element UI</div>
            <div>· Axios</div>
            <div>· Echarts</div>
            <div>· qs</div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>开发环境</span>
            </div>
            <div>· JDK 8</div>
            <div>· MySql 8.*</div>
            <div>· Maven 3.6.3</div>
            <div>· Node 16.13.0</div>
            <div>· IDEA</div>
          </el-card>
        </el-col>
      </el-row>

    </el-card>

    <el-card class="box-card" :body-style="{ padding: '10px'}">
      <template #header>
        <div class="card-header">
          <span>访问量统计</span>
        </div>
      </template>
      <div id="chart" class="main_container"></div>
    </el-card>

  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      userInfo: {}
    }
  },
  mounted() {
    this.initCharts()
  },
  created() {
    this.getUserInfo()
  },
  methods: {
    initCharts() {
      // 初始化echarts实例
      var myChart = this.$echarts.init(document.getElementById("chart"))
      // 绘制图表
      myChart.setOption({
        title: {
          text: "最近一周访问量统计",
        },
        tooltip: {},
        xAxis: {
          data: ['A', 'B', 'C', 'D', 'E', 'F']
        },
        yAxis: {},
        series: [
          {
            data: [10, 22, 28, 23, 19, 20],
            type: 'line',
            label: {
              show: false,
              position: 'bottom',
              textStyle: {
                fontSize: 20
              }
            },
            lineStyle: {
              normal: {
                color: '#048CCE',
                width: 4,
                // type: 'dashed'
              }
            }
          }]
      })
    },
    getUserInfo() {
      this.$axios.get("sys/user/getCurrentUser").then(res => {
        console.log(res)
        this.userInfo = res.data.data
      })
    }
  }
}
</script>

<style scoped>
.main_container {
  width: 100%;
  height: 300px;
  overflow: hidden;
}
</style>
