import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useChatRoomStore = defineStore("chatroom", () => {
  // =========== STATE ===============

  // =========== GETTER ===============
  async function loadParticipantInfo(participantId) {
    // 참가자 프사, 칭호, 이름 정보를 표시하기 위한 함수
    try {
      const response = await axios.get(
        `${import.meta.env.VITE_USER_API}/${userId}`
      );

      console.log("받아온 user 정보:" + JSON.stringify(response.data.data)); // 0508 기준. be merge 후 test 가능한 코드

      return response.data.data;
    } catch (error) {
      console.error("Error fetching chat room list:", error);
      throw error;
    }
  }

  // =========== SETTER ===============

  // =========== ACTION ===============

  return {
    loadParticipantInfo,
  };
});
