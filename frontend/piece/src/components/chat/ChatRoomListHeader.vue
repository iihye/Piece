<template>
    <div id="chatRoomListHeader">
        <button class="chatroomlistheader-chatRoomListButton" @click="setActiveButton(0)" :class="{ 'chatroomlistheader-active': activeButtonIndex === 0 }">
            <p>내가 참여한 채팅방</p>
        </button>
        <button class="chatroomlistheader-chatRoomListButton" @click="setActiveButton(1)" :class="{ 'chatroomlistheader-active': activeButtonIndex === 1 }">
            <p>일대일 채팅</p>
        </button>
    </div>

</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useChatRoomStore } from '@/stores/chatroom';

const chatRoomStore = useChatRoomStore();

const activeButtonIndex = ref(0); // 오픈채팅방 목록을 기본으로 조회하게 지정

console.log("기본 선택값입니다:"+activeButtonIndex.value);
const setActiveButton = (index) => {
  activeButtonIndex.value = index;
  chatRoomStore.setIsPersonal(index); // 오픈채팅/개인채팅 여부 설정
  console.log("오픈채팅/개인채팅 여부를 변경했습니다."+chatRoomStore.getIsPersonal);
};

onMounted(() => {
  const buttons = document.querySelectorAll('#chatRoomListHeader button');
  buttons.forEach(button => {
    const pContent = button.querySelector('p').textContent;
    button.style.setProperty('--button-content', `"${pContent}"`);
  });
});
</script>

<style scopped>
@import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap');
/* Button CSS */
.chatroomlistheader-chatRoomListButton {
    margin-top:5px;
    height: 50px;
    width: 50%;
    border: none;
    background: none;
    cursor: pointer;
}

.chatroomlistheader-chatRoomListButton {
  --primary-color: #111;
  --hovered-color: #ff3c56;
  position: relative;
  display: flex;
  font-family: "Do Hyeon", sans-serif;
  font-size:18px;
  gap: 0.5rem;
  align-items: center;
}

.chatroomlistheader-chatRoomListButton p {
    margin: 0 auto;
  position: relative;
  color: var(--primary-color);
}

.chatroomlistheader-chatRoomListButton::after {
  position: absolute;
  content: "";
  width: 0;
  left: 0;
  bottom: -3px;
  background: var(--hovered-color);
  height: 2px;
  transition: 0.2s ease-out;
}

.chatroomlistheader-chatRoomListButton p::before {
  position: absolute;
  /*   box-sizing: border-box; */
  content: var(--button-content, "");
  width: 0%;
  inset: 0;
  color: var(--hovered-color);
  overflow: hidden;
  transition: 0s ease-out;
}

.chatroomlistheader-chatRoomListButton:hover::after {
  width: 100%;
}

.chatroomlistheader-chatRoomListButton:hover p::before {
  width: 100%;
}

/* 버튼 활성화 관련 css */
.chatroomlistheader-chatRoomListButton.chatroomlistheader-active {
  background-color: #e8e8e8;
  color: #ff3c56; /* 예시로 색상을 변경합니다. */
}

/* template CSS*/
#chatRoomListHeader{
    display: flex;
    justify-content: center;
    /* border:1px solid slateblue; */
    font-family: "Do Hyeon", sans-serif;
}
.chatroomlistheader-chatRoomListButton{
    height: 50px;
    width: 50%;
    text-align: center;
    line-height: 50px; /* height와 같게 하면 가운데 정렬 */
    /* border:1px solid red; */
}

</style>