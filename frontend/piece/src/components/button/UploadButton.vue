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
import { ref } from "vue";
import axios from "axios";

defineProps({
    roundButtonContent: String,
});

const emit = defineEmits(["uploadSuccess", "uploadError"]);

const fileInput = ref(null);

function uploadImage() {
    fileInput.value.click();
}

async function handleFileSelected(event) {
    console.log("file selected");
    const file = event.target.files[0];
    if (!file) {
        emit("uploadError", "No file selected");
        return;
    }

    try {
        console.log("file uploaded");

        const URL = presignedURL.data.data;
        const uploadFile = await axios.put(URL, file, {
            headers: {
                "Content-Type": file.type,
                Authorization: undefined, // Authorization 헤더 제외
            },
        });

        console.log('url is ', URL);
        console.log('success');
        if (uploadFile.status === 200 || uploadFile.status === 204) {
            emit("uploadSuccess", "Image uploaded successfully");
        } else {
            emit("uploadError", "Failed to upload image");
        }
    } catch (error) {
        emit("uploadError", error.message);
    }
}
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
}
</style>
