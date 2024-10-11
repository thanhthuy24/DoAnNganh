<template>
    <AdminLayout>
        <main>
            <div class="flex justify-between">
                <h1 class="mt-5 font-large">Course</h1>
                <div class="mt-3">
                    <router-link to="/course-view-admin">
                        <button type="button" class="text-white bg-blue-700 hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 font-medium rounded-full text-sm px-5 py-2.5 text-center me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                            Add course
                        </button>
                    </router-link>
                </div>
            </div>
            <div>
                <h1>Let's check your update today!!</h1>
            </div>
            <div class="border-style ml-7 mt-5">
                <div class="relative w-full my-5 ml-5">
                    <div class="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                        <svg class="w-4 h-4" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                        </svg>
                    </div>
                    <input 
                        type="text" 
                        id="simple-search" 
                        class="bg-gray-50 border search-input border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                        placeholder="Search branch name..." required />
                </div>
                <div>
                    <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
                    <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                        <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                            <tr>
                                <th scope="col" class="px-6 py-3">
                                    ID
                                </th>
                                <th scope="col" class="px-6 py-3">
                                    Course's name
                                </th>
                                <th scope="col" class="px-6 py-3">
                                    Teacher
                                </th>
                                <th scope="col" class="px-6 py-3">
                                    Price
                                </th>
                                <th scope="col" class="px-6 py-3">
                                    Lessons
                                </th>
                                <th scope="col" class="px-6 py-3">
                                    Status
                                </th>
                                <th scope="col" class="px-6 py-3">
                                    Actions
                                </th>
                            </tr>
                        </thead>
                    <tbody>
                        <template v-for="(item, id) in courses" :key="id">
                            <tr class="odd:bg-white odd:dark:bg-gray-900 even:bg-gray-50 even:dark:bg-gray-800 border-b dark:border-gray-700">
                                <td class="px-6 py-4" style="color: blue; text-decoration: underline;">
                                    <router-link 
                                        :to="{ name: 'RegisterFormDetails', params: { registerId: item.id || 'defaultId' } }"
                                    >
                                    {{item.id}}
                                    </router-link>
                                </td>
                                <td class="px-6 py-4">
                                    {{item.name}}
                                </td>
                                <!-- <td class="px-6 py-4">
                                    {{item.user?.username}}
                                </td>
                                <td class="px-6 py-4">
                                    {{item.user?.username}}
                                </td>
                                <td class="px-6 py-4">
                                     <span :class="item.status ? 'text-green-500' : 'text-red-500'">
                                        {{ item.status ? 'Approved' : 'Pending' }}
                                    </span>
                                </td>
                                <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                    {{ truncateReason(item.reason) }}
                                </th> -->
                            </tr>
                        </template>
                    </tbody>
                </table>
                </div>
                </div>
            </div>
        </main>
    </AdminLayout>
</template>

<script>
import APIs, { endpoints } from "@/configs/APIs";
import AdminLayout from "@/layouts/admin.vue";
import { onMounted, ref } from "vue";
// import { useRoute } from "vue-router";
import { useStore } from "vuex";

export default ({
    components: {
    AdminLayout,
},
    setup() {
        const store = useStore();
        // const route = useRoute();

        // const user = computed(() => store.state.user);
        const token = store.getters.token;

        const currentPage = ref(0);
        const itemsPerPage = ref(10);
        const totalPages = ref(0);

        const courses = ref([]);
        const loadCourses = async(page, limit) => {
            try {
                const res = await APIs.get(`${endpoints.courses}?page=${page}&&limit=${limit}`);
                courses.value = res.data.courses;
                totalPages.value = res.data.totalPages;
                // console.log(courses.value);
            } catch(err){
                console.error(err);
            }
        }

            const handlePageChange = (newPage) => {
                currentPage.value = newPage;
                loadCourses(currentPage.value, itemsPerPage.value); // Gọi lại để lấy dữ liệu cho trang mới
            };

        onMounted(() => {
            loadCourses(currentPage.value, itemsPerPage.value);
        })

        return {
            token,
            courses,
            handlePageChange,
            currentPage, 
            itemsPerPage,
            totalPages,
        }
    },
})
</script>
<style>
    .font-large {
        font-size: large;
        font-weight: bold;
    }

    .border-style {
        border: 1px solid black;
        width: 1200px;
    }
</style>