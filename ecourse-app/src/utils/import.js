import { defineAsyncComponent } from "vue";

export function registerGlobalComponents(app){
    app.component('admin-layout',
        defineAsyncComponent(() => import("@/layouts/admin"))
    );

    app.component('default-layout',
        defineAsyncComponent(() => import("@/layouts/default"))
    );
}