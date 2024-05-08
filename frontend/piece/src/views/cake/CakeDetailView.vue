<template>
    <div v-if="item" class="cake-detail-view">
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
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const item = ref(null);

const fakeDatabase = [
    { id: 1, title: '범죄도시4', image: 'path/to/image1.jpg', category: '영화', status: '진행 예정' },
    { id: 2, title: 'IM HERO 임영웅 콘서트', image: 'path/to/image2.jpg', category: '콘서트', status: '진행 예정' },
    { id: 3, title: '데미안', image: 'path/to/image3.jpg', category: '뮤지컬', status: '진행 중' },
    { id: 4, title: '아트', image: 'path/to/image4.jpg', category: '전시', status: '진행 예정' }
];

onMounted(() => {
    const itemId = parseInt(route.params.id);
    item.value = fakeDatabase.find(i => i.id === itemId);
});
</script>

<style>
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