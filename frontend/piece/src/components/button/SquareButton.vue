<template>
    <div class="squarebutton-container">
        <button
            @click="squareButtonFunction"
            :disabled="!isSquareDisable"
            class="squarebutton"
        >
            {{ squareButtonContent }}
        </button>
    </div>
</template>

<script setup>
import { defineProps } from "vue";

defineProps({
    squareButtonContent: String,
    squareButtonFunction: Function,
    isSquareDisable: Boolean,
});
</script>

<style>
@import "@/components/css/color.css";

.squarebutton {
    background-color: var(--main-color);
    border: 0;
    width: 22.5rem;
    height: 4rem;
    color: var(--white-color);
    font-family: "Semi";
    font-size: 1rem;
}

.squarebutton:disabled {
    background-color: var(--gray-color);
    border: 0;
    width: 22.5rem;
    height: 4rem;
    color: var(--gray2-color);
}
</style>
