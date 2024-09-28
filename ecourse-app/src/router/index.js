import {createRouter, createWebHistory} from "vue-router"
import HomeView from "../views/home/Home-view.vue"

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
    
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});

export default router;