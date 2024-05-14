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
import { useFileUploadStore } from "@/stores/fileupload";

// defineProps({
//     roundButtonContent: String,
// });

const { roundButtonContent } = defineProps({
    roundButtonContent: String,
});

const emit = defineEmits(["uploadSuccess", "uploadError"]);
const fileInput = ref(null);
const store = useFileUploadStore();

function uploadImage() {
    fileInput.value.click();
}

async function handleFileSelected(event) {
    const file = event.target.files[0];
    try {
        const url = await store.getPreSignedUrl(file);
        await store.putFileUpload(url, file);
        emit("SUCCESS", url);
    } catch (error) {
        emit("ERROR", error);
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
