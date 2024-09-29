import { createApp, nextTick } from 'vue'
import "./assets/styles/tailwind.css"
import "./assets/styles/global.css"
import App from './App.vue'
import router from './router'
import { initFlowbite } from 'flowbite'
import { VueCookies } from 'vue3-cookies'
import store from '@/store/index.js'
import ToastPlugin from "vue3-toaster";
// import 'vue3-toaster/dist/index.css'; 

import { registerGlobalComponents } from './utils/import'


const app = createApp(App);
registerGlobalComponents(app);
app.use(router);
app.use(VueCookies, {expireTimes: '7d'}); // Cấu hình thời gian hết hạn cookie
app.use(store);
app.use(ToastPlugin, {
    position: 'top-right',  
    timeout: 3000           
  });
store.dispatch('initializeStore');
// app.component('font-awesome-icon', FontAwesomeIcon)

app.mount('#app', () => {
    nextTick(() => {
        initFlowbite();

    })
});
