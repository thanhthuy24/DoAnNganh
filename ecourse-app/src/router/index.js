import {createRouter, createWebHistory} from "vue-router"
import HomeView from "../views/home/Home-view.vue"
import CourseEnrolled from "@/views/courses/course-enrolled.vue";
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
    {
        path: "/cart",
        name: "Cart",
        meta: {
            layout: "cart"
        },
        component: (() => import("@/views/cart/cart-courses.vue"))
    },
    {
        path: "/user",
        name: "User",
        meta: {
            layout: "user"
        },
        component: (() => import("@/views/user/user-information.vue"))
    },
    {
        path: `/course-enrolled/:courseId`,
        name: "CourseEnrolled",
        meta: {
            layout: "CourseEnrolled"
        },
        component: CourseEnrolled
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