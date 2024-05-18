import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useCakeDetailStore = defineStore("cakedetail", () => {
    const cakeDetail = ref({});
    const cakeChatList = ref([]);
    const cakeHeartCount = ref(0);
    const cakeHeartState = ref(false);
    const cakeCultureType = ref("");

    const setCakeCultureType = function (cultureType) {
        cakeCultureType.value = cultureType;
    };

    const getCakeCultureType = computed(() => {
        return cakeCultureType.value;
    });

    const fetchCakeDetail = async (concertId) => {
        try {
            const response = await axios.get(
                `${import.meta.env.VITE_REST_PIECE_API}/cultures/kopis/${concertId}`
            );
            if (response.data.code === "FIND_KOPIS_CULTURE_SUCCESS") {
                cakeDetail.value = response.data.data;
                cakeHeartState.value = response.data.data.isHearted;
            }
        } catch (error) {
            console.error("공연 상세정보 가져오기 실패", error);
        }
    };

    const fetchHeartCount = async (cultureId) => {
        try {
            const response = await axios.get(
                `${import.meta.env.VITE_REST_PIECE_API}/cultures/heart/count/${cultureId}`
            );
            if (response.data.code === "COUNT_CULTURE_HEART_SUCCESS") {
                cakeHeartCount.value = response.data.data;
            }
        } catch (error) {
            console.error("하트수 가져오기 실패", error);
        }
    };

    const toggleHeart = async (cultureId) => {
        try {
            const response = await axios.post(
                `${import.meta.env.VITE_REST_PIECE_API}/cultures/heart/${cultureId}`
            );
            if (response.data.code === "TOGGLE_HEART_SUCCESS") {
                await fetchHeartCount(cultureId);
                cakeHeartState.value = true;
            }
            return response.data;
        } catch (error) {
            console.error("찜하기 실패", error);
            throw error;
        }
    };

    const removeHeart = async (cultureId) => {
        try {
            const response = await axios.delete(
                `${import.meta.env.VITE_REST_PIECE_API}/cultures/heart/${cultureId}`
            );
            if (response.data.code === "REMOVE_HEART_SUCCESS") {
                await fetchHeartCount(cultureId);
                cakeHeartState.value = false;
            }
            return response.data;
        } catch (error) {
            console.error("찜하기 취소 실패", error);
            throw error;
        }
    };

    const findCakeChatList = async (concertId) => {
        try {
            const response = await axios.get(
                `${import.meta.env.VITE_REST_PIECE_API}/chats/${concertId}`
            );
            if (response.data.code === "FIND_CHAT_LIST_SUCCESS") {
                cakeChatList.value = response.data.data;
            }
        } catch (error) {
            console.error("채팅목록 가져오기 실패", error);
        }
    };

    const joinChatRoom = async (cultureId) => {
        try {
            const response = await axios.post(
                `${import.meta.env.VITE_REST_PIECE_API}/chats/join/${cultureId}`
            );
            return response.data;
        } catch (error) {
            console.error("채팅방 가져오기 실패", error);
            throw error;
        }
    };

    return {
        cakeDetail,
        cakeChatList,
        cakeHeartCount,
        cakeHeartState,
        cakeCultureType,

        setCakeCultureType,
        getCakeCultureType,

        fetchCakeDetail,
        fetchHeartCount,
        findCakeChatList,
        toggleHeart,
        removeHeart,
        joinChatRoom,

        getCakeDetail: () => cakeDetail.value,
        getCakeChatList: () => cakeChatList.value,
        getCakeHeartCount: () => cakeHeartCount.value,
        getCakeHeartState: () => cakeHeartState.value,
    };
});
