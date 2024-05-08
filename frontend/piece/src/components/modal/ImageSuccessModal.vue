<template>
    <div class="imagesuccessmodal-background-container">
        <div class="imagesuccessmodal-main-container">
            <div class="imagesuccessmodal-modal-title">{{ modalTitle }}</div>
            <div class="imagesuccessmodal-modal-img">
                <img
                    src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Food/Birthday%20Cake.png"
                    alt="Birthday Cake"
                    width="160"
                    height="160"
                />
            </div>
            <div class="imagesuccessmodal-button-container">
                <button class="imagesuccessmodal-button-success" @click="handleSuccessClick">확인</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { defineProps } from "vue";

defineProps({
    modalTitle: {
        type: String,
    },
    handleSuccessClick: Function,
});
</script>

<style>
@import "@/components/css/color.css";
@import "@/components/css/font.css";

.imagesuccessmodal-background-container {
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

.imagesuccessmodal-main-container {
    width: flex;
    max-width: 16rem;
    height: flex;
    border-radius: 1rem;
    background-color: var(--white-color);
    padding: 2rem 1.6rem 2rem 1.6rem;
}

.imagesuccessmodal-modal-title {
    font-family: "Semi";
    font-size: 1.4rem;
    color: var(--black-color);
    margin-bottom: 2rem;
}

.imagesuccessmodal-modal-img {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 2rem;
}

.imagesuccessmodal-button-container {
    width: 16rem;
    display: flex;
    justify-content: end;
    align-items: center;
}

.imagesuccessmodal-button-success {
    width: flex;
    height: flex;
    font-family: "Bold";
    font-size: 1rem;
    background-color: var(--white-color);
    border: 0;
    border-radius: 0.625rem;
    color: var(--main-color);
}
</style>
