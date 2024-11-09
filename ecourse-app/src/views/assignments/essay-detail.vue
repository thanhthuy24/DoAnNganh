<template>
    <AppLayout>
        <main class="mt-10" style="margin-left: 13%">
            <div class="flex justify-around">
                <div>
                    <p style="font-weight: bold">{{assignment.name}}</p>
                    <div class="mt-8 ml-8">
                        <form>
                            <div v-for="(item, index) in questions" :key="index">
                                <label 
                                    for="description" 
                                    class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                                    Câu {{index + 1}}: {{item.content}}</label>
                                <textarea id="description" rows="8" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" placeholder="Your description here"></textarea>
                            </div>
                            <!-- <button v-if="checkStatusDone"
                                disabled
                                style="
                                    width: 210px;
                                    margin-left: 3%;
                                    margin-top: 1%;"
                                type="submit"
                                class="text-white bg-slate-400 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800"
                            >
                                Submitted
                            </button> -->

                            <button
                                style="
                                    width: 210px;
                                    margin-left: 3%;
                                    margin-top: 1%;"
                                type="submit"
                                class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800"
                            >
                                Submit
                            </button>
                        </form>
                        
                    </div>
                </div>
                <div class="border-score">
                    <div>
                        <p style="
                        padding: 10px;
                        margin-left: 35%;
                        font-size: large;
                        font-weight: bold;
                    ">Result</p>
                    </div>
                    <hr />
                    <div>
                        <p class="py-5 px-5">Score: <span style="color: red; font-weight: bold;"></span></p>
                        <p class="py-5 px-5">Remark: <span style="color: red; font-weight: bold;"></span></p>
                    </div>
                </div>
            </div>
        </main>
    </AppLayout>
</template>

<script>
import { authAPIs, endpoints } from "@/configs/APIs";
import AppLayout from "@/layouts/default.vue"
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";

export default ({
    components: {
    AppLayout,
  }, 
    setup() {
        const route = useRoute();
        const store = useStore();
        const assignmentId = route.params.assignmentId || route.query.assignmentId;
        const token = store.getters.token;

        const assignment = ref('');
        const loadAssignment = async() => {
            try {
                let res = await authAPIs().get(`${endpoints.assignment}/${assignmentId}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    }
                })
                assignment.value = res.data;
            } catch(err){
                console.error(err);
            }
        }

        onMounted(() => {
            loadAssignment();
        })

        return {
            assignmentId, token, 
            assignment, 
        }
    },
})
</script>
