<template>
    <div class="mainview-main-container">
        <div class="mainview-service-container">
            <TutorialModal v-if="isTutorialModal && isLogin" />
            <!-- <NothingPiece></NothingPiece>

            <PieceRecentList class="mainview-recent"></PieceRecentList> -->
        </div>
    </div>
    <RouterLink v-if="!isLogin" class="mainview-login-router" :to="{ name: 'login' }">로그인</RouterLink>

</template>

<script setup>
import { ref, onMounted } from "vue";
import { useCommonStore } from "@/stores/common";
import TutorialModal from "@/components/modal/TutorialModal.vue";
import { isTutorialModal } from '@/stores/util';
import { useUserStore } from "@/stores/user";
// import PieceRecentList from '@/components/main/PieceRecentList.vue';
// import NothingPiece from '@/components/main/NothingPiece.vue';

const userStore = useUserStore();
const commonStore = useCommonStore();
const isLogin = ref(false);

onMounted(async () => {
    commonStore.headerTitle = "piece";
    commonStore.headerType = "header1";

    if (localStorage.getItem("accessToken")) {
        isLogin.value = true;
    }
});

</script>

<style>
.mainview-main-container {
    width: 100%;
    height: calc(100vh - 7.25rem);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

.mainview-service-container {
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 4rem;
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
