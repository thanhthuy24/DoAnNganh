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
                                    <!-- <router-link 
                                        :to="{ name: 'RegisterFormDetails', params: { registerId: item.id || 'defaultId' } }"
                                    > -->
                                    {{item.id}}
                                    <!-- </router-link> -->
                                </td>
                                <td class="px-6 py-4">
                                    {{item.name}}
                                </td>
                                <td class="px-6 py-4">
                                    {{item.teacher?.user?.firstName}} {{item.teacher?.user?.lastName}} 
                                </td>
                                <td class="px-6 py-4">
                                    {{formatCurrencyWithRounding(item.price)}} VND
                                </td>
                                <td class="px-6 py-4">
                                     {{ lessonsCount[item.id] !== 0 ? lessonsCount[item.id] : 0 }}
                                </td>
                                <td class="px-6 py-4">
                                     <span style="font-weight: bold" :class="item.isActive ? 'text-green-500' : 'text-red-500'">
                                        {{ item.isActive ? 'Active' : 'Inactice' }}
                                    </span>
                                </td>
                                <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                    <router-link 
                                        :to="{ name: 'CourseDetailAdmin', 
                                                params: { courseId: item.id || 'defaultId' } }">
                                        <button class="mr-3 style-icon-actions" style="background-color: #D6EFED">
                                            <svg style="margin-left: 2.5px;" class="w-6 h-6 text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" viewBox="0 0 24 24">
                                                <path stroke="currentColor" stroke-width="2" d="M21 12c0 1.2-4.03 6-9 6s-9-4.8-9-6c0-1.2 4.03-6 9-6s9 4.8 9 6Z"/>
                                                <path stroke="currentColor" stroke-width="2" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z"/>
                                            </svg>
                                        </button>
                                    </router-link>
                                    <button class="mr-3 style-icon-actions" style="background-color: #F7E8F6">
                                        <svg style="margin-left: 2.5px;" class="w-6 h-6 text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" viewBox="0 0 24 24">
                                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M18 5V4a1 1 0 0 0-1-1H8.914a1 1 0 0 0-.707.293L4.293 7.207A1 1 0 0 0 4 7.914V20a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-5M9 3v4a1 1 0 0 1-1 1H4m11.383.772 2.745 2.746m1.215-3.906a2.089 2.089 0 0 1 0 2.953l-6.65 6.646L9 17.95l.739-3.692 6.646-6.646a2.087 2.087 0 0 1 2.958 0Z"/>
                                        </svg>
                                    </button>
                                    <button 
                                    @click="handleButton(item.id)"
                                    style="background-color: #F95454" 
                                    class="style-icon-actions" data-modal-target="popup-modal" data-modal-toggle="popup-modal" 
                                      >
                                        <svg style="margin-left: 2.5px;" class="w-6 h-6 text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" viewBox="0 0 24 24">
                                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 7h14m-9 3v8m4-8v8M10 3h4a1 1 0 0 1 1 1v3H9V4a1 1 0 0 1 1-1ZM6 7h12v13a1 1 0 0 1-1 1H7a1 1 0 0 1-1-1V7Z"/>
                                        </svg>
                                    </button>
                                    <div id="popup-modal" tabindex="-1" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
                                        <div class="relative p-4 w-full max-w-md max-h-full">
                                            <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
                                                <button type="button" class="absolute top-3 end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-hide="popup-modal">
                                                    <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                                                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                                                    </svg>
                                                    <span class="sr-only">Close modal</span>
                                                </button>
                                                <div class="p-4 md:p-5 text-center">
                                                    <svg class="mx-auto mb-4 text-gray-400 w-12 h-12 dark:text-gray-200" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 11V6m0 8h.01M19 10a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"/>
                                                    </svg>
                                                    <h3 class="mb-5 text-lg font-normal text-gray-500 dark:text-gray-400">Are you sure you want to delete this course?</h3>
                                                    <p 
                                                        style="font-size: small"
                                                    class="mb-5 text-gray-500 dark:text-gray-400">It's delete all lessons, videos and assignments too!!</p>
                                                    <button @click="deleteCourse(selectCourseId)" data-modal-hide="popup-modal" type="button" class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 dark:focus:ring-red-800 font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center">
                                                        Yes, I'm sure
                                                    </button>
                                                    <button data-modal-hide="popup-modal" type="button" class="py-2.5 px-5 ms-3 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700">No, cancel</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </th>
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
import APIs, { authAPIs, endpoints } from "@/configs/APIs";
import AdminLayout from "@/layouts/admin.vue";
import { onMounted, ref } from "vue";
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
        const lessonsCount = ref({});
        const loadCourses = async(page, limit) => {
            try {
                const res = await APIs.get(`${endpoints.courses}?page=${page}&&limit=${limit}`);
                courses.value = res.data.courses;
                totalPages.value = res.data.totalPages;
                // console.log(courses.value);
                // countLessons(courses.value.id);
                courses.value.forEach(course => {
                    countLessons(course.id);  // Truyền courseId để đếm số bài học
                });
            } catch(err){
                console.error(err);
            }
        }

        const handlePageChange = (newPage) => {
            currentPage.value = newPage;
            loadCourses(currentPage.value, itemsPerPage.value); // Gọi lại để lấy dữ liệu cho trang mới
        };
        
        const selectCourseId = ref('');
        const handleButton = (courseId) => {
            selectCourseId.value = courseId;
        }

        const deleteCourse = async(selectCourseId) => {
            console.log(selectCourseId);
            try {
                await authAPIs().delete(`${endpoints.courses}/${selectCourseId}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                console.log("delete successful");
                await loadCourses(currentPage.value, itemsPerPage.value);
            } catch(err) {
                console.error(err);
            }
        }

        onMounted(() => {
            loadCourses(currentPage.value, itemsPerPage.value);
        })
        
        const countLesson = ref(null);
        const countLessons = async(courseId) => {
            try {
                let res = await authAPIs().get(`${endpoints.lessons}/${courseId}/count`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                }
                });
                lessonsCount.value[courseId] = res.data;
                // console.log( courseId + ": " + lessonsCount.value[courseId]);
            } catch(err){
                console.error(err);
            }
        }

        const formatCurrencyWithRounding = (value) => {
            const roundedValue = Math.floor(value) + (value % 1 >= 0.5 ? 1 : 0);
            return roundedValue.toLocaleString().replace(/\B(?=(\d{3})+(?!))/g, ",");
        };

        return {
            token,
            courses,
            handlePageChange,
            currentPage, 
            itemsPerPage,
            totalPages,
            formatCurrencyWithRounding,
            countLesson,
            lessonsCount,
            deleteCourse,
            selectCourseId, handleButton
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
    
    .style-icon-actions {
        width: 1.7rem;
        height: 1.7rem;
        border-radius: 10px;
    }
</style>