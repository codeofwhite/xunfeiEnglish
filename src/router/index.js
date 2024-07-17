import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import smartTalkRoutes from "@/router/smartTalkRoutes.js";
import userRoutes from "@/router/userRoutes.js";
import wordRoutes from "@/router/wordRoutes.js";
import xunfeiRoutes from "@/router/xunfeiRoutes.js";
import Test from "@/views/test/Test.vue";
import aboutRoutes from "@/router/aboutRoutes/aboutRoutes.js";
import statisticRoutes from "@/router/statisticRoutes.js";
import Test2 from "@/views/test/Test2.vue";
import bookRoutes from "@/router/bookRoutes.js";
import store from '@/stores/user.js'; // 引入Vuex store

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: HomeView
        },
        {
            path: '/test',
            name: 'Test',
            component: Test
        },
        {
            path: '/test2',
            name: 'Test2',
            component: Test2
        }
    ].concat(userRoutes, smartTalkRoutes, wordRoutes, xunfeiRoutes, aboutRoutes, statisticRoutes, bookRoutes)
})

// 定义白名单
const whiteList = ['/login', '/', '/readBook', '/vbIndex', '/smartTalk']; // 不需要登录即可访问的路由

// 添加导航守卫
router.beforeEach((to, from, next) => {
    if (!store.state.isLoggedIn && !whiteList.includes(to.path)) {
        alert('请登录')
        next('/login'); // 未登录且不在白名单中，则重定向到登录页面
    } else {
        next(); // 已登录则继续导航
    }
});

export default router
