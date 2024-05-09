<template>
    <div>
        <input
            type="file"
            @change="uploadImage"
            ref="fileInput"
            style="display: none"
        />
        <button class="fileuploader-button" @click="triggerFileUpload">
            {{ buttonText }}
        </button>
    </div>
</template>

<script setup>
import { ref, defineProps, defineEmits } from "vue";
import axios from "axios";
import { getPreSignedUrl } from "@/stores/fileupload";

const props = defineProps({
    buttonText: {
        type: String,
        default: "Upload Image",
    },
});
const emits = defineEmits(["uploaded", "error"]);

const fileInput = ref(null);

function triggerFileUpload() {
    fileInput.value.click();
}

async function uploadImage(event) {
    const file = event.target.files[0];
    if (!file) {
        emits("error", "선택된 파일 없슴");
        return;
    }

    const fileName = encodeURIComponent(file.name);
    try {
        // const { url } = await getPreSignedUrl(fileName);
        const url = await getPreSignedUrl(fileName);

        if (!url) {
            emits("error", "URL 받아오기 실패");
            return;
        }

        const uploadResponse = await axios.put(url, file, {
            headers: {
                "Content-Type": file.type,
            },
        });

        if (uploadResponse.status === 200 || uploadResponse.status === 204) {
            emits("uploaded", URL.createObjectURL(file));
        } else {
            emits(
                "error",
                "Upload failed with status: " + uploadResponse.status
            );
        }
    } catch (error) {
        emits('error', error.message);
    }
}
</script>

<style>
.fileuploader-button {
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