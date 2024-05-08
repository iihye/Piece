<template>
    <div class="profileimgview-main-title">프로필 이미지를 등록해주세요</div>
    <div class="profileimgview-main-content">원형을 클릭해주세요</div>
    <div class="profileimgview-container">
        <img :src="profileImage" alt="프로필 이미지">
    </div>
    <FileUploader @uploaded="handleUpload" @error="handleError" buttonText="사진 올리기" />
</template>

<script setup>
import { ref } from 'vue';
import FileUploader from '@/components/item/FileUploader.vue';

const profileImage = ref('@/assets/logo.png');

function handleUpload(url) {
    profileImage.value = url;
}

function handleError(error) {
    console.error('업로드 실패', error);
}
</script>

<style>
.profileimgview-main-title {
    font-family: "Semi";
    font-size: 1.4rem;
    color: var(--black-color);
    margin-bottom: 0.6rem;
    user-select: none;
}

.profileimgview-main-content {
    font-family: "Regular";
    font-size: 1rem;
    color: var(--gray2-color);
    margin: 0;
    margin-bottom: 1rem;
    display: flex;
    justify-content: left;
    align-items: center;
    user-select: none;
}

.profileimgview-container {
    margin: 20px 0;
    border-radius: 50%;
    width: 150px;
    height: 150px;
    overflow: hidden;
    display: inline-block;
    background: #eee;
}

.profileimgview-container img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.button {
    background-color: #f06767;
    color: white;
    border: none;
    padding: 10px 20px;
    cursor: pointer;
    border-radius: 5px;
    font-size: 16px;
}

.button:hover {
    background-color: #e05656;
}
</style>