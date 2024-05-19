<template>
    <div class="mainview-main-container">

        <TutorialModal v-if="isTutorialModal && isLogin" />

        <LastYearPiece v-if="isLogin && oneYear"></LastYearPiece>
        <NothingPiece v-if="(isLogin && !oneYear) || !isLogin" />

        <PieceRecentList class="mainview-recent"></PieceRecentList>

    </div>

</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useCommonStore } from "@/stores/common";
import TutorialModal from "@/components/modal/TutorialModal.vue";
import { isTutorialModal } from '@/stores/util';
import { useUserStore } from "@/stores/user";
import PieceRecentList from '@/components/main/PieceRecentList.vue';
import NothingPiece from '@/components/main/NothingPiece.vue';
import LastYearPiece from '@/components/main/LastYearPiece.vue';

const userStore = useUserStore();
const commonStore = useCommonStore();
const isLogin = ref(false);

// 일년 전 조각 조회
const oneYear = computed(() => userStore.getOneYearPiece);

onMounted(async () => {
    commonStore.headerTitle = "piece";
    commonStore.headerType = "header1";

    if (localStorage.getItem("accessToken")) {
        isLogin.value = true;
    }
    await userStore.readTutorial();
    await userStore.readOneYearPiece();
});

</script>

<style>
.mainview-main-container {
    /* width: 100%; */
    width: 360px;
    height: calc(100vh - 7.25rem);
    display: flex;
    flex-direction: column;
    justify-content: space-around;
}

.mainview-image-container {
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
}

.mainview-sub-container {
    display: flex;
}

.mainview-sub-image {
    width: 1.4rem;
    height: 1.4rem;
    margin-right: 0.6rem;
}

.mainview-sub-content {
    font-family: "Semi";
    font-size: 1.2rem;
    text-align: left;
    justify-content: center;
    align-items: center;
}

.mainview-login-router {
    position: fixed;
    bottom: 10rem;
    left: 50%;
    transform: translateX(-50%);
    display: flex;
    justify-content: center;
    align-items: center;
    font-family: "Semi";
    font-size: 1.2rem;
    color: var(--main-color);
    margin-top: 1rem;
    text-decoration: none;
    user-select: none;
}
</style>
