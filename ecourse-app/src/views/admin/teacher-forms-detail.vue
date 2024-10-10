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
                            <form @submit.prevent="approvalRegister(form.id, form.position, form.reason, form.user?.id)">
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
                                        :value="moment(form.user?.createdDate).format('DD/MM/YYYY')"
                                        type="text" name="lastName" id="lastName" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Type your last name" required="">
                                    </div>
                                    <div class="w-full">
                                        <label for="firstName" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">First Name</label>
                                        <input 
                                        disabled
                                        :value="form.user?.firstName"
                                        type="text" name="firstName" id="firstName" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Type your first name" required="">
                                    </div>
                                    <div class="w-full ">
                                        <label for="lastName" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Last Name</label>
                                        <input
                                        disabled
                                        :value="form.user?.lastName"
                                         type="text" name="lastName" id="lastName" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Type your last name" required="">
                                    </div>
                                    <div class="w-full">
                                        <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Email</label>
                                        <input 
                                        disabled
                                        :value="form.user?.email"
                                        type="email" name="email" id="email" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Type your first name" required="">
                                    </div>
                                    <div class="w-full ">
                                        <label for="phone" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Phone</label>
                                        <input 
                                        disabled
                                        :value="form.user?.phone"
                                        type="number" name="phone" id="phone" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Type your last name" required="">
                                    </div>
                                    <div class="w-full ">
                                        <label for="status" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">User status</label>
                                        <input :class="form.user?.isActive ? 'text-green-500' : 'text-red-500'"
                                        disabled
                                        :value="form.user?.isActive ? 'Active' : 'Inactive'" 
                                        type="text" name="isActive" id="isActive" class="font-bold bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" required="">
                                    </div>
                                    <div class="sm:col-span-2">
                                        <label for="position" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Position</label>
                                        <input 
                                        disabled
                                        :value="form.position"
                                         type="text" name="position" id="position" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Type your position you apply" required="">
                                    </div>
                                    <div class="sm:col-span-2">
                                        <label for="reason" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">The reason why you apply for being Teacher:</label>
                                        <textarea
                                        disabled
                                        :value="form.reason"
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
import moment from 'moment';

import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';

export default ({
    components: {
    AdminLayout,
},
    setup() {
        const store = useStore();
        const route = useRoute();

        const notify = () => {
            toast("Check successul!!", {
                "theme": "auto",
                "type": "success","autoClose": 3000,
                "dangerouslyHTMLString": true
                })
            }

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
                // position.value = form.value.position;
                // reason.value = form.value.reason;
                // console.log(position);
                // console.log(reason);
            } catch(err){
                console.error(err);
            }
        }

        const position = ref('');
        const reason = ref('');
        const userId = ref('');
        // const status = ref('');

        const approvalRegister = async(registerId, pos, rea, id) => {
            try {
                let token = store.getters.token;
                await authAPIs().patch(`${endpoints.approvalForm}/${registerId}`, 
                {
                    position: pos,
                    reason: rea,
                    user_id: id,
                    status: true
                },{
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                console.log("456");
                notify();
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
            form, 
            moment,
            approvalRegister,
            notify, position, reason, userId
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