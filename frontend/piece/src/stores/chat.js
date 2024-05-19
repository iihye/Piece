import { defineStore } from "pinia";
import axios from "axios";

export const useChatStore = defineStore("chat", () => {
    // =========== STATE ===============

    // =========== GETTER ===============

    // =========== ACTION ===============
    async function getChatMessageList(chatRoomId) {
        try {
            const response = await axios.get(
                `${import.meta.env.VITE_REST_CHAT_API}/chats/list/${chatRoomId}`
            );

            console.log("getChatMessageList()");

            // 채팅방 메시지 불러오기
            // response.data.data.forEach((m) => {
            //   console.log(
            //     m.chatRoomId,
            //     m.senderId,
            //     m.content,
            //     m.createdAt,
            //     m.profileImage
            //   );
            // });

            return response.data.data;
        } catch (error) {
            console.error("Error fetching chat room list:", error);
            throw error;
        }
    }

    return {
        getChatMessageList,
    };
});
