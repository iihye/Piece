<template>
    <div class="roundbutton-container">
        <button @click="roundButtonFunction" :disabled="!isRoundDisable" class="roundbutton">
            {{ roundButtonContent }}
        </button>
    </div>
</template>

<script setup>
import { defineProps } from "vue";

defineProps({
    roundButtonContent: String,
    roundButtonFunction: Function,
    isRoundDisable: Boolean,
});
</script>

<style>
/* @import "@/components/css/color.css"; */

.roundbutton {
    background-color: var(--main-color);
    border: 0;
    border-radius: 0.625rem;
    width: 21.25rem;
    height: 4rem;
    color: var(--white-color);
    font-family: "Semi";
    font-size: 1rem;
    user-select: none;
}

.roundbutton:disabled {
    background-color: var(--gray-color);
    border: 0;
    border-radius: 0.625rem;
    width: 21.25rem;
    height: 4rem;
    color: var(--gray2-color);
}

.roundbutton:hover {
    cursor: pointer;
}

.roundbutton:disabled:hover {
    cursor: not-allowed;
}
</style>
