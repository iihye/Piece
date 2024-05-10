<template>
    <div>
        <!-- image -->
        <img class="cakedetailview-image-image" :src="data.imageUrl" />

        <!-- heart -->
        <div class="cakedetailview-icon-container">
            <div class="cakedetailview-heart-container">
                <font-awesome-icon
                    class="cakedetailview-heart-icon"
                    :icon="cakeHeartState ? ['fas', 'heart'] : ['far', 'heart']"
                    style="color: var(--main-color)"
                    @click="handleHeartClick"
                />
            </div>
            <div class="cakedetailview-heart-message">
                {{ data.heartCnt }}명이 찜하고 있어요
            </div>
        </div>

        <hr />

        <!-- title -->
        <div class="cakedetailview-item-container">
            <div
                class="cakedetailview-item-type"
                :class="{
                    'movie-background': data.cultureType === 'MOVIE',
                    'theater-background': data.cultureType === 'THEATER',
                    'musical-background': data.cultureType === 'MUSICAL',
                    'concert-background': data.cultureType === 'CONCERT',
                    'other-background':
                        data.cultureType !== 'MOVIE' &&
                        data.cultureType !== 'THEATER' &&
                        data.cultureType !== 'MUSICAL' &&
                        data.cultureType !== 'CONCERT',
                }"
            >
                <p v-if="data.cultureType === 'MOVIE'">영화</p>
                <p v-else-if="data.cultureType === 'THEATER'">연극</p>
                <p v-else-if="data.cultureType === 'MUSICAL'">뮤지컬</p>
                <p v-else-if="data.cultureType === 'CONCERT'">콘서트</p>
                <p v-else>기타</p>
            </div>

            <div class="cakedetailview-item-title">{{ data.title }}</div>
        </div>

        <!-- content -->
        <div class="cakedetailview-content-container">
            <div class="cakedetailview-content-content">{{ data.content }}</div>
        </div>

        <hr />

        <!-- chat -->
        <div class="cakedetailview-chat-container">
            <div class="cakedetailview-chat-title">채팅방</div>
            <div class="cakedetailview-chat-container">
                <ChatItem
                    v-for="(item, index) in cakeChatList"
                    class="cakedetailview-chat-item"
                    :key="index"
                    :chatRoomId="item.chatRoomId"
                    :senderLabel="item.senderLabel"
                    :senderNickname="item.senderNickname"
                    :senderImg="item.senderImg"
                    :content="item.content"
                    :createdAt="item.createdAt"
                ></ChatItem>
            </div>
        </div>

        <!-- button -->
        <RoundButton
            class="cakedetailview-button"
            :roundButtonContent="'채팅 참여하기'"
            :roundButtonFunction="handleChatParticipate"
            :isRoundDisable="true"
        ></RoundButton>
    </div>
</template>

<script setup>
import router from "@/router";
import { ref, computed, onMounted } from "vue";
import { useCommonStore } from "@/stores/common";
import { useCakeStore } from "@/stores/cake";
import ChatItem from "@/components/chat/ChatItem.vue";
import RoundButton from "@/components/button/RoundButton.vue";

const commonStore = useCommonStore();
const store = useCakeStore();

const data = ref({
    imageUrl:
        "https://i.namu.wiki/i/IyVsoRT2tRo75y6uqUPLIeN03DgD1RkDKDyWeIafvUKSOnNdZvb9-Le-AhcKQ9OlCkuaI--jqGIy9ffzyrMHZMlUvUlzD-YCDSu-RC5JIFGcjzgt3cOrBwc-cG9Ryjh2BBP4PIqfyEFm3KJqzH81nw.webp",
    heartCnt: 25,
    cultureType: "ETC",
    title: "SHOW WHAT I WANT",
    content: "정보들 ",
});

const cakeHeartState = ref(false);
const cakeChatList = computed(() => store.getCakeChatList);
const cakeChatUser = computed(() => store.getCakeChatUser);
const cakeChatUserLabel = computed(() => store.getCakeChatUserLabel);

const handleHeartClick = () => {
    alert("하트 클릭");
    cakeHeartState.value = !cakeHeartState.value;
    // TODO: 찜 api 연결
    // TODO: 찜 명수 업데이트
};

const handleChatParticipate = () => {
    alert("채팅 참여하기 클릭");
    // TODO: 채팅 참여하기 연결
};

onMounted(async () => {
    commonStore.headerTitle = "케이크 상세보기";
    commonStore.headerType = "header2";

    // const itemId = parseInt(route.params.id);
    // item.value = fakeDatabase.find(i => i.id === itemId);

    // TODO: 찜 상태 가져오기
    // TODO: 찜 명수 업데이트
    // TODO: 채팅방 가져오기
    await store.findCakeChatList();

    console.log(cakeChatList.value);
});
</script>

<style>
/* image */
.cakedetailview-image-image {
    width: 100%;
    height: 360px;
    object-fit: cover;
    user-select: none;
}

/* heart */
.cakedetailview-icon-container {
    display: flex;
    flex-direction: column;
    margin: 2rem 0 2rem 0;
    user-select: none;
}

.cakedetailview-heart-container {
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 0.6rem 2rem 0 2rem;
    margin-bottom: 0.6rem;
}

.cakedetailview-heart-icon {
    width: 1.2rem;
    height: 1.2rem;
}

.cakedetailview-heart-icon:hover {
    cursor: pointer;
}

.cakedetailview-heart-message {
    display: flex;
    justify-self: center;
    align-self: center;
    text-align: center;
    margin-bottom: 0.6rem;
    font-family: "Regular";
    font-size: 1rem;
    color: var(--gray2-color);
}

/* title */
.cakedetailview-item-container {
    display: flex;
    justify-self: center;
    align-items: center;
    flex-direction: row;
    margin: 2rem 0rem 0.4rem 0rem;
    user-select: none;
}

.cakedetailview-item-type {
    font-family: "Semi";
    font-size: 1rem;
    color: var(--white-color);
    user-select: none;
    display: inline-block;
    line-height: 0.1rem;
    padding: 0rem 0.4rem;
    border-radius: 0.5rem;
    background-color: var(--main-color);
}

.cakedetailview-item-title {
    font-family: "Semi";
    font-size: 1rem;
    color: var(--gray2-color);
    margin-left: 0.4rem;
}

/* content */
.cakedetailview-content-container {
    margin: 2rem 0 2rem 0;
    user-select: none;
}

.cakedetailview-content-content {
    font-family: "Regular";
    font-size: 1rem;
    color: var(--black-color);
}

/* chat */
.cakedetailview-chat-container {
    margin: 2rem 0 2rem 0;
    user-select: none;
}

.cakedetailview-chat-title {
    font-family: "Bold";
    font-size: 1.2rem;
    color: var(--gray2-color);
    margin-bottom: 1rem;
}

/* button */
.cakedetailview-button {
    margin: 2rem 0 2rem 0;
}
</style>
