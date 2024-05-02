import { defineStore } from 'pinia';
import axios from 'axios';

export const useChatStore = defineStore(
    'chat',
    () => {
        // =========== STATE ===============

        // =========== GETTER ===============

        // =========== ACTION ===============
        async function getChatMessageList(chatRoomId) {
            try {
                const response = await axios.get(`${import.meta.env.VITE_REST_URL}/chats/list/${chatRoomId}`);

                console.log("getChatMessageList()");

                response.data.forEach(m=>{
                    console.log(m.chatRoomId, m.senderId, m.content, m.createdAt);
                })
                
                return response.data;

            } catch (error) {
                console.error('Error fetching chat room list:', error);
                throw error;
            }
        };

    return {
        getChatMessageList
    };
});
