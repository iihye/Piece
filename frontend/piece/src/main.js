import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";

import { library } from "@fortawesome/fontawesome-svg-core";
import { fas } from "@fortawesome/free-solid-svg-icons";
import { far } from "@fortawesome/free-regular-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import axios from "axios";

import "./registerServiceWorker";

library.add(fas);
library.add(far);

const app = createApp(App);
app.component("font-awesome-icon", FontAwesomeIcon);

app.use(createPinia());
app.use(router);

app.mount("#app");

function initializeAuth() {
    const accessToken = localStorage.getItem("accessToken");
    if (accessToken) {
        axios.defaults.headers.common["Authorization"] = accessToken;
    }
}
initializeAuth();
