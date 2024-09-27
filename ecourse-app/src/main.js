import { createApp, nextTick } from 'vue'
import "./assets/styles/tailwind.css"
import "./assets/styles/global.css"
import App from './App.vue'
import router from './router'
import { initFlowbite } from 'flowbite'

import { registerGlobalComponents } from './utils/import'
// initFlowbite();

const app = createApp(App);
registerGlobalComponents(app);
app.use(router);
app.mount('#app', () => {
    nextTick(() => {
        initFlowbite();

    })
});
