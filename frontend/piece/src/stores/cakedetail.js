import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

export const useCakeDetailStore = defineStore('cakeDetail', () => {
    const cakeDetail = ref({});
    const cakeChatList = ref([]);
    const cakeChatUser = ref({});
    const cakeChatUserLabel = ref("");

    const getCakeDetail = computed(() => cakeDetail.value);
    const getCakeChatList = computed(() => cakeChatList.value);
    const getCakeChatUser = computed(() => cakeChatUser.value);
    const getCakeChatUserLabel = computed(() => cakeChatUserLabel.value);

    const fetchCakeDetail = async (concertId) => {
        try {
            const response = await axios.get(`${import.meta.env.VITE_REST_PIECE_API}/cultures/kopis/${concertId}`);
            if (response.data.code === "FIND_KOPIS_CULTURE_SUCCESS") {
                cakeDetail.value = response.data.data;
            } else {
                console.error('Failed to fetch cake detail');
            }
        } catch (error) {
            console.error('Error fetching cake detail:', error);
        }
    };

    const findCakeChatList = async (concertId) => {
        try {
            const response = await axios.get(`${import.meta.env.VITE_REST_PIECE_API}/chat/${concertId}`);
            if (response.data.code === "FIND_CHAT_SUCCESS") {
                cakeChatList.value = response.data.data;
            } else {
                console.error('Failed to fetch chat list');
            }
        } catch (error) {
            console.error('Error fetching chat list:', error);
        }
    };

    return {
        cakeDetail,
        cakeChatList,
        cakeChatUser,
        cakeChatUserLabel,
        getCakeDetail,
        getCakeChatList,
        getCakeChatUser,
        getCakeChatUserLabel,
        fetchCakeDetail,
        findCakeChatList
    };
});
