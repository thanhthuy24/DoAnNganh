<template>
    <AdminLayout>
        <main>
            <section class="bg-white dark:bg-gray-900">
                <div class="flex justify-between">
                    <h1 class="mt-5 font-large">Add Course</h1>
                    <div class="mt-3">
                        <button type="button" class="py-2.5 px-5 me-2 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-full border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700">
                            Cancel
                        </button>
                        <router-link to="/course-list-admin">
                            <button type="button" class="text-white bg-blue-700 hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 font-medium rounded-full text-sm px-5 py-2.5 text-center me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                                List courses
                            </button>
                        </router-link>
                    </div>
                </div>
                <div>
                    <h1>Let's check your update today!!</h1>
                </div>
                <div class="border-style ml-7 mt-5">
                    <div>
                        <p class="font-large ml-6 my-5">Description</p>
                    </div>
                    <div>
                        <form class="ml-6 mb-5" @submit.prevent="createCourse">
                            <div class="sm:col-span-2 mr-5">
                                <label for="name" class="block mb-3 mt-2 text-sm font-medium text-gray-900 dark:text-white">Course Name: <span style="color: red">*</span></label>
                                <input 
                                v-model="name"
                                type="text" name="name" id="name" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Type your course name" required="">
                            </div>
                            <div class="sm:col-span-2 mr-5">
                                <label for="description" class="block mb-3 mt-5 text-sm font-medium text-gray-900 dark:text-white">Course Description: <span style="color: red">*</span></label>
                                <textarea
                                v-model="description"
                                 id="description" rows="4" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Your description here"></textarea>
                            </div>
                            <div class="mb-5 mr-5">
                                <label class="block mb-3 mt-5 text-sm font-medium text-gray-900 dark:text-white" for="image">Course Image: <span style="color: red">*</span></label>
                                <input @change="handleFileUpload" class="block w-full text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 dark:text-gray-400 focus:outline-none dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400" aria-describedby="user_avatar_help" id="avatar" type="file" required />
                            </div>
                            <div class="mr-5">
                                <label for="category" class="block mb-3 mt-5 text-sm font-medium text-gray-900 dark:text-white">Category: <span style="color: red">*</span></label>
                                <select 
                                    @change="logCategory" 
                                    v-model="categoryId" id="category" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500">
                                    <option v-for="(item, id) in categories" :key="id" :value="item.id" selected="">{{item.name}}</option>                     
                                </select>
                            </div>
                            <div class="mr-5">
                                <label for="tag" class="block mb-3 mt-5 text-sm font-medium text-gray-900 dark:text-white">Tag: <span style="color: red">*</span></label>
                                <select 
                                    v-model="tagId"
                                    id="tag" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500">
                                    <option v-for="(item, id) in tags" :key="id" :value="item.id" selected="">{{item.name}}</option>                     
                                </select>
                            </div>
                            <div class="mr-5">
                                <label for="teacher" class="block mb-3 mt-5 text-sm font-medium text-gray-900 dark:text-white">Teacher: <span style="color: red">*</span></label>
                                <select 
                                    @change="logTeacher" 
                                    v-model="teacherId"
                                    id="teacher" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500">
                                    <option v-for="(item, id) in teachers" :value="item.id" :key="id" selected="">{{item.user?.username}}</option>                     
                                </select>
                            </div>
                            <div class="flex">
                                <div class="w-full">
                                <label for="price" class="block mb-3 mt-5 text-sm font-medium text-gray-900 dark:text-white">Price: <span style="color: red">*</span></label>
                                <input
                                v-model.number="price"
                                 type="number" name="price" style="width: 350px" id="price" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="VND" required="">
                            </div>
                            <div class="w-full">
                                <label for="discount" class="block mb-3 mt-5 text-sm font-medium text-gray-900 dark:text-white">Discount: <span style="color: red">*</span></label>
                                <input 
                                v-model="dicount"
                                type="number" step="0.01" name="discount" style="width: 350px" id="discount" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="%" required="">
                            </div>
                        </div>
                            
                        <button style="width: 130px; margin-left: 87%" type="submit" class="text-white mt-5 bg-blue-700 hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 font-medium rounded-full text-sm px-5 py-2.5 text-center me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                            Submit
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
import { useStore } from "vuex";


export default ({
    components: {
    AdminLayout,
},
    setup() {
        const name = ref('');
        const description = ref('');
        const price = ref('');
        const dicount = ref(null);
        const categoryId = ref(null);
        const file = ref('');
        const tagId = ref(null);
        const teacherId = ref(null);
        const image = ref('');

        const createCourse = async() => {
            const formData = new FormData();

            formData.append('name', name.value);
            formData.append('description', description.value);
            formData.append('price', price.value);
            let discountValue = parseFloat(dicount.value);
            if (isNaN(discountValue)) {
                discountValue = 0.0; 
            }
            formData.append('discount', discountValue);

            // Check if categoryId is not undefined or null
            if (categoryId.value != null && categoryId.value !== undefined) {
                formData.append('categoryId', categoryId.value);
            } else {
                console.error('categoryId is invalid:', categoryId.value);
            }

            // Check if tagId is not undefined or null
            if (tagId.value != null && tagId.value !== undefined) {
                formData.append('tagId', tagId.value);
            }

            // Check if teacherId is not undefined or null
            if (teacherId.value != null && teacherId.value !== undefined) {
                formData.append('teacherId', teacherId.value);
            }

            // Check if the file exists before appending
            if (image.value) {
                formData.append('file', image.value);
            }
            // console.log(formData.value);

            try{
                const token = store.getters.token;
                await authAPIs().post(endpoints.courses, formData, {
                headers: { 
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'multipart/form-data' 
                }
            });
                console.log(formData);
            }catch(err){{
                console.error(err);
            }}
        
        }

        const logCategory = () => {
            console.log('Selected Category:', categoryId.value);
        };

        const logTag = () => {
            console.log('Selected Category:', tagId.value);
        };

        const logTeacher = () => {
            console.log('Selected Category:', teacherId.value);
        };

        const handleFileUpload = (event) => {
            if (event.target.files && event.target.files.length > 0) {
                image.value = event.target.files[0];
                console.log(image.value); // Store the first selected file
            } else {
                image.value = null;
                console.log(image); // Reset avatar if no file selected
            }
        };

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
            fetchCategories();
            fetchTags();
            fetchTeacher();
        })

        return {
            name, description, price, dicount,
            categoryId, file, tagId, teacherId,
            categories, tags, teachers,createCourse,
            handleFileUpload,
            logCategory, logTag, logTeacher

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