<template>
    <AppLayout>
        <main class="ml-32 mt-10">
            <div class="flex">
                <div style="width: 980px; border: 1px solid black;" >
                    <div>
                        <div>
                            <video class="w-full" autoplay controls>
                                <source :src="selectedVideo" type="video/mp4">
                                Your browser does not support the video tag.
                            </video>
                        </div>

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
                            <div class="hidden p-4 rounded-lg bg-gray-50 dark:bg-gray-800" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                                <p class="text-sm text-gray-500 dark:text-gray-400">This is some placeholder content the 
                                    <strong class="font-medium text-gray-800 dark:text-white">Profile tab's associated content</strong>.
                                     Clicking another tab will toggle the visibility of this one for the next. The tab JavaScript swaps 
                                     classes to control the content visibility and styling.</p>
                            </div>
                            <div class="hidden p-4 rounded-lg bg-gray-50 dark:bg-gray-800" id="dashboard" role="tabpanel" aria-labelledby="dashboard-tab">
                                <p class="text-sm text-gray-500 dark:text-gray-400">This is some placeholder content the <strong class="font-medium text-gray-800 dark:text-white">Dashboard tab's associated content</strong>. Clicking another tab will toggle the visibility of this one for the next. The tab JavaScript swaps classes to control the content visibility and styling.</p>
                            </div>
                            <div
                             class="hidden p-4 rounded-lg bg-gray-50 dark:bg-gray-800" id="settings" role="tabpanel" aria-labelledby="settings-tab">
                                
                                <div>
                                    <p class="mb-5">Give me some questions</p>
                                <form @submit.prevent="addComment">
                                    <input 
                                        v-model="newCmt"
                                        placeholder="Comment this lesson"
                                        type="text" 
                                        id="content" 
                                        class="block w-full p-4 text-gray-900 border border-gray-300 rounded-lg bg-gray-50 text-base focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                                
                                    <button 
                                style="margin-left: 91%;" type="submit"
                                    class="text-white mt-3 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">
                                    Submit</button>
                                </form>
                                </div>
                                <div v-for="c in comments" :key="c.id" class="">
                                    <div class="flex">
                                        <img class="w-9 h-9 rounded-full" :src="c.user?.avatar" alt="user photo">
                                        <p class="ml-5 mt-1">{{c.user?.username}}</p>
                                    </div>
                                    <div>
                                        <input :value="c.content"
                                            style="width: 700px; margin-left: 6%; margin-top: 10px;"
                                            disabled
                                            type="text" id="default-input" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                                        <div class="flex mt-3" style="margin-left: 7%">
                                            <button @click="addLike(c.id)">
                                            <svg class="mr-2 w-6 h-6 text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" viewBox="0 0 24 24">
                                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12.01 6.001C6.5 1 1 8 5.782 13.001L12.011 20l6.23-7C23 8 17.5 1 12.01 6.002Z"/>
                                            </svg></button>
                                            <span>{{ newLikes[c.id] !== undefined ? newLikes[c.id] : 0 }} likes</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="ml-32 mt-10">
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
                <div>
                    <div style="width: 500px" class="ml-5 bg-gray-200 rounded-full h-2.5 dark:bg-gray-700">
                        <div class="bg-blue-600 h-2.5 rounded-full" style="width: 45%"></div>
                    </div>
                    <div style="border: 1px solid black; width: 500px" class="mt-5 ml-5">
                    <!-- Missing quotation mark is now added -->
                        <div v-for="l in lessons" :key="l.id" class="mt-5 mx-5">
                            <h1
                                @click="loadComments(l.id, currentPage.value, itemsPerPage.value)"
                                style="font-weight: bold">{{ l.name }} (
                                {{l.videos?.length}} videos
                                )
                            </h1>
                            <h2 v-if="l.videos && l.videos.length > 0" class="ml-3">
                                <ul>
                                   <li class="mt-2" v-for="(video, index) in l.videos" :key="video.id">
                                        <button @click="selectVideo(video.name)"> <!-- Pass video.name directly -->
                                            Video {{ index + 1 }}
                                        </button>
                                    </li>
                                </ul>
                            </h2>
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
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
import Pagination from '@/views/Pagination-view.vue'

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

        const selectedVideo = ref(null);
        const totalPages = ref(0);

        const loadLessons = async(courseId) => {
            try {
                let token = store.getters.token;
                let res = await authAPIs().get(`${endpoints.lessons}/${courseId}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                lessons.value = res.data;
                // console.log(lessons.value);
            } catch(ex){
                console.error(ex);
            }
        }

        const lId = ref('');

         const selectVideo = (video) => {
            selectedVideo.value = video; 
            console.log(video);
            };

        const currentPage = ref(0);
        const itemsPerPage = ref(12);

        const loadComments = async(lessonId, page = currentPage.value, limit = itemsPerPage.value) => {
            try {
                let token = store.getters.token;
                let res = await authAPIs().get(`${endpoints.comments}/${lessonId}?page=${page}&limit=${limit}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                comments.value = res.data.comments;

                lId.value = lessonId;
                totalPages.value = res.data.totalPages;
                
                // Gọi loadLike cho mỗi comment
                for (const comment of comments.value) {
                    await loadLike(comment.id); // Gọi loadLike với commentId
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
        

        const newLikes = ref([]); // Sử dụng ref để theo dõi sự thay đổi của newLikes

        const loadLike = async(commentID) => {
            try {
                let token = store.getters.token;
                let res = await authAPIs().get(`${endpoints.like}/${commentID}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                // console.log(res.data);
                // Cập nhật số lượng like vào newLikes theo commentId
                newLikes.value[commentID] = res.data || 0; // Kiểm tra xem res.data.likes có tồn tại không
                // console.log('Likes for comment', commentID, ':', newLikes.value[commentID]);
            } catch (ex) {
                console.error(ex);
            }
        };



        const handlePageChange = (lesonId, newPage) => {
            currentPage.value = newPage;
            loadComments(lesonId, currentPage.value, itemsPerPage.value); // Gọi lại để lấy dữ liệu cho trang mới
        };

        onMounted(async () => {
            await loadLessons(courseId);
            if (lessons.value.length > 0) {
                // Giả sử bạn muốn lấy comments của bài học đầu tiên
                loadComments(lessons.value[0].id, currentPage.value, itemsPerPage.value);
            }
        });

        return {
            AppLayout,
            courseId,
            lessons,
            comments,
            loadComments, 
            selectVideo,
            selectedVideo,
            handlePageChange, 
            currentPage,
            itemsPerPage,
            totalPages,
            addComment,
            newCmt,
            addLike,
            loadLike,
            newLikes
        }
    },
})
</script>