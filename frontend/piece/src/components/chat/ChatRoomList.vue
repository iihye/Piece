<template>
    <div id="chatRoomList">
        <ul style="list-style:none; padding-inline-start:0px;">
            <li class="chatroomlist-cardItems" v-for="chatRoom in chatRoomList" :key="chatRoom" @click="goToChatConversation(chatRoom.chatRoomId)">
                <div class="chatroomlist-chatRoomCard">
                    <img class="chatroomlist-chatRoomThumbnail" :src="chatRoom.imageUrl" alt="썸네일" />
                </div>
                <div class="chatroomlist-chatRoomCardInfo">
                    <span class="chatroomlist-chatRoomName">
                        {{ chatRoom.chatRoomName }}
                    </span>
                </div>
            </li>
        </ul>
    </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRouter } from "vue-router";
import { useChatRoomStore } from '@/stores/chatroom';

const router=useRouter();
const chatRoomStore = useChatRoomStore();

const chatRoomList = ref([
    {
        "chatRoomId": 1,
        "cultureId": 1,
        "updatedAt": "업데이트시간",
        "imageUrl": "src/assets/logo.png", // 전역으로 받아올 예정
        "chatRoomName": "Vue.js란 과연 무엇인가?",
        "isPersonal": false,
        "isOpened": true,
    }
]); // chatroomList를 reactive하게 만들기 위해 ref 사용

// 채팅방 목록 받기
async function fetchRooms(userId, isPersonal) {
    try {
        const chatRooms=await chatRoomStore.getChatRoomList(userId, isPersonal);
        console.log("store에서 채팅방 목록을 받았습니다.");
        chatRooms.forEach(m=>{
            console.log(m.chatRoomId);
            chatRoomList.value.push(m);
        });

    } catch (error) {
        console.error("Error fetching chat logs:", error);
    }
}

watch(() => chatRoomStore.isPersonal, (newValue, oldValue) => {
  console.log('isPersonal 값이 변경되었습니다:'+newValue);
  // 변경된 값에 대한 추가적인 처리를 수행할 수 있습니다.
  // 채팅방 정보 갱신
  chatRoomList.value=[];

    fetchRooms(1, newValue);
});


const goToChatConversation = (chatRoomId) => {
    // 선택된 채팅방 id 변경
    chatRoomStore.setChatRoomId(chatRoomId);

    router.push('/chat');
    // 채팅 메시지 페이지로 이동
};

// 페이지가 마운트될 때 chatroomList를 가져오도록 설정
onMounted(() => {
    fetchRooms(1, 0); // default: 오픈 채팅방
    // chatRoomList.value=chatRoomStore.getChatRoomList;
});
</script>

<style scopped>
#chatRoomList{
    border:1px solid red;
}
.chatroomlist-chatRoomCard{
    width:29%;
}
.chatroomlist-cardItems:hover{
    background-color: rgb(255, 244, 248);
}
.chatroomlist-cardItems:active{
    background-color: rgb(255, 214, 214);
}
.chatroomlist-chatRoomName{
    font-weight:bold;
    font-size:20px;
}
.chatroomlist-cardItems{
    display:flex;
    justify-content: flex-start;
}
.chatroomlist-chatRoomThumbnail{
    width:100%;
    margin-left:10px;
    margin-right:10px;
}
.chatroomlist-chatRoomCardInfo{
    margin-left:20px;
}
</style>