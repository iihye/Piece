import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useChatRoomStore = defineStore("chatroom", () => {
  // =========== STATE ===============

  const isPersonal = ref(0); // chatRoomListView로 보내주기 위한 변수
  const chatRoomId = ref(0); // chatConversationView로 보내주기 위한 변수
  const chatRoomListValue = ref([]);
  const chatRoom = ref({});
  const partnerInfo = ref({});

  // =========== GETTER ===============
  const getChatRoomId = computed(() => chatRoomId.value);

  const getIsPersonal = computed(() => isPersonal.value);

  const getChatRoomListValue = computed(() => chatRoomListValue.value);

  const getChatRoom = computed(() => chatRoom.value);

  const getPartnerInfo = computed(() => partnerInfo.value);

  // =========== SETTER ===============

  function setChatRoomId(chatRoomId) {
    this.chatRoomId = chatRoomId;
  }

  function setIsPersonal(isPersonal) {
    this.isPersonal = isPersonal;
  }

  // =========== ACTION ===============
  async function createChatRoom(cultureId, chatRoomName, isPersonal) {
    console.log("채팅방을 생성합니다.");
    const chatRoomsRequestDto = {
      cultureId: cultureId,
      chatRoomName: chatRoomName,
      isPersonal: isPersonal,
    };

    try {
      const response = await axios.post(
        `${import.meta.env.VITE_REST_CHAT_API}/chatrooms/create`,
        chatRoomsRequestDto
      );

      console.log("생성된 채팅방 id:" + JSON.stringify(response.data.data));

      return response.data.data;
    } catch (error) {
      console.error("Error fetching chat room list:", error);
      throw error;
    }
  }

  async function joinChatRoom(chatRoomId, userId) {
    // authenticated user로 수정 필요
    console.log(chatRoomId + "번 방에 " + userId + "번 유저가 참가합니다.");
    const participantsRequestDto = {
      chatRoomId: chatRoomId,
      userId: userId,
    };

    try {
      const response = await axios.post(
        `${import.meta.env.VITE_REST_CHAT_API}/participate`,
        participantsRequestDto
      );
    } catch (error) {
      console.error("Error fetching chat room list:", error);
      throw error;
    }
  }

  async function getChatRoomList(isPersonal) {
    // participate 리스트 반환
    console.log("채팅방 목록을 가져옵니다:" + isPersonal);
    const isParticipateRequestDto = {
      isPersonal: isPersonal,
    };

    try {
      const response = await axios.post(
        `${import.meta.env.VITE_REST_CHAT_API}/isparticipate`,
        isParticipateRequestDto
      );
      this.chatRoomListValue = response.data.data; // 받은 데이터로 상태 업데이트

      console.log("채팅방 목록 호출 결과" + JSON.stringify(response.data));

      response.data.data.forEach((m) => {
        console.log(m);
      });

      console.log("채팅방 value:" + this.chatRoomListValue[0]);

      return response.data.data;
    } catch (error) {
      console.error("Error fetching chat room list:", error);
      throw error;
    }
  }

  function getOpenChatRoomInfo(chatroomId) {
    // chatRoom 갱신
    // 입장한 채팅방 정보
    // chatRoomListValue에서 해당 chatroomId에 대한 채팅방을 찾습니다.
    chatRoom.value = chatRoomListValue.value.find(
      (room) => room.chatRoomId === chatroomId
    );

    console.log("로그인한 사용자 id:" + localStorage.getItem("userId"));

    // console.log("현재 채팅방:" + JSON.stringify(chatRoom.value));
  }

  function getPersonalChatRoomInfo(chatroomId) {
    // chatRoom 갱신
    // 입장한 채팅방 정보
    // chatRoomListValue에서 해당 chatroomId에 대한 채팅방을 찾습니다.
    chatRoom.value = chatRoomListValue.value.find(
      (room) => room.chatRoomId === chatroomId
    );

    console.log("로그인한 사용자 id:" + localStorage.getItem("userId"));

    chatRoom.value.participants.forEach((p) => {
      if(p.userId!==1){
        console.log("🫠얘 1이 아니에요!!"+p.userId);
      }
      console.log("참가자 userId 출력 테스트:"+p.userId);
    });

    console.log(
      "상대방 정보:" +
        JSON.stringify(
          chatRoom.value.participants.find(
            (participant) =>
              // participant.userId !== localStorage.getItem("userId")
            participant.userId !==1
          )
        )
    );

    // 현재 채팅방의 참가자 중 상대방의 정보 받아오기
    partnerInfo.value = chatRoom.value.participants.find(
      // (participant) => participant.userId !== localStorage.getItem("userId")
      (participant) => participant.userId != 1 // 임의값
    );

    console.log("참가자 출력 테스트");

    chatRoom.value.participants.forEach((p) => {
      console.log(p);
    });
    // console.log("현재 채팅방:" + JSON.stringify(chatRoom.value));
  }

  return {
    isPersonal,
    chatRoomId,
    chatRoomListValue,
    chatRoom,
    partnerInfo,
    getChatRoomId,
    getIsPersonal,
    getChatRoomListValue,
    getChatRoom,
    getPartnerInfo,
    setIsPersonal,
    setChatRoomId,
    createChatRoom,
    joinChatRoom,
    getChatRoomList,
    getOpenChatRoomInfo,
    getPersonalChatRoomInfo,
  };
});
