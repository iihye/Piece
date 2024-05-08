<template>
    <div class="noitem-main-container">
        <div class="noitem-icon-container">
            <font-awesome-icon
                class="noitem-icon-icon"
                :icon="['far', 'face-frown']"
            />
        </div>
        <div class="noitem-content-container">{{ content }}</div>
    </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
    content: String,
});
</script>

<style>
.noitem-main-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: 100%;
    font-size: 1.2rem;
    font-family: "Semi";
    color: var(--gray2-color);
}

.noitem-icon-container {
    width: 1.6rem;
    height: 1.6rem;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 0.4rem;
}

.noitem-icon-icon {
    width: 1.6rem;
    height: 1.6rem;
}
</style>
