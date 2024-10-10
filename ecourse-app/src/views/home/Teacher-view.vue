<template>
    <AppLayout>
        <main class="ml-44">
            <h1>teacher here --- </h1>
            <div style="width: 1400px; border: 1px solid black;" class="mt-16">
                <h1 class="ml-5 mt-5">YOUR COURSES</h1>
                <div class="grid grid-cols-3 gap-4 mb-4 ml-12 mt-10">
					<div v-for="e in courses" :key="e.id"
						class="w-full max-w-sm bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700"
						>
                        <!-- <router-link
							:to="{ name: 'CourseEnrolled', params: { courseId: e.course?.id || 'defaultId' } }"
                            > -->
                        <img class="p-8 rounded-t-lg" :src="e.image" alt="product image" />
                    <!-- </router-link > -->
						<div class="px-5 pb-5 flex-grow">
							<!-- <router-link 
								class="text-xl font-semibold tracking-tight text-gray-900 dark:text-white" 
								:to="{ name: 'CourseEnrolled', params: { courseId: e.course?.id || 'defaultId' } }"> -->
								{{e.name}}
							<!-- </router-link> -->
                        </div>
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
                let res = await APIs.get(`${endpoints.courseTeacher}/${teacher.value[0].id}`, {
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
            // loadCourses(teacherId);
        })

        return {
            teacher,
            courses
        }
    },
})
</script>
