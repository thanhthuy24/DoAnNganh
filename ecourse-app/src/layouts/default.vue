<!-- eslint-disable -->
<!--eslint-disable-next-line no-mixed-spaces-and-tabs-->
<template>  
    <header>
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
						<router-link to="/">
							<a href="#" class="mr-10 block py-2 px-3 text-white bg-blue-700 rounded md:bg-transparent md:text-blue-700 md:p-0 dark:text-white md:dark:text-blue-500" aria-current="page">Home</a>
						</router-link>
					</li>
					<li>
						<a href="#" class="mr-10 block py-2 px-3 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent">About</a>
					</li>
					<li>
						<a href="#" class="mr-10 block py-2 px-3 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent">Details</a>
					</li>
                    <div class="flex" v-if="isLoggedIn">
                        <li>
							<router-link to="/cart">
                            <a class="mr-10 flex block py-2 px-3 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent">
                                <svg-icon type="mdi" :path="cartPath"></svg-icon>
								<span class="inline-flex items-center justify-center w-4 h-4 ms-2 text-xs font-semibold text-blue-800 bg-blue-200 rounded-full">
								{{total}}
								</span>
                            </a>
							</router-link>
                        </li>
                        <li>
                            <a href="#" class="mr-10 block py-2 px-3 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:border-0 md:hover:text-blue-700 md:p-0 dark:text-white md:dark:hover:text-blue-500 dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent">
                                <svg-icon type="mdi" :path="bellPath"></svg-icon>
                            </a>
                        </li>
                    </div>
					
				</ul>
    		</div>
		</div>
		<div v-if="isLoggedIn" class="flex items-center md:order-2 space-x-3 md:space-x-0 rtl:space-x-reverse">
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
				<span class="block text-sm text-gray-900 dark:text-white">{{ user.username }}</span>
				<span class="block text-sm  text-gray-500 truncate dark:text-gray-400">{{ user.email }}</span>
				</div>
				<ul class="py-2" aria-labelledby="user-menu-button">
				<li>
					<router-link to="/user">
					<a href="#" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">Information</a>
					</router-link>
				</li>
				<li>
					<a href="#" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">Settings</a>
				</li>
				<li><router-link to="">
					<a v-if="isLoggedIn" @click="logout" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">Sign out</a>
					</router-link>
				</li>
				
				</ul>
			</div>
		</div>
        <div v-else>
            <router-link to="/login">
                <button type="button" class="text-white bg-gradient-to-br from-purple-600 to-blue-500 hover:bg-gradient-to-bl focus:ring-4 focus:outline-none focus:ring-blue-300 dark:focus:ring-blue-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2">
                    Login
                </button>
            </router-link>
        </div>
	</div>	
	</nav>
    </header>
    <main>
        <slot />
    </main>
    <footer>

    </footer>
</template>

<script>
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiCartOutline, mdiBellOutline, mdiMagnify } from '@mdi/js';
import { computed } from 'vue';
import { useStore } from 'vuex';
import { useCookies } from 'vue3-cookies';
// import { useCookies } from 'vue3-cookies';
// const { cookies } = useCookies();

export default {
    name: "AppLayout",
    components: {
        SvgIcon,
    },
    setup() {
        const cartPath = mdiCartOutline;
        const bellPath = mdiBellOutline;
        const searchPath = mdiMagnify;

        const store = useStore();
		const { cookies } = useCookies();
		const totalQuantity = computed(() => store.state.totalQuantity);

		const isLoggedIn = computed(() => store.state.isLoggedIn);
    const user = computed(() => store.state.user);

	const total = computed(() => {
            // Update the cookie value whenever totalQuantity changes
            cookies.set("totalQuantity", totalQuantity.value);
            return totalQuantity.value; // or any calculation you need
        });

    // Định nghĩa các phương thức
    const login = () => {
      // Thông tin người dùng giả lập
      const userInfo = { name: 'John Doe', email: 'john@example.com' };
      store.dispatch('login', userInfo);
    };

    const logout = () => {
      store.dispatch('logout');
    };

	// onMounted(() => {
	// 	store.dispatch('updateCart', totalQuantity);
	// })

    return {
      isLoggedIn,
      user,
      login,
      logout,
	cartPath,
		bellPath,
		totalQuantity,
		searchPath,
		total
    };

    },
}
</script>
