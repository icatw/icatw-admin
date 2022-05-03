import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Index from '../views/Index.vue'
import User from '../views/sys/User.vue'
import Role from '../views/sys/Role.vue'
import Menu from '../views/sys/Menu.vue'
import axios from '../axios';
import store from "@/store";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home,
        children: [
            {path: '/index', name: 'Index', component: Index},
            {path: '/sys/users', name: 'SysUser', component: User},
            {path: '/sys/roles', name: 'SysRole', component: Role},
            {path: '/sys/menus', name: 'SysMenu', component: Menu},
            {path: '/userCenter', name: 'UserCenter', component: () => import('../views/UserCenter')},
        ]
    },
    {
        path: '/about',
        name: 'About',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
    },

    {
        path: '/login',
        name: 'Login',
        component: Login
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})
router.beforeEach((to, from, next) => {
        axios.get("/sys/menu/nav", {
            headers: {
                Authorization: localStorage.getItem("token")
            }
        }).then(res => {
            //拿到menulist
            store.commit("setMenuList", res.data.data.nav)
            //    拿到用户权限
            store.commit("setPermList", res.data.data.authoritys)
            //动态绑定路由
            let newRoutes = router.options.routes

        })
        next()
    }
)
export default router
