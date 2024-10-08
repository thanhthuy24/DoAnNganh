<template>
    <AdminLayout>
        <main>
            <div>
                <p class="font-p1 mt-5 mb-5">CONFIRM TEACHER'S FORM</p>
                <p class="mb-5">Require to be Teacher</p>
            </div>
            <div style="width: 980px; border: 1px solid black;">
                <div>
                    <p class="mt-5 mb-5 ml-5 font-bold" >INFORMATION</p>
                </div>
                <div class="flex" :key="form.id">
                    <div>
                        <img :src="form.user?.avatar" 
                            class="w-32 h-32 rounded-full ml-5 mb-5" alt="User Avatar" />
                    </div>
                    <div class="ml-36">
                        <!-- <div class="py-8 px-4 mx-auto max-w-2xl lg:py-16"> -->
                            <form>
                                <div class="grid gap-4 sm:grid-cols-2 sm:gap-6">
                                    <div class="w-full">
                                        <label for="firstName" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Username</label>
                                        <input 
                                        disabled
                                        :value="form.user?.firstName"
                                        type="text" name="firstName" id="firstName" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Type your first name" required="">
                                    </div>
                                    <div class="w-full ">
                                        <label for="lastName" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Join date</label>
                                        <input 
                                        disabled
                                        :value="form.user?.createdDate"
                                        type="text" name="lastName" id="lastName" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Type your last name" required="">
                                    </div>
                                    <div class="w-full">
                                        <label for="firstName" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">First Name</label>
                                        <input type="text" name="firstName" id="firstName" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Type your first name" required="">
                                    </div>
                                    <div class="w-full ">
                                        <label for="lastName" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Last Name</label>
                                        <input type="text" name="lastName" id="lastName" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Type your last name" required="">
                                    </div>
                                    <div class="w-full">
                                        <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Email</label>
                                        <input type="email" name="email" id="email" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Type your first name" required="">
                                    </div>
                                    <div class="w-full ">
                                        <label for="phone" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Phone</label>
                                        <input type="number" name="phone" id="phone" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Type your last name" required="">
                                    </div>
                                    <div class="w-full ">
                                        <label for="status" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">User status</label>
                                        <input type="text" name="status" id="status" disabled class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" required="">
                                    </div>
                                    <div class="sm:col-span-2">
                                        <label for="position" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Position</label>
                                        <input 
                                        v-model="position"
                                         type="text" name="position" id="position" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Type your position you apply" required="">
                                    </div>
                                    <div class="sm:col-span-2">
                                        <label for="reason" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">The reason why you apply for being Teacher:</label>
                                        <textarea
                                        v-model="reason"
                                         id="reason" rows="8" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Reason"></textarea>
                                    </div>
                                </div>
                                <button
                                 type="submit" style="margin-left: 89%" class="mt-5 focus:outline-none text-white bg-green-700 hover:bg-green-800 focus:ring-4 focus:ring-green-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800">
                                    Send</button>
                            </form>
                        <!-- </div> -->
                    </div>
                </div>
            </div>
        </main>
    </AdminLayout>
</template>
<script>
import { authAPIs, endpoints } from "@/configs/APIs";
import AdminLayout from "@/layouts/admin.vue";
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";

export default ({
    components: {
    AdminLayout,
},
    setup() {
        const store = useStore();
        const route = useRoute();

        const registerId = route.params.registerId || route.query.registerId;

        const form = ref([]);

        const loadRegisterForm = async(registerId) => {
            try {
                let token = store.getters.token;
                let res = await authAPIs().get(`${endpoints.registerForm}/${registerId}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                form.value = res.data;
                console.log(form.value);
            } catch(err){
                console.error(err);
            }
        }

        onMounted(() => {
            loadRegisterForm(registerId);
        })
        
        return {
            AdminLayout,
            route,
            registerId,
            form
        }
    },
})
</script>
<style>
    .font-p1 {
        font-size: x-large;
        font-weight: bold;
    }
</style>