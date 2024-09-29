import { createApp, nextTick } from 'vue'
import "./assets/styles/tailwind.css"
import "./assets/styles/global.css"
import App from './App.vue'
import router from './router'
import { initFlowbite } from 'flowbite'
import { VueCookies } from 'vue3-cookies'
import store from '@/store/index.js'

// import { library, config } from '@fortawesome/fontawesome-svg-core'
// import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
// import { faStar } from '@fortawesome/free-solid-svg-icons';

import { registerGlobalComponents } from './utils/import'

// config.familyDefault = "classic";
// config.styleDefault = "duotone";

// library.add(faStar);

const app = createApp(App);
registerGlobalComponents(app);
app.use(router);
app.use(VueCookies, {expireTimes: '7d'}); // Cấu hình thời gian hết hạn cookie
app.use(store);
// app.component('font-awesome-icon', FontAwesomeIcon)

app.mount('#app', () => {
    nextTick(() => {
        initFlowbite();

    })
});
