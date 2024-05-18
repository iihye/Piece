<template>
    <div>
        <!-- image -->
        <img class="cakedetailview-image-image" :src="data.posterImageUrl" />

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
                {{ cakeHeartCount }}명이 찜하고 있어요
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
        <div class="cakedetailview-content-content">{{ data.overview }}</div>
        <div class="cakedetailview-content-runtime">상영 시간: {{ data.runtime }}</div>
        <div v-if="data.castList && data.castList.length > 0" class="cakedetailview-content-cast">
            출연진: {{ data.castList.join(', ') }}
        </div>
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
        :isRoundDisable="false"
    ></RoundButton>
    </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from "vue";
import { useRoute } from "vue-router";
import { useCommonStore } from "@/stores/common";
import { useCakeDetailStore } from "@/stores/cakedetail";
import { useUserStore } from "@/stores/user";
import ChatItem from "@/components/chat/ChatItem.vue";
import RoundButton from "@/components/button/RoundButton.vue";

const commonStore = useCommonStore();
const cakeDetailStore = useCakeDetailStore();
const userStore = useUserStore(); // user store 추가
const route = useRoute();

const data = ref({
    posterImageUrl: "",
    heartCnt: 0,
    cultureType: "",
    title: "",
    overview: "",
    runtime: "",
    castList: [],
});

const cakeHeartState = ref(false);
const cakeChatList = computed(() => cakeDetailStore.getCakeChatList());
const cakeHeartCount = computed(() => cakeDetailStore.getCakeHeartCount());

watch(cakeHeartCount, (newCount) => {
    data.value.heartCnt = newCount;
});

const handleHeartClick = async () => {
    const newHeartState = !cakeHeartState.value;

    try {
        if (newHeartState) {
        // 하트 추가
            await cakeDetailStore.toggleHeart(data.value.cultureId);
        } else {
            // 하트 취소
            await cakeDetailStore.removeHeart(data.value.cultureId);
        }
        cakeHeartState.value = newHeartState;
        userStore.setHeartState(data.value.cultureId, newHeartState);
        await cakeDetailStore.fetchHeartCount(data.value.cultureId);
    } catch (error) {
        console.error("Failed to toggle heart", error);
    }
};

const handleChatParticipate = async () => {
    alert("채팅 참여하기 클릭");

    // 채팅 참여하기 연결
    try {
        await cakeDetailStore.joinChatRoom(data.value.cultureId);
      // 채팅방으로 이동하는 로직을 추가하세요.
    } catch (error) {
        console.error("Failed to join chat room", error);
    }
};

onMounted(async () => {
    commonStore.headerTitle = "케이크 상세보기";
    commonStore.headerType = "header2";

    const concertId = route.params.concertId;
    const cultureId = route.params.cultureId;

    if (!concertId || !cultureId) {
        console.error("Missing required parameters");
        return;
    }

    await cakeDetailStore.fetchCakeDetail(concertId);
    await cakeDetailStore.fetchHeartCount(cultureId);
    await cakeDetailStore.findCakeChatList(concertId);

    data.value = {
        ...cakeDetailStore.cakeDetail,
    };

    // 페이지 로드 시 하트 상태 설정
    cakeHeartState.value = userStore.getHeartState(cultureId) || cakeDetailStore.cakeDetail.isHearted;
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

.cakedetailview-content-runtime {
    font-family: "Regular";
    font-size: 1rem;
    color: var(--black-color);
    margin-top: 0.5rem;
}

.cakedetailview-content-cast {
    font-family: "Regular";
    font-size: 1rem;
    color: var(--black-color);
    margin-top: 0.5rem;
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


