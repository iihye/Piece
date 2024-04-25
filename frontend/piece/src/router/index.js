import { createRouter, createWebHistory } from "vue-router";
import MainView from "@/views/main/MainView.vue";
// login
import LoginView from "@/views/login/LoginView.vue";
import SignInView from "@/views/login/SignInView.vue";
import KakaoView from "@/views/login/KakaoView.vue";
import SuccessView from "@/views/login/SuccessView.vue";
// piecelist
import PieceListMainView from "@/views/piecelist/PieceListMainView.vue";
import PieceMakeView from "@/views/piecemake/PieceMakeView.vue";
import ChatView from "@/views/chat/ChatView.vue";
import MypageView from "@/views/mypage/MypageView.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/",
            name: "main",
            component: MainView,
        },
        {
            path: "/login",
            name: "login",
            component: LoginView,
        },
        {
            path: "/signin",
            name: "signin",
            component: SignInView,
        },
        {
            path: "/kakao",
            name: "kakao",
            component: KakaoView,
        },
        {
            path: "/signin/success",
            name: "signinSuccess",
            component: SuccessView,
        },
        {
            path: "/piecelist",
            name: "piecelistmain",
            component: PieceListMainView,
        },
        {
            path: "/piecemake",
            name: "piecemake",
            component: PieceMakeView,
        },
        {
            path: "/chat",
            name: "chat",
            component: ChatView,
        },
        {
            path: "/mypage",
            name: "mypage",
            component: MypageView,
        },
    ],
});

export default router;
