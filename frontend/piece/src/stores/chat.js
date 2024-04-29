import { defineStore } from 'pinia';
import axios from 'axios';

const baseUrl="http://localhost:8080";

export const useChatStore = defineStore(
    'chat',
    () => {
        // =========== STATE ===============

        // =========== GETTER ===============

        // =========== ACTION ===============
        async function getChatMessageList(chatRoomId) {
            try {
                const response = await axios.get(`${baseUrl}/chat/list/${chatRoomId}`);

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
