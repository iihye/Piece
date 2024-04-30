import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';

export const useChatRoomStore = defineStore(
    'chatroom',
    () => {
    // =========== STATE ===============

    const isPersonal=ref(0); // chatRoomListView로 보내주기 위한 변수
    const chatRoomId=ref(0); // chatConversationView로 보내주기 위한 변수
    const chatRoomListValue=ref([]);

    // =========== GETTER ===============
    const getChatRoomId=computed(() =>
        chatRoomId.value
    );

    const getIsPersonal=computed(() =>
        isPersonal.value
    );

    const getChatRoomListValue=computed(() =>
        chatRoomListValue.value
    );

    // =========== SETTER ===============

    function setChatRoomId(chatRoomId){
        this.chatRoomId=chatRoomId
    };

    function setIsPersonal(isPersonal){
        this.isPersonal=isPersonal
    };

    // =========== ACTION ===============

    async function getChatRoomList(userId, isPersonal) {
        console.log("채팅방 목록을 가져옵니다.");
        const chatRoomListRequestDto={
            "userId": userId, // 임의 test값 부여
            "isPersonal": isPersonal
        };

        try {
            const response = await axios.post(`${import.meta.env.VITE_REST_URL}/isparticipate`, chatRoomListRequestDto);
            this.chatRoomListValue = response.data; // 받은 데이터로 상태 업데이트

            console.log("응답 결과"+response.data);

            response.data.forEach(m=>{
                console.log(m);
            });

            return response.data;

        } catch (error) {
            console.error('Error fetching chat room list:', error);
            throw error;
        }
    }

    return {
        isPersonal,
        chatRoomId,
        chatRoomListValue,
        getChatRoomId,
        getIsPersonal,
        getChatRoomListValue,
        getChatRoomId,
        setIsPersonal,
        setChatRoomId,
        getChatRoomList
    }
});
