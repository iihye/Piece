<template>
    <div class="app">
        <TheHeader class="TheHeader" />
        <PieceProgressBar v-if="isProgressBar" class="pieceProgressBar"></PieceProgressBar>
        <div class="app-content">
            <RouterView />
        </div>
        <TheFooter class="TheFooter" />
    </div>
</template>

<script setup>
import { computed } from "vue";
import { RouterView } from "vue-router";
import TheHeader from "./components/common/TheHeader.vue";
import TheFooter from "./components/common/TheFooter.vue";
import { useCommonStore } from "./stores/common";
import PieceProgressBar from "./components/item/PieceProgressBar.vue"; 

const commonStore = useCommonStore();
const headerTitle = computed(() => commonStore.getHeaderTitle);
const progress = computed(() => commonStore.getProgress);
const isProgressBar = computed(() => (headerTitle.value === "조각 만들기" && progress.value > 0) ? true : false);
</script>

<style>
.app {
    max-width: 360px;
    min-height: 100vh;
    margin: 0 auto;
    display: flex;
    flex-direction: column;
}

.TheHeader {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 3.25rem;
    z-index: 85;
}

.pieceProgressBar {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    margin-top: 3.25rem;
}

.app-content {
    padding-top: 3.25rem;
    margin-bottom: 4rem;
    min-height: calc(100vh - 7.25rem);
}

.TheFooter {
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 4rem;
    z-index: 85;
    background-color: var(--white-color);
}
</style>
