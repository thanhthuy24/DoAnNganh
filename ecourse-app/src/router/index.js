import {createRouter, createWebHistory} from "vue-router"
import HomeView from "../views/home/Home-view.vue"
import store from '@/store/index.js'

const routes = [
    {
        path: "/",
        name: "Home",
        meta: {
            layout: "default",
    },
        component: HomeView
    },
    {
        path: "/admin",
        name: "Admin",
        meta: {
            layout: "admin"
        },
        component: (() => import("@/views/home/Admin-view.vue"))
    },
    {
        path: "/login",
        name: "Login",
        meta: {
            layout: "login"
        },
        component: (() => import("@/views/login/login.vue"))
    },
    {
        path: "/register",
        name: "Register",
        meta: {
            layout: "register"
        },
        component: (() => import("@/views/login/register.vue"))
    },
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});

router.beforeEach((to, from, next) => {
    const isLoggedIn = store.getters.isLoggedIn;
    
    if(to.matched.some(record => record.meta.requiresAuth) && !isLoggedIn) {
        next({
            name: "Login"
        })
    }else{
        next();
    }
})

export default router;