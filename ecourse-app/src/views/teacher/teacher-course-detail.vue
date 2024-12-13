<template>
    <AppLayout>
        <main class="ml-20 mt-10">
            <div class="p-4 mb-4 mr-10 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400" role="alert">
                <span class="font-medium">Choose a video in lessons to follow!!</span> 
            </div>
            <div class="flex">
                <div style="width: 980px; border: 1px solid black;" >
                    <div>
                        <video ref="videoPlayer" style="width: 980px" controls @ended="handleVideoEnded(currentVideoId)">
                            <source :src="videoUrl" type="video/mp4">
                            Your browser does not support the video tag.
                        </video>
                    </div>
                    <div>
                        <div class="mb-4 border-b border-gray-200 dark:border-gray-700">
                            <ul class="flex flex-wrap -mb-px text-sm font-medium text-center" id="default-tab" data-tabs-toggle="#default-tab-content" role="tablist">
                                <li class="me-2" role="presentation">
                                    <button class="inline-block p-4 border-b-2 rounded-t-lg" id="profile-tab" data-tabs-target="#profile" type="button" role="tab" aria-controls="profile" aria-selected="false">Assignments</button>
                                </li>
                                <li class="me-2" role="presentation">
                                    <button class="inline-block p-4 border-b-2 rounded-t-lg hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300" id="settings-tab" data-tabs-target="#settings" type="button" role="tab" aria-controls="settings" aria-selected="false">Forum</button>
                                </li>
                            </ul>
                        </div>

                        <div id="default-tab-content">
                            <div class="hidden p-4 rounded-lg dark:bg-gray-800" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                                <div class="flex justify-between">
                                    <button type="button" style="background-color: #608BC1" class="ml-5 text-white hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 font-medium rounded-full text-sm px-5 py-2.5 text-center me-2 mb-3
                                        dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                                        Create Assignment
                                    </button>
                                    <button type="button" style="background-color: #BC7C7C" class="mr-8 text-white hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 font-medium rounded-full text-sm px-5 py-2.5 text-center me-2 mb-3
                                        dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                                        List Assignments
                                    </button>
                                </div>
                                
                                <div 
                                    v-for="(item, index) in assignments" :key="index"
                                    class="border-assignment ml-5"
                                >
                                    <div class="flex justify-between">
                                        <!-- <router-link
                                             v-if="item.tag && item.tag.id === 5"
                                            :to="{ name: 'AssignmentDetail', params: { assignmentId: item.id || 'defaultId' } }"
                                            > -->
                                            <p style="font-weight: bold">{{item.name}}</p>
                                        <!-- </router-link>  -->
                                        <!-- <router-link
                                             v-else
                                            :to="{ name: 'AssignmentEssayDetail', params: { assignmentId: item.id || 'defaultId' } }"
                                            >
                                            <p style="font-weight: bold">{{item.name}}</p>
                                        </router-link> -->
                                        <button
                                            style="width: 120px"
                                            type="button" 
                                            class="flex focus:outline-none text-white bg-green-700 hover:bg-green-800 focus:ring-4 focus:ring-green-300 font-medium rounded-lg text-sm 
                                            px-5 py-2.5 me-2 mb-2 dark:bg-green-600 dark:hover:bg-green-700
                                            dark:focus:ring-green-800">
                                            <svg class="w-6 h-6 text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" viewBox="0 0 24 24">
                                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8.5 11.5 11 14l4-4m6 2a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"/>
                                            </svg>
 
                                            <p class="ml-2 mt-1">{{totalAssignmentDoneNum[item.id]}} / {{total}}</p>
                                        </button>
                                    </div>
                                    <div class="mt-5 flex justify-between">
                                        <p>Created date: <span style="color: red">
                                            {{formatDate(item.createdDate)}}
                                            </span></p>
                                        <p>Deadline: <span style="color: red">
                                            {{formatDate(item.dueDate)}}
                                            </span>
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div
                             class="hidden p-4 rounded-lg dark:bg-gray-800" id="settings" role="tabpanel" aria-labelledby="settings-tab">
                                5555
                            </div>
                        </div>
                    </div>
                </div>
                <div style="border: 1px solid black; width: 500px" class="mt-5 ml-5">
                        <div 
                            v-for="(item, index) in lessons" 
                            :key="index"
                        >
                            <h2 :id="`accordion-color-heading-${index}`">
                                <button 
                                    type="button" 
                                    class="flex items-center justify-between w-full p-5 font-small  text-gray-500 border border-b-0 border-gray-200  focus:ring-4 focus:ring-blue-200 dark:focus:ring-blue-800 dark:border-gray-700 dark:text-gray-400 hover:bg-blue-100 dark:hover:bg-gray-800 gap-3"
                                    @click="toggleAccordion(index)"
                                    :aria-expanded="activeAccordion === index"
                                >
                                <!-- @click="loadComments(item.id, currentPage.value, itemsPerPage.value)" -->
                                    <span 
                                         
                                    >
                                    {{ item.name }}</span>
                                    <svg 
                                       
                                        class="w-3 h-3" 
                                        :class="{'rotate-180': activeAccordion === index}" 
                                        aria-hidden="true" 
                                        xmlns="http://www.w3.org/2000/svg" 
                                        fill="none" 
                                        viewBox="0 0 10 6"
                                    >
                                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5L5 1 1 5" />
                                    </svg>
                                </button>
                            </h2>
                            <div v-if="item.videos && item.videos.length > 0"
                                :id="`accordion-color-body-${index}`" 
                                v-show="activeAccordion === index" 
                                class="p-5 border border-b-0 border-gray-200 dark:border-gray-700 dark:bg-gray-900"
                                :aria-labelledby="`accordion-color-heading-${index}`"
                            >
                            <div 
                            class="flex"
                            v-for="(video, index1) in item.videos" :key="index1">
                                <p 
                                    style="cursor: pointer; "
                                    class="mb-5 text-gray-500 dark:text-gray-400 "
                                    @click="handleVideo(video)"
                                >
                                    Video {{index1 + 1}} 
                                </p>
                            </div>
                        </div>
                    </div>
                    <hr/>
                </div>
            </div>
        </main>
    </AppLayout>
