<template>
    <AppLayout>
        <main class="mt-10" style="margin-left: 13%">
            <div class="flex justify-around">
                <div>
                    <p style="font-weight: bold">{{assignment.name}}</p>
                    <div class="mt-8 ml-8">
                        <form @submit.prevent="addAnswerChoice">
                            <div v-for="(item, index) in questions" :key="index">
                                <p>Câu {{index + 1}}: {{item.content}}</p>
                                <div class="my-3 ml-8">
                                    <ul class="w-48 text-sm font-medium text-gray-900 bg-white border border-gray-200 rounded-lg dark:bg-gray-700 dark:border-gray-600 dark:text-white">
                                        <li
                                            v-for="(itemChoice, indexChoice) in questions[index].choices" :key="indexChoice"
                                            class="w-full border-b border-gray-200 rounded-t-lg dark:border-gray-600">
                                            <div class="flex items-center ps-3">
                                                <input 
                                                    :checked="getSelectedChoiceId(item.id) === itemChoice.id"
                                                    :id="`radio-choice-${index}-${indexChoice}`" 
                                                    type="radio" 
                                                    :value="itemChoice.content" 
                                                    :name="`radio-choice-${index}`"
                                                    @change="logChoiceId(item.id, itemChoice.id)"
                                                    class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-700 dark:focus:ring-offset-gray-700 focus:ring-2 dark:bg-gray-600 dark:border-gray-500">
                                                <label 
                                                    :for="`radio-choice-${index}-${indexChoice}`" 
                                                    class="w-full py-3 ms-2 text-sm font-medium text-gray-900 dark:text-gray-300">
                                                    {{itemChoice.content}}
                                                </label>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <button v-if="checkStatusDone"
                                disabled
                                style="
                                    width: 210px;
                                    margin-left: 3%;
                                    margin-top: 1%;"
                                type="submit"
                                class="text-white bg-slate-400 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800"
                            >
                                Submitted
                            </button>

                            <button v-else
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
                    ">Score</p>
                    </div>
                    <hr />
                    <div>
                        <p class="py-5 px-5">Target: </p>
                        <p class="py-5 px-5">Remark: </p>
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
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';

export default ({
    components: {
    AppLayout,
  }, setup() {
    const route = useRoute();
    const store = useStore();
    const assignmentId = route.params.assignmentId || route.query.assignmentId;
    const token = store.getters.token;

    const notify = () => {
      toast("Do this assignment successully!!", {
        "theme": "auto",
        "type": "success","autoClose": 3000,
        "dangerouslyHTMLString": true
        })
    }

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

    const questions = ref({});
    const loadQuestions = async() => {
        try {
            let res = await authAPIs().get(`${endpoints.question}/assignment/${assignmentId}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            })
            questions.value = res.data;
            // console.log(questions.value);
        } catch(err){
            console.error(err);
        }
    }

    const answerChoices = ref([]);
    const loadAnswerChoices = async() => {
        try {
            let res = await authAPIs().get(`${endpoints.answerDone}/assignment/${assignmentId}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            })
            answerChoices.value = res.data;
            console.log(answerChoices.value);
        } catch(err){
            console.error(err);
        }
    }

    const getSelectedChoiceId = (questionId) => {
        const answer = answerChoices.value.find(ans => ans.question_id === questionId);
        return answer ? answer.choice_id : null;
    };


    const checkStatusDone = ref([]);
    const loadCheckStatusDone = async(assignmentId) => {
        try {
            let res = await authAPIs().get(`${endpoints.assignmentDone}/assignment/${assignmentId}`,{
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            })

            checkStatusDone.value = res.data;
            // console.log(checkStatusDone.value);
        } catch(err) {
            console.error(err);
        }
    }

    const answers = ref([]);
    const addAnswerChoice = async() => {
        try {
            await authAPIs().post(`${endpoints.answerDone}/${assignmentId}`,
                answers.value
            ,{
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            })
            await addAssignmentDone();
            await loadCheckStatusDone(assignmentId);
            notify();
        } catch(err) {
            console.error(err);
        }
    }

    const addAssignmentDone = async() => {
        try {
            await authAPIs().post(`${endpoints.assignmentDone}`,
                {assignment_id: assignmentId}
            ,{
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            })
        } catch(err) {
            console.error(err);
        }
    }

    const logChoiceId = (questionId, choiceId) => {
        const existingAnswer = answers.value.find(ans => ans.questionId === questionId);
        if (existingAnswer) {
            existingAnswer.choiceId = choiceId;
        } else {
            answers.value.push({
            question_id: questionId,
            choice_id: choiceId,
            assignment_id: assignmentId
        });
        }
    };
        
    onMounted(() => {
        loadAssignment();
        loadQuestions();
        loadAnswerChoices();
        loadCheckStatusDone(assignmentId);
    })

    return {
        notify,
        assignmentId, assignment, questions, logChoiceId,
        answerChoices, checkStatusDone, getSelectedChoiceId,
        answers, addAnswerChoice
    }
  }
})
</script>
<style scoped>
    .border-score {
        border: 1px solid black;
        border-radius: 10px;
        width: 300px;
        height: 200px;
        padding: 10px
    }
</style>