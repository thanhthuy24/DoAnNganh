<template>
    <AppLayout>
        <main class="ml-16">
            <div class="mt-16">
                <h1 class="ml-5 mt-5 font-large-1">List Your Courses</h1>
                <div style="width: 1380px; border: 3px dashed #C7C7C7;"  
                    class="grid grid-cols-3 gap-4 ml-6 my-8">
					<div v-for="e in courses" :key="e.id"
						class="ml-8 my-8 mr-8
                            w-full max-w-sm bg-white border
                             border-gray-200 rounded-lg shadow
                              dark:bg-gray-800 dark:border-gray-700"
						>
                            <img class="p-8 rounded-t-lg" :src="e.image" alt="product image" />
						<div class="px-8 pb-8 flex-grow h-16 flex items-center justify-center">
                            {{e.name}}
                        </div>
                        <router-link 
                            :to="{ name: 'TeacherCourseDetail', params: { courseId: e.id || 'defaultId' } }"
                                >
                            <div class="px-8 pb-8 flex justify-center">
                                <button 
                                    type="button" 
                                    class="text-white bg-gradient-to-br from-purple-600 to-blue-500 hover:bg-gradient-to-bl focus:ring-4 focus:outline-none focus:ring-blue-300 dark:focus:ring-blue-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2">
                                    Access
                                </button>
                            </div>
                        </router-link>
                    </div>
                </div>
            </div>
        </main>
    </AppLayout>
</template>
<script>
import APIs, { authAPIs, endpoints } from "@/configs/APIs";
import AppLayout from "@/layouts/default.vue";
import { computed, onMounted, ref } from "vue";
import { useStore } from "vuex";
export default ({
    components: {
    AppLayout,
},
    setup() {
        const store = useStore();
        const user = computed(() => store.state.user);
        const token = store.getters.token;
        const teacher = ref([]);

        const loadTeacher = async() => {
            try {
                let res = await authAPIs().get(`${endpoints.teacherInfor}/${user.value.id}`,{
                    headers: {
                    Authorization: `Bearer ${token}`
                }}
                );

                teacher.value = res.data;
                console.log(teacher.value);
                loadCourses();
            } catch(err){
                console.error(err);
            }
        }

        const courses = ref([]);

        const loadCourses = async() => {
            try {
                console.log("456")
                let res = await APIs.get(`${endpoints.courseTeacher}/${teacher.value.id}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                console.log("45678")
                courses.value = res.data;
                if(courses.value == null)
                    console.log("không có khóa học nào hiện tại đang đảm nhận!!");
                console.log(courses.value);
            } catch(err){
                console.error(err);
            }
        }

        onMounted(() => {
            loadTeacher();
            loadCourses();
        })

        return {
            teacher, user,
            courses
        }
    },
})
</script>
<style scoped>
    .font-large-1 {
        font-size: xx-large;
        font-weight: bold;
    }
</style>