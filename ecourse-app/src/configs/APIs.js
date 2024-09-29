import axios from "axios";
// import { useCookies } from "vue3-cookies";

// const { cookies } = useCookies();

const BASE_URL = "http://localhost:8080/api/";

export const endpoints = {
    login: '/users/login',
    currentUser: '/users/current-user',
    register: '/users/register',

    checkEnrollment: `/enrollments/check-enrollment`,
    

    categories: '/categories',
    courses: '/courses'
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