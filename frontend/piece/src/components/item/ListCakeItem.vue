<template>
    <div class="listCakeItem-main-container">
        <div class="listCakeItem-image-container">
            <img class="listCakeItem-image-img" :src="imageUrl" :alt="title" />
        </div>
        <div class="listCakeItem-item-container">
            <div
                class="listCakeItem-item-type"
                :class="{
                    'movie-background': cultureType === 'MOVIE',
                    'theater-background': cultureType === 'THEATER',
                    'musical-background': cultureType === 'MUSICAL',
                    'concert-background': cultureType === 'CONCERT',
                    'other-background':
                        cultureType !== 'MOVIE' &&
                        cultureType !== 'THEATER' &&
                        cultureType !== 'MUSICAL' &&
                        cultureType !== 'CONCERT',
                }"
            >
                <p v-if="cultureType === 'MOVIE'">영화</p>
                <p v-else-if="cultureType === 'THEATER'">연극</p>
                <p v-else-if="cultureType === 'MUSICAL'">뮤지컬</p>
                <p v-else-if="cultureType === 'CONCERT'">콘서트</p>
                <p v-else>기타</p>
            </div>
            <div class="listCakeItem-item-title">{{ title }}</div>
        </div>
    </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
    cultureId: Number,
    cultureType: String,
    code: String,
    title: String,
    imageUrl: String,

    frontImg: String,
    title: String,
    handleItemClick: Function,
});
</script>

<style>
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

.listCakeItem-main-container {
    display: flex;
    justify-content: left;
    align-items: center;
    width: 100%;
    height: calc(100% - 7.25rem);
    padding: 0.4rem 1rem 0.4rem 1rem;
}

.listCakeItem-image-container {
    position: relative;
}

.listCakeItem-image-img {
    width: 6rem;
    height: 6rem;
    border-radius: 1rem;
    object-fit: cover;
}

.listCakeItem-item-container {
    margin: 0 0 0 0.4rem;
}

.listCakeItem-item-type {
    font-family: "Semi";
    font-size: 1rem;
    color: var(--white-color);
    user-select: none;
    display: inline-block;
    line-height: 0.1rem;
    margin-bottom: 0.4rem;
    padding: 0rem 0.4rem;
    border-radius: 0.5rem;
    background-color: var(--main-color);
}

.listCakeItem-item-title {
    display: -webkit-box;
    -webkit-line-clamp: 2; 
    -webkit-box-orient: vertical;
    overflow: hidden;
    white-space: normal;
    line-height: 1.5em; 
    max-height: 3em;

    font-family: "Semi";
    font-size: 1rem;
    color: var(--black-color);
    user-select: none;
    width: 15rem;
}
</style>
