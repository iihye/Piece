<template>
    <div class="chatconversationview-main-container">
        <!-- <ChatItem
            v-for="(item, index) in cakeChatList"
            :key="index"
            :chatRoomId="item.chatRoomId"
            :senderLabel="item.senderLabel"
            :senderNickname="item.senderNickname"
            :senderImg="item.senderImg"
            :content="item.content"
            :createdAt="item.createdAt"
        ></ChatItem> -->

        <div id="chatconversationview-chatBox">
            <div id="chatconversationview-messages">
                <ul style="list-style: none; padding-inline-start: 0rem; margin: 0px">
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
                                <!-- 헤더 + 메시지 -->
                                <div>
                                    <!-- 칭호 + 이름 -->
                                    <div
                                        class="chatconversationview-userHeader"
                                    >
                                        <div
                                            class="chatconversationview-userTitle"
                                        >
                                            {{ item.title }}
                                        </div>
                                        <div
                                            class="chatconversationview-userName"
                                        >
                                            {{ item.nickname }}
                                        </div>
                                    </div>
                                    <div
                                        class="chatconversationview-messageAndTimeFrom"
                                    >
                                        <div
                                            class="chatconversationview-bubble"
                                        >
                                            <div
                                                class="chatconversationview-fromThem"
                                            >
                                                {{ item.content }}
                                            </div>
                                        </div>
                                        <!-- 보낸 시간 -->
                                        <div
                                            class="chatconversationview-sendDate"
                                        >
                                            <div>
                                                {{ item.createdAt }}
                                            </div>
                                        </div>
                                        <!-- <p class="testTime">테스트 시간 27:98</p> -->
                                    </div>
                                </div>
                            </div>

                            <!-- 내가 보낸 메시지라면 -->
                            <div
                                v-else
                                class="chatconversationview-messageAndTimeTo"
                            >
                                <!-- 보낸 시간 -->
                                <div class="chatconversationview-sendDate">
                                    {{ item.createdAt }}
                                </div>
                                <div class="chatconversationview-bubble">
                                    <p class="chatconversationview-fromMe">
                                        {{ item.content }}
                                    </p>
                                </div>
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
                                <!-- 헤더 + 메시지 -->
                                <div>
                                    <!-- 칭호 + 이름 -->
                                    <div
                                        class="chatconversationview-userHeader"
                                    >
                                        <span
                                            class="chatconversationview-userTitle"
                                        >
                                            {{ item.title }}
                                        </span>
                                        <span
                                            class="chatconversationview-userName"
                                        >
                                            {{ item.nickname }}
                                        </span>
                                    </div>
                                    <div
                                        class="chatconversationview-messageAndTimeFrom"
                                    >
                                        <!-- 메세지 버블 -->
                                        <div
                                            class="chatconversationview-bubble"
                                        >
                                            <p
                                                class="chatconversationview-fromThem"
                                            >
                                                {{ item.content }}
                                            </p>
                                        </div>
                                        <!-- 보낸 시간 -->
                                        <div
                                            class="chatconversationview-sendDate"
                                        >
                                            {{ item.createdAt }}
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- 내가 보낸 메시지라면 -->
                            <div
                                v-else
                                class="chatconversationview-messageAndTimeTo"
                            >
                                <!-- 보낸 시간 -->
                                <div class="chatconversationview-sendDate">
                                    {{ item.createdAt }}
                                </div>
                                <div class="chatconversationview-bubble">
                                    <p class="chatconversationview-fromMe">
                                        {{ item.content }}
                                    </p>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
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

const defaultProfileImage = ref("src/assets/defaultprofile.png");

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
.chatconversationview-test{
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    bottom: 4rem;
}


.chatconversationview-main-container {
    display: flex;
    flex-direction: column;
    height: calc(100vh - 7.25rem - 4rem);
    overflow-x: hidden;
    overflow-y: scroll;
    transition: scroll-behavior 0.5s ease-in-out;
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
    /* border: 0.063rem solid var(--black-color);
  width: 25rem;
  height: 42.5rem;
  margin: 0 auto; */
}

#chatconversationview-messages {
    width: 100%;
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
    margin-bottom: 0.4rem;
}

.chatconversationview-fromCard {
    justify-content: flex-start;
}

.chatconversationview-toCard {
    justify-content: flex-end;
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

.chatconversationview-bubble p::before,
.chatconversationview-bubble p::after {
    bottom: -0.1rem;
    content: "";
    height: 1rem;
    position: absolute;
}

/* 메시지 */
p.chatconversationview-fromMe {
    align-self: flex-end;
    background-color: var(--main-color);
    color: var(--white-color);
}

p.chatconversationview-fromMe::before {
    border-bottom-left-radius: 0.8rem 0.7rem;
    border-right: 1rem solid var(--main-color);
    right: -0.35rem;
    transform: translate(0, -0.1rem);
}

p.chatconversationview-fromMe::after {
    background-color: var(--white-color);
    border-bottom-left-radius: 0.5rem;
    right: -2.5rem;
    transform: translate(-1.875rem, -0.125rem);
    width: 0.625rem;
}

p[class^="chatconversationview-from"] {
    margin: 0.5rem 0;
    width: fit-content;
}

p.chatconversationview-fromMe ~ p.chatconversationview-fromMe {
    margin: 0.25rem 0 0;
}

p.chatconversationview-fromMe ~ p.chatconversationview-fromMe:not(:last-child) {
    margin: 0.25rem 0 0;
}

p.chatconversationview-fromMe ~ p.chatconversationview-froMme:last-child {
    margin-bottom: 0.5rem;
}

p.chatconversationview-fromThem {
    align-items: flex-start;
    background-color: var(--sub-color);
    color: var(--black-color);
    z-index: 1;
}

p.chatconversationview-fromThem:before {
    border-bottom-right-radius: 0.8rem 0.7rem;
    border-left: 1rem solid var(--sub-color);
    left: -0.35rem;
    transform: translate(0, -0.1rem);
}

p.chatconversationview-fromThem::after {
    background-color: var(--white-color);
    border-bottom-right-radius: 0.5rem;
    left: 1.25rem;
    transform: translate(-1.875rem, -0.125rem);
    width: 0.625rem;
}

/* 프로필 사진 */
.chatconversationview-profileImage img {
    padding-top: 0.313rem;
    margin-left: 0.625rem;
    width: 3.125rem;
    height: 3.125rem;
    border-radius: 50%;
}

/* 상대 메시지 헤더 */
.chatconversationview-fromHeader {
    display: flex;
    justify-content: flex-start;
}

/* 상대 칭호+이름 */
.chatconversationview-userHeader {
    padding-top: 0.313rem;
    padding-left: 0.625rem;
}

/* 상대 칭호 */
.chatconversationview-userTitle {
    padding-left: 0rem;
    font-family: "Semi";
    font-size: 1.1rem;
    color: var(--main-color);
}

/* 상대 이름 */
.chatconversationview-userName {
    padding-left: 0.375rem;
    font-family: "Medium";
    font-size: 1.1rem;
    color: var(--gray2-color);
}

/* 시간 정보 */
.chatconversationview-sendDate {
    font-family: "Regular";
    font-size: 0.8rem;
    display: flex;
    justify-content: flex-end;
    align-items: flex-end;
    margin: 0 0.4rem 0.4rem 0;
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
    flex-direction: row;
}
</style>
