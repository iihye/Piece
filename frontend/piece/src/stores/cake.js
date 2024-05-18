// import { ref, reactive, computed } from "vue";
// import { defineStore } from "pinia";
// import router from "@/router";
// import axios from "axios";

// export const useCakeStore = defineStore(
//     "cake",
//     () => {
//         // =========== STATE ===============
//         const selectedOptionCakeList = ref("ALL");
//         const cakeList = ref({});
//         const cakeListFiltered = ref({});
//         const cakeChatList = ref({});
//         const cakeChatUser = ref({});
//         const cakeChatUserLabel = ref("");

//         // =========== GETTER ===============
//         const getSelectOptionCakeList = computed(
//             () => selectedOptionCakeList.value
//         );

//         const setSelectOptionCakeList = (option) => {
//             selectedOptionCakeList.value = option;
//         };

//         const getCakeList = computed(() => cakeList.value);

//         const getCakeListFiltered = computed(() => cakeListFiltered.value);

//         const getCakeChatList = computed(() => cakeChatList.value);

//         const getCakeChatUser = computed(() => cakeChatUser.value);

//         const getCakeChatUserLabel = computed(() => cakeChatUserLabel.value);

//         // =========== ACTION ===============
//         const findCakeList = function (cultureType, pageSize) {
//             if (cultureType === "ALL") {
//                 cultureType = null;
//             }

//             let url = `${
//                 import.meta.env.VITE_REST_PIECE_API
//             }/cultures?pageSize=${pageSize}&cultureType`;

//             if (cultureType) {
//                 url += `=${cultureType}`;
//             }

//             axios({
//                 url: url,
//             })
//                 .then((res) => {
//                     cakeList.value = res.data.data.dataList;
//                     const nextpage = res.data.data.nextPage;
//                     cakeListFiltered.value = computedFilteredCakeList();
//                 })
//                 .catch((err) => {});
//         };

//         function computedFilteredCakeList() {
//             if (selectedOptionCakeList.value === "ALL") {
//                 return cakeList.value;
//             } else if (selectedOptionCakeList.value === "MOVIE") {
//                 return cakeList.value.filter(
//                     (item) => item.cultureType === "MOVIE"
//                 );
//             } else if (selectedOptionCakeList.value === "THEATER") {
//                 return cakeList.value.filter(
//                     (item) => item.cultureType === "THEATER"
//                 );
//             } else if (selectedOptionCakeList.value === "MUSICAL") {
//                 return cakeList.value.filter(
//                     (item) => item.cultureType === "MUSICAL"
//                 );
//             } else if (selectedOptionCakeList.value === "CONCERT") {
//                 return cakeList.value.filter(
//                     (item) => item.cultureType === "CONCERT"
//                 );
//             } else if (selectedOptionCakeList.value === "ETC") {
//                 return cakeList.value.filter(
//                     (item) => item.cultureType === "ETC"
//                 );
//             } else {
//                 return [];
//             }
//         }

//         const findCakeChatList = function (chatRoomId, count) {
//             cakeChatList.value = [
//                 {
//                     chatRoomId: 1,
//                     senderId: 1,
//                     content: "어서오세요",
//                     createdAt: "2024-05-03T03:39:28.288+00:00",
//                     senderLabel: "새로운",
//                     senderNickname: "김싸피",
//                     senderImg:
//                         "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
//                 },
//                 {
//                     chatRoomId: 1,
//                     senderId: 2,
//                     content: "이번에 서울 시사회 잡으신 분 있나요?",
//                     createdAt: "2024-05-03T05:35:25.076+00:00",
//                     senderLabel: "새로운",
//                     senderNickname: "김싸피",
//                     senderImg:
//                         "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
//                 },
//                 {
//                     chatRoomId: 1,
//                     senderId: 1,
//                     content:
//                         "저도 이번에 피씨방 가서 도전했는데 어렵더라구요ㅜㅜ 이번에는 꼭 가고 싶었는데 말이죠ㅜㅜ",
//                     createdAt: "2024-05-03T05:35:33.430+00:00",
//                     senderLabel: "새로운",
//                     senderNickname: "김싸피",
//                     senderImg:
//                         "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
//                 },
//             ];

//             // TODO: api 연결 테스트하기
//             // axios({
//             //     url: `${import.meta.env.VITE_REST_CHAT_API}/list`,
//             //     method: "GET",
//             //     params: {
//             //         chatroomId: chatRoomId,
//             //         count: count,
//             //     },
//             // })
//             //     .then((res) => {
//             //         cakeChatList.value = res.data.data;
//             //     })
//             //     .catch((err) => {});

//             // cakeChatList.value.forEach((item) => {
//             //     findCakeChatUser(item.senderId);
//             //     item.senderNickname = cakeChatUser.value.nickname;
//             //     console.log(cakeChatUser.value);
//             //     item.senderLabel = cakeChatUserLabel.value;
//             // });

//             // console.log(cakeChatList.value);
//         };

//         const findCakeChatUser = function (userId) {
//             axios({
//                 url: `${
//                     import.meta.env.VITE_REST_USER_API
//                 }/users/find/${userId}`,
//                 method: "GET",
//             })
//                 .then((res) => {
//                     cakeChatUser.value = res.data;
//                     consoe.log("cakeChatUser", cakeChatUser.value);
//                     if (cakeChatUser.value.labelId !== null) {
//                         findCakeChatUserLabel(cakeChatUser.value.labelId);
//                     }
//                 })
//                 .catch((err) => {});
//         };

//         const findCakeChatUserLabel = function (labelId) {
//             axios({
//                 url: `${import.meta.env.VITE_REST_PIECE_API}/labels/${labelId}`,
//                 method: "GET",
//             })
//                 .then((res) => {
//                     cakeChatUserLabel.value = res.data.data;
//                 })
//                 .catch((err) => {});
//         };

//         return {
//             // state
//             selectedOptionCakeList,
//             cakeList,
//             cakeListFiltered,
//             cakeChatList,
//             cakeChatUser,
//             cakeChatUserLabel,
//             // getter
//             getSelectOptionCakeList,
//             setSelectOptionCakeList,
//             getCakeList,
//             getCakeListFiltered,
//             getCakeChatList,
//             getCakeChatUser,
//             getCakeChatUserLabel,
//             // action
//             findCakeList,
//             computedFilteredCakeList,
//             findCakeChatList,
//             findCakeChatUser,
//             findCakeChatUserLabel,
//         };
//     },
//     { persist: true }
// );
import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useCakeStore = defineStore('cake', () => {
    const selectedOptionCakeList = ref("ALL");
    const cakeList = ref([]);
    const cakeListFiltered = ref([]);
    const nextPageUrl = ref(null);
    const loading = ref(false);

    const getSelectOptionCakeList = computed(() => selectedOptionCakeList.value);
    const getCakeList = computed(() => cakeList.value);
    const getCakeListFiltered = computed(() => cakeListFiltered.value);
    const isLoading = computed(() => loading.value);

    const setSelectOptionCakeList = (option) => {
        selectedOptionCakeList.value = option;
    };

    const findCakeList = async function (cultureType, pageSize) {
        if (cultureType === "ALL") {
            cultureType = null;
        }

        let url = `${import.meta.env.VITE_REST_PIECE_API}/cultures?pageSize=${pageSize}&cultureType`;

        if (cultureType) {
            url += `=${cultureType}`;
        }

        loading.value = true;
        try {
            const res = await axios.get(url);
            cakeList.value = res.data.data.dataList;
            nextPageUrl.value = res.data.data.nextPage;
            cakeListFiltered.value = computedFilteredCakeList();
        } catch (err) {
            console.error("Error fetching cake list:", err);
        } finally {
            loading.value = false;
        }
    };

    const loadMoreCakes = async () => {
        if (!nextPageUrl.value || loading.value) return;

        loading.value = true;
        try {
            const res = await axios.get(nextPageUrl.value);
            cakeList.value = [...cakeList.value, ...res.data.data.dataList];
            nextPageUrl.value = res.data.data.nextPage;
            cakeListFiltered.value = computedFilteredCakeList();
        } catch (err) {
            console.error("Error loading more cakes:", err);
        } finally {
            loading.value = false;
        }
    };

    function computedFilteredCakeList() {
        if (selectedOptionCakeList.value === "ALL") {
            return cakeList.value;
        } else {
            return cakeList.value.filter(
                (item) => item.cultureType === selectedOptionCakeList.value
            );
        }
    }

    return {
        cakeList,
        cakeListFiltered,
        nextPageUrl,
        loading,
        selectedOptionCakeList,
        getSelectOptionCakeList,
        getCakeList,
        getCakeListFiltered,
        isLoading,
        setSelectOptionCakeList,
        findCakeList,
        loadMoreCakes
    };
});