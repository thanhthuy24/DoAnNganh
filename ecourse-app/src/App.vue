<!-- eslint-disable -->
<template>
<ToastContainer />
    <component :is="layout">
        <router-view />
    </component>
</template>

<script>
import { computed } from "vue";
import { useRoute } from "vue-router";
import {PUBLIC_LAYOUT} from "@/constants"
import 'flowbite';


export default {
    setup(){
        const route = useRoute();
        console.log(route);
        return{
            layout: computed(() => (route.meta.layout || PUBLIC_LAYOUT) + '-layout')
        }
    },
    
}
</script>

