<template>
    <div class="chatconversationview-main-container">
        <ul class="chatconversationview-sub-container" style="list-style: none; padding-inline-start: 0rem; margin: 0px">
            <!-- 저장되어 있던 메시지 목록 -->
            <li v-for="item in chatMessages" :key="item">
                <div
                    class="chatconversationview-messageCard"
                    :class="
                        item.senderId != loginUserId
                            ? 'chatconversationview-fromCard'
                            : 'chatconversationview-toCard'
                    "
                >
                    <!-- 상대가 보낸 메시지 -->
                    <div
                        class="chatconversationview-fromHeader"
                        v-if="item.senderId != loginUserId"
                    >
                        <!-- 프로필 이미지 -->
                        <div class="chatconversationview-profileImage">
                            <!-- <img
                                src="@/assets/defaultprofile.png"
                                @click="openModal(item)"
                            /> -->
                            <img
                                v-if="item.profileImage != null || item.profileImage == ''"
                                :src="item.profileImage"
                                @click="openModal(item)"
                            />
                            <img
                                v-else
                                src="@/assets/defaultprofile.png"
                                @click="openModal(item)"
                            />
                        </div>

                        <!-- 메시지 관련 부분 시작-->
                        <div class="chatconversationv-senderview-container">
                            <div class="chatconversationview-userHeader">
                                <div class="chatconversationview-userTitle">
                                    {{ item.title }}
                                </div>
                                <div class="chatconversationview-userName">
                                    {{ item.nickname }}
                                </div>
                            </div>
                            <div class="chatconversationview-sender-container">
                                <div class="chatconversationview-sender-content">{{ item.content }}</div>
                                <div class="chatconversationview-sender-time">{{ item.createdAt }}</div>
                            </div> 
                        </div>
                    </div>

                    <!-- 내가 보낸 메시지라면 -->
                    <div
                        v-else
                        class="chatconversationview-receiver-container"
                    >
                        <div class="chatconversationview-receiver-time">{{ item.createdAt }}</div>
                        <div class="chatconversationview-receiver-content">{{ item.content }}</div>
                    </div>
                </div>
            </li>

            <!-- 저장할 메시지 목록-->
            <li v-for="item in storeMessages" :key="item">
                <div
                    class="chatconversationview-messageCard"
                    :class="
                        item.senderId != loginUserId
                            ? 'chatconversationview-fromCard'
                            : 'chatconversationview-toCard'
                    "
                >
                    <!-- 상대가 보낸 메시지 -->
                    <div
                        class="chatconversationview-fromHeader"
                        v-if="item.senderId != loginUserId"
                    >
                        <!-- 프로필 이미지 -->
                        <div class="chatconversationview-profileImage">
                            <img
                                v-if="item.profileImage != null"
                                :src="item.profileImage"
                                @click="openModal(item)"
                            />
                            <img
                                v-else
                                :src="defaultProfileImage"
                                @click="openModal(item)"
                            />
                        </div>

                        <!-- 메시지 관련 부분 시작-->
                        <div class="chatconversationv-senderview-container">
                            <div class="chatconversationview-userHeader">
                                <div class="chatconversationview-userTitle">
                                    {{ item.title }}
                                </div>
                                <div class="chatconversationview-userName">
                                    {{ item.nickname }}
                                </div>
                            </div>
                            <div class="chatconversationview-sender-container">
                                <div class="chatconversationview-sender-content">{{ item.content }}</div>
                                <div class="chatconversationview-sender-time">{{ item.createdAt }}</div>
                            </div> 
                        </div>
                    </div>

                    <!-- 내가 보낸 메시지라면 -->
                    <div
                        v-else
                        class="chatconversationview-receiver-container"
                    >
                        <div class="chatconversationview-receiver-time">{{ item.createdAt }}</div>
                        <div class="chatconversationview-receiver-content">{{ item.content }}</div>
                    </div>
                </div>
            </li>
        </ul>
    </div>

    <!-- input button -->
    <div class="chatconversationview-inputWindow">
        <div class="chatconversationview-inputGroup">
            <input
                @keyup.enter="send"
                type="text"
                class="chatconversationview-messageForm"
                v-model="content"
                placeholder="메세지 입력"
            />
        </div>
        <button @click="send" class="chatconversationview-generate">
            <font-awesome-icon
                class="chatconversationview-icon"
                :icon="['fas', 'paper-plane']"
            />
        </button>
    </div>

    <!-- modal -->
    <UserProfileModal
        v-if="showUserProfileModalInChatConversation"
        :userImageUrl="userProfileModalProps.userImageUrl"
        :userLabel="userProfileModalProps.userLabel"
        :userName="userProfileModalProps.userName"
        @handleChatClick="handleChat(userProfileModalProps.userId)"
        :handleReportClick="handleReport"
        :handleClose="handleClose"
    ></UserProfileModal>
