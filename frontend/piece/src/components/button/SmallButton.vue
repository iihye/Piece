<template>
    <div class="smallbutton-container">
        <button @click="smallButtonFunction" class="smallbutton">{{ smallButtonContent }}</button>
    </div>
</template>

<script setup>

import { defineProps } from 'vue';

defineProps({
    smallButtonContent: String,
    smallButtonFunction: Function
});

</script>

<style scoped>
@import "@/components/css/color.css";

.smallbutton {
    background-color: var(--main-color);
    border: 0;
    border-radius: 0.625rem;
    height: 2.5rem;
    width: 7.5rem;
    color: var(--white-color);
}
</style>