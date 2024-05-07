<template>
    <div class="userprofilemodal-background-container">
        <div class="userprofilemodal-main-container">
            <div class="userprofilemodal-modal-image">
                <img
                    class="userprofilemodal-user-image"
                    :src="userImageUrl"
                    alt="Modal Image"
                />
            </div>
            <div class="userprofilemodal-user-label">{{ userLabel }}</div>
            <div class="userprofilemodal-user-name">{{ userName }}</div>
            <button
                class="userprofilemodal-button-chat"
                @click="handleChatClick"
            >
                1:1 채팅하기
            </button>
            <button
                class="userprofilemodal-button-report"
                @click="handleReportClick"
            >
                신고하기
            </button>
        </div>
    </div>
</template>

<script setup>
import { defineProps } from "vue";

defineProps({
    userImageUrl: {
        type: String,
    },
    userLabel: {
        type: String,
    },
    userName: {
        type: String,
    },
    handleChatClick: Function,
    handleReportClick: Function,
});
</script>

<style scoped>
@import "@/components/css/color.css";
@import "@/components/css/font.css";

.userprofilemodal-background-container {
    position: fixed;
    width: 100vw;
    height: 100vh;
    background-color: var(--modal-color);
    top: 0;
    left: 0;
    margin: 0;
    z-index: 99 !important;
    display: flex;
    justify-content: center;
    align-items: center;
}

.userprofilemodal-main-container {
    width: flex;
    max-width: 16rem;
    height: flex;
    border-radius: 1rem;
    background-color: var(--white-color);
    padding: 2rem 1.6rem 2rem 1.6rem;
}

.userprofilemodal-modal-image {
    margin-bottom: 0.8rem;
    display: flex;
    justify-content: center;
    align-items: center;
}

.userprofilemodal-user-image {
    width: 6rem;
    height: 6rem;
    border: 1px solid var(--gray-color);
    border-radius: 50%;
}

.userprofilemodal-user-label {
    font-family: "Medium";
    font-size: 1.2rem;
    color: var(--main-color);
    margin: 0;
    margin-bottom: 0.4rem;
    display: flex;
    justify-content: center;
    align-items: center;
}

.userprofilemodal-user-name {
    font-family: "Semi";
    font-size: 1.2rem;
    color: var(--black-color);
    margin: 0;
    margin-bottom: 1rem;
    display: flex;
    justify-content: center;
    align-items: center;
}

.userprofilemodal-button-chat {
    width: 16rem;
    height: 3.2rem;
    font-family: "Bold";
    font-size: 1rem;
    background-color: var(--main-color);
    margin-bottom: 0.8rem;
    border: 0;
    border-radius: 0.625rem;
    color: var(--white-color);
}

.userprofilemodal-button-report {
    width: 16rem;
    height: 3.2rem;
    font-family: "Bold";
    font-size: 1rem;
    background-color: var(--gray2-color);
    border: 0;
    border-radius: 0.625rem;
    color: var(--white-color);
}
</style>
