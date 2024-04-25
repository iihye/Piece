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
// mypage
import MypageView from "@/views/mypage/MypageView.vue";
import NicknameView from "@/views/mypage/NicknameView.vue";
import PasswordView from "@/views/mypage/PasswordView.vue";
import ProfileImgView from "@/views/mypage/ProfileImgView.vue";
import LabelView from "@/views/mypage/LabelView.vue";
import PieceStatisticsView from "@/views/mypage/PieceStatisticsView.vue";
import ConsumeStatisticsView from "@/views/mypage/ConsumeStatisticsView.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/",
            name: "main",
            component: MainView,
        },
        // login
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
        // mypage
        {
            path: "/mypage",
            name: "mypage",
            component: MypageView,
        },
        {
            path: "/mypage/nickname",
            name: "nickname",
            component: NicknameView,
        },
        {
            path: "/mypage/password",
            name: "password",
            component: PasswordView,
        },
        {
            path: "/mypage/profileimg",
            name: "profileImg",
            component: ProfileImgView,
        },
        {
            path: "/mypage/label",
            name: "label",
            component: LabelView,
        },
        {
            path: "/mypage/piece",
            name: "piece",
            component: PieceStatisticsView,
        },
        {
            path: "/mypage/consume",
            name: "consume",
            component: ConsumeStatisticsView,
        },
    ],
});

export default router;
