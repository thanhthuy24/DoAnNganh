import axios from "axios";
// import { useCookies } from "vue3-cookies";

// const { cookies } = useCookies();

const BASE_URL = "http://localhost:8080/api/";

export const endpoints = {
    categories: '/categories',
    courses: '/courses'
}

export const authAPIs = () => {
    return axios.create({
        baseURL: BASE_URL,
        headers: {
            // Authorization: cookies.get('token')
        }
    })
}

export default axios.create({
    baseURL: BASE_URL
})