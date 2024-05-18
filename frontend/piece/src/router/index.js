import { createRouter, createWebHistory } from "vue-router";
import MainView from "@/views/main/MainView.vue";
// login
import LoginView from "@/views/login/LoginView.vue";
import SignInView from "@/views/login/SignInView.vue";
import KakaoView from "@/views/login/KakaoView.vue";
import SuccessView from "@/views/login/SuccessView.vue";
// piecemake
import PieceMakeView from "@/views/piecemake/PieceMakeView.vue";
import PieceImageView from "@/views/piecemake/PieceImageView.vue";
import ImageCropView from "@/views/piecemake/ImageCropView.vue";
import PieceInfoView from "@/views/piecemake/PieceInfoView.vue";
import PieceCommentView from "@/views/piecemake/PieceCommentView.vue";
import PieceFrontView from "@/views/piecemake/PieceFrontView.vue";
import PieceBackgroundView from "@/views/piecemake/PieceBackgroundView.vue";
import PieceSaveView from "@/views/piecemake/PieceSaveView.vue";
// piecelist
import PieceListMainView from "@/views/piecelist/PieceListMainView.vue";
import PieceListView from "@/views/piecelist/PieceListView.vue";
import PieceCalendarView from "@/views/piecelist/PieceCalendarView.vue";
import PieceListMyView from "@/views/piecelist/PieceListMyView.vue";
import PieceListHeartView from "@/views/piecelist/PieceListHeartView.vue";
import PieceDetailView from "@/views/piecedetail/PieceDetailView.vue";
import RecordDetailView from "@/views/piecedetail/RecordDetailView.vue";
import PieceSearchView from "@/views/piecelist/PieceSearchView.vue";
import PieceMySearchView from "@/views/piecelist/PieceMySearchView.vue";
// chat
import ChatRoomView from "@/views/chat/ChatRoomView.vue";
import ChatConversationView from "@/views/chat/ChatConversationView.vue";
// mypage
import MypageView from "@/views/mypage/MypageView.vue";
import NicknameView from "@/views/mypage/NicknameView.vue";
import PasswordView from "@/views/mypage/PasswordView.vue";
import ProfileImgView from "@/views/mypage/ProfileImgView.vue";
import LabelView from "@/views/mypage/LabelView.vue";
import PieceStatisticsView from "@/views/mypage/PieceStatisticsView.vue";
import ConsumeStatisticsView from "@/views/mypage/ConsumeStatisticsView.vue";
//cake
import CakeDetailView from "@/views/cake/CakeDetailView.vue";
import CakeListView from "@/views/cake/CakeListView.vue";

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
    // piecemake
    {
      path: "/piece",
      name: "piecemake",
      component: PieceMakeView,
    },
    {
      path: "/piece/image",
      name: "pieceimage",
      component: PieceImageView,
    },
    {
      path: "/piece/image/crop",
      name: "pieceimagecrop",
      component: ImageCropView,
    },
    {
      path: "/piece/info",
      name: "pieceinfo",
      component: PieceInfoView,
    },
    {
      path: "/piece/comment",
      name: "piececomment",
      component: PieceCommentView,
    },
    {
      path: "/piece/front",
      name: "piecefront",
      component: PieceFrontView,
    },
    {
      path: "/piece/background",
      name: "piecebackground",
      component: PieceBackgroundView,
    },
    {
      path: "/piece/save",
      name: "piecesave",
      component: PieceSaveView,
    },
    // piecelist
    {
      path: "/piecelist/main",
      name: "piecelistmain",
      component: PieceListMainView,
    },
    {
      path: "/piecelist/cake",
      name: "cakelist",
      component: CakeListView,
    },
    {
      path: "/piecelist/cake/:cakeId",
      name: "cakeDetail",
      component: CakeDetailView,
    },
    {
      path: "/piecelist",
      name: "pieceList",
      component: PieceListView,
    },
    {
      path: "/piecelist/my/cal",
      name: "pieceCalendar",
      component: PieceCalendarView,
    },
    {
      path: "/piecelist/my/list",
      name: "pieceListMy",
      component: PieceListMyView,
    },
    {
      path: "/piecelist/heart/list",
      name: "pieceListHeart",
      component: PieceListHeartView,
    },
    {
      path: "/piecelist/:pieceId",
      name: "pieceDetail",
      component: PieceDetailView,
    },
    {
      path: "/piecelist/record/:pieceId",
      name: "recordDetail",
      component: RecordDetailView,
    },
    {
      path: "/piecelist/search/:keyword",
      name: "piecesearch",
      component: PieceSearchView,
    },
    {
      path: "/piecelist/search/my/:keyword",
      name: "piecemysearch",
      component: PieceMySearchView,
    },
    // chat
    {
      path: "/chatroom",
      name: "chatRoom",
      component: ChatRoomView,
    },
    {
      path: "/chat",
      name: "chat",
      component: ChatConversationView,
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
    {
      path: "/mypage/piece",
      name: "piecestatistics",
      component: PieceStatisticsView,
    },
    // cake
    {
      path: "/cake",
      name: "cakeList",
      component: CakeListView,
    },
    {
      path: "/views/:id",
      name: "CakeDetail",
      component: CakeDetailView,
      props: true,
    },
  ],
});

// navigation guard
router.beforeEach((to, from, next) => {
  window.scrollTo(0, 0);
  // token이 없을 때
  if (
    localStorage.getItem("accessToken") == "" ||
    localStorage.getItem("accessToken") == null
  ) {
    if (to.name == "main" || to.name == "login" || to.name == "signin") {
      next();
    } else {
      window.alert("로그인이 필요합니다.");
      next({ name: "login" });
    }
  } else {
    next();
  }
});

export default router;
