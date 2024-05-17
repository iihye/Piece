<template>
    <div id="chatRoomListHeader">
        <button
            class="chatroomlistheader-chatRoomListButton"
            @click="setActiveButton(0)"
            :class="{ 'chatroomlistheader-active': activeButtonIndex === 0 }"
        >
            <p>내가 참여한 채팅방</p>
        </button>
        <button
            class="chatroomlistheader-chatRoomListButton"
            @click="setActiveButton(1)"
            :class="{ 'chatroomlistheader-active': activeButtonIndex === 1 }"
        >
            <p>일대일 채팅</p>
        </button>
    </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useChatRoomStore } from "@/stores/chatroom";

const chatRoomStore = useChatRoomStore();

const activeButtonIndex = ref(0); // 오픈채팅방 목록을 기본으로 조회하게 지정

console.log("기본 선택값입니다:" + activeButtonIndex.value);
const setActiveButton = (index) => {
    activeButtonIndex.value = index;
    chatRoomStore.setIsPersonal(index); // 오픈채팅/개인채팅 여부 설정
    console.log(
        "오픈채팅/개인채팅 여부를 변경했습니다." + chatRoomStore.getIsPersonal
    );
};

onMounted(() => {
    const buttons = document.querySelectorAll("#chatRoomListHeader button");
    buttons.forEach((button) => {
        const pContent = button.querySelector("p").textContent;
        button.style.setProperty("--button-content", `"${pContent}"`);
    });
});
</script>

<style>
@import "@/components/css/color.css";
@import "@/components/css/font.css";

/* Button CSS */
.chatroomlistheader-chatRoomListButton {
    height: 3.125rem;
    width: 50%;
    border: none;
    background: none;
    cursor: pointer;
    font-family: "Semi";
}

.chatroomlistheader-chatRoomListButton {
    --primary-color: var(--black-color);
    --hovered-color: var(--black-color);
    position: relative;
    display: flex;
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
    bottom: -0.1875rem;
    background: var(--black-color);
    height: 0.125rem;
    transition: 0.2s ease-out;
}

.chatroomlistheader-chatRoomListButton p::before {
    position: absolute;
    content: var(--button-content, "");
    width: 0%;
    inset: 0;
    color: var(--black-color);
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
    font-family: "Bold";
    border-bottom: 1px solid var(--black-color);
}

/* template CSS*/
#chatRoomListHeader {
    display: flex;
    justify-content: center;
    height: 3.4rem;
    margin-bottom: 0.6rem;
}
.chatroomlistheader-chatRoomListButton {
    height: 3.125rem;
    width: 50%;
    text-align: center;
    line-height: 3.125rem;
}
</style>
