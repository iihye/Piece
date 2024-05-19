<template>
    <div class="fileuploader-button">
        <button @click="uploadImage">{{ roundButtonContent }}</button>
        <input
            type="file"
            ref="fileInput"
            @change="handleFileSelected"
            style="display: none"
        />
    </div>
</template>

<script setup>
import { defineProps } from "vue";
import "@/components/css/color.css";

const { roundButtonContent } = defineProps({
    roundButtonContent: String,
});
</script>

<style>
.fileuploader-button {
    display: flex;
    justify-content: center;
    align-items: center;
}

.fileuploader-button button {
    background-color: var(--main-color);
    border: 0;
    border-radius: 0.625rem;
    height: 2.5rem;
    width: 7.5rem;
    color: var(--white-color);
    font-family: "Semi";
    font-size: 1rem;
    cursor: pointer;
}
</style>
