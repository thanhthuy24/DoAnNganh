<template>
    <AppLayout>
        <main>
            <section class="bg-white py-8 antialiased dark:bg-gray-900 md:py-16">
                <div class="mx-auto max-w-screen-xl px-4 2xl:px-0">
                    <div class="flex items-center gap-2">
                    <h2 class="text-2xl font-semibold text-gray-900 dark:text-white">Reviews</h2>

                    <div class="mt-2 flex items-center gap-2 sm:mt-0">
                        <div class="flex items-center gap-0.5">
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
                        <p class="text-sm font-medium leading-none text-gray-500 dark:text-gray-400">({{average}})</p>
                        <a href="#" class="text-sm font-medium leading-none text-gray-900 underline hover:no-underline dark:text-white"> {{countReviews}} Reviews </a>
                    </div>
                    </div>

                    <div class="my-6 gap-8 sm:flex sm:items-start md:my-8">
                        <div class="shrink-0 space-y-4">
                            <p class="text-2xl font-semibold leading-none text-gray-900 dark:text-white">{{average}} out of 5</p>
                            <button 
                                type="button" 
                                data-modal-target="review-modal" 
                                data-modal-toggle="review-modal" 
                                class="mb-2 me-2 rounded-lg text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium text-sm px-5 py-2.5 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">Write a review</button>
                        </div>

                        <div class="mt-6 min-w-0 flex-1 space-y-3 sm:mt-0">
                            <div class="flex items-center gap-2" v-for="starCount in 5" :key="starCount">
                                <p class="w-2 shrink-0 text-start text-sm font-medium leading-none text-gray-900 dark:text-white">{{ 6 - starCount}}</p>
                                <svg class="h-4 w-4 shrink-0 text-yellow-300" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 24 24">
                                    <path d="M13.849 4.22c-.684-1.626-3.014-1.626-3.698 0L8.397 8.387l-4.552.361c-1.775.14-2.495 2.331-1.142 3.477l3.468 2.937-1.06 4.392c-.413 1.713 1.472 3.067 2.992 2.149L12 19.35l3.897 2.354c1.52.918 3.405-.436 2.992-2.15l-1.06-4.39 3.468-2.938c1.353-1.146.633-3.336-1.142-3.477l-4.552-.36-1.754-4.17Z" />
                                </svg>
                                <div class="h-1.5 w-80 rounded-full bg-gray-200 dark:bg-gray-700">
                                    <div class="h-1.5 rounded-full bg-yellow-300" 
                                    :style="{ width: progressByStar[6 - starCount] + '%'}"
                                ></div>
                            </div>
                                <a href="#" class="w-8 shrink-0 text-right text-sm font-medium leading-none text-primary-700 hover:underline dark:text-primary-500 sm:w-auto sm:text-left">
                                    {{rates[6 - starCount]}} <span class="hidden sm:inline">reviews</span></a>
                        </div>
                    </div>
                </div>

                <div 
                    v-for="(item, index) in reviews" :key="index"
                    class="mt-6 divide-y divide-gray-200 dark:divide-gray-700">
                    <div class="gap-3 pb-6 sm:flex sm:items-start" >
                        <div class="shrink-0 space-y-2 sm:w-48 md:w-72">
                            <div class="flex items-center gap-0.5">
                                <svg v-for="n in item.rating" :key="n" class="h-4 w-4 text-yellow-300" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 24 24">
                                    <path d="M13.849 4.22c-.684-1.626-3.014-1.626-3.698 0L8.397 8.387l-4.552.361c-1.775.14-2.495 2.331-1.142 3.477l3.468 2.937-1.06 4.392c-.413 1.713 1.472 3.067 2.992 2.149L12 19.35l3.897 2.354c1.52.918 3.405-.436 2.992-2.15l-1.06-4.39 3.468-2.938c1.353-1.146.633-3.336-1.142-3.477l-4.552-.36-1.754-4.17Z" />
                                </svg>
                            </div>

                            <div class="space-y-0.5">
                                <p class="text-base font-semibold text-gray-900 dark:text-white">{{item.user?.username}}</p>
                                <p class="text-sm font-normal text-gray-500 dark:text-gray-400">{{formattedDate(item.ratingDate)}}</p> 
                                <!-- November 18 2023 at 15:35 -->
                            </div>

                            <div class="inline-flex items-center gap-1">
                                <svg class="h-5 w-5 text-primary-700 dark:text-primary-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 24 24">
                                <path
                                    fill-rule="evenodd"
                                    d="M12 2c-.791 0-1.55.314-2.11.874l-.893.893a.985.985 0 0 1-.696.288H7.04A2.984 2.984 0 0 0 4.055 7.04v1.262a.986.986 0 0 1-.288.696l-.893.893a2.984 2.984 0 0 0 0 4.22l.893.893a.985.985 0 0 1 .288.696v1.262a2.984 2.984 0 0 0 2.984 2.984h1.262c.261 0 .512.104.696.288l.893.893a2.984 2.984 0 0 0 4.22 0l.893-.893a.985.985 0 0 1 .696-.288h1.262a2.984 2.984 0 0 0 2.984-2.984V15.7c0-.261.104-.512.288-.696l.893-.893a2.984 2.984 0 0 0 0-4.22l-.893-.893a.985.985 0 0 1-.288-.696V7.04a2.984 2.984 0 0 0-2.984-2.984h-1.262a.985.985 0 0 1-.696-.288l-.893-.893A2.984 2.984 0 0 0 12 2Zm3.683 7.73a1 1 0 1 0-1.414-1.413l-4.253 4.253-1.277-1.277a1 1 0 0 0-1.415 1.414l1.985 1.984a1 1 0 0 0 1.414 0l4.96-4.96Z"
                                    clip-rule="evenodd"
                                />
                                </svg>
                                <p class="text-sm font-medium text-gray-900 dark:text-white">Verified purchase</p>
                            </div>
                        </div>

                        <div class="mt-4 min-w-0 flex-1 space-y-4 sm:mt-0">
                            <p class="text-base font-normal text-gray-500 dark:text-gray-400">
                                {{item.comment}}    
                            </p>

                        <div class="flex items-center gap-4">
                            <p class="text-sm font-medium text-gray-500 dark:text-gray-400">Was it helpful to you?</p>
                            <div class="flex items-center">
                                <input id="reviews-radio-1" type="radio" value="" name="reviews-radio" class="h-4 w-4 border-gray-300 bg-gray-100 text-primary-600 focus:ring-2 focus:ring-primary-500 dark:border-gray-600 dark:bg-gray-700 dark:ring-offset-gray-800 dark:focus:ring-primary-600" />
                                <label for="reviews-radio-1" class="ms-2 text-sm font-medium text-gray-900 dark:text-gray-300"> Yes: 3 </label>
                            </div>
                            <div class="flex items-center">
                                <input id="reviews-radio-2" type="radio" value="" name="reviews-radio" class="h-4 w-4 border-gray-300 bg-gray-100 text-primary-600 focus:ring-2 focus:ring-primary-500 dark:border-gray-600 dark:bg-gray-700 dark:ring-offset-gray-800 dark:focus:ring-primary-600" />
                                <label for="reviews-radio-2" class="ms-2 text-sm font-medium text-gray-900 dark:text-gray-300">No: 0 </label>
                            </div>
                        </div>
                    </div>
                </div>
                    </div>
                </div>
            </section>
            <div id="review-modal" tabindex="-1" aria-hidden="true" class="fixed left-0 right-0 top-0 z-50 hidden h-[calc(100%-1rem)] max-h-full w-full items-center justify-center overflow-y-auto overflow-x-hidden md:inset-0 antialiased">
    <div class="relative max-h-full w-full max-w-2xl p-4">
    <!-- Modal content -->
        <div class="relative rounded-lg bg-white shadow dark:bg-gray-800">
      <!-- Modal header -->
        <div class="flex items-center justify-between rounded-t border-b border-gray-200 p-4 dark:border-gray-700 md:p-5">
            <div>
            <h3 class="mb-1 text-lg font-semibold text-gray-900 dark:text-white">Add a review for:</h3>
            <a href="#" class="font-medium text-primary-700 hover:underline dark:text-primary-500">
                {{courses.name}}
            </a>
            </div>
            <button type="button" class="absolute right-5 top-5 ms-auto inline-flex h-8 w-8 items-center justify-center rounded-lg bg-transparent text-sm text-gray-400 hover:bg-gray-200 hover:text-gray-900 dark:hover:bg-gray-600 dark:hover:text-white" data-modal-toggle="review-modal">
            <svg class="h-3 w-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6" />
            </svg>
            <span class="sr-only">Close modal</span>
            </button>
        </div>
      <!-- Modal body -->
        <form class="p-4 md:p-5" @submit.prevent="addReview">
            <div class="mb-4 grid grid-cols-2 gap-4">
                <div class="col-span-2">
                    <div class="flex items-center">
                        <svg 
                        v-for="star in 5" :key="star"
                        @click="setRating(star)"
                        :class="star <= rating ? 'text-yellow-300' : 'text-gray-300 dark:text-gray-500'"
                        class="h-6 w-6 cursor-pointer"
                        aria-hidden="true" xmlns="http://www.w3.org/2000/svg" 
                        fill="currentColor" viewBox="0 0 22 20">
                            <path d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z" />
                        </svg>
                        <span class="ms-2 text-lg font-bold text-gray-900 dark:text-white">{{rating}} out of 5</span>
                    </div>
                </div>
                <div class="col-span-2">
                    <label for="description" class="mb-2 block text-sm font-medium text-gray-900 dark:text-white">Review description</label>
                    <textarea 
                    v-model="comment"
                    id="description" rows="6" class="mb-2 block w-full rounded-lg border border-gray-300 bg-gray-50 p-2.5 text-sm text-gray-900 focus:border-primary-500 focus:ring-primary-500 dark:border-gray-600 dark:bg-gray-700 dark:text-white dark:placeholder:text-gray-400 dark:focus:border-primary-500 dark:focus:ring-primary-500" required=""></textarea>
                    <p class="ms-auto text-xs text-gray-500 dark:text-gray-400">Problems with the product or delivery? <a href="#" class="text-primary-600 hover:underline dark:text-primary-500">Send a report</a>.</p>
                </div>
            </div>
            <div class="border-t border-gray-200 pt-4 dark:border-gray-700 md:pt-5">
                <button type="submit" data-modal-toggle="review-modal" class="mb-2 me-2 rounded-lg text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium text-sm px-5 py-2.5 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">Add review</button>
                <button type="button" data-modal-toggle="review-modal" class="me-2 rounded-lg border border-gray-200 bg-white px-5 py-2.5 text-sm font-medium text-gray-900 hover:bg-gray-100 hover:text-primary-700 focus:z-10 focus:outline-none focus:ring-4 focus:ring-gray-100 dark:border-gray-600 dark:bg-gray-800 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white dark:focus:ring-gray-700">Cancel</button>
            </div>
        </form>   
    </div>
  </div>
</div>
        </main>
    </AppLayout>
</template>
<script>
import APIs, { authAPIs, endpoints } from "@/configs/APIs";
import AppLayout from "@/layouts/default.vue"
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';

export default ({
    components: {
    AppLayout,
  },
    setup() {
        const route = useRoute();
        const store = useStore();
        const courseId = route.params.courseId || route.query.courseId;

        const token = store.getters.token;

        const totalPages = ref(0);
        const currentPage = ref(0);
        const itemsPerPage = ref(12);

        const notifySuccess = () => {
            toast("Rating successfully", {
            "theme": "auto",
            "type": "success","autoClose": 3000,
            "dangerouslyHTMLString": true
            })
        }

        const notifyError = () => {
            toast("You've rated before!!", {
            "theme": "auto",
            "type": "error","autoClose": 3000,
            "dangerouslyHTMLString": true
            })
        }

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

        const reviews = ref([]);
        const loadReviews = async(page = currentPage.value, limit = itemsPerPage.value) => {
            try {
                let res = await authAPIs().get(`${endpoints.rating}/course/${courseId}?page=${page}&limit=${limit}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
                );
                reviews.value = res.data.ratings;
                console.log(reviews.value);
                totalPages.value = res.data.totalPages;
            } catch(err) {
                console.error(err);
            }
        }

        const countReviews = ref(null);
        const loadCountReviews = async() => {
            try {
                let res = await authAPIs().get(`${endpoints.rating}/${courseId}/count`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
                );
                countReviews.value = res.data;
                console.log(countReviews.value);
            } catch(err) {
                console.error(err);
            }
        }

        const rating = ref(0);
        const setRating = (star) => {
            rating.value = star;
            // console.log(rating.value);
        }

        const comment = ref('');
        const addReview = async() => {
            try {
                await authAPIs().post(`${endpoints.rating}`,
                    {
                        course_id: courseId,
                        comment: comment.value,
                        rating: rating.value
                    }
                , {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
                );
                comment.value = '';
                rating.value = '';
                await loadReviews();
                await loadCountReviews();
                await loadAverage();
                await loadProgressByStar();
                notifySuccess();
            } catch(err) {
                notifyError();
            }
        }

        onMounted(() => {
            loadAverage();
            loadReviews();
            loadCountReviews();
            loadCourse();
            for (let star = 1; star <= 5; star++) {
                    loadCountRating(star);
                    loadProgressByStar(star);
                }
        })

        const formattedDate = (timestamp) => {
            const date = new Date(timestamp);
            return new Intl.DateTimeFormat('en-US', {
                month: 'long',
                day: 'numeric',
                year: 'numeric',
                hour: 'numeric',
                minute: 'numeric',
            }).format(date);
        };

        return {
            notifySuccess, notifyError, courses,
            average, progressByStar, rates, loadAverage, loadProgressByStar,
            totalPages, currentPage, itemsPerPage, formattedDate,
            reviews, countReviews, rating, setRating, loadReviews, 
            comment, addReview, loadCountReviews
        }
    },
})
</script>
