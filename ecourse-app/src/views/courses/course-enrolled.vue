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
                                    <button class="inline-block p-4 border-b-2 rounded-t-lg" id="profile-tab" data-tabs-target="#profile" type="button" role="tab" aria-controls="profile" aria-selected="false">Overview</button>
                                </li>
                                <li class="me-2" role="presentation">
                                    <button class="inline-block p-4 border-b-2 rounded-t-lg hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300" id="dashboard-tab" data-tabs-target="#dashboard" type="button" role="tab" aria-controls="dashboard" aria-selected="false">Q&A</button>
                                </li>
                                <li class="me-2" role="presentation">
                                    <button class="inline-block p-4 border-b-2 rounded-t-lg hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300" id="settings-tab" data-tabs-target="#settings" type="button" role="tab" aria-controls="settings" aria-selected="false">Forum</button>
                                </li>
                            </ul>
                        </div>
                        <div id="default-tab-content">
                            <div class="hidden p-4 rounded-lg dark:bg-gray-800" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                                <div>
                                    <p class="font-style-course">{{courses.name}}</p>    
                                    <p class="mt-5">Created date: <span style="color: grey">{{formatDate(courses.createdDate)}}</span></p>
                                    <p class="mt-5">Intructor: {{courses.teacher?.user?.firstName}} {{courses.teacher?.user?.lastName}}</p>
                                </div> <hr class="mt-5 mb-5" />   
                                <div class="flex justify-around">
                                    <div>
                                        <p style="font-weight: bold">Detailed ratings</p>
                                        <div v-for="starCount in 5" :key="starCount">
                                            <div class="mt-7 h-1.5 w-80 rounded-full bg-gray-200 dark:bg-gray-700">
                                                <div class="h-1.5 rounded-full bg-yellow-300" :style="{ width: progressByStar[6 - starCount] + '%' }"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div style="margin-top: 25px;">
                                        <div v-for="starCount in 5" :key="starCount" class="mt-3 flex">
                                            <svg v-for="i in (6 - starCount)" :key="i" class="h-6 w-6 text-yellow-300" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 24 24">
                                                <path d="M13.849 4.22c-.684-1.626-3.014-1.626-3.698 0L8.397 8.387l-4.552.361c-1.775.14-2.495 2.331-1.142 3.477l3.468 2.937-1.06 4.392c-.413 1.713 1.472 3.067 2.992 2.149L12 19.35l3.897 2.354c1.52.918 3.405-.436 2.992-2.15l-1.06-4.39 3.468-2.938c1.353-1.146.633-3.336-1.142-3.477l-4.552-.36-1.754-4.17Z" />
                                            </svg>
                                            <div>
                                                <span 
                                                    class="ml-5"
                                                    style="color: gold; font-weight: bold">
                                                ({{rates[6 - starCount]}})</span>
                                            </div>
                                            
                                        </div>
                                    </div>
                                    
                                    <div>
                                        <p style="font-weight: bold">Average review</p>
                                        <div class="box-average">
                                            <div class="mt-10">
                                                <p
                                                    style="font-size: xx-large; font-weight: bold; margin-left: 35%;" >
                                                    {{average}}
                                                </p>
                                            </div>
                                            <div class="flex ml-5 mb-3 mt-1">
                                                <svg v-for="n in Math.floor(average)" :key="n" class="h-6 w-6 text-yellow-300" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 24 24">
                                                    <path d="M13.849 4.22c-.684-1.626-3.014-1.626-3.698 0L8.397 8.387l-4.552.361c-1.775.14-2.495 2.331-1.142 3.477l3.468 2.937-1.06 4.392c-.413 1.713 1.472 3.067 2.992 2.149L12 19.35l3.897 2.354c1.52.918 3.405-.436 2.992-2.15l-1.06-4.39 3.468-2.938c1.353-1.146.633-3.336-1.142-3.477l-4.552-.36-1.754-4.17Z"/>
                                                </svg>

                                                <svg v-if="average % 1 >= 0.5" class="h-6 w-6 text-yellow-300" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 24 24">
                                                    <defs>
                                                        <linearGradient id="half">
                                                            <stop offset="50%" stop-color="currentColor"/>
                                                            <stop offset="50%" stop-color="transparent"/>
                                                        </linearGradient>
                                                    </defs>
                                                    <path d="M13.849 4.22c-.684-1.626-3.014-1.626-3.698 0L8.397 8.387l-4.552.361c-1.775.14-2.495 2.331-1.142 3.477l3.468 2.937-1.06 4.392c-.413 1.713 1.472 3.067 2.992 2.149L12 19.35l3.897 2.354c1.52.918 3.405-.436 2.992-2.15l-1.06-4.39 3.468-2.938c1.353-1.146.633-3.336-1.142-3.477l-4.552-.36-1.754-4.17Z" fill="url(#half)"/>
                                                </svg>
                                            </div>
                                            <span style="font-size: large; margin-left: 35%;">
                                                Rating
                                            </span>
                                        </div>
                                        <router-link 
                                        :to="{ name: 'CourseRating', params: { courseId: courseId || 'defaultId' } }"
                                        >
                                            <button 
                                                type="button"
                                                class="mt-5 ml-4 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800"
                                                >
                                                View a review  
                                            </button>
                                        </router-link>
                                    </div>
                                </div>
                            </div>
                            <div 
                            class="hidden p-4 rounded-lg dark:bg-gray-800" id="dashboard" role="tabpanel" aria-labelledby="dashboard-tab">
                                <div 
                                    v-for="(item, index) in assignments" :key="index"
                                    class="border-assignment"
                                >
                                    <div class="flex justify-between">
                                        <router-link
                                            :to="{ name: 'AssignmentDetail', params: { assignmentId: item.id || 'defaultId' } }"
                                            >
                                            <p style="font-weight: bold">{{item.name}}</p>
                                        </router-link>
                                        <button v-if="assignmentDone[item.id]"
                                            :style="{
                                                width: '100px',
                                                borderRadius: '5px',
                                                backgroundColor: 'green',
                                                color: 'white' 
                                            }" >
                                            Done
                                        </button>
                                        <button v-else
                                            :style="{
                                                width: '100px',
                                                borderRadius: '5px',
                                                backgroundColor: isExpired(item.dueDate) ? 'blue' : 'red',
                                                color: 'white' 
                                            }" >
                                            {{isExpired(item.dueDate) ? "Do now!!" : "Time out"}}
                                        </button>
                                    </div>
                                    <div class="mt-5">
                                        <p>Deadline: <span style="color: red">
                                            {{formatDate(item.dueDate)}}
                                            </span></p>
                                    </div>
                                </div>
                            </div>
                            <div
                             class="hidden p-4 rounded-lg dark:bg-gray-800" id="settings" role="tabpanel" aria-labelledby="settings-tab">
                                
                                <div v-if="lId">
                                    <p class="mb-5">Give me some questions</p>
                                    <form @submit.prevent="addComment">
                                        <textarea
                                            v-model="newCmt"
                                            id="content" 
                                            rows="3" 
                                            class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-primary-500 focus:border-primary-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-primary-500 dark:focus:border-primary-500" 
                                            placeholder="Comment this lesson"></textarea>
                                
                                        <button 
                                            style="margin-left: 90%;" type="submit"
                                            class="text-white mt-3 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">
                                        Submit</button>
                                    </form>
                                </div>
                                <div v-else>
                                    <div class="p-4 mb-4 mr-10 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400" role="alert">
                                        <span class="font-medium">Choose a lesson want to comment and review </span> 
                                    </div>
                                </div>
                                <div v-for="c in comments" :key="c.id" class="mt-5">
                                    <div class="flex">
                                        <img class="w-9 h-9 rounded-full" :src="c.user?.avatar" alt="user photo">
                                        <p class="ml-5 mt-1">{{c.user?.username}}</p>
                                        <p class="ml-5 mt-1">{{formatDate(c.createdDate)}}</p>
                                    </div>
                                    <div>
                                        <input :value="c.content"
                                            style="width: 700px; margin-left: 6%; margin-top: 10px;"
                                            disabled
                                            type="text" id="default-input" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                                        <div class="flex">
                                            <div class="flex mt-3" style="margin-left: 7%">
                                                <button @click="addLike(c.id)">
                                                    <svg class="mr-2 w-6 h-6 text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" viewBox="0 0 24 24">
                                                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12.01 6.001C6.5 1 1 8 5.782 13.001L12.011 20l6.23-7C23 8 17.5 1 12.01 6.002Z"/>
                                                    </svg>
                                                </button>
                                                <span class="mt-2">{{ newLikes[c.id] !== undefined ? newLikes[c.id] : 0 }} likes</span>
                                            </div>
                                            <div class="mt-2">
                                                <button @click="toggleReplyInput(c.id)">
                                                    <svg class="mt-3 ml-5 w-6 h-6 text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" viewBox="0 0 24 24">
                                                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 10.5h.01m-4.01 0h.01M8 10.5h.01M5 5h14a1 1 0 0 1 1 1v9a1 1 0 0 1-1 1h-6.6a1 1 0 0 0-.69.275l-2.866 2.723A.5.5 0 0 1 8 18.635V17a1 1 0 0 0-1-1H5a1 1 0 0 1-1-1V6a1 1 0 0 1 1-1Z"/>
                                                    </svg>
                                                </button>
                                            </div>
                                            <div
                                                style="cursor: pointer; " 
                                                class="mt-5 ml-5"
                                                @click="loadReplyComment(c.id)"
                                                >
                                                <p
                                                    style="color: grey; font-size: 12px"
                                                >Read reply comments!</p>
                                            </div>
                                            
                                        </div>
                                    </div>
                                    <div v-if="replyInputVisible[c.id]" class="mt-3" style="margin-left: 7%">
                                        <form @submit.prevent="addReply(c.id)">
                                        <input v-model="replyText[c.id]" 
                                            style="width: 600px; margin-top: 10px;" 
                                            type="text" 
                                            placeholder="Write your reply..." 
                                            class="border border-gray-300 text-sm rounded-lg p-2.5">
                                            <button type="submit" class="ml-3 bg-blue-500 text-white rounded-lg px-4 py-2">
                                                Submit
                                            </button>
                                        </form>
                                    </div>
                                    <div 
                                        v-if="listReply[c.id] && listReply[c.id].length > 0"
                                        style="margin-left: 18%"
                                        class="mt-2"    
                                    >
                                        <div v-for="reply in listReply[c.id]" :key="reply.id" class="mt-3">
                                            <div class="flex">
                                                <img class="w-9 h-9 rounded-full" :src="reply.user?.avatar" alt="user photo">
                                                <p class="ml-5 mt-2">{{reply.user?.username}}</p>
                                                <p class="ml-5 mt-2">{{formatDate(reply.createdDate)}}</p>
                                            </div>
                                            <div>
                                                <input :value="reply.content"
                                                    style="width: 551px; margin-left: 8%; margin-top: 10px; background-color: #FFF6F6"
                                                    disabled
                                                    type="text" id="default-input" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                                        
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div v-if="lId" class="ml-32 mt-10">
                                    <pagination
                                        :currentPage="currentPage"
                                        :totalPages="totalPages"
                                        :itemsPerPage="itemsPerPage"
                                        @page-changed="handlePageChange"
                                    />
                                </div>
                                
                            </div>
                        </div>

                    </div>
                </div>
                <div class="mr-10">
                    <div style="width: 500px" class="ml-5 bg-gray-200 rounded-full h-4 dark:bg-gray-700">
                        <div 
                            class="bg-blue-600 text-xs font-small text-blue-100 text-center p-0.5 leading-none rounded-full" 
                            :style="{ width: progress ? progress + '%' : '0%' }"> {{progress}}%</div>
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
                                    <span 
                                         @click="loadComments(item.id, currentPage.value, itemsPerPage.value)"
                                    >{{ item.name }}</span>
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
                                
                                <svg v-if="isVideoComplete(video.id)"
                                    style="color: green"
                                    class="ml-5 w-6 h-6 text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 24 24">
                                    <path fill-rule="evenodd" d="M2 12C2 6.477 6.477 2 12 2s10 4.477 10 10-4.477 10-10 10S2 17.523 2 12Zm13.707-1.293a1 1 0 0 0-1.414-1.414L11 12.586l-1.793-1.793a1 1 0 0 0-1.414 1.414l2.5 2.5a1 1 0 0 0 1.414 0l4-4Z" clip-rule="evenodd"/>
                                </svg>
                            </div>
                               
                            </div>
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
import { onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
import Pagination from '@/views/Pagination-view.vue'
import { format } from 'date-fns';

export default({
    components: {
    AppLayout,
    Pagination
  },
    setup() {
        const route = useRoute();
        const store = useStore();

        const courseId = route.params.courseId || route.query.courseId;
        const lessons = ref([]);
        const comments = ref([]);
        
        const totalPages = ref(0);
        const currentPage = ref(0);
        const itemsPerPage = ref(12);

        const courses = ref({});
        const loadCourse = async() => {
            try {
                let res = await APIs.get(`${endpoints.courses}/${courseId}`);
                courses.value = res.data;
                console.log(courses.value);
            } catch(err){
                console.error(err);
            }
        }
        
        const videoUrl = ref('');
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

        const currentVideoId = ref(null);
        const handleVideo = (video) => {
            videoUrl.value = video.name;
            currentVideoId.value = video.id;
        }

        const rates = ref({});
        const loadCountRating = async(rate) => {
            try {
                let res = await authAPIs().get(`${endpoints.rating}/${courseId}/rate/${rate}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
                );
                rates.value[rate] = res.data;
            } catch(err) {
                console.error(err);
            }
        }

        const progress = ref(null);
        const loadProgress = async() => {
            try {
                let res = await authAPIs().post(`${endpoints.progress}/course/${courseId}`, null, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    }
                })

                progress.value = Math.round(res.data);
                // console.log(progress.value);
            } catch(err) {
                console.error(err);
            }
        }

        const average = ref('');
        const loadAverage = async() => {
            try {
                let res = await authAPIs().get(`${endpoints.rating}/${courseId}`,{
                    headers: {
                        Authorization: `Bearer ${token}`,
                    }
                })

                average.value = res.data;
                console.log(average.value);
            } catch(err) {
                console.error(err);
            }
        }

        const assignments = ref({});
        const loadAssignments = async() => {
            try {
                let res = await authAPIs().get(`${endpoints.assignment}/course/${courseId}`,{
                    headers: {
                        Authorization: `Bearer ${token}`,
                    }
                })

                assignments.value = res.data;
                // console.log(assignments.value);
                assignments.value.forEach(item => {
                    loadAssignmentDone(item.id);
                });
            } catch(err) {
                console.error(err);
            }
        }

        const assignmentDone = ref({});
        const loadAssignmentDone = async(assignmentId) => {
            try {
                let res = await authAPIs().get(`${endpoints.assignmentDone}/assignment/${assignmentId}`,{
                    headers: {
                        Authorization: `Bearer ${token}`,
                    }
                })

                assignmentDone.value[assignmentId] = res.data;
                console.log(assignmentDone.value);
            } catch(err) {
                console.error(err);
            }
        }

        const lId = ref('');
        const loadComments = async(lessonId, page = currentPage.value, limit = itemsPerPage.value) => {
            try {
                let token = store.getters.token;
                let res = await authAPIs().get(`${endpoints.comments}/${lessonId}?page=${page}&limit=${limit}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                comments.value = res.data.comments;
                console.log(comments.value)
                lId.value = lessonId;
                totalPages.value = res.data.totalPages;
                
                // Gọi loadLike cho mỗi comment
                for (const comment of comments.value) {
                    await loadLike(comment.id); // Gọi loadLike với commentId
                    await loadReplyComment(comment);
                }

            } catch(ex){
                console.error(ex);
            }
        }

        const newCmt = ref('');
        const addComment = async() => {
            console.log('Button clicked');
            try {
                let token = store.getters.token;
                let res = await authAPIs().post(`${endpoints.comments}`, 
                { content: newCmt.value,
                    lesson_id: lId.value,
                },
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                // comments.value.unshift(res.data);
                console.log('Comment added:', res.data);
                 await loadComments(lId.value, currentPage.value, itemsPerPage.value); // In ra kết quả trả về từ API
                newCmt.value = '';
            } catch(ex){
                console.error(ex);
            }
        }

        const addLike = async(commentId) => {
            console.log('Like clicked');
            try {
                let token = store.getters.token;
                let res = await authAPIs().post(`${endpoints.like}`, 
                { 
                    comment_id: commentId,
                },
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                // comments.value.unshift(res.data);
                console.log('Like added:', res.data);
                 await loadLike(commentId);
            } catch(ex){
                console.error(ex);
            }
        }

        let token = store.getters.token;
        const newLikes = ref([]); // Sử dụng ref để theo dõi sự thay đổi của newLikes
        const loadLike = async(commentID) => {
            try {
                
                let res = await authAPIs().get(`${endpoints.like}/${commentID}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                newLikes.value[commentID] = res.data || 0;
            } catch (ex) {
                console.error(ex);
            }
        };

        // const replyCmt = ref('');
        const addReply = async(commentId) => {
            console.log('reply clicked ' + commentId);
            try {
                const replyContent = replyText.value[commentId] || ''; // Get the reply text for this comment

                if (replyContent.trim() === '') {
                    return alert('Please enter a reply!');
                }

                await authAPIs().post(`${endpoints.reply}`, 
                { 
                    comment_id: commentId,
                    content: replyContent
                },
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                 await loadReplyComment(commentId);
                 replyContent.value = '';
            } catch(ex){
                console.error(ex);
            }
        }

        const listReply = ref([]);
        const loadReplyComment = async (commentId) => {
            try {
                let res = await authAPIs().get(`${endpoints.reply}/${commentId}`, {
                    headers: {
                        Authorization: `Bearer ${store.getters.token}`,
                    },
                });
                listReply.value[commentId] = res.data; // Attach replies to the comment object
                console.log("reply comment: ", res.data);
            } catch (err) {
                console.error(err);
            }
        };
        
        const replyInputVisible = ref({});
        const replyText = ref({});

        const toggleReplyInput = (commentId) => {
            replyInputVisible.value = {
                ...replyInputVisible.value,
                [commentId]: !replyInputVisible.value[commentId], // Toggle visibility for this comment
            };
        };

        const user = store.getters.user;

        const videosCompleted = ref([]);
        const loadVideosCompleted = async() => {
            try {
                let res = await authAPIs().get(`${endpoints.listVideosCompleted}/user/${user.id}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                })
                videosCompleted.value = res.data;
                loadProgress();
                // console.log(videosCompleted.value[0].video.id);
            } catch(err) {
                console.error(err);
            }
        }
        
        const isVideoComplete = (videoId) => {
            if (!videosCompleted.value || videosCompleted.value.length === 0) {
                return false; // No videos have been completed
            }
            return videosCompleted.value.some(v => v.video?.id === videoId);
        }

        const handleVideoEnded = async(videoId) => {
            try {
                await authAPIs().post(endpoints.listVideosCompleted, {
                    video_id: videoId
                }, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    }
                })
                // console.log("done!");
                loadVideosCompleted();
                
            } catch(err) {
                console.error("Video đã được lưu thành công trước đó!");
            }
        };
        
        const progressByStar = ref({});
        const loadProgressByStar = async(rate) => {
            try {
                let res = await authAPIs().get(`${endpoints.rating}/${courseId}/rating/${rate}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
                );
                progressByStar.value[rate] = res.data;
            } catch(err) {
                console.error(err);
            }
        }

        const handlePageChange = (lesonId, newPage) => {
            currentPage.value = newPage;
            loadComments(lesonId, currentPage.value, itemsPerPage.value); // Gọi lại để lấy dữ liệu cho trang mới
        };

        const activeAccordion = ref(null);

        const toggleAccordion = (index) => {
            activeAccordion.value = activeAccordion.value === index ? null : index;
        };

        onMounted(() => {
            loadLessons(courseId);
            if (lessons.value.length > 0) {
                // Giả sử bạn muốn lấy comments của bài học đầu tiên
                loadComments(lessons.value[0].id, currentPage.value, itemsPerPage.value);
            }
            loadVideosCompleted();
            loadCourse();
            loadAverage();
            loadAssignments();
            for (let star = 1; star <= 5; star++){
                    loadCountRating(star);
                    loadProgressByStar(star);
                }
                
        });

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

        return {
            AppLayout,
            courseId, lessons, comments, loadComments, lId,
            handlePageChange, currentPage, itemsPerPage, totalPages,
            addComment, newCmt, addLike, loadLike, newLikes,
            activeAccordion, toggleAccordion,
            handleVideo, videoUrl, videoPlayer, handleVideoEnded,
            videosCompleted, isVideoComplete, currentVideoId, progress,
            addReply, listReply, loadReplyComment, replyInputVisible, replyText, toggleReplyInput,
            courses, 
            rates, progressByStar, average,
            assignments, formatDate, isExpired,
            assignmentDone
        }
    },
    
})
</script>
<style scoped>
    .style-hr {
        width: 499px;
        margin-left: -20px;
        font-weight: bold;
        background-color: black;
        height: 1.5px;
        margin-top: 20px
    }
    .font-style-course {
        font-size: x-large;
        font-weight: bold;
    }
    .box-average {
        width: 161px;
        height: 121px;
        background-color: whitesmoke;
        margin-top: 12%
    }
    .border-assignment {
        width: 900px;
        background-color: whitesmoke;
        margin-bottom: 10px;
        border-radius: 10px;
        padding: 20px;
    }
</style>