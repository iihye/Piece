<template>
    <div>
        <FileUploader @uploaded="handleUpload" @error="handleError" buttonText="Upload New Image" />
    </div>
</template>

<script setup>
import FileUploader from './FileUploader.vue';

function handleUpload(url) {
    console.log('이미지 업로드 성공 : ', url);
}

function handleError(error) {
    console.error('이미지 업로드 실패 : ', error);
}
</script>