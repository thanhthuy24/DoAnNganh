<template>
    <AppLayout >
        <main class="ml-32 mt-10">
            <div style="width: 1400px; border: 1px solid black">
                <h1 class="ml-5">USER INFORMATION</h1>
                <div class="mt-10 ml-5 flex">
                    <div>
                        <img class="w-40 h-40 rounded-full" :src="require('@/assets/avatar-default.png')" alt="user photo">
                        <button type="button" class="text-white mt-5 ml-3 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">
                            Change avatar</button>
                    </div>
                    <div v-if="isLoggedIn" style="margin-left: 14%;">
                        <div class="flex">
                            <label for="username" class="mt-4 mr-5 block mb-2 text-sm font-medium text-gray-900 dark:text-white">Username</label>
                            <input type="text" 
                                v-model="user.username"
                             id="username" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="John" required />
                        </div>
                        <form class="mt-5">
                            <div class="grid gap-6 mb-6 md:grid-cols-2">
                                <div class="flex">
                                    <label style="width: 100px" for="firstName" class="mt-4 mr-5 block mb-2 text-sm font-medium text-gray-900 dark:text-white">First name</label>
                                    <input
                                    v-model="user.firstName" style="width: 300px"
                                     type="text" id="firstName" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="John" required />
                                </div>
                                <div class="flex">
                                    <label style="width: 100px" for="lastName" class="mt-4 mr-5 block mb-2 text-sm font-medium text-gray-900 dark:text-white">Last name</label>
                                    <input
                                        v-model="user.lastName" style="width: 300px"
                                     type="text" id="lastName" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Doe" required />
                                </div>
                                <div class="flex">
                                    <label style="width: 100px" for="email" class="mt-4 mr-5 block mb-2 text-sm font-medium text-gray-900 dark:text-white">Email</label>
                                    <input 
                                    v-model="user.email" style="width: 300px"
                                    type="email" id="email" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Flowbite" required />
                                </div>  
                                <div class="flex">
                                    <label style="width: 100px" for="phone" class="mt-4 mr-5 block mb-2 text-sm font-medium text-gray-900 dark:text-white">Phone number</label>
                                    <input 
                                    v-model="user.phone" style="width: 300px"
                                    type="tel" id="phone" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="123-45-678" pattern="[0-9]{3}-[0-9]{2}-[0-9]{3}" required />
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div style="width: 1400px; border: 1px solid black;" class="mt-16">
                <h1 class="ml-5 mt-5">YOUR COURSES</h1>
                <div class="grid grid-cols-3 gap-4 mb-4 ml-12 mt-10">
					<div v-for="e in enrollments" :key="e.id"
						class="w-full max-w-sm bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700"
						>
                        <router-link
							:to="{ name: 'CourseEnrolled', params: { courseId: e.course?.id || 'defaultId' } }"
                            >
                        <img class="p-8 rounded-t-lg" :src="e.course?.image" alt="product image" />
                    </router-link >
						<div class="px-5 pb-5 flex-grow">
							<router-link 
								class="text-xl font-semibold tracking-tight text-gray-900 dark:text-white" 
								:to="{ name: 'CourseEnrolled', params: { courseId: e.course?.id || 'defaultId' } }">
								{{e.course?.name}}
							</router-link>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </AppLayout>
</template>
<script>
import { authAPIs, endpoints } from "@/configs/APIs";
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
        const isLoggedIn = computed(() => store.state.isLoggedIn);
        const enrollments = ref([]);
        const token = store.getters.token;

        const loadEnrollments = async() => {
            try {
                const res = await authAPIs().get(endpoints.getEnrollments ,{
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            enrollments.value = res.data;
            console.log(enrollments.value);
            } catch(err) {
                console.error(err);
            }
        }

        onMounted(() => {
            loadEnrollments();
        })
        return {
            AppLayout, 
            user,
            isLoggedIn, enrollments,
            
        }
    },
})
</script>