</template>

<script setup>
import { ref, onMounted, nextTick } from "vue";
import { useChatRoomStore } from "@/stores/chatroom";
import { useChatStore } from "@/stores/chat";
import { useCommonStore } from "@/stores/common";
import { useWebSocketStore } from "@/stores/websocket";

// modal test import
import { useRouter } from "vue-router";
import UserProfileModal from "@/components/modal/UserProfileModal.vue";

// const defaultProfileImage = ref("src/assets/defaultprofile.png");

// modal test const
const router = useRouter();
const chatRoomStore = useChatRoomStore();
const commonStore = useCommonStore();
// modal 테스트
const showUserProfileModalInChatConversation = ref(false);

// modal 정보들

const userProfileModalProps = ref({
    userImageUrl: "", // 사용자 프로필 이미지 URL
    userLabel: "", // 사용자 레이블
    userName: "", // 사용자 이름
    userId: null,
});

// 정보 전해주기
const openModal = (item) => {
    showUserProfileModalInChatConversation.value = true;

    userProfileModalProps.value.userImageUrl = item.profileImage;
    userProfileModalProps.value.userLabel = item.title;
    userProfileModalProps.value.userName = item.nickname;
    userProfileModalProps.value.userId = item.senderId;
};

// modal에서 chat 클릭했을 때 실행되는 함수
const handleChat = async (userId) => {
    alert("1:1 채팅하기 클릭, 현재 창 user Id: " + userId);

    try {
        const createdChatRoomId = await chatRoomStore.createPersonalChatRoom(
            "개인채팅방 생성 테스트",
            userId
        );

        // 1:1채팅방 생성중복 처리 필요

        // 만약 중복이 아니라면

        // sender 채팅방 참가 처리
        console.log(createdChatRoomId + "번 방에 참가를 시도합니다.");
        await chatRoomStore.joinChatRoom(createdChatRoomId, 1); // authenticateduser로 수정 필요
        // receiver 채팅방 참가 처리
        await chatRoomStore.joinChatRoom(createdChatRoomId, userId);

        // 참가한 채팅방 목록 갱신
        await chatRoomStore.getChatRoomList(1, true);

        // 현재 방 번호 갱신
        chatRoomStore.setChatRoomId(createdChatRoomId);
        // 현재 방 정보 갱신
        chatRoomStore.getPersonalChatRoomInfo(createdChatRoomId);
        chatRoomInfo.value = chatRoomStore.getChatRoom;

        // 구독정보 갱신 필요
        subscription.unsubscribe();
        subscribe(createdChatRoomId);
        // 메시지 클리어하고 로딩
        chatMessages.value = [];
        fetchMessages();

        // 중복이라면 바로 로딩
    } catch (error) {
        console.error("Error handling chat:", error);
        console.log(
            "이미 있는 채팅방의 번호는? " + error.response.data.chatRoomId
        );

        const alreadyExistsChatRoomId = error.response.data.chatRoomId;
        // 에러 처리

        // 개인방 찾기 필요한데?

        // 현재 방 번호 갱신
        chatRoomStore.setChatRoomId(alreadyExistsChatRoomId);
        // 개인방 리스트 받아오기
        await chatRoomStore.getChatRoomList(1);
        chatRoomStore.getPersonalChatRoomInfo(alreadyExistsChatRoomId);
        // 저장되어 있는 방 리스트(1:1 or open)를 1:1로 갱신. 1:1방 데이터 조회를 위함

        await chatRoomStore.getChatRoomList(1); // 채팅방 리스트를 개인 채팅방으로 변경
        chatRoomInfo.value = chatRoomStore.getChatRoom;

        console.log(
            "중복이라서 갱신한 채팅방 정보:" +
                JSON.stringify(chatRoomInfo.value)
        );

        // 상대방 정보 갱신
        partnerInfo.value = chatRoomStore.getPartnerInfo;

        // 구독정보 갱신 필요
        subscription.unsubscribe();
        subscribe(alreadyExistsChatRoomId);

        // 메시지 클리어하고 로딩
        chatMessages.value = [];
        fetchMessages();
    }
};

// modal에서 report 클릭했을 때 실행되는 함수
const handleReport = () => {
    alert("신고 클릭");
};

// modal에서 close 클릭했을 때 실행되는 함수
const handleClose = () => {
    showUserProfileModalInChatConversation.value =
        !showUserProfileModalInChatConversation.value;
};

const goToChatConversation = (chatRoomId) => {
    // 모달 내 이동을 위해 필요
    // 선택된 채팅방 id 변경
    chatRoomStore.setChatRoomId(chatRoomId);

    router.push("/chat");
    // 채팅 메시지 페이지로 이동
};

// 하위는 모달과 관련없는 코드들

const chatStore = useChatStore();
const webSocketStore = useWebSocketStore();
const loginUserId = ref();

const stompClient = webSocketStore.getStompClient();

let content = ref(""); //v-model. input message
let subscription;

console.log("채팅방 번호:" + chatRoomStore.getChatRoomId);
console.log("웹소켓 정보:" + webSocketStore.getStompClient());

const chatMessages = ref([]);
const storeMessages = ref([]);
const chatRoomInfo = ref({});
const partnerInfo = ref({});

// chatMessages.value.push({
//   // 테스트 용도
//   chatRoomId: 1,
//   senderId: 2,
//   title: "얼박사 킬러",
//   nickname: "user2",
//   content: "ㅎㅇ",
//   profileImage: defaultProfileImage, // img 태그에 userId 기반으로 받아온 프사 적용 필요
//   // createdAt: "오전 7:04",
// }); // 테스트 데이터

async function fetchLoginUserId() {
    loginUserId.value = await localStorage.getItem("userId");
    console.log("로그인되어 있는 userId:", loginUserId.value);
}

// 채팅 메세지 받기
async function fetchMessages() {
    try {
        const chatLogs = await chatStore.getChatMessageList(
            chatRoomStore.getChatRoomId
        ); // 현재 번호 채팅방 메시지 목록 불러오기

        chatLogs.forEach((m) => {
            m.createdAt = new Intl.DateTimeFormat("ko-KR", {
                hour: "numeric",
                minute: "numeric",
                hour12: true,
            }).format(new Date(m.createdAt));
            chatMessages.value.push(m);
        });
    } catch (error) {
        console.error("Error fetching chat logs:", error);
    }
}

const scrollToBottom = () => {
    nextTick(() => {
        const messageBox = document.getElementById("messages");
        messageBox.scrollTop = messageBox.scrollHeight;
    });
};

const send = () => {
    console.log("send()?");

    if (stompClient) {
        console.log("전송");
        const msg = {
            chatRoomId: chatRoomInfo.value.chatRoomId,
            senderId: loginUserId.value,
            content: content.value,
            createdAt: Date.now(),
        };

        stompClient.send(
            "/pub/chats/" + chatRoomInfo.value.chatRoomId,
            JSON.stringify(msg),
            {}
        );

        content.value = "";
        // scrollToBottom();
    }
};

const disconnect = () => {
    if (stompClient) {
        stompClient.disconnect();
    }
};

const subscribe = (chatRoomId) => {
    storeMessages.value = []; // 추가되었던 채팅 데이터 초기화

    console.log("subscribing: " + "/sub/" + chatRoomId);

    subscription = stompClient.subscribe(
        "/sub/" + chatRoomId,
        (tick) => {
            console.log(chatRoomId + "번 방으로 메시지를 전달합니다.");
            console.log("tick.body.content:" + JSON.parse(tick.body).content);

            let message = JSON.parse(tick.body);
            // TIMESTAMP를 오전 1:30 형태로 변환
            message.createdAt = new Intl.DateTimeFormat("ko-KR", {
                hour: "numeric",
                minute: "numeric",
                hour12: true,
            }).format(new Date(message.createdAt));

            storeMessages.value.push(message);

            console.log("🎈storeMessages:");

            console.log(storeMessages.value);
        },
        {}
    );
};

onMounted(() => {
    fetchLoginUserId();

    fetchMessages();

    // isPersonal 여부에 따라 가져오는 데이터 형식 다름

    if (chatRoomStore.getIsPersonal === false) {
        chatRoomStore.getOpenChatRoomInfo(chatRoomStore.getChatRoomId);
    } else {
        chatRoomStore.getPersonalChatRoomInfo(chatRoomStore.getChatRoomId);

        partnerInfo.value = chatRoomStore.getPartnerInfo;

        console.log("상대방 정보:" + JSON.stringify(partnerInfo.value));
    }

    chatRoomInfo.value = chatRoomStore.getChatRoom;

    console.log("mounted()" + chatRoomInfo.value.chatRoomId);

    subscribe(chatRoomInfo.value.chatRoomId);

    console.log(
        "현재 페이지에서 보유한 방 정보:" + JSON.stringify(chatRoomInfo.value)
    );

    console.log("채팅방 정보:" + chatRoomStore.getChatRoom.chatRoomName);

    console.log("############## chatRoomStore ##############");
    console.log(chatRoomInfo.value);
    console.log("#############################################");

    // header
    const userId = localStorage.getItem("userId");
    if (chatRoomInfo.value.chatRoomName === "개인채팅방") {
        chatRoomInfo.value.participants.forEach((p) => {
            if (p.userId != userId) {
                commonStore.headerType = "header5";
                commonStore.headerChatName = p.nickname;
                commonStore.headerChatImg = p.profileImage;

                // TODO: commonStore에서 headerGoOut 함수 채팅방 나가기 api 호출
            }
        });
    }
});
</script>

<style>
.chatconversationview-main-container {
    display: flex;
    flex-direction: column;
    height: calc(100vh - 7.25rem - 5rem);
    overflow-x: hidden;
    overflow-y: scroll;
    transition: scroll-behavior 0.5s ease-in-out;
    padding-top: 1rem;
}

.chatconversationview-main-container::-webkit-scrollbar {
    display: none;
}

.chatconversationview-sub-container {
    display: flex;
    flex-direction: column;
}

/* input messsage form */
.chatconversationview-inputWindow {
    position: fixed;
    display: flex;
    justify-content: space-between;
    align-content: center;
    align-items: center;
    width: 22.2rem;
    height: 4rem;
    bottom: 4rem;
    background-color: var(--white-color);
}

.chatconversationview-inputGroup {
    display: flex;
    justify-content: center;
    height: 3rem;
}

.chatconversationview-messageForm {
    width: 16.2rem;
    font-size: 1rem;
    padding-left: 1.2rem;
    padding-right: 1.2rem;
    border-radius: 2rem;
    font-family: "Regular";
    background-color: var(--gray-color);
    border: none;
    outline: none;
}

.chatconversationview-generate {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 3rem;
    height: 3rem;
    background: var(--main-color);
    color: var(--white-color);

    border: none;
    border-radius: 50%;
    overflow: hidden;
    transition: all 0.2s;
    cursor: pointer;
    z-index: 3;
}

.chatconversationview-icon {
    display: flex;
    justify-content: center;
    align-content: center;
    width: 1.4rem;
    height: 1.4rem;
    padding-right: 0.2rem;
}

/*  */
#chatconversationview-chatBox {
    width: 22.2rem;
    margin: 0 auto;
}

#chatconversationview-messages {
    width: 22.2rem;
    display: flex;
    /* overflow-x: hidden;
    overflow-y: scroll;
    transition: scroll-behavior 0.5s ease-in-out; */
}
#chatconversationview-messages::-webkit-scrollbar {
    display: none;
}

/* 메세지 카드 */
.chatconversationview-messageCard {
    width: 22.2rem;
    display: flex;
    margin-bottom: 0.8rem;
}

.chatconversationview-fromCard {
    justify-content: flex-start;
    max-width: 20.2rem;
    padding-right: 2rem;
}

.chatconversationview-toCard {
    justify-content: flex-end;
    max-width: 20.2rem;
    padding-left: 2rem;
}

/* 메시지 버블 */
.chatconversationview-bubble {
    border-radius: 0.25rem;
    display: flex;
    flex-direction: column;
    max-width: 15rem;
}

.chatconversationview-bubble p {
    border-radius: 1.15rem;
    line-height: 1.25;
    max-width: 100%;
    padding: 0.5rem 0.875rem;
    position: relative;
    word-wrap: break-word;
    font-family: "Regular";
    font-size: 1rem;
}

/* 내가 보내는 메시지 부분*/
p.chatconversationview-fromMe {
    align-self: flex-end;
    background-color: var(--main-color);
    color: var(--white-color);
}

/* 상대가 보내는 메시지 부분 */
p.chatconversationview-fromThem {
    align-items: flex-start;
    background-color: var(--sub-color);
    color: var(--black-color);
}


/* 상대가 보낸 메시지 */
/* 헤더 */
.chatconversationview-fromHeader {
    display: flex;
    justify-content: flex-start;
}

/* 프로필 사진 */
.chatconversationview-profileImage img {
    width: 2.6rem;
    height: 2.6rem;
    border: 1px solid var(--gray-color);
    border-radius: 50%;
    object-fit: cover;
}

.chatconversationv-senderview-container{
    display: flex;
    flex-direction: column;
    margin-left: 0.6rem;
}

/* 칭호+이름 */
.chatconversationview-userHeader {
    display: flex;
    justify-self: row;
    margin-bottom: 0.4rem;
}

/* 칭호 */
.chatconversationview-userTitle {
    font-family: "bold";
    font-size: 0.8rem;
    color: var(--main-color);
    margin-right: 0.4rem;
}

/* 이름 */
.chatconversationview-userName {
    font-family: "Semi";
    font-size: 0.8rem;
    color: var(--gray2-color);
}

/* 메시지+시간 */
.chatconversationview-sender-container{
    display: flex;
    justify-content: center;
    align-items: flex-start;
    width: 100%;
}

/* 메시지 */
.chatconversationview-sender-content{
    max-width: 14rem;
    overflow-wrap: break-word;
    padding: 0.4rem;
    background-color: var(--sub-color);
    border-radius: 0.4rem;
    padding: 0.8rem 0.8rem 0.8rem 0.8rem;
    font-family: "Regular";
    font-size: 1rem;
    color: var(--black-color);
    line-height: 1.2rem;
}

/* 시간 */
.chatconversationview-sender-time{
    font-family: "Regular";
    font-size: 0.8rem;
    color: var(--gray2-color);
    margin-left: 0.2rem;
    display: flex;
    align-self: flex-end;
}

/* 내가 보낸 메시지 */
/* 메시지+시간 */
.chatconversationview-receiver-container{
    display: flex;
    justify-content: flex-end;
    align-items: flex-end;
    width: 100%;
}

/* 메시지 */
.chatconversationview-receiver-content{
    max-width: 14rem;
    overflow-wrap: break-word;
    padding: 0.4rem;
    background-color: var(--main-color);
    border-radius: 0.4rem;
    padding: 0.8rem 0.8rem 0.8rem 0.8rem;
    font-family: "Regular";
    font-size: 1rem;
    color: var(--white-color);
    line-height: 1.2rem;
}

/* 시간 */
.chatconversationview-receiver-time{
    font-family: "Regular";
    font-size: 0.8rem;
    color: var(--gray2-color);
    margin-right: 0.2rem;
    display: flex;
    align-self: flex-end;
}

/* 시간 정보 */
.chatconversationview-sendDate {
    font-family: "Regular";
    font-size: 0.8rem;
    display: flex;
    justify-content: flex-end;
    align-items: flex-end;
    padding: 0 0.2rem 0.4rem 0.2rem;
}
.chatconversationview-sendDate p {
    margin-bottom: 0;
    text-align: bottom;
}
.chatconversationview-messageAndTimeTo {
    display: flex;
    flex-direction: row;
}
.chatconversationview-messageAndTimeTo .sendDate {
    text-align: right;
}
.chatconversationview-messageAndTimeFrom {
    display: flex;
}
</style>
