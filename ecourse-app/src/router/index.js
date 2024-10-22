import {createRouter, createWebHistory} from "vue-router"
import HomeView from "../views/home/Home-view.vue"
import AdminLayout from "@/layouts/admin.vue";
import CourseEnrolled from "@/views/courses/course-enrolled.vue";
import AssignmentDetail from "@/views/assignments/assignment-detail.vue";
import RegisterForm from "@/views/register/register-form-user.vue";
import TeacherList from "@/views/admin/teachers/teacher-form-list.vue";
import CourseAdmin from "@/views/admin/courses/course-create.vue";
import CourseListAdmin from "@/views/admin/courses/course-list.vue";
import CourseDetailAdmin from "@/views/admin/courses/course-detail.vue";
import LessonListAdmin from "@/views/admin/lessons/lesson-list.vue";
import LessonDetailAdmin from "@/views/admin/lessons/lesson-detail.vue";
import LessonCreateAdmin from "@/views/admin/lessons/lesson-create.vue";
import TeacherView from "@/views/home/Teacher-view.vue";
import RegisterFormDetails from "@/views/admin/teachers/teacher-forms-detail.vue";
import { useStore } from "vuex";

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
            layout: "admin",
            requiresAuth: true,
            role: 'Admin'
        },
        component: AdminLayout
    },
    {
        path: "/teacher-view",
        name: "Teacher",
        meta: {
            layout: "teacher-view",
            requiresAuth: true,
            role: 'Teacher'
        },
        component: TeacherView
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
    {
        path: `/assignment/:assignmentId`,
        name: "AssignmentDetail",
        meta: {
            layout: "AssignmentDetail"
        },
        component: AssignmentDetail
    },
    {
        path: `/register-form`,
        name: "RegisterForm",
        meta: {
            layout: "RegisterForm"
        },
        component: RegisterForm
    },
    {
        path: `/teacher`,
        name: "TeacherList",
        meta: {
            layout: "TeacherList"
        },
        component: TeacherList
    },
    {
        path: `/course-list-admin`,
        name: "CourseListAdmin",
        meta: {
            layout: "CourseListAdmin"
        },
        component: CourseListAdmin
    },
    {
        path: `/course-view-admin`,
        name: "CourseAdmin",
        meta: {
            layout: "CourseAdmin"
        },
        component: CourseAdmin
    },
    {
        path: `/course-detail/:courseId`,
        name: "CourseDetailAdmin",
        meta: {
            layout: "CourseDetailAdmin"
        },
        component: CourseDetailAdmin
    },
    {
        path: `/register-form-detail/:registerId`,
        name: "RegisterFormDetails",
        meta: {
            layout: "RegisterFormDetails"
        },
        component: RegisterFormDetails
    },
    {
        path: `/lessons-list-admin`,
        name: "LessonListAdmin",
        meta: {
            layout: "LessonListAdmin"
        },
        component: LessonListAdmin
    },
    {
        path: `/lesson-detail/:lessonId`,
        name: "LessonDetailAdmin",
        meta: {
            layout: "LessonDetailAdmin"
        },
        component: LessonDetailAdmin
    },
    {
        path: `/lesson-create`,
        name: "LessonCreateAdmin",
        meta: {
            layout: "LessonCreateAdmin"
        },
        component: LessonCreateAdmin
    },
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});

router.beforeEach((to, from, next) => {
    const store = useStore();
    const isLoggedIn = store.getters.isLoggedIn;
    // const userRole = store.getters.userRole;
    
    if(to.matched.some(record => record.meta.requiresAuth) && !isLoggedIn) {
        next({
            name: "Login"
        })
    }else{
        next();
    }
})
// router.beforeEach((to, from, next) => {
//     const store = useStore();
//     const isLoggedIn = store.getters.isAuthenticated;
//     const userRole = store.getters.userRole;
  
//     if (to.matched.some(record => record.meta.requiresAuth)) {
//       if (!isLoggedIn) {
//         return next({ name: 'Login' });  // Nếu chưa đăng nhập, chuyển đến trang Login
//       }
      
//       if (to.meta.role && to.meta.role !== userRole) {
//         return next();  // Nếu role không hợp lệ, chuyển đến trang Forbidden
//       }
//     }
  
//     next();
//   });


export default router;