import { createApp, nextTick } from 'vue'
import "./assets/styles/tailwind.css"
import "./assets/styles/global.css"
import App from './App.vue'
import router from './router'
import { initFlowbite } from 'flowbite'
import { VueCookies } from 'vue3-cookies'
import store from '@/store/index.js'

import { registerGlobalComponents } from './utils/import'


const app = createApp(App);
registerGlobalComponents(app);
app.use(router);
app.use(VueCookies, {expireTimes: '7d'}); // Cấu hình thời gian hết hạn cookie
app.use(store);
store.dispatch('initializeStore');
app.mount('#app', () => {
    nextTick(() => {
        initFlowbite();

    })
});