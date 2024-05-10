<template>
    <div>
        <img class="cakedetailview-image-image" :src="data.imageUrl" />

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

        <div>
            {{ data.content }}
        </div>

        <hr />

        <div class="cakedetailview-chat-title">채팅방</div>
    </div>
    <!-- <div v-if="item" class="cake-detail-view">
        <img :src="item.image" alt="poster" class="event-image"/>
        <img :src="heart" alt="heart" class="heart"/>
        <img :src="chatting" alt="chat" class="chat"/>
        <div class="event-info">
            <p><strong>{{ item.title }}</strong> <div class="status-box">{{ item.status }}</div></p>
            <p><h4>공연 일시</h4>
            2024.05.25 ~ 2024.05.26</p>
            <p><h4>공연 시간</h4>
            180분</p>
        </div>
        <hr>
        <h4>채팅방</h4>
    </div>
    <div v-else>
        <p>Loading or no item found...</p>
    </div> -->
</template>

<script setup>
import router from "@/router";
import { ref, onMounted } from "vue";
import { useCommonStore } from "@/stores/common";

const commonStore = useCommonStore();

const data = ref({
    imageUrl:
        "https://i.namu.wiki/i/IyVsoRT2tRo75y6uqUPLIeN03DgD1RkDKDyWeIafvUKSOnNdZvb9-Le-AhcKQ9OlCkuaI--jqGIy9ffzyrMHZMlUvUlzD-YCDSu-RC5JIFGcjzgt3cOrBwc-cG9Ryjh2BBP4PIqfyEFm3KJqzH81nw.webp",
    heartCnt: 25,
    cultureType: "ETC",
    title: "SHOW WHAT I WANT",
    content: "정보들 ",
});

const cakeHeartState = ref(false);

const handleHeartClick = () => {
    alert("하트 클릭");
    cakeHeartState.value = !cakeHeartState.value;
};

const item = ref(null);

const fakeDatabase = [
    {
        id: 1,
        title: "범죄도시4",
        image: "path/to/image1.jpg",
        category: "영화",
        status: "진행 예정",
    },
    {
        id: 2,
        title: "IM HERO 임영웅 콘서트",
        image: "path/to/image2.jpg",
        category: "콘서트",
        status: "진행 예정",
    },
    {
        id: 3,
        title: "데미안",
        image: "path/to/image3.jpg",
        category: "뮤지컬",
        status: "진행 중",
    },
    {
        id: 4,
        title: "아트",
        image: "path/to/image4.jpg",
        category: "전시",
        status: "진행 예정",
    },
];

onMounted(() => {
    commonStore.headerTitle = "케이크 상세보기";
    commonStore.headerType = "header2";

    // const itemId = parseInt(route.params.id);
    // item.value = fakeDatabase.find(i => i.id === itemId);
});
</script>

<style>
/* image */
.cakedetailview-image-image {
    width: 100%;
    height: 360px;
    object-fit: cover;
}

/* heart */
.cakedetailview-icon-container {
    display: flex;
    flex-direction: column;
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

/* item */
.cakedetailview-item-container {
    display: flex;
    justify-self: center;
    align-items: center;
    flex-direction: row;
    margin-bottom: 0.4rem;
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

.cake-detail-view {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 20px;
}

.event-image {
    width: 200px;
    height: auto;
    margin-bottom: 20px;
}

.event-info h1 {
    font-size: 24px;
    color: #333;
}

.event-info p {
    font-size: 18px;
    color: #666;
    margin: 5px 0;
}

.status-box {
    display: inline-block;
    padding: 5px 10px;
    border-radius: 20px;
    margin-bottom: 5px;
    background-color: var(--main-color); /* 기본 배경색 */
    color: #fff; /* 흰색 글씨 */
}
</style>
