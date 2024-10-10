<!-- eslint-disable -->
<template>
    <div class="flex mt-10 mx-auto justify-end">
        <div>
            <img :src="require('@/assets/logo.png')" style="width: 300px" class="mx-auto" />
            <h5 class="font-bold " style="font-size: xx-large;">Wellcome back</h5>
            <p class="my-3">Start your website in seconds. Don’t have an account? 
                <router-link to="/register">
                    <span class="font-bold" style="color: blue; text-decoration: underline;">Sign up</span>
                </router-link>
            </p>
            <form class="max-w-md mx-auto" @submit.prevent="login">
                <div>
                    <div class="relative z-0 w-full mb-5 group">
                        <label for="username" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Username</label>
                        <input required 
                            v-model="username"
                            type="text" id="username" aria-describedby="helper-text-explanation" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="ex: john123">
                    </div>
                    <div class="mb-5">
                        <label for="password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Password</label>
                        <input 
                            v-model="password"
                            type="password" id="password" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" required />
                    </div>
                    
                </div>
                
                <div class="relative my-5">
                    <hr class="border-gray-300" />
                    <span class="absolute left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-white px-2"> or </span>
                </div>
                
                <div>
                    <button style="width: 445px;" type="button" class="text-white bg-[#3b5998] hover:bg-[#3b5998]/90 focus:ring-4 focus:outline-none focus:ring-[#3b5998]/50 font-medium rounded-lg text-sm px-5 py-2.5 inline-flex items-center justify-center dark:focus:ring-[#3b5998]/55 me-2 mb-2">
                    <svg class="w-4 h-4 me-2" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 8 19">
                        <path fill-rule="evenodd" d="M6.135 3H8V0H6.135a4.147 4.147 0 0 0-4.142 4.142V6H0v3h2v9.938h3V9h2.021l.592-3H5V3.591A.6.6 0 0 1 5.592 3h.543Z" clip-rule="evenodd"/>
                    </svg>
                    Sign in with Facebook
                </button>
                </div>
                <div>
                    <button style="width: 445px;" type="button" class=" text-black bg-[#f6f6f6] hover:bg-sky-300 focus:ring-4 focus:outline-none focus:ring-[#4285F4]/50 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center justify-center dark:focus:ring-[#4285F4]/55 me-2 mb-2">
                        <svg class="w-4 h-4 me-2" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 18 19">
                            <path fill-rule="evenodd" d="M8.842 18.083a8.8 8.8 0 0 1-8.65-8.948 8.841 8.841 0 0 1 8.8-8.652h.153a8.464 8.464 0 0 1 5.7 2.257l-2.193 2.038A5.27 5.27 0 0 0 9.09 3.4a5.882 5.882 0 0 0-.2 11.76h.124a5.091 5.091 0 0 0 5.248-4.057L14.3 11H9V8h8.34c.066.543.095 1.09.088 1.636-.086 5.053-3.463 8.449-8.4 8.449l-.186-.002Z" clip-rule="evenodd"/>
                        </svg>
                    Sign in with Google
                    </button>
                </div>
                <router-link to="/">
            <p style="margin-left: 71%;
                            color: blue;
                            font-style: italic;"
                class="my-5"
                            >
                            Forgot password?</p>
                </router-link>
            <button 
                type="submit" 
                style="width: 445px;"
                class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                >
                Sign in to your account</button>
            </form>
        </div>
        <div>
            <img :src="require('@/assets/login1.jpg')" style="width: 900px" />
        </div>
    </div>
    

</template>

<script>
import { ref } from "vue"
import { useRouter } from "vue-router";
import { useStore } from "vuex";


export default({
    setup() {
        const store = useStore();
        const router = useRouter();
        const username = ref('');
        const password = ref('');

        const login = async () => {
            const user = {username: username.value, password: password.value};
        
            await store.dispatch('login', user);
            const userRole = store.getters.userRole;
            
            console.log(userRole);
            
            if (userRole === 'ADMIN') {
                router.push('/admin');
            } else if (userRole === 'TEACHER'){
                router.push('/teacher-view');
            } else {
                router.push('/');
            }
            }

        return {
            login,
            username,
            password
        }
    },
})
</script>
