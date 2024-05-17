<template>
    <div class="chatRoomListHeader-main-container">
        <div
            class="chatroomlist-cardItems"
            v-for="chatRoom in chatRoomList"
            :key="chatRoom"
            @click="goToChatConversation(chatRoom)"
        >
            <!-- 오픈채팅 -->
            <div
                v-if="chatRoom.isPersonal === false"
                class="chatroomlist-sub-container"
            >
                <div class="chatroomlist-item-container">
                    <img
                        class="chatroomlist-item-squreImage"
                        :src="chatRoom.culture.imageUrl"
                        alt="profileImage"
                    />
                    <div class="chatroomlist-info-conatiner">
                        <div class="chatroomlist-info-nickname">
                            {{ chatRoom.culture.title }}
                        </div>
                        <div class="chatroomlist-info-message">
                            {{ chatRoom.participantCount }}명이 대화하고 있어요!
                        </div>
                        <div class="chatroomlist-info-time">n분 전</div>
                    </div>
                </div>
            </div>

            <!-- 일대일 채팅 -->
            <div
                v-if="chatRoom.isPersonal === true"
                class="chatroomlist-sub-container"
            >
                <div
                    class="chatroomlist-item-container"
                    v-for="participant in chatRoom.participants"
                    :key="participant.userId"
                >
                    <img
                        class="chatroomlist-item-circleImage"
                        v-if="participant.userId != loginUserId"
                        :src="participant.profileImage"
                        alt="profileImage"
                    />
                    <div
                        class="chatroomlist-info-conatiner"
                        v-if="participant.userId != loginUserId"
                    >
                        <div class="chatroomlist-info-nickname">
                            {{ participant.nickname }}
                        </div>
                        <div class="chatroomlist-info-message">
                            {{ chatRoom.lastMessage }}
                        </div>
                        <div class="chatroomlist-info-time">n분 전</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import { useChatRoomStore } from "@/stores/chatroom";

const router = useRouter();
const chatRoomStore = useChatRoomStore();
const loginUserId = ref();

const chatRoomList = ref([
    // {
    //     "chatRoomId": 1,
    //     "cultureId": 1,
    //     "updatedAt": "업데이트시간",
    //     "imageUrl": "src/assets/logo.png", // 전역으로 받아올 예정
    //     "chatRoomName": "Vue.js란 과연 무엇인가?",
    //     "isPersonal": false,
    //     "isOpened": true,
    // }
]); // chatroomList를 reactive하게 만들기 위해 ref 사용

async function fetchLoginUserId() {
    loginUserId.value = await localStorage.getItem("userId");
    console.log("로그인되어 있는 userId:", loginUserId.value);
}

// 채팅방 목록 받기
async function fetchRooms(isPersonal) {
    try {
        const chatRooms = await chatRoomStore.getChatRoomList(isPersonal);
        console.log("store에서 채팅방 목록을 받았습니다.");
        chatRooms.forEach((m) => {
            chatRoomList.value.push(m);
        });
    } catch (error) {
        console.error("Error fetching chat logs:", error);
    }
}

watch(
    () => chatRoomStore.isPersonal,
    (newValue, oldValue) => {
        console.log("isPersonal 값이 변경되었습니다:" + newValue);
        // 변경된 값에 대한 추가적인 처리를 수행할 수 있습니다.
        // 채팅방 정보 갱신
        chatRoomList.value = [];

        console.log(
            "받아올 채팅방 목록 변경. 현재 login user id:" + loginUserId.value
        );

        fetchRooms(newValue);
    }
);

const goToChatConversation = (chatRoom) => {
    // 선택된 채팅방 정보 변경
    chatRoomStore.setChatRoomId(chatRoom.chatRoomId);
    chatRoomStore.setIsPersonal(chatRoom.isPersonal);

    router.push("/chat");
    // 채팅 메시지 페이지로 이동
};

// 페이지가 마운트될 때 chatroomList를 가져오도록 설정
onMounted(async () => {
    await fetchLoginUserId();

    await fetchRooms(0); // default: 오픈 채팅방
});
</script>

<style>
.chatRoomListHeader-main-container {
    display: flex;
    flex-direction: column;
    height: calc(100vh - 7.25em - 3.2rem);
}

.chatroomlist-cardItems {
    display: flex;
    justify-content: flex-start;
    padding: 0.4rem;
}

.chatroomlist-cardItems:hover {
    background-color: var(--sub-color);
}

.chatroomlist-cardItems:active {
    background-color: var(--main-color);
}

/* item */
.chatroomlist-sub-container {
    width: 100%;
}

.chatroomlist-item-container {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    margin-right: 1rem;
}

.chatroomlist-item-squreImage {
    width: 5rem;
    height: 5rem;
    border: 1px solid var(--gray-color);
    border-radius: 10%;
    margin-right: 0.6rem;
}

.chatroomlist-item-circleImage {
    width: 5rem;
    height: 5rem;
    border: 1px solid var(--gray-color);
    border-radius: 50%;
    margin-right: 0.6rem;
}

/* info */
.chatroomlist-info-container {
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
    height: auto;
}

.chatroomlist-info-nickname {
    font-family: "Bold";
    font-size: 1rem;
    color: var(--black-color);
    margin-bottom: 0.1rem;
}

.chatroomlist-info-message {
    font-family: "Regular";
    font-size: 1rem;
    color: var(--gray2-color);
    margin-bottom: 0.1rem;
}

.chatroomlist-info-time {
    font-family: "Regular";
    font-size: 1rem;
    color: var(--main-color);
    margin-bottom: 0.1rem;
}
</style>
