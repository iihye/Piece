<!-- <template>
    <div>
        <input type="file" @change="uploadImage" ref="fileInput" style="display: none">
        <button @click="triggerFileUpload">{{ buttonText }}</button>
    </div>
</template>

<script setup>
import { ref, defineProps, defineEmits } from 'vue';
import axios from 'axios';

const props = defineProps({
    buttonText: {
        type: String,
        default: 'Upload Image'
    }
});
const emits = defineEmits(['uploaded', 'error']);

const fileInput = ref(null);

function triggerFileUpload() {
    fileInput.value.click();
}

async function uploadImage(event) {
    const file = event.target.files[0];
    if (!file) {
        emits('error', 'No file selected');
        return;
    }

    const fileName = encodeURIComponent(file.name);
    try {
        // const { url } = await getPreSignedUrl(fileName);
        const url = await getPreSignedUrl(fileName);
        console.log("Presigned URL received:", url); // URL 로그 출력

        if (!url) {
            emits('error', 'Received undefined URL');
            return;
        }

        const uploadResponse = await axios.put(url, file, {
            headers: {
                'Content-Type': file.type
            }
        });

        console.log("Upload response:", uploadResponse); // 업로드 응답 로그

        if (uploadResponse.status === 200 || uploadResponse.status === 204) {
            emits('uploaded', URL.createObjectURL(file));
        } else {
            emits('error', 'Upload failed with status: ' + uploadResponse.status);
        }
    } catch (error) {
        console.error('Error during file upload:', error);
        emits('error', error.message);
    }
}
</script> -->

<template>

</template>

<script>
</script>

<style></style>