<template>
    <div class="cake-item">
        <div class="cake-image">
            <img :src="item.image" alt="image" class="cake-image">
        </div>
        <div class="cake-details">
            <p><div class="category-box">{{ item.category }}</div> {{ item.status }}</p>
            <h3 @click="goToDetail(item.id)">{{ item.title }}</h3>
        </div>
    </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
const props = defineProps({
    item: {
        type: Object,
        required: true
    }
});

const router = useRouter();

const goToDetail = (id) => {
    router.push({ name: 'CakeDetail', params: { id } });
};
</script>

<style>
.cake-item {
    display: flex;
    align-items: center;
    margin: 10px;
}

.cake-image {
    margin-right: 10px;
}

.cake-image img {
    width: 100px;
    height: auto;
}

.cake-details {
    text-align: left;
}

.cake-details h3 {
    margin: 0 0 5px;
}

.category-box {
    display: inline-block;
    padding: 5px 10px;
    border-radius: 20px;
    margin-bottom: 5px;
}

.category-box {
    display: inline-block;
    padding: 5px 10px;
    border-radius: 20px;
    margin-bottom: 5px;
    background-color: var(--other-color); /* 기본 배경색 */
    color: #fff; /* 흰색 글씨 */
}

.musical .category-box {
    background-color: var(--musical-color); /* 뮤지컬 카테고리 색상 */
}

.movie .category-box {
    background-color: var(--movie-color); /* 영화 카테고리 색상 */
}

.theater .category-box {
    background-color: var(--theater-color); /* 연극 카테고리 색상 */
}

.concert .category-box {
    background-color: var(--concert-color); /* 콘서트 카테고리 색상 */
}

.status {
    margin: 0;
}
</style>