import { ref } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useCakeDetailStore = defineStore("cakedetail", () => {
    const cakeDetail = ref({});
    const cakeChatList = ref([]);
    const cakeHeartCount = ref(0);

    const fetchCakeDetail = async (concertId) => {
        try {
            const response = await axios.get(`${import.meta.env.VITE_REST_PIECE_API}/cultures/kopis/${concertId}`);
        if (response.data.code === "FIND_KOPIS_CULTURE_SUCCESS") {
            cakeDetail.value = response.data.data;
        }
        } catch (error) {
            console.error("공연 정보 불러오기 실패", error);
        }
    };

    const fetchHeartCount = async (cultureId) => {
        try {
            const response = await axios.get(`${import.meta.env.VITE_REST_PIECE_API}/heart/count/${cultureId}`);
            if (response.data.code === "COUNT_CULTURE_HEART_SUCCESS") {
                cakeHeartCount.value = response.data.data;
            }
        } catch (error) {
            console.error("찜개수 불러오기 실패", error);
        }
    };

    const findCakeChatList = async (concertId) => {
        try {
            const response = await axios.get(`${import.meta.env.VITE_REST_PIECE_API}/chats/${concertId}`);
            if (response.data.code === "FIND_CHAT_LIST_SUCCESS") {
                cakeChatList.value = response.data.data;
            }
        } catch (error) {
            console.error("채팅 미리보기 불러오기 실패", error);
        }
    };

    return {
    cakeDetail,
    cakeChatList,
    cakeHeartCount,
    fetchCakeDetail,
    fetchHeartCount,
    findCakeChatList,
    getCakeDetail: () => cakeDetail.value,
    getCakeChatList: () => cakeChatList.value,
    getCakeHeartCount: () => cakeHeartCount.value,
    };
});
