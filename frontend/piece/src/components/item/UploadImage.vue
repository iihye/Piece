<template>
    <div>
        <FileUploader @uploaded="handleUpload" @error="handleError" buttonText="Upload New Image" />
    </div>
</template>

<script setup>
import FileUploader from '@/components/item/FileUploader.vue';

function handleUpload(url) {
}

function handleError(error) {
}
</script>