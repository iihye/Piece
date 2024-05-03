<template>
    <div class="loadingmodal-background-container">
        <div class="loadingmodal-main-container">
            <div class="loadingmodal-modal-title">{{ modalTitle }}</div>
            <div class="loadingmodal-modal-content" v-html="modalContent"></div>
            <div class="loadingmodal-modal-img">
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 100 100"
                    preserveAspectRatio="xMidYMid"
                    width="200"
                    height="200"
                    style="
                        shape-rendering: auto;
                        display: block;
                        background: transparent;
                    "
                    xmlns:xlink="http://www.w3.org/1999/xlink"
                >
                    <g>
                        <g>
                            <circle cx="60" cy="50" r="4" fill="#ff9494">
                                <animate
                                    attributeName="cx"
                                    repeatCount="indefinite"
                                    dur="1s"
                                    values="95;35"
                                    keyTimes="0;1"
                                    begin="-0.67s"
                                ></animate>
                                <animate
                                    attributeName="fill-opacity"
                                    repeatCount="indefinite"
                                    dur="1s"
                                    values="0;1;1"
                                    keyTimes="0;0.2;1"
                                    begin="-0.67s"
                                ></animate>
                            </circle>
                            <circle cx="60" cy="50" r="4" fill="#ff9494">
                                <animate
                                    attributeName="cx"
                                    repeatCount="indefinite"
                                    dur="1s"
                                    values="95;35"
                                    keyTimes="0;1"
                                    begin="-0.33s"
                                ></animate>
                                <animate
                                    attributeName="fill-opacity"
                                    repeatCount="indefinite"
                                    dur="1s"
                                    values="0;1;1"
                                    keyTimes="0;0.2;1"
                                    begin="-0.33s"
                                ></animate>
                            </circle>
                            <circle cx="60" cy="50" r="4" fill="#ff9494">
                                <animate
                                    attributeName="cx"
                                    repeatCount="indefinite"
                                    dur="1s"
                                    values="95;35"
                                    keyTimes="0;1"
                                    begin="0s"
                                ></animate>
                                <animate
                                    attributeName="fill-opacity"
                                    repeatCount="indefinite"
                                    dur="1s"
                                    values="0;1;1"
                                    keyTimes="0;0.2;1"
                                    begin="0s"
                                ></animate>
                            </circle>
                        </g>
                        <g transform="translate(-15 0)">
                            <path
                                d="M50 50L20 50A30 30 0 0 0 80 50Z"
                                fill="#fff5e4"
                                transform="rotate(90 50 50)"
                            ></path>
                            <path
                                d="M50 50L20 50A30 30 0 0 0 80 50Z"
                                fill="#fff5e4"
                            >
                                <animateTransform
                                    attributeName="transform"
                                    type="rotate"
                                    repeatCount="indefinite"
                                    dur="1s"
                                    values="0 50 50;45 50 50;0 50 50"
                                    keyTimes="0;0.5;1"
                                ></animateTransform>
                            </path>
                            <path
                                d="M50 50L20 50A30 30 0 0 1 80 50Z"
                                fill="#fff5e4"
                            >
                                <animateTransform
                                    attributeName="transform"
                                    type="rotate"
                                    repeatCount="indefinite"
                                    dur="1s"
                                    values="0 50 50;-45 50 50;0 50 50"
                                    keyTimes="0;0.5;1"
                                ></animateTransform>
                            </path>
                        </g>
                        <g></g>
                    </g>
                </svg>
            </div>
            <div class="loadingmodal-button-container">
                <button
                    class="loadingmodal-button-fail"
                    @click="handleFailClick"
                >
                    취소
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
    handleFailClick: Function,
});
</script>

<style scoped>
@import "@/components/css/color.css";
@import "@/components/css/font.css";

.loadingmodal-background-container {
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

.loadingmodal-main-container {
    width: flex;
    max-width: 16rem;
    height: flex;
    border-radius: 1rem;
    background-color: var(--white-color);
    padding: 2rem 1.6rem 2rem 1.6rem;
}

.loadingmodal-modal-title {
    font-family: "Semi";
    font-size: 1.4rem;
    color: var(--black-color);
    margin-bottom: 2rem;
}

.loadingmodal-modal-content {
    font-family: "Medium";
    font-size: 1.2rem;
    color: var(--black-color);
    margin: 0;
    margin-bottom: 2rem;
    display: flex;
    justify-content: left;
    align-items: center;
}

.loadingmodal-modal-img {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 2rem;
}

.loadingmodal-button-container {
    width: 16rem;
    display: flex;
    justify-content: left;
    align-items: center;
}

.loadingmodal-button-fail {
    width: flex;
    height: flex;
    font-family: "Bold";
    font-size: 1rem;
    background-color: var(--white-color);
    border: 0;
    border-radius: 0.625rem;
    color: var(--gray2-color);
}
</style>
