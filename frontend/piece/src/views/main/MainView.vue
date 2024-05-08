<template>
    <div class="mainview-main-container">
        <div class="mainview-service-container">
            <img
                class="mainview-service-image"
                src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Food/Shortcake.png"
                alt="Shortcake"
            />

            <div class="mainview-service-content">
                케이크의 마지막 조각을 <br />감상평으로 채워주세요!
            </div>
        </div>
    </div>

    <div class="mainview-sub-container">
        <img
            class="mainview-sub-image"
            src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Food/Shortcake.png"
            alt="Shortcake"
        />
        <div class="mainview-sub-content">지금 조각이 완성되고 있어요!</div>
    </div>

    <RouterLink class="mainview-login-router" :to="{ name: 'login' }"
        >로그인</RouterLink
    >
</template>

<script setup>
import { onMounted } from "vue";
import { useCommonStore } from "@/stores/common";

const commonStore = useCommonStore();

onMounted(() => {
    commonStore.headerTitle = "piece";
    commonStore.headerType = "header1";
});
</script>

<style>
.mainview-main-container {
    width: 100%;
    height: 100%;
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

.mainview-service-image {
    width: 6rem;
    height: 6rem;
    margin-bottom: 2rem;
}

.mainview-service-content {
    font-family: "Semi";
    font-size: 1.2rem;
    text-align: center;
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
