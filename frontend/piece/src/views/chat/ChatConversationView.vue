<template>
    <div id="chatBox"> <!-- v-if 걸어서 채팅방 존재 여부 판독 예정 -->
        <div id="messages">
            <ul style="list-style:none; padding-inline-start: 0px;">
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
import { ref, onMounted, nextTick } from 'vue';
import { useChatRoomStore } from '@/stores/chatroom';
import { useChatStore } from '@/stores/chat';
import SockJS from 'sockjs-client/dist/sockjs.min.js';
import Stomp from 'webstomp-client';

const chatRoomStore = useChatRoomStore();
const chatStore=useChatStore();

const givenUserNumber=1; // userStore로 가져올 예정

let stompClient = null;
let socket = null;
let subscription = null;
let content=null; //v-model. input message

console.log("채팅방 번호:"+chatRoomStore.getChatRoomId);

const chatMessages=ref([]);
const storeMessages = ref([]);

// 채팅 메세지 받기
async function fetchMessages() {
    try {
        const chatLogs = await chatStore.getChatMessageList(1); // 1번 채팅방 메시지 목록 불러오기
        console.log("store에서 채팅 메시지를 받았습니다.");
        chatLogs.forEach(m=>{
            // console.log(m.chatRoomId, m.senderId, m.content, m.createdAt);
            chatMessages.value.push(m);
        });

        console.log("최종 메시지:"+JSON.stringify(chatMessages.value));

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
    connect(1); // 1번방 구독했다고 가정
    console.log('전송');
    const msg = {
      chatRoomId: 1, // 테스트 용도
      senderId: 1, // 테스트 용도
      content: content,
      createdAt: new Intl.DateTimeFormat('ko-KR', { hour: 'numeric', minute: 'numeric', hour12: true }).format(new Date()), // client time
    };

    console.log('보내려는 메시지:' + JSON.stringify(msg));

    stompClient.send('/pub/chat/' + '1', JSON.stringify(msg), {});

    content = '';
    scrollToBottom();
  }
};

// 사실 기능은 subscribe.
const connect = (chatRoomId) => {
//   chatMessages.value = []; // 채팅 데이터 초기화
  storeMessages.value = []; // 추가되었던 채팅 데이터 초기화

  console.log('subscribing: ' + '/sub/' + chatRoomId);

  subscription = stompClient.subscribe('/sub/' + chatRoomId, tick => {
    console.log(chatRoomId + '번 방으로 메시지를 전달합니다.');
    console.log('tick.body.content:' + JSON.parse(tick.body).content);

    storeMessages.value.push(JSON.parse(tick.body));
  });
};

const disconnect = () => {
  if (stompClient) {
    stompClient.disconnect();
  }
};

onMounted(() => {
    fetchMessages();

  console.log('mounted()');
  socket = new SockJS('http://localhost:8080/ws');

  stompClient = Stomp.over(socket);
  const headers = {
    userNumber: '1',
  };

  stompClient.connect(
    headers,
    frame => {
      console.log('웹소켓 연결에 성공했습니다.');
    },
    error => {
      console.log(error);
    }
  );
});
</script>

<style scopped>
@import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap');

*{
    font-family: "Do Hyeon", sans-serif;
}

/* 메시지 목록 창 */
#messages{
    display:flex;
    border:1px solid red;
    overflow-x: hidden;
    overflow-y: scroll;
    transition: scroll-behavior 0.5s ease-in-out;
    height:75vh;
}
#messages::-webkit-scrollbar {
    /* display: none; */
}

#inputWindow{
    bottom:0;
    width:100%;
    border:1px solid purple;
}
#inputGroup{
    border:1px solid blue;
    display:flex;
    justify-content: center;
}

/* 전송 버튼 */
.chatconversationview-generate {
width:55px;
  font-family: inherit;
  font-size: 20px;
  background: #ff678b;
  color: white;
  padding: 0.7em 1em;
  padding-left: 14px;
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
  margin-left: 0.3em;
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
  transform: translateX(1.2em) rotate(45deg) scale(1.1);
}

.chatconversationview-generate:hover span {
  transform: translateX(5em);
}

.chatconversationview-generate:active {
  transform: scale(0.95);
}

/* 텍스트 입력 창 */
#messageForm{
    font-size:1rem;
    padding-left:1rem;
    width:70%;
    border-radius:50px;
    margin-right:10px;
  }
  #messageForm:focus {
    border-color: #ff96b0; /* 선택되었을 때의 테두리 색상을 변경합니다. */
    box-shadow: 0 0 0 0.2rem rgba(255, 159, 186, 0.25); /* 선택되었을 때의 그림자 효과를 추가합니다. */
    outline: 0; /* 기본 선택 효과를 제거합니다. */
    transition: 0.3s;
}

/* 메세지 카드 */
.chatconversationview-messageCard{
    width:370px;
    display:flex;
    border:1px solid blue;
}
.chatconversationview-fromCard{
    justify-content: flex-start;
}
.chatconversationview-toCard{
    justify-content: flex-end;
}
/* 메시지 버블 */
.chatconversationview-bubble {
    border:1px solid green;
    border-radius: 0.25rem;
    display: flex;
    flex-direction: column;
    font-family: "SanFrancisco";
    font-size: 1rem;
    /* margin: 0 auto 1rem; */
    max-width: 600px;
  }
  
  .chatconversationview-bubble p {
    border-radius: 1.15rem;
    line-height: 1.25;
    max-width: 100%;
    padding: 0.5rem 0.875rem;
    position: relative;
    word-wrap: break-word;
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
    background-color: #ff9f9f;
    color: #ffffff;
  }
  
  p.chatconversationview-fromMe::before {
    border-bottom-left-radius: 0.8rem 0.7rem;
    border-right: 1rem solid #ff9f9f;
    right: -0.35rem;
    transform: translate(0, -0.1rem);
  }
  
  p.chatconversationview-fromMe::after {
    background-color: #ffffff;
    border-bottom-left-radius: 0.5rem;
    right: -40px;
    transform: translate(-30px, -2px);
    width: 10px;
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
    background-color: #ffd3d3;
    color: #000;
    z-index: 1;
  }
  
  p.chatconversationview-fromThem:before {
    border-bottom-right-radius: 0.8rem 0.7rem;
    border-left: 1rem solid #ffd3d3;
    left: -0.35rem;
    transform: translate(0, -0.1rem);
  }
  
  p.chatconversationview-fromThem::after {
    background-color: #ffffff;
    border-bottom-right-radius: 0.5rem;
    left: 20px;
    transform: translate(-30px, -2px);
    width: 10px;
  }

/* 프로필 사진 */
.chatconversationview-profileImage img{
    padding-top:5px;
    margin-left:10px;
    width:50px;
}

/* 상대 메시지 헤더 */
.chatconversationview-fromHeader{
    display:flex;
    justify-content: flex-start;
}

/* 상대 칭호+이름 */
.chatconversationview-userHeader{
    padding-top:5px;
    padding-left:10px;
}

/* 상대 칭호 */
.chatconversationview-userTitle{
    padding-left:8px;
    font-weight:bold;
    color:#ff819e;
}

/* 상대 이름 */
.chatconversationview-userName{
    padding-left:6px;
    color:#6e6e6e;
}

/* 시간 정보 */
.chatconversationview-sendDate{
    width:60px;
    height:90%;
    border:1px solid red;
}
.chatconversationview-sendDate p{
    margin-top:60%;
    margin-bottom:0;
}
.chatconversationview-messageAndTimeTo{
    display:flex;
    flex-direction: row;
    border:1px solid blue;
}
.chatconversationview-messageAndTimeTo .sendDate{
    text-align: right;
}
.chatconversationview-messageAndTimeFrom{
    display:flex;
    flex-direction: row;
    border:1px solid purple;
}
</style>