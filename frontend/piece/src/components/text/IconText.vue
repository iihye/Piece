<template>
    <div class="icontext-main-container" @click="handleClick">
        <div class="icontext-icon-container">
            <div class="icontext-icon-background"></div>
            <font-awesome-icon
                class="icontext-icon-icon"
                :icon="itemIcon"
                style="color: var(--main-color)"
                width="0.8rem"
                height="0.8rem"
            />
        </div>
        <div class="icontext-item-content">{{ itemContent }}</div>
    </div>
</template>

<script setup>
import { defineProps } from "vue";

defineProps({
    itemContent: String,
    itemIcon: String,
    handleClick: Function,
});
</script>

<style scoped>
.icontext-main-container {
    display: flex;
    justify-content: left;
    align-items: center;
    margin-bottom: 0.8rem;
}

.icontext-item-content {
    font-family: "Regular";
    font-size: 1rem;
    color: var(--black-color);
    margin-left: 0.4rem;
    display: flex;
    justify-content: left;
    align-items: center;
}

.icontext-icon-container {
    position: relative;
    width: 1.6rem;
    height: 1.6rem;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-right: 0.4rem;
}

.icontext-icon-background {
    position: absolute;
    top: 0;
    left: 0;
    width: 1.6rem;
    height: 1.6rem;
    border-radius: 50%;
    background-color: var(--sub-color);
}

.icontext-icon-container > .icontext-icon-background + .icontext-icon-icon {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}
</style>
