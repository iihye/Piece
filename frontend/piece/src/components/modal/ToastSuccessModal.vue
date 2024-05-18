<template>
    <div class="toastsuccessmodal-background-container">
        <div class="toastsuccessmodal-main-container">
            <div class="toastsuccessmodal-icon-container">
                <div class="toastsuccessmodal-icon-background"></div>
                <font-awesome-icon
                    class="toastsuccessmodal-icon-icon"
                    :icon="iconTitle"
                    style="color: var(--white-color)"
                />
            </div>
            <div class="toastsuccessmodal-modal-content">{{ modalTitle }}</div>
        </div>
    </div>
</template>

<script setup>
import { defineProps } from "vue";

defineProps({
    iconTitle: {
        type: String,
    },
    modalTitle: {
        type: String,
    },
});
</script>

<style>
.toastsuccessmodal-background-container {
    position: fixed;
    width: 100vw;
    height: 100vh;
    top: 0;
    left: 0;
    margin: 0;
    z-index: 99 !important;
    display: flex;
    justify-content: center;
    align-items: center;
    user-select: none;
}

.toastsuccessmodal-main-container {
    display: flex;
    justify-content: left;
    align-items: center;
    position: fixed;
    top: 3.5rem;
    width: 18rem;
    height: 1rem;
    border-radius: 0.6rem;
    background-color: var(--modal2-color);
    padding: 1.4rem;
}

.toastsuccessmodal-modal-content {
    font-family: "Regular";
    font-size: 1rem;
    color: var(--white-color);
    margin-left: 0.4rem;
    display: flex;
    justify-content: left;
    align-items: center;
    user-select: none;
}

.toastsuccessmodal-icon-container {
    position: relative;
    width: 1.8rem;
    height: 1.8rem;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-right: 0.4rem;
}

.toastsuccessmodal-icon-background {
    position: absolute;
    top: 0;
    left: 0;
    width: 1.8rem;
    height: 1.8rem;
    border-radius: 50%;
    background-color: var(--main-color);
}

.toastsuccessmodal-icon-container
    > .toastsuccessmodal-icon-background
    + .toastsuccessmodal-icon-icon {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}
</style>
