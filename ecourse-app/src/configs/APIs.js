import axios from "axios";
// import { useCookies } from "vue3-cookies";

// const { cookies } = useCookies();

const BASE_URL = "http://localhost:8080/api/";

export const endpoints = {
    login: '/users/login',
    currentUser: '/users/current-user',
    register: '/users/register',
    users: '/users',

    checkEnrollment: `/enrollments/check-enrollment`,
    getCourse: (courseId) => `/courses/${courseId}`,
    getEnrollments: '/enrollments/get-courses',
    courseTeacher: '/courses/teacher',

    lessons: `/lessons/course`,
    comments: '/comments',
    like: '/like',

    cart:'/cart',
    checkout: '/payment/create-payment',
    updatePayment: '/payment/update-payment',

    categories: '/categories',
    courses: '/courses',

    token: '/token',

    registerForm: '/register',
    registerStatus: '/register/user',
    listRegister: '/register/user-form',
    approvalForm: '/register/update',

    teacherInfor: '/teachers/user',

}

export const authAPIs = () => {
    // const token = cookies.get('token');
    return axios.create({
        baseURL: BASE_URL,
        headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
        }
    })
}

export default axios.create({
    baseURL: BASE_URL
})