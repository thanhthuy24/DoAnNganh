<template>
    <AdminLayout>
        <main>
            <section class="bg-white dark:bg-gray-900">
                <div class="flex justify-between">
                    <h1 class="mt-5 font-large">Add Lesson</h1>
                </div>
                <div>
                    <h1>Let's check your update today!!</h1>
                </div>
                <div class="border-style ml-7 mt-5">
                    <div>
                        <p class="font-large ml-6 my-5">Description</p>
                    </div>
                    <div>
                        <form class="ml-6 mb-5" @submit.prevent="createLesson">
                            <div class="sm:col-span-2 mr-5">
                                <label for="name" class="block mb-3 mt-2 text-sm font-medium text-gray-900 dark:text-white">Lesson Name: <span style="color: red">*</span></label>
                                 <input 
                                    v-model="name"
                                    type="text" 
                                    name="name" 
                                    id="name" 
                                    class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" 
                                    placeholder="Type your course name" 
                                    required="">    
                            </div>
                            <div class="sm:col-span-2 mr-5">
                                <label for="description" class="block mb-3 mt-5 text-sm font-medium text-gray-900 dark:text-white">Lesson Description: <span style="color: red">*</span></label>
                                <textarea
                                v-model="description"
                                 id="description" rows="4" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Your description here"></textarea>
                            </div>
                            <!-- <div class="mb-5 mr-5 mt-5">
                                <label class="block mb-3 mt-5 text-sm font-medium text-gray-900 dark:text-white" for="image">List Videos: <span style="color: red">*</span></label>
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
                            </div> -->
                             <div>
                                <label for="file-upload" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                                    Upload Video Files: <span style="color: red">*</span>
                                </label>
                                <input 
                                    @change="handleFileUpload" 
                                    type="file" 
                                    multiple 
                                    accept=".mp4"
                                    class="block w-full mb-5 text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 dark:text-gray-400 focus:outline-none dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400" 
                                    id="file-upload" 
                                >
                                <label style="color: red; font-size: small; font-weight: bold">
                                    Tối đa 5 file .mp4, dung lượng không quá 20MB
                                </label>
                                <div v-if="errorMessage" class="text-red-500 mt-2">---{{ errorMessage }}</div>
                            </div>
                            <div class="mr-5">
                                <label for="course" class="block mb-3 mt-5 text-sm font-medium text-gray-900 dark:text-white">Course: <span style="color: red">*</span></label>
                                <select 
                                    v-model="courseId" 
                                    @change="logCourse"
                                    id="course" 
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
// import store from "@/store";
import { onMounted, ref } from "vue";
import { useStore } from "vuex";
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';

export default ({
    components: {
        AdminLayout,
    },
    setup() {
        const store = useStore();
        const token = store.getters.token;
        const name = ref('');
        const description = ref('');

        const courseId = ref('');
        const lessonId = ref('');
        const videos = ref([]);

        const notify = () => {
            toast("Course has been created sucessfully!!", {
                "theme": "auto",
                "type": "success","autoClose": 3000,
                "dangerouslyHTMLString": true
            })
        }

        const createLesson = async() => {
            const formData = new FormData();

            formData.append('name', name.value);
            formData.append('description', description.value);

            // Check if categoryId is not undefined or null
            if (courseId.value != null && courseId.value !== undefined) {
                formData.append('courseId', courseId.value);
            } else {
                console.error('courseId is invalid:', courseId.value);
            }

            try{
                let res = await authAPIs().post(endpoints.lessonsList, formData, {
                    headers: { 
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'multipart/form-data' 
                    }
                });
                name.value = ''; description.value = '', courseId.value = ''
                lessonId.value = res.data.id;
                uploadVideos(lessonId.value);
                notify();
                
            }catch(err){{
                console.error(err);
            }}
        }

        const uploadVideos = async(id) => {
            try {
                const formVideos = new FormData();
                videos.value.forEach((file) => {
                    formVideos.append('files', file);
                });

                await authAPIs().post(`${endpoints.lessonsList}/uploads/${id}`, formVideos, {
                    headers: { 
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'multipart/form-data' 
                    }
                })
                console.log(videos.value);
            }catch(err){
                console.error(err);
            }
        }
        const logCourse = () => {
            console.log('Selected course:', courseId.value);
        };

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

        const errorMessage = ref('');

        const handleFileUpload = (event) => {
            console.log("File upload event triggered!");
            const selectedFiles = event.target.files;
            const maxFileSize = 20 * 1024 * 1024; // 20MB
            const validFileTypes = ['video/mp4']; // Có thể thêm các loại tệp khác nếu cần

            errorMessage.value = ''; // Reset thông báo lỗi

            if (selectedFiles.length > 5) {
                errorMessage.value = 'Bạn chỉ có thể tải lên tối đa 5 tệp.';
                return;
            }

            const filesArray = Array.from(selectedFiles);
            let totalSize = 0;

            for (let file of filesArray) {
                // Kiểm tra kích thước tệp
                if (file.size > maxFileSize) {
                    errorMessage.value = 'Kích thước tệp không được vượt quá 20MB.';
                    return;
                }

                 totalSize += file.size;

                // Kiểm tra kiểu tệp
                if (!validFileTypes.includes(file.type)) {
                    errorMessage.value = 'Chỉ hỗ trợ tệp .mp4.';
                    return;
                }
            }
            if (totalSize > maxFileSize) {
                errorMessage.value = 'Tổng kích thước của các tệp tải lên không được vượt quá 20MB.';
                return;
            }

            // Nếu tất cả đều hợp lệ, lưu trữ tệp
            videos.value = filesArray;
            console.log(videos.value); // In ra danh sách tệp đã chọn
        };

        onMounted(() => {
            loadCourses();
        })

        return {
            name, description, courseId, lessonId, createLesson, videos,
            courses, logCourse, handleFileUpload, errorMessage, notify

        }
    },
})
</script>