</template>
<script>
import { authAPIs, endpoints } from "@/configs/APIs";
import AppLayout from "@/layouts/default.vue";
import { format } from "date-fns";
import { computed, onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";

export default ({
    components: {
        AppLayout,
    },
    setup() {
        const route = useRoute();
        const store = useStore();
        const user = computed(() => store.state.user);
        const token = store.getters.token;
        
        const courseId = route.params.courseId || route.query.courseId;

        const assignments = ref([]);
        const loadAssignments = async() => {
            try {
                let res = await authAPIs().get(`${endpoints.assignment}/course/${courseId}`,{
                    headers: {
                        Authorization: `Bearer ${token}`,
                    }
                })
                assignments.value = res.data;
                for (let assignment of assignments.value) {
                    await loadTotalAssignmentsDone(assignment.id);
                }
                // console.log(assignments.value);
            } catch(err) {
                console.error(err);
            }
        }

        const totalAssignmentDoneNum = ref({});
        const loadTotalAssignmentsDone = async(assignmentId) => {
            try {
                let res = await authAPIs().get(`${endpoints.assignmentDone}/assignment/${assignmentId}/count`,{
                    headers: {
                        Authorization: `Bearer ${token}`,
                    }
                })
                totalAssignmentDoneNum.value[assignmentId] = res.data;
                console.log(totalAssignmentDoneNum.value);
            } catch(err) {
                console.error(err);
            }
        }

        const total = ref(0);
        const loadTotalEnrollment = async() => {
            try {
                let res = await authAPIs().get(`${endpoints.enrollments}/course/${courseId}/count`,{
                    headers: {
                        Authorization: `Bearer ${token}`,
                    }
                })
                total.value = res.data;
                console.log(total.value);
            } catch(err) {
                console.error(err);
            }
        }

        const videoUrl = ref('');
        const lessons = ref([]);
        const loadLessons = async(courseId) => {
            try {
                let token = store.getters.token;
                let res = await authAPIs().get(`${endpoints.lessons}/${courseId}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                lessons.value = res.data;
                // console.log(lessons.value[0].videos);
            } catch(ex){
                console.error(ex);
            }
        }

        // const lId = ref('');
        // const loadComments = async(lessonId, page = currentPage.value, limit = itemsPerPage.value) => {
        //     try {
        //         let token = store.getters.token;
        //         let res = await authAPIs().get(`${endpoints.comments}/${lessonId}?page=${page}&limit=${limit}`, {
        //             headers: {
        //                 Authorization: `Bearer ${token}`,
        //             },
        //         });
        //         comments.value = res.data.comments;
        //         console.log(comments.value)
        //         lId.value = lessonId;
        //         totalPages.value = res.data.totalPages;
                
        //         // Gọi loadLike cho mỗi comment
        //         for (const comment of comments.value) {
        //             await loadLike(comment.id); // Gọi loadLike với commentId
        //             await loadReplyComment(comment);
        //         }

        //     } catch(ex){
        //         console.error(ex);
        //     }
        // }

        const currentVideoId = ref(null);
        const handleVideo = (video) => {
            // console.log("5454")
            videoUrl.value = video.name;
            currentVideoId.value = video.id;
        }

        const activeAccordion = ref(null);

        const toggleAccordion = (index) => {
            activeAccordion.value = activeAccordion.value === index ? null : index;
        };

        const videoPlayer = ref(null);
        watch(videoUrl, () => {
            if (videoPlayer.value) {
                videoPlayer.value.load(); // Load the new video source
            }
        }); 

        const formatDate = (timestamp) => {
            try {
                const date = new Date(timestamp); // Chuyển đổi timestamp thành đối tượng Date
                return format(date, 'dd/MM/yyyy'); // Định dạng ngày theo kiểu dd/MM/yyyy
            } catch (error) {
                console.error("Lỗi khi định dạng ngày: ", error);
                return "Invalid date";
            }
        };

        const isExpired = (dueDate) => {
            const now = new Date();
            // console.log(now);
            return dueDate > now.getTime();
        }

        onMounted(() => {
            loadAssignments();
            loadLessons(courseId);
            loadTotalEnrollment();
        })
        return {
            courseId, user, token, isExpired,
            assignments, 
            videoUrl, currentVideoId, 
            lessons, 
            toggleAccordion, formatDate, activeAccordion,
            total, totalAssignmentDoneNum,
            handleVideo, 
        }
    },
})
</script>
<style scoped>
    .border-assignment {
            width: 900px;
            background-color: whitesmoke;
            margin-bottom: 10px;
            border-radius: 10px;
            padding: 20px;
        }
</style>
