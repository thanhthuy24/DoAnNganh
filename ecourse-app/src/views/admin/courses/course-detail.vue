<template>
    <AdminLayout>
        <main>
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
                        <form class="ml-6 mb-5" @submit.prevent="updateCourse">
                            <div class="sm:col-span-2 mr-5">
                                <label for="name" class="block mb-3 mt-2 text-sm font-medium text-gray-900 dark:text-white">Course Name: <span style="color: red">*</span></label>
                                 <input 
                                    v-model="course.name"
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
                                v-model="course.description"
                                 id="description" rows="4" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Your description here"></textarea>
                            </div>
                            <div class="mb-5 mr-5 mt-5">
                                <label class="block mb-3 mt-5 text-sm font-medium text-gray-900 dark:text-white" for="image">Course Image: <span style="color: red">*</span></label>
                                <img :src="course.image" class="w-52 h-52" />
                            </div>
                            <div class="mr-5">
                                <label for="category" class="block mb-3 mt-5 text-sm font-medium text-gray-900 dark:text-white">Category: <span style="color: red">*</span></label>
                                
                                <select 
                                    v-model="cateName" 
                                    id="category" 
                                    class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500">
                                    <option 
                                        v-for="(item, id) in categories" 
                                        :key="id" 
                                        :value="item.id">
                                        {{ item.name }}
                                    </option>                     
                                </select>
                            </div>
                            <div class="mr-5">
                                <label for="tag" class="block mb-3 mt-5 text-sm font-medium text-gray-900 dark:text-white">Tag: <span style="color: red">*</span></label>
                                <select 
                                    v-model="tagName"
                                    id="tag" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500">
                                    <option 
                                    v-for="(item, id) in tags" 
                                    :key="id" 
                                    :value="item.id">
                                    {{item.name}}</option>                     
                                </select>
                            </div>
                            <div class="mr-5">
                                <label for="teacher" class="block mb-3 mt-5 text-sm font-medium text-gray-900 dark:text-white">Teacher: <span style="color: red">*</span></label>
                                <select 
                                    v-model="teacherName"
                                    id="teacher" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500">
                                    <option v-for="(item, id) in teachers" :value="item.id" :key="id" selected="">{{item.user?.username}}</option>                     
                                </select>
                            </div>
                            <div class="flex">
                                <div class="w-full">
                                <label for="price" class="block mb-3 mt-5 text-sm font-medium text-gray-900 dark:text-white">Price: <span style="color: red">*</span></label>
                                <input
                                v-model.number="course.price"
                                 type="number" name="price" style="width: 350px" id="price" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="VND" required="">
                            </div>
                            <div class="w-full">
                                <label for="discount" class="block mb-3 mt-5 text-sm font-medium text-gray-900 dark:text-white">Discount: <span style="color: red">*</span></label>
                                <input 
                                v-model="course.discount"
                                type="number" step="0.01" name="discount" style="width: 350px" id="discount" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="%" required="">
                            </div>
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
import APIs, { authAPIs, endpoints } from "@/configs/APIs";
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
        // const store = useStore();   

        const courseId = route.params.courseId || route.query.courseId;
        const course = ref({});
        const cateName = ref('');
        const tagName = ref('');
        const teacherName = ref('');
        const loadCourse = async() => {
            try {
                let res = await APIs.get(`${endpoints.courses}/${courseId}`);
                course.value = res.data;
                cateName.value = res.data.category.id;
                tagName.value = res.data.tag.id;
                teacherName.value = res.data.teacher.id;
                console.log(course.value);
                console.log(tagName.value);
            } catch(err){
                console.error(err);
            }
        }

        const notify = () => {
            toast("Course has been updated sucessfully!!", {
                "theme": "auto",
                "type": "success","autoClose": 3000,
                "dangerouslyHTMLString": true
            })
        }

        const updateCourse = async() => {
            try {
                const formData = new FormData();

                formData.append('name', course.value.name);
                formData.append('description', course.value.description);
                formData.append('price', course.value.price);
                let discountValue = parseFloat(course.value.discount);
                if (isNaN(discountValue)) {
                    discountValue = 0.0; 
                }
                formData.append('discount', discountValue);

                // Check if categoryId is not undefined or null
                if (cateName.value != null && cateName.value !== undefined) {
                    formData.append('categoryId', cateName.value);
                } else {
                    console.error('categoryId is invalid:', cateName.value);
                }

                // Check if tagId is not undefined or null
                if (tagName.value != null && tagName.value !== undefined) {
                    formData.append('tagId', tagName.value);
                }

                // Check if teacherId is not undefined or null
                if (teacherName.value != null && teacherName.value !== undefined) {
                    formData.append('teacherId', teacherName.value);
                }

                const token = store.getters.token;
                await authAPIs().put(`${endpoints.courses}/${courseId}`, formData, {
                    headers: { 
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'multipart/form-data' 
                    }
                })
                notify();
            } catch(err){
                console.error(err);
            }
        }

        const categories = ref([]);
        const fetchCategories = async () => {
            try {
                const res = await APIs.get(endpoints.categories);
                categories.value = res.data;
                // console.log(categories.value);
            } catch (error) {
                console.error(error);
		}
        };

        const store = useStore();
        const tags = ref([]);
        const fetchTags = async() => {
            try {
                const token = store.getters.token;
                const res = await authAPIs().get(endpoints.tag, {
                    headers: {
                        Authorization: `Bearer ${token}`	
                    }
                });
                tags.value = res.data;
                // console.log(res.data);
            } catch (error) {
                console.error(error);
		}
        }

        const teachers = ref([]);
        const fetchTeacher = async() => {
            try {
                const res = await APIs.get(endpoints.teachers);
                teachers.value = res.data;
                // console.log(res.data);
            } catch (error) {
                console.error(error);
        }}

        onMounted(() => {
            loadCourse();
            fetchCategories();
            fetchTags();
            fetchTeacher();
        })

        return {
            courseId,
            course, tags, teachers, categories,
            cateName, tagName, teacherName,
            updateCourse, notify
        }
    },
})
</script>
