<template>
    <AdminLayout>
        <main>
            <div class="flex justify-between">
                <div class="w-full md:w-1/2">
                    <form class="flex items-center">
                        <label for="simple-search" class="sr-only">Search</label>
                        <div class="relative w-full">
                            <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                                <svg aria-hidden="true" class="w-5 h-5 text-gray-500 dark:text-gray-400" fill="currentColor" viewbox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                    <path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd" />
                                </svg>
                            </div>
                            <input type="text" id="simple-search" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full pl-10 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Search" required="">
                        </div>
                    </form>
                </div>
                <div class="w-full md:w-auto flex flex-col md:flex-row space-y-2 md:space-y-0 items-stretch md:items-center justify-end md:space-x-3 flex-shrink-0">
                    <button type="button" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">
                        List Teachers
                    </button>
                </div>
            </div>
            <div class="mt-2 mb-5 font-large">
                <h2>APPROVAL FORM</h2>
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
                                    Position
                                </th>
                                <th scope="col" class="px-6 py-3">
                                    Username
                                </th>
                                <th scope="col" class="px-6 py-3">
                                    Status
                                </th>
                                <th scope="col" class="px-6 py-3">
                                    Reason
                                </th>
                            </tr>
                        </thead>
                    <tbody>
                        <template v-for="(item, id) in registers" :key="id">
                            <tr class="odd:bg-white odd:dark:bg-gray-900 even:bg-gray-50 even:dark:bg-gray-800 border-b dark:border-gray-700">
                                <td class="px-6 py-4" style="color: blue; text-decoration: underline;">
                                    <router-link 
                                        :to="{ name: 'RegisterFormDetails', params: { registerId: item.id || 'defaultId' } }"
                                    >
                                    {{item.id}}
                                    </router-link>
                                </td>
                                <td class="px-6 py-4">
                                    {{item.position}}
                                </td>
                                <td class="px-6 py-4">
                                    {{item.user?.username}}
                                </td>
                                <td class="px-6 py-4">
                                     <span :class="item.status ? 'text-green-500' : 'text-red-500'">
                                        {{ item.status ? 'Approved' : 'Pending' }}
                                    </span>
                                </td>
                                <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                    {{ truncateReason(item.reason) }}
                                </th>
                            </tr>
                        </template>
                    </tbody>
                </table>
                </div>
            </div>
        </main>
    </AdminLayout>
</template>
<script>
import { authAPIs, endpoints } from "@/configs/APIs";
import AdminLayout from "@/layouts/admin.vue";
import { onMounted, ref } from "vue";
// import { useRoute } from "vue-router";
import { useStore } from "vuex";

export default ({
    components: {
    AdminLayout,
},
    setup() {
        // const route = useRoute();
        const store = useStore();
        
        const registers = ref([]);
        

        const loadRegisters = async() => {
            try {
                let token = store.getters.token;
                let res = await authAPIs().get(endpoints.registerForm, {
                    headers: {
                         Authorization: `Bearer ${token}`,
                    }
                });
                registers.value = res.data;
                // console.log(res.data);
            } catch(err){
                console.error(err);
            }
        }

        const truncateReason = (reason) => {
            return reason.length > 50 ? reason.slice(0, 50) + '...' : reason;
        }

        onMounted(() => {
            loadRegisters();
        })

        return {
            AdminLayout,
            registers,
            truncateReason
        }
    },
    
})
</script>
