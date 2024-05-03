<template>
    <div id="chatBox"> <!-- v-if 걸어서 채팅방 존재 여부 판독 예정 -->
        <!-- 오픈채팅 헤더 정보. 헤더에 들어갈 예정 -->
        <div v-if="chatRoomInfo.isPersonal==false">
            <h2>오픈채팅 이름:{{ chatRoomInfo.chatRoomName }}</h2>
            <img :src="chatRoomInfo.culture.imageUrl" alt="해당 채팅방 이미지">
        </div>
        <!-- 1:1채팅 헤더 정보. 헤더에 들어갈 예정 -->
        <div v-else>
            <h2>상대방 닉네임</h2>
            <img src="" alt="상대방 프로필사진">
        </div>
        <div id="messages">
            <ul style="list-style:none; padding-inline-start: 0rem;">
                <!-- 저장되어 있던 메시지 목록 -->
                <li v-for="item in chatMessages" :key="item">
                    <div class="chatconversationview-messageCard" :class="item.senderId!=givenUserNumber? 'chatconversationview-fromCard' : 'chatconversationview-toCard'">
                        <!-- 상대가 보낸 메시지 -->
                        <div class="chatconversationview-fromHeader" v-if="item.senderId != givenUserNumber">
                            <!-- 프로필 이미지 -->
                            <div class="chatconversationview-profileImage">
                                <img src="@/assets/logo.png" />
                            </div>

                            <!-- 메시지 관련 부분 시작-->
                            <!-- 헤더 + 메시지 -->
                            <div>
                                <!-- 칭호 + 이름 -->
                                <div class="chatconversationview-userHeader"> 
                                    <span class="chatconversationview-userTitle">
                                        {{ item.title }}
                                    </span>
                                    <span class="chatconversationview-userName">
                                        {{ item.nickname }}
                                    </span>
                                </div>
                                <div class="chatconversationview-messageAndTimeFrom">
                                    <!-- 메세지 버블 -->
                                    <div class="chatconversationview-bubble">
                                        <p class="chatconversationview-fromThem">
                                            {{item.content}}
                                        </p>
                                    </div>
                                    <!-- 보낸 시간 -->
                                    <div class="chatconversationview-sendDate">
                                        <p>
                                            {{ item.createdAt }}
                                        </p>
                                    </div>
                                    <!-- <p class="testTime">테스트 시간 27:98</p> -->
                                </div>
                            </div>
                        </div>
                        <!-- 내가 보낸 메시지라면 -->
                        <div v-else class="chatconversationview-messageAndTimeTo">
                            <!-- 보낸 시간 -->
                            <div class="chatconversationview-sendDate">
                                <p>
                                    {{ item.createdAt }}
                                </p>
                            </div>
                            <div class="chatconversationview-bubble">
                                <p class="chatconversationview-fromMe">
                                    {{item.content}}
                                </p>
                            </div>
                        </div>
                    </div>
                </li>
                <!-- 저장할 메시지 목록-->
                <li v-for="item in storeMessages" :key="item">
                    <div class="chatconversationview-messageCard" :class="item.senderId!=givenUserNumber? 'chatconversationview-fromCard' : 'chatconversationview-toCard'">
                        <!-- 상대가 보낸 메시지 -->
                        <div class="chatconversationview-fromHeader" v-if="item.senderId != givenUserNumber">
                            <!-- 프로필 이미지 -->
                            <div class="chatconversationview-profileImage">
                                <img src="@/assets/logo.png" />
                            </div>

                            <!-- 메시지 관련 부분 시작-->
                            <!-- 헤더 + 메시지 -->
                            <div>
                                <!-- 칭호 + 이름 -->
                                <div class="chatconversationview-userHeader"> 
                                    <span class="chatconversationview-userTitle">
                                        {{ item.title }}
                                    </span>
                                    <span class="chatconversationview-userName">
                                        {{ item.nickname }}
                                    </span>
                                </div>
                                <div class="chatconversationview-messageAndTimeFrom">
                                    <!-- 메세지 버블 -->
                                    <div class="chatconversationview-bubble">
                                        <p class="chatconversationview-fromThem">
                                            {{item.content}}
                                        </p>
                                    </div>
                                    <!-- 보낸 시간 -->
                                    <div class="chatconversationview-sendDate">
                                        <p>
                                            {{ item.createdAt }}
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- 내가 보낸 메시지라면 -->
                        <div v-else class="chatconversationview-messageAndTimeTo">
                            <!-- 보낸 시간 -->
                            <div class="chatconversationview-sendDate">
                                <p>
                                    {{ item.createdAt }}
                                </p>
                            </div>
                            <div class="chatconversationview-bubble">
                                <p class="chatconversationview-fromMe">
                                    {{item.content}}
                                </p>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
        </div> 
        <!-- input message form -->
        <div id="inputWindow">
            <div id="inputGroup">
                <input @keyup.enter="send" type="text" id="messageForm" v-model="content" class="chatconversationview-formControl" placeholder="메세지를 입력하세요."/>
                <button @click="send" class="chatconversationview-generate">
                    <div class="chatconversationview-svgWrapper-1">
                        <div class="chatconversationview-svgWrapper">
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                viewBox="0 0 24 24"
                                width="24"
                                height="24"
                            >
                                <path fill="none" d="M0 0h24v24H0z"></path>
                                    <path
                                        fill="currentColor"
                                        d="M1.946 9.315c-.522-.174-.527-.455.01-.634l19.087-6.362c.529-.176.832.12.684.638l-5.454 19.086c-.15.529-.455.547-.679.045L12 14l6-8-8 6-8.054-2.685z"
                                    >
                                </path>
                            </svg>
                        </div>
                    </div>
                </button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, nextTick} from 'vue';
import { useChatRoomStore } from '@/stores/chatroom';
import { useChatStore } from '@/stores/chat';
import { useWebSocketStore } from '@/stores/websocket';

const chatRoomStore = useChatRoomStore();
const chatStore=useChatStore();
const webSocketStore=useWebSocketStore();
const stompClient=webSocketStore.getStompClient();
const givenUserNumber=1; // userStore로 가져올 예정
let content=ref(''); //v-model. input message
let subscription;

console.log("채팅방 번호:"+chatRoomStore.getChatRoomId);
console.log("웹소켓 정보:"+webSocketStore.getStompClient());

const chatMessages=ref([]);
const storeMessages = ref([]);
const chatRoomInfo=ref({});

chatMessages.value.push({
    // chatRoomId: 1, // 테스트 용도
    // senderId: 2, // 테스트 용도
    // title: "얼박사 킬러",
    // nickname: "김싸피",
    // content: "ㅎㅇ",
    // createdAt: "오전 7:04",
}); // 테스트 데이터

// 채팅 메세지 받기
async function fetchMessages() {
    try {
        const chatLogs = await chatStore.getChatMessageList(1); // 1번 채팅방 메시지 목록 불러오기

        chatLogs.forEach(m=>{
            m.createdAt = new Intl.DateTimeFormat('ko-KR', { hour: 'numeric', minute: 'numeric', hour12: true }).format(new Date(m.createdAt));
            chatMessages.value.push(m);
        });

    } catch (error) {
        console.error("Error fetching chat logs:", error);
    }
}

const scrollToBottom = () => {
    nextTick(() => {
        const messageBox = document.getElementById('messages');
        messageBox.scrollTop = messageBox.scrollHeight;
    });
};

const send = () => {
    console.log('send()?');

    if (stompClient) {
        console.log('전송');
        const msg = {
        chatRoomId: 1, // 테스트 용도
        senderId: 1, // 테스트 용도
        content: content.value,
        createdAt: Date.now()
        };

        stompClient.send('/pub/chats/' + '1', JSON.stringify(msg), {});

        content.value = '';
        scrollToBottom();
    }
};

const disconnect = () => {
    if (stompClient) {
        stompClient.disconnect();
    }
};

const subscribe = (chatRoomId) => {
    storeMessages.value = []; // 추가되었던 채팅 데이터 초기화

    console.log('subscribing: ' + '/sub/' + chatRoomId);

    subscription = stompClient.subscribe('/sub/' + chatRoomId, tick => {
        console.log(chatRoomId + '번 방으로 메시지를 전달합니다.');
        console.log('tick.body.content:' + JSON.parse(tick.body).content);

        let message = JSON.parse(tick.body);
        // TIMESTAMP를 오전 1:30 형태로 변환
        message.createdAt = new Intl.DateTimeFormat('ko-KR', { hour: 'numeric', minute: 'numeric', hour12: true }).format(new Date(message.createdAt));
        
        storeMessages.value.push(message);
        
        console.log("🎈storeMessages:");

        console.log(storeMessages.value);
    },{});
};

onMounted(() => {
    fetchMessages();

    console.log('mounted()');

    subscribe(chatRoomStore.getChatRoomId);
    chatRoomStore.getChatRoomInfo(chatRoomStore.getChatRoomId);

    console.log("채팅방 정보:"+chatRoomStore.getChatRoom.chatRoomName);

    chatRoomInfo.value=chatRoomStore.getChatRoom;

    console.log("현재 페이지에서 보유한 방 정보:"+JSON.stringify(chatRoomStore.getChatRoom));
});
</script>

<style>
@import "@/components/css/color.css";
@import "@/components/css/font.css";

.testTime{
    margin-top: 20%;
    margin-bottom: 0;
    font-size: 0.9rem ;
}
#chatBox{
  border:0.063rem solid var(--black-color);
  width: 25rem;
  height: 42.5rem;
  margin: 0 auto;
}

/* 메시지 목록 창 */
#messages{
    display:flex;
    border:0.063rem solid var(--red-color);
    overflow-x: hidden;
    overflow-y: scroll;
    transition: scroll-behavior 0.5s ease-in-out;
    height:37.5rem;
}
#messages::-webkit-scrollbar {
    /* display: none; */
}

#inputWindow{
    /* bottom:0; */
    width:100%;
    border:0.063rem solid purple;
}
#inputGroup{
    border:0.063rem solid blue;
    display:flex;
    justify-content: center;
}

/* 전송 버튼 */
.chatconversationview-generate {
width:3.4rem;
background: var(--main-color);
color: var(--white-color);
padding: 1rem 1rem;
padding-left: 0.9rem;
display: flex;
align-items: center;
border: none;
border-radius: 100%;
overflow: hidden;
transition: all 0.2s;
cursor: pointer;
z-index: 3;
}

.chatconversationview-generate span {
display: block;
margin-left: 0.3rem;
transition: all 0.3s ease-in-out;
}

.chatconversationview-generate svg {
display: block;
transform-origin: center center;
transition: transform 0.3s ease-in-out;
}

.chatconversationview-generate:hover .svg-wrapper {
animation: fly-1 0.6s ease-in-out infinite alternate;
}

.chatconversationview-generate:hover svg {
transform: translateX(1.2rem) rotate(45deg) scale(1.1);
}

.chatconversationview-generate:hover span {
transform: translateX(5rem);
}

.chatconversationview-generate:active {
transform: scale(0.95);
}

/* 텍스트 입력 창 */
#messageForm{
    font-size:1rem;
    padding-left:1rem;
    width:70%;
    border-radius:3.125rem;
    margin-right:0.625rem;
    font-family:"Regular";
}

#messageForm:focus {
border-color: var(--sub-color); /* 선택되었을 때의 테두리 색상을 변경합니다. */
box-shadow: 0 0 0 0.2rem rgba(255, 159, 186, 0.25); /* 선택되었을 때의 그림자 효과를 추가합니다. */
outline: 0; /* 기본 선택 효과를 제거합니다. */
transition: 0.3s;
}

/* 메세지 카드 */
.chatconversationview-messageCard{
    width:23.125rem;
    display:flex;
    border:0.063rem solid blue;
}

.chatconversationview-fromCard{
    justify-content: flex-start;
}

.chatconversationview-toCard{
    justify-content: flex-end;
}

/* 메시지 버블 */
.chatconversationview-bubble {
    border:0.063rem solid green;
    border-radius: 0.25rem;
    display: flex;
    flex-direction: column;
    max-width: 15.0rem;
}

.chatconversationview-bubble p {
    border-radius: 1.15rem;
    line-height: 1.25;
    max-width: 100%;
    padding: 0.5rem 0.875rem;
    position: relative;
    word-wrap: break-word;
    font-family:"Regular";
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
.chatconversationview-profileImage img{
    padding-top:0.313rem;
    margin-left:0.625rem;
    width:3.125rem;
}

/* 상대 메시지 헤더 */
.chatconversationview-fromHeader{
    display:flex;
    justify-content: flex-start;
}

/* 상대 칭호+이름 */
.chatconversationview-userHeader{
    padding-top:0.313rem;
    padding-left:0.625rem;
}

/* 상대 칭호 */
.chatconversationview-userTitle{
    padding-left:0rem;
    font-family:"Semi";
    font-size:1.1rem;
    color:var(--main-color);
}

/* 상대 이름 */
.chatconversationview-userName{
    padding-left:0.375rem;
    font-family:"Medium";
    font-size:1.1rem;
    color:var(--gray2-color);
}

/* 시간 정보 */
.chatconversationview-sendDate{
    width:4.0rem;
    height:100%;
    border:0.063rem solid (--red-color);
    font-size:0.9rem;
    font-family: "Regular";
    margin-top:15%;
}
.chatconversationview-sendDate p{
    margin-bottom:0;
    text-align: bottom;
}
.chatconversationview-messageAndTimeTo{
    display:flex;
    flex-direction: row;
    border:0.063rem solid blue;
}
.chatconversationview-messageAndTimeTo .sendDate{
    text-align: right;
}
.chatconversationview-messageAndTimeFrom{
    display:flex;
    flex-direction: row;
    border:0.063rem solid purple;
}
</style>