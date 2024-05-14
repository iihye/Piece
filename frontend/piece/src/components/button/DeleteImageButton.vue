<!-- <template>
    <button @click="deleteProfileImage" class="delete-image-btn">프로필 이미지 삭제</button>
</template> -->

<template>
    <div class="fileuploader-button">
        <button @click="uploadImage">{{ roundButtonContent }} 사진 삭제하기</button>
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

defineProps({
    roundButtonContent: String,
    roundButtonFunction: Function,
    isRoundDisable: Boolean,
});
</script>

<style></style>