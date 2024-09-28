// <!-- eslint-disable -->
<!--eslint-disable-next-line no-mixed-spaces-and-tabs-->
<template>
  	<nav class="bg-white border-gray-200 dark:bg-gray-900">
	<div class="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
		<img :src="require('@/assets/logo.png')" class="h-16" alt="HR*YJ Logo" />
		<div class="flex">
			<label for="simple-search" class="sr-only">Search</label>
			<div class="relative w-full">
				<div class="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
					<svg class="w-4 h-4" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
						<path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
					</svg>
				</div>
			<input 
				type="text" 
				id="simple-search" 
				class="bg-gray-50 border search-input border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
				placeholder="Search branch name..." required />
			</div>
			<button type="submit" class="p-2.5 ms-2 text-sm font-medium text-white bg-blue-700 rounded-lg border border-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
				<svg class="w-4 h-4" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
					<path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
				</svg>
				<span class="sr-only">Search</span>
			</button>
		</div>
		<div>
			<div class="hidden w-full md:block md:w-auto" id="navbar-default">
				<ul class="font-medium flex flex-col p-4 md:p-0 mt-4 border border-gray-100 rounded-lg bg-gray-50 md:flex-row md:space-x-8 rtl:space-x-reverse md:mt-0 md:border-0 md:bg-white dark:bg-gray-800 md:dark:bg-gray-900 dark:border-gray-700">
					<li>
						<a href="#" class="mr-10 block py-2 px-3 text-white bg-blue-700 rounded md:bg-transparent md:text-blue-700 md:p-0 dark:text-white md:dark:text-blue-500" aria-current="page">Home</a>
					</li>
					<li>
						<a href="#" class="mr-10 block py-2 px-3 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent">About</a>
					</li>
					<li>
						<a href="#" class="mr-10 block py-2 px-3 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent">Details</a>
					</li>
					<li>
						<a href="#" class="mr-10 block py-2 px-3 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent">
							<svg-icon type="mdi" :path="cartPath"></svg-icon>
						</a>
					</li>
					<li>
						<a href="#" class="mr-10 block py-2 px-3 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent">
							<svg-icon type="mdi" :path="bellPath"></svg-icon>
						</a>
					</li>
				</ul>
    		</div>
		</div>
		<div class="flex items-center md:order-2 space-x-3 md:space-x-0 rtl:space-x-reverse">
			<button type="button" 
					class="flex text-sm bg-gray-800 rounded-full md:me-0 focus:ring-4 focus:ring-gray-300 dark:focus:ring-gray-600" 
					id="user-menu-button"
					@click="toggleDropdown"
					aria-expanded="false" data-dropdown-toggle="user-dropdown" data-dropdown-placement="bottom">
				<span class="sr-only">Open user menu</span>
				<img class="w-8 h-8 rounded-full" :src="require('@/assets/avatar-default.png')" alt="user photo">
			</button>
			<!-- Dropdown menu -->
			<div class="z-50 hidden my-4 text-base list-none bg-white divide-y divide-gray-100 rounded-lg shadow dark:bg-gray-700 dark:divide-gray-600" id="user-dropdown">
				<div class="px-4 py-3">
				<span class="block text-sm text-gray-900 dark:text-white">Bonnie Green</span>
				<span class="block text-sm  text-gray-500 truncate dark:text-gray-400">name@flowbite.com</span>
				</div>
				<ul class="py-2" aria-labelledby="user-menu-button">
				<li>
					<a href="#" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">Dashboard</a>
				</li>
				<li>
					<a href="#" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">Settings</a>
				</li>
				<li>
					<a href="#" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">Sign out</a>
				</li>
				
				</ul>
			</div>
		</div>
	</div>	
	</nav>

	<div>
		<div id="default-carousel" class="relative w-full" data-carousel="slide">
			<!-- Carousel wrapper -->
			<div class="relative h-96 overflow-hidden rounded-lg md:h-96">
				<!-- Item 1 -->
				<div class="hidden duration-700 ease-in-out" data-carousel-item>
					<img :src="require('@/assets/caro1.png')" class="absolute block w-full -translate-x-1/2 -translate-y-1/2 top-1/2 left-1/2" alt="...">
				</div>
				<!-- Item 2 -->
				<div class="hidden duration-700 ease-in-out" data-carousel-item>
					<img  :src="require('@/assets/caro2.png')" class="absolute block w-full -translate-x-1/2 -translate-y-1/2 top-1/2 left-1/2" alt="...">
				</div>
				<!-- Item 3 -->
				<div class="hidden duration-700 ease-in-out" data-carousel-item>
					<img :src="require('@/assets/caro3.png')" class="absolute block w-full -translate-x-1/2 -translate-y-1/2 top-1/2 left-1/2" alt="...">
				</div>
			</div>
			<!-- Slider indicators -->
			<div class="absolute z-30 flex -translate-x-1/2 bottom-5 left-1/2 space-x-3 rtl:space-x-reverse">
				<button type="button" class="w-3 h-3 rounded-full" aria-current="true" aria-label="Slide 1" data-carousel-slide-to="0"></button>
				<button type="button" class="w-3 h-3 rounded-full" aria-current="false" aria-label="Slide 2" data-carousel-slide-to="1"></button>
				<button type="button" class="w-3 h-3 rounded-full" aria-current="false" aria-label="Slide 3" data-carousel-slide-to="2"></button>
				<button type="button" class="w-3 h-3 rounded-full" aria-current="false" aria-label="Slide 4" data-carousel-slide-to="3"></button>
				<button type="button" class="w-3 h-3 rounded-full" aria-current="false" aria-label="Slide 5" data-carousel-slide-to="4"></button>
			</div>
			<!-- Slider controls -->
			<button type="button" class="absolute top-0 start-0 z-30 flex items-center justify-center h-full px-4 cursor-pointer group focus:outline-none" data-carousel-prev>
				<span class="inline-flex items-center justify-center w-10 h-10 rounded-full bg-white/30 dark:bg-gray-800/30 group-hover:bg-white/50 dark:group-hover:bg-gray-800/60 group-focus:ring-4 group-focus:ring-white dark:group-focus:ring-gray-800/70 group-focus:outline-none">
					<svg class="w-4 h-4 text-white dark:text-gray-800 rtl:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
						<path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 1 1 5l4 4"/>
					</svg>
					<span class="sr-only">Previous</span>
				</span>
			</button>
			<button type="button" class="absolute top-0 end-0 z-30 flex items-center justify-center h-full px-4 cursor-pointer group focus:outline-none" data-carousel-next>
				<span class="inline-flex items-center justify-center w-10 h-10 rounded-full bg-white/30 dark:bg-gray-800/30 group-hover:bg-white/50 dark:group-hover:bg-gray-800/60 group-focus:ring-4 group-focus:ring-white dark:group-focus:ring-gray-800/70 group-focus:outline-none">
					<svg class="w-4 h-4 text-white dark:text-gray-800 rtl:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
						<path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 9 4-4-4-4"/>
					</svg>
					<span class="sr-only">Next</span>
				</span>
			</button>
		</div>

		
		</div>
		<div class="flex">
			<aside id="default-sidebar" 
					class="ml-3 mt-5 z-40 w-64 h-screen transition-transform -translate-x-full sm:translate-x-0" 
					aria-label="Sidebar">
			<div class="h-full px-3 py-4 overflow-y-auto bg-gray-50 dark:bg-gray-800">
      		<ul class="space-y-2 font-medium mb-3">
				<label>Sort by Filter</label>
				<li>
					<a href="#" class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
					<svg class="w-6 h-6 text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" viewBox="0 0 24 24">
						<path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m13 19 3.5-9 3.5 9m-6.125-2h5.25M3 7h7m0 0h2m-2 0c0 1.63-.793 3.926-2.239 5.655M7.5 6.818V5m.261 7.655C6.79 13.82 5.521 14.725 4 15m3.761-2.345L5 10m2.761 2.655L10.2 15"/>
					</svg>

					<span class="ms-3">{{ categories.length ? categories[0].name : 'Loading...' }}</span>
					</a>
				</li>
				<li>
					<a href="#" class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
					<svg class="w-6 h-6 text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" viewBox="0 0 24 24">
						<path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 9h.01M8.99 9H9m12 3a9 9 0 1 1-18 0 9 9 0 0 1 18 0ZM6.6 13a5.5 5.5 0 0 0 10.81 0H6.6Z"/>
					</svg>

					<span class="ms-3">{{ categories.length ? categories[3].name : 'Loading...' }}</span>
					</a>
				</li>
				<li>
					<button type="button" class="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-gray-100 dark:text-white dark:hover:bg-gray-700" aria-controls="dropdown-example" data-collapse-toggle="dropdown-example">
						<svg class="w-6 h-6 text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" viewBox="0 0 24 24">
							<path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 8v8m0-8a2 2 0 1 0 0-4 2 2 0 0 0 0 4Zm0 8a2 2 0 1 0 0 4 2 2 0 0 0 0-4Zm12 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4Zm0 0V9a3 3 0 0 0-3-3h-3m1.5-2-2 2 2 2"/>
						</svg>

						<span class="flex-1 ms-3 text-left rtl:text-right whitespace-nowrap">IT</span>
						<svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
							<path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 4 4 4-4"/>
						</svg>
					</button>
					<ul id="dropdown-example" class="hidden py-2 space-y-2">
						<li>
							<a href="#" class="flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-gray-100 dark:text-white dark:hover:bg-gray-700">
								{{ categories.length ? categories[1].name : 'Loading...' }}</a>
						</li>
						<li>
							<a href="#" class="flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-gray-100 dark:text-white dark:hover:bg-gray-700">
								{{ categories.length ? categories[2].name : 'Loading...' }}</a>
						</li>
						
					</ul>
         	</li>
			</ul>
			<hr/>
			<div class="mt-3">
				<form class="max-w-md mx-auto mb-3">
					<div class="mb-5">
						<label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">From price</label>
						<input 
							type="number" 
							id="email" 
							class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="from price" required />
					</div>
					<div class="mb-5">
						<label for="password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">To price</label>
						<input type="number" id="password" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required />
					</div>
					<button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Submit</button>
				</form>
				<hr/>
				<div>
					<label for="email" class="mt-3 block mb-2 text-sm font-medium text-gray-900 dark:text-white">Rating</label>
					<div v-for="star in stars" :key="star">
						<label class="flex">
						<input 
							class="ml-5 mr-3"
							type="radio"
							:value="star" 
							v-model="selectedRating"
							@change="filterRating"
							/>
								<font-awesome-icon 
									v-for="star in stars" 
									:key="star" 
									:icon="starIcon" 
									style="color: gold; font-size: 20px; margin-bottom: 5px; margin-top: -2px;" /> 
						</label>
					</div>
				</div>
			</div>
			</div>
		</aside>
		 <div class="p-4 ml-3 flex-grow bg-white">
			<div class="p-4 border-2 border-gray-200 border-dashed rounded-lg dark:border-gray-700">
				<div class="grid grid-cols-3 gap-4 mb-4">
					<div v-for="course in courses" :key="course.id"
						class="w-full max-w-sm bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700"
						>
						<router-link
							to="/">
        					<img class="p-8 rounded-t-lg" :src="course.image" alt="product image" />
    					</router-link >
						<div class="px-5 pb-5">
						<router-link 
							class="text-xl font-semibold tracking-tight text-gray-900 dark:text-white" 
							to="/">
							{{course.name}}
						</router-link>
						<p class="mt-3">{{course.description}}</p>
						<div class="flex justify-between mt-2">
							<span class="bg-purple-100 text-gray-800 text-xs font-medium inline-flex items-center px-2.5 py-0.5 rounded me-2 dark:bg-gray-700 dark:text-gray-400 border border-gray-500 ">
							<svg class="w-3 h-3 me-1.5 text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 24 24">
								<path fill-rule="evenodd" d="M11 4.717c-2.286-.58-4.16-.756-7.045-.71A1.99 1.99 0 0 0 2 6v11c0 1.133.934 2.022 2.044 2.007 2.759-.038 4.5.16 6.956.791V4.717Zm2 15.081c2.456-.631 4.198-.829 6.956-.791A2.013 2.013 0 0 0 22 16.999V6a1.99 1.99 0 0 0-1.955-1.993c-2.885-.046-4.76.13-7.045.71v15.081Z" clip-rule="evenodd"/>
							</svg>
						?? lessons
						</span>

						<span class="bg-purple-100 text-gray-800 text-xs font-medium inline-flex items-center px-2.5 py-0.5 rounded me-2 dark:bg-gray-700 dark:text-gray-400 border border-gray-500 ">
							<svg class="w-3 h-3 me-1.5 text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 24 24">
  								<path fill-rule="evenodd" d="M12 6a3.5 3.5 0 1 0 0 7 3.5 3.5 0 0 0 0-7Zm-1.5 8a4 4 0 0 0-4 4 2 2 0 0 0 2 2h7a2 2 0 0 0 2-2 4 4 0 0 0-4-4h-3Zm6.82-3.096a5.51 5.51 0 0 0-2.797-6.293 3.5 3.5 0 1 1 2.796 6.292ZM19.5 18h.5a2 2 0 0 0 2-2 4 4 0 0 0-4-4h-1.1a5.503 5.503 0 0 1-.471.762A5.998 5.998 0 0 1 19.5 18ZM4 7.5a3.5 3.5 0 0 1 5.477-2.889 5.5 5.5 0 0 0-2.796 6.293A3.501 3.501 0 0 1 4 7.5ZM7.1 12H6a4 4 0 0 0-4 4 2 2 0 0 0 2 2h.5a5.998 5.998 0 0 1 3.071-5.238A5.505 5.505 0 0 1 7.1 12Z" clip-rule="evenodd"/>
							</svg>

						?? students
						</span>

						</div>
						
						<div class="flex items-center mt-3 mb-5">
							<div class="flex items-center space-x-1 rtl:space-x-reverse">
								<svg class="w-4 h-4 text-yellow-300" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 22 20">
									<path d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z"/>
								</svg>
								<svg class="w-4 h-4 text-gray-200 dark:text-gray-600" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 22 20">
									<path d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z"/>
								</svg>
							</div>
							<span class="bg-blue-100 text-blue-800 text-xs font-semibold px-2.5 py-0.5 rounded dark:bg-blue-200 dark:text-blue-800 ms-3">5.0</span>
						</div>
						<div class="flex">
							<del class="text-xl font-bold text-gray-900 dark:text-white">
								{{formatCurrencyWithRounding(course.price)}} VNĐ
							</del>
							<br />
							
						</div>
						<div class="flex items-center justify-between">
							<span class=" text-xl font-bold" style="color: red">
								{{ formatCurrencyWithRounding(course.price * (1 - course.discount / 100)) }} VNĐ
							</span>
							<router-link 
								to="/" 
								class=" text-white bg-gradient-to-br from-purple-600 to-blue-500 hover:bg-gradient-to-bl focus:ring-4 focus:outline-none focus:ring-blue-300 dark:focus:ring-blue-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2"
								>
								<span>
								Add to cart
								</span>
							</router-link>
						</div>
					</div>
					</div>
				</div>
			</div> 
			<pagination 
				:currentPage="currentPage"
				:totalPages="totalPages"
				:itemsPerPage="itemsPerPage"
				@page-changed="handlePageChange"
			/>
		</div>
		
	</div>

</template>

<script>
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiCartOutline, mdiBellOutline, mdiMagnify, mdiStarCircle } from '@mdi/js';
import { endpoints } from '@/configs/APIs.js'
import APIs from '@/configs/APIs.js';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { faStar } from '@fortawesome/free-solid-svg-icons'
import { onMounted, ref } from 'vue';
import Pagination from '@/views/Pagination-view.vue'

export default {
  name: "my-cool-component",
  components: {
    SvgIcon,
    FontAwesomeIcon, 
	Pagination
  },
  setup() {
    const selectedRating = ref(null);
    
	const categories = ref([]);
    const courses = ref([]);

	const currentPage = ref(0);
	const itemsPerPage = ref(12);
	const totalPages = ref(0);


    const stars = [1, 2, 3, 4, 5];
    const items = ['Column 1', 'Column 2', 'Column 3', 'Column 4'];

    const cartPath = mdiCartOutline;
    const bellPath = mdiBellOutline;
    const searchPath = mdiMagnify;
    const starPath = mdiStarCircle;
    const starIcon = faStar;

    const fetchCategories = async () => {
		try {
			const res = await APIs.get(endpoints.categories);
			categories.value = res.data;

		} catch (error) {
			console.error(error);
		}
    };

    const fetchCourses = async (page, limit) => {
		try {
			const res = await APIs.get(`${endpoints.courses}?page=${page}&&limit=${limit}`);
			courses.value = res.data.courses;
			totalPages.value = res.data.totalPages;
			// console.log(res.data.courses);
			
		} catch (error) {
			console.error(error);
		}
    };

	const formatCurrencyWithRounding = (value) => {
		const roundedValue = Math.floor(value) + (value % 1 >= 0.5 ? 1 : 0);
		return roundedValue.toLocaleString().replace(/\B(?=(\d{3})+(?!))/g, ",");
	};


    const handlePageChange = (newPage) => {
            currentPage.value = newPage;
            fetchCourses(currentPage.value, itemsPerPage.value); // Gọi lại để lấy dữ liệu cho trang mới
        };

    onMounted(() => {
      fetchCategories();
      fetchCourses(currentPage.value, itemsPerPage.value);
    });

    return {
		selectedRating,
		categories,
		courses,
		stars,
		items,
		cartPath,
		bellPath,
		searchPath,
		starPath,
		starIcon,
		fetchCategories,
		fetchCourses,
		currentPage, 
		itemsPerPage,
		totalPages,
		// customPrice,
		handlePageChange,
		formatCurrencyWithRounding
		// prevPage,
		// nextPage
    };
  }
};
</script>

<style>
.search-input {
	width: 400px;
}

.grid-container {
  display: grid; /* Sử dụng grid */
  grid-template-columns: repeat(4, 1fr); /* 4 cột */
  gap: 10px; /* Khoảng cách giữa các cột và hàng */
}

.grid-item {
  padding: 20px;
  background-color: lightgray;
  text-align: center;
}

</style>