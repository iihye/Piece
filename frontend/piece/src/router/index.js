import { createRouter, createWebHistory } from "vue-router";
import MainView from "@/views/main/MainView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "mainPage",
      component: MainView,
    },
  ],
});

export default router;
