<template>
    <div class="listImageItem-main-container">
        <div class="listImageItem-image-container">
            <div class="listImageItem-item-type"
                :class="{
                    'movie-background': performanceType === 'MOVIE',
                    'theater-background': performanceType === 'THEATER',
                    'musical-background': performanceType === 'MUSICAL',
                    'concert-background': performanceType === 'CONCERT',
                    'other-background': performanceType !== 'MOVIE' && performanceType !== 'THEATER' && performanceType !== 'MUSICAL' && performanceType !== 'CONCERT'
                }">
                <p v-if="performanceType === 'MOVIE'">영화</p>
                <p v-else-if="performanceType === 'THEATER'">연극</p>
                <p v-else-if="performanceType === 'MUSICAL'">뮤지컬</p>
                <p v-else-if="performanceType === 'CONCERT'">콘서트</p>
                <p v-else>기타</p>
            </div>
                <img
                class="listImageItem-item-img"
                :src="frontImg"
                :alt="title"
            />
        </div>
        <div class="listImageItem-item-title">{{ title }}</div>
    </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
    pieceId: Number,
    performanceType: String,
    frontImg: String,
    title: String,
    handleItemClick: Function,
});
</script>

<style>
@import "@/components/css/color.css";
@import "@/components/css/font.css";

.movie-background {
    background-color: var(--movie-color) !important;
}

.theater-background {
    background-color: var(--theater-color) !important;
}

.musical-background {
    background-color: var(--musical-color) !important;
}

.concert-background {
    background-color: var(--concert-color) !important;
}

.other-background {
    background-color: var(--other-color) !important;
}

.listImageItem-main-container{
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column; 
    margin: 0 3rem 1rem 3rem;
}

.listImageItem-image-container {
    position: relative;
}

.listImageItem-item-type {
    font-family: "Semi";
    font-size: 1rem;
    color: var(--white-color);
    user-select: none;
    display: inline-block;
    line-height: 0.1rem;
    padding: 0rem 0.4rem; 
    border-radius: 0.5rem; 
    background-color: var(--main-color); 
    position: absolute;
    top: 0.4rem;
    left: 0.4rem;
}

.listImageItem-item-img {
    width: 8rem;
    height: 10rem;
}

.listImageItem-item-title{
    font-family: "Semi";
    font-size: 1rem;
    color: var(--black-color);
}
</style>
