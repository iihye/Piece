<template>
    <div class="imagedetailmodal-background-container">
        <div class="imagedetailmodal-main-container">
            <img :src="imgUrl" alt="Detail Image" />
            <div class="imagedetailmodal-button-container">
                <SmallButton
                    class="imagedetailmodal-button-button"
                    :smallButtonContent="'삭제'"
                    :smallButtonFunction="handleDeleteClick"
                ></SmallButton>
                <SmallButton
                    class="imagedetailmodal-button-button"
                    :smallButtonContent="'닫기'"
                    :smallButtonFunction="handleClick"
                ></SmallButton>
            </div>
        </div>
    </div>
</template>

<script setup>
import { defineProps } from "vue";
import SmallButton from "@/components/button/SmallButton.vue";

defineProps({
    imgUrl: String,
    imgIndex: Number,
    handleClick: Function,
    handleDeleteClick: Function,
});
</script>

<style>
.imagedetailmodal-background-container {
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

.imagedetailmodal-main-container {
    width: flex;
    max-width: 20rem;
    height: flex;
    border-radius: 1rem;
    background-color: var(--white-color);
    padding: 2rem 1.6rem 2rem 1.6rem;
}

.imagedetailmodal-main-container img {
    width: 20rem;
    height: 20rem;
}

.imagedetailmodal-button-container {
    display: flex;
    justify-content: center;
    align-items: center;
}

.imagedetailmodal-button-button {
    margin: 1.4rem 0.4rem 0 0.4rem;
}
</style>
