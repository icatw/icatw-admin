import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Index from '../views/Index.vue'
import User from '../views/sys/User.vue'
import Role from '../views/sys/Role.vue'
import Menu from '../views/sys/Menu.vue'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home,
        children: [
            {path: '/index', name: 'Index', component: Index},
            {path: '/users', name: 'SysUser', component: User},
            {path: '/roles', name: 'SysRole', component: Role},
            {path: '/menus', name: 'SysMenu', component: Menu}
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

export default router
