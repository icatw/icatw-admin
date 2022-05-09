import Vue from 'vue'
import Vuex from 'vuex'
import menus from "./modules/menus";

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        token: '',
        // currentPathName: ''
    },
    mutations: {
        SET_TOKEN: (state, token) => {
            state.token = token
            localStorage.setItem("token", token)
        },
        // setPath(state) {
        //     state.currentPathName = localStorage.getItem("currentPathName")
        // }
    },
    actions: {},
    modules: {
        menus
    }
})
