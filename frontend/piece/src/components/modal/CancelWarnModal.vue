<template>
    <div class="background-container">
        <div class="main-container">
            <div class="modal-title">{{ modalTitle }}</div>
            <div class="modal-content" v-html="modalContent"></div>
            <div class="button-container">
                <button class="button-cancel" @click="handleCancelClick">
                    {{ buttonCancel }}
                </button>
                <button class="button-warn" @click="handleWarnClick">
                    {{ buttonWarn }}
                </button>
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
    modalContent: {
        type: String,
    },
    buttonCancel: {
        type: String,
        default: "취소",
    },
    buttonWarn: {
        type: String,
        default: "나가기",
    },
    handleCancelClick: Function,
    handleWarnClick: Function,
});
</script>

<style>
@import "@/components/css/color.css";
@import "@/components/css/font.css";

.background-container {
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

.main-container {
    width: flex;
    max-width: 16rem;
    height: flex;
    border-radius: 1rem;
    background-color: var(--white-color);
    padding: 2rem 1.6rem 2rem 1.6rem;
}

.modal-title {
    font-family: "Semi";
    font-size: 1.4rem;
    color: var(--black-color);
    margin-bottom: 2rem;
}

.modal-content {
    font-family: "Medium";
    font-size: 1.2rem;
    color: var(--black-color);
    margin: 0;
    margin-bottom: 2rem;
    display: flex;
    justify-content: left;
    align-items: center;
}

.button-container {
    width: 16rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.button-warn {
    width: 7.6rem;
    height: 3.2rem;
    font-family: "Bold";
    font-size: 1rem;
    background-color: var(--red-color);
    border: 0;
    border-radius: 0.625rem;
    color: var(--white-color);
}

.button-cancel {
    width: 7.6rem;
    height: 3.2rem;
    font-family: "Bold";
    font-size: 1rem;
    background-color: var(--gray-color);
    border: 0;
    border-radius: 0.625rem;
    color: var(--gray2-color);
}
</style>
