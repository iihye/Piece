import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useCakeDetailStore = defineStore('cakeDetail', () => {
    const cakeDetail = ref({});
    const cakeChatList = ref([]);
    const cakeHeartCount = ref(0);

    const getCakeDetail = computed(() => cakeDetail.value);
    const getCakeChatList = computed(() => cakeChatList.value);
    const getCakeHeartCount = computed(() => cakeHeartCount.value);

    const fetchCakeDetail = async (concertId) => {
        try {
            const response = await axios.get(`${import.meta.env.VITE_REST_PIECE_API}/cultures/kopis/${concertId}`);
            if (response.data.code === "FIND_KOPIS_CULTURE_SUCCESS") {
                cakeDetail.value = response.data.data;
            }
        } catch (error) {
            console.error("Failed to fetch cake detail", error);
        }
    };

    const fetchHeartCount = async (cultureId) => {
        try {
            const response = await axios.get(`${import.meta.env.VITE_REST_PIECE_API}/heart/count/${cultureId}`);
            if (response.data.code === "COUNT_CULTURE_HEART_SUCCESS") {
                cakeHeartCount.value = response.data.data;
            }
        } catch (error) {
            console.error("Failed to fetch heart count", error);
        }
    };

    const findCakeChatList = async (concertId) => {
        try {
            const response = await axios.get(`${import.meta.env.VITE_REST_PIECE_API}/chats/${concertId}`);
            if (response.data.code === "FIND_CHAT_LIST_SUCCESS") {
                cakeChatList.value = response.data.data;
            }
        } catch (error) {
            console.error("Failed to fetch chat list", error);
        }
    };

    return {
        getCakeDetail,
        getCakeChatList,
        getCakeHeartCount,
        fetchCakeDetail,
        fetchHeartCount,
        findCakeChatList,
    };
});
