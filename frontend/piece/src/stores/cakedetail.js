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
      console.error("Failed to fetch cake detail", error);
    }
  };

  const fetchHeartCount = async (cultureId) => {
    try {
      const response = await axios.get(`${import.meta.env.VITE_REST_PIECE_API}/cultures/heart/count/${cultureId}`);
      if (response.data.code === "COUNT_CULTURE_HEART_SUCCESS") {
        cakeHeartCount.value = response.data.data;
        console.log('cakeHearCount.value: ', cakeHeartCount.value);
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

  const toggleHeart = async (cultureId) => {
    try {
      const response = await axios.post(`${import.meta.env.VITE_REST_PIECE_API}/heart/toggle/${cultureId}`);
      return response.data;
    } catch (error) {
      console.error("Failed to toggle heart", error);
      throw error;
    }
  };

  const joinChatRoom = async (cultureId) => {
    try {
      const response = await axios.post(`${import.meta.env.VITE_REST_PIECE_API}/chats/join/${cultureId}`);
      return response.data;
    } catch (error) {
      console.error("Failed to join chat room", error);
      throw error;
    }
  };

  return {
    cakeDetail,
    cakeChatList,
    cakeHeartCount,
    fetchCakeDetail,
    fetchHeartCount,
    findCakeChatList,
    toggleHeart,
    joinChatRoom,
    getCakeDetail: () => cakeDetail.value,
    getCakeChatList: () => cakeChatList.value,
    getCakeHeartCount: () => cakeHeartCount.value,

  };
});
