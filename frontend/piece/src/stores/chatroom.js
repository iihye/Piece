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
    const chatRoom=ref({});

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

    const getChatRoom=computed(()=>
        chatRoom.value
    );

    // =========== SETTER ===============

    function setChatRoomId(chatRoomId){
        this.chatRoomId=chatRoomId
    };

    function setIsPersonal(isPersonal){
        this.isPersonal=isPersonal
    };

    // =========== ACTION ===============

    async function getChatRoomList(userId, isPersonal) { // participate 리스트 반환
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

            console.log("채팅방 value:"+this.chatRoomListValue[0]);

            return response.data;

        } catch (error) {
            console.error('Error fetching chat room list:', error);
            throw error;
        }
    }

    function getChatRoomInfo(chatroomId) { // 입장한 채팅방 정보
        // chatRoomListValue에서 해당 chatroomId에 대한 채팅방을 찾습니다.
        chatRoom.value = chatRoomListValue.value.find(room => room.chatRoomId === chatroomId);

        // console.log("현재 채팅방 이름:"+chatRoom.value.chatRoomName);
    }
    return {
        isPersonal,
        chatRoomId,
        chatRoomListValue,
        chatRoom,
        getChatRoomId,
        getIsPersonal,
        getChatRoomListValue,
        getChatRoom,
        setIsPersonal,
        setChatRoomId,
        getChatRoomList,
        getChatRoomInfo,
    }
});
