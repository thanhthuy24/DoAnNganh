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

    enrollments: '/enrollments',

    certificate: '/certificate/download',

    lessonsList: '/lessons',

    videos: '/videos',

    listVideosCompleted: '/video-completed',

    rating: '/rating',

    assignment: '/assignments',

    answerDone: '/answer-choices',

    question: 'questions',

    assignmentDone: '/assignment-done',

    score: '/score',

    progress: '/progress',

    lessons: `/lessons/course`,
    comments: '/comments',
    reply: '/reply',
    like: '/like',

    cart:'/cart',
    checkout: '/payment/create-payment',
    updatePayment: '/payment/update-payment',

    categories: '/categories',
    courses: '/courses',

    token: '/token',
    tag: '/tags',

    teachers: '/teachers/all',

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