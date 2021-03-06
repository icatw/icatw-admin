import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Index from '../views/Index.vue'
import axios from '../axios';
import store from "@/store";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home,
        children: [
            {path: '/index', name: 'Index', meta: {title: "首页"}, component: Index},
            // {path: '/sys/users', name: 'SysUser', component: User},
            // {path: '/sys/roles', name: 'SysRole', component: Role},
            // {path: '/sys/menus', name: 'SysMenu', component: Menu},
            {
                path: '/userCenter',
                name: 'UserCenter',
                meta: {title: "个人中心"},
                component: () => import('../views/UserCenter')
            },
        ]
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
        //
        // localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称，为了在Header组件中去使用
        // store.commit("setPath")  // 触发store的数据更新

        let hasRoute = store.state.menus.hasRoute
        let token = localStorage.getItem("token")
        if (to.path == '/login') {
            next()
        } else if (!token) {
            next({path: '/login'})
        } else if (token && !hasRoute) {
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
                res.data.data.nav.forEach(menu => {
                    if (menu.children) {
                        menu.children.forEach(e => {
                            //转换路由
                            let route = menuToRoute(e)
                            //    把路由添加到路由管理中
                            if (route) {
                                newRoutes[0].children.push(route)
                            }
                        })
                    }
                })
                router.addRoutes(newRoutes)
                hasRoute = true
                store.commit("changeRouteStatus", hasRoute)
            })
        }

        next()
    }
)
//导航转成路由
const menuToRoute = (menu) => {
    if (!menu.component) {
        return null
    }
    let route = {
        name: menu.name,
        path: menu.path,
        meta: {
            icon: menu.icon,
            title: menu.title
        }
    }
    route.component = () => import('../views/' + menu.component + '.vue')
    return route
}
export default router
