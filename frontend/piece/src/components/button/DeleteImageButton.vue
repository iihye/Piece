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

<style></style>