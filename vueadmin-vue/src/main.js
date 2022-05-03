import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Element from "element-ui"
import "element-ui/lib/theme-chalk/index.css"
import axios from './axios'

require("./mock") //引入mock数据，关闭则注释该行

Vue.prototype.$axios = axios

Vue.config.productionTip = false
Vue.use(Element)
new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')

import Router from 'vue-router'

const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}
