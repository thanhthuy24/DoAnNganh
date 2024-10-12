<template>
    <AdminLayout>
        <main> 
            <!-- <h1>{{lessonId}}</h1> -->
            <section class="bg-white dark:bg-gray-900">
                <div class="flex justify-between">
                    <h1 class="mt-5 font-large">Update Course</h1>
                </div>
                <div>
                    <h1>Let's check your update today!!</h1>
                </div>
                <div class="border-style ml-7 mt-5">
                    <div>
                        <p class="font-large ml-6 my-5">Description</p>
                    </div>
                    <div>
                        <form class="ml-6 mb-5" @submit.prevent="updateLesson">
                            <div class="sm:col-span-2 mr-5">
                                <label for="name" class="block mb-3 mt-2 text-sm font-medium text-gray-900 dark:text-white">Course Name: <span style="color: red">*</span></label>
                                 <input 
                                    v-model="lesson.name"
                                    type="text" 
                                    name="name" 
                                    id="name" 
                                    class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" 
                                    placeholder="Type your course name" 
                                    required="">    
                            </div>
                            <div class="sm:col-span-2 mr-5">
                                <label for="description" class="block mb-3 mt-5 text-sm font-medium text-gray-900 dark:text-white">Course Description: <span style="color: red">*</span></label>
                                <textarea
                                v-model="lesson.description"
                                 id="description" rows="4" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Your description here"></textarea>
                            </div>
                            <div class="mb-5 mr-5 mt-5">
                                <label class="block mb-3 mt-5 text-sm font-medium text-gray-900 dark:text-white" for="image">Course Image: <span style="color: red">*</span></label>
                                <div class="flex" style="display: flex; flex-wrap: wrap; gap: 10px;">
                                    <video 
                                        v-for="(item, id) in videos" 
                                        :key="id" 
                                        style="width: 280px; height: 180px; flex: 0 0 calc(33.33% - 10px);" 
                                        class="mr-5"
                                        controls>
                                        <source :src="item.name" type="video/mp4">
                                        Your browser does not support the video tag.
                                    </video>
                                </div>
                            </div>
                            <div class="mr-5">
                                <label for="course" class="block mb-3 mt-5 text-sm font-medium text-gray-900 dark:text-white">Course: <span style="color: red">*</span></label>
                                <select 
                                    v-model="courseName" 
                                    id="category" 
                                    class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500">
                                    <option 
                                        v-for="(item, id) in courses" 
                                        :key="id" 
                                        :value="item.id">
                                        {{ item.name }}
                                    </option>                     
                                </select>
                            </div>                            
                            <button style="width: 130px; margin-left: 87%" type="submit" class="text-white mt-5 bg-blue-700 hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 font-medium rounded-full text-sm px-5 py-2.5 text-center me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                                Update 
                            </button>
                        </form>
                    </div>
                </div>
            </section>
        </main>
    </AdminLayout>
</template>
<script>
import { authAPIs, endpoints } from "@/configs/APIs";
import AdminLayout from "@/layouts/admin.vue";
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';

export default ({
    components: {
    AdminLayout,
},
    setup() {
        const route = useRoute();
        const store = useStore();   
        let token = store.getters.token;
        const lessonId = route.params.lessonId || route.query.lessonId;

        const lesson = ref({});
        const courseName = ref('');

        const notify = () => {
            toast("Course has been updated sucessfully!!", {
                "theme": "auto",
                "type": "success","autoClose": 3000,
                "dangerouslyHTMLString": true
            })
        }
        
        const loadLesson = async() => {
            try {
                // console.log("5454")
                
                let res = await authAPIs().get(`${endpoints.lessonsList}/${lessonId}`,{
                    headers: { 
                        Authorization: `Bearer ${token}` 
                    }
                });
                lesson.value = res.data;
                courseName.value = res.data.course.id;
                // console.log(courseName.value);
            } catch(err){
                console.error(err);
            }
        }

        const courses = ref([]);
        const loadCourses = async() => {
            try {
                let res = await authAPIs().get(`${endpoints.courses}/name-course`,{
                    headers: { 
                        Authorization: `Bearer ${token}` 
                    }
                });
                courses.value = res.data;
                // console.log(res.data);
            } catch(err){
                console.error(err);
            }
        }
        
        const videos = ref([]);
        const loadVideosByLesson = async() => {
            try {
                let res = await authAPIs().get(`${endpoints.videos}/lesson/${lessonId}`,{
                    headers: { 
                        Authorization: `Bearer ${token}` 
                    }
                });
                videos.value = res.data;
                // console.log(res.data);
            } catch(err){
                console.error(err);
            }
        }

        const updateLesson = async() => {
            const formData = new FormData();

            formData.append('name', lesson.value.name);
            formData.append('description', lesson.value.description);

            // Check if teacherId is not undefined or null
            if (courseName.value != null && courseName.value !== undefined) {
                formData.append('courseId', courseName.value);
            }

            const token = store.getters.token;
            await authAPIs().put(`${endpoints.lessonsList}/${lessonId}`, formData, {
                headers: { 
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'multipart/form-data' 
                }
            })
            notify();
        }

        onMounted(() => {
            loadLesson();
            loadCourses();
            loadVideosByLesson();
        })

        return {
            lessonId,
            lesson, courses, courseName, videos,
            updateLesson

        }
    },
})
</script>
