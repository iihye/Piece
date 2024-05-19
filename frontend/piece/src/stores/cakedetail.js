import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useCakeDetailStore = defineStore("cakedetail", () => {
    const cakeDetail = ref({});
    const cakeChatList = ref([]);
    const cakeHeartCount = ref(0);
    const cakeHeartState = ref(false);
    const cakeCultureType = ref("");

    const setCakeCultureType = (cultureType) => {
        cakeCultureType.value = cultureType;
    };

    const getCakeCultureType = computed(() => cakeCultureType.value);
    const getCakeDetail = computed(() => cakeDetail.value);
    const getCakeChatList = computed(() => cakeChatList.value);
    const getCakeHeartCount = computed(() => cakeHeartCount.value);
    const getCakeHeartState = computed(() => cakeHeartState.value);


    const fetchConcertCakeDetail = async (concertId) => {
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

    const fetchTmdbDetail = async (code) => {
        try {
            const response = await axios.get(`${import.meta.env.VITE_REST_PIECE_API}/cultures/tmdb/${code}`);
            if (response.data.code === "FIND_TMDB_CULTURE_SUCCESS") {
                cakeDetail.value = response.data.data;
                cakeHeartState.value = response.data.data.isHearted;
            } else {
                console.error('Failed to fetch TMDB details:', response.data);
                selectedMovie.value = null;
            }
        } catch (error) {
            console.error("TMDB 상세정보 가져오기 실패", error);
            selectedMovie.value = null;
            throw error;
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
        fetchConcertCakeDetail,
        fetchTmdbDetail,
        fetchHeartCount,
        findCakeChatList,
        toggleHeart,
        removeHeart,
        joinChatRoom,

        getCakeDetail,
        getCakeChatList,
        getCakeHeartCount,
        getCakeHeartState,
        getCakeCultureType,
    };
});
