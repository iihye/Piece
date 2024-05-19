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

    <!-- modal -->
    <LoadingModal
        v-if="loadingModal"
        :modalTitle="'이미지를 등록하고 있어요'"
        :modalContent="'조금만 기다려주세요'"
        :handleFailClick="handleFail"
    ></LoadingModal>
</template>

<script setup>
import { ref, computed } from "vue";
import { useFileUploadStore } from "@/stores/fileupload";
import LoadingModal from "@/components/modal/LoadingModal.vue";

const { roundButtonContent } = defineProps({
    roundButtonContent: String,
});

const emit = defineEmits(["uploadSuccess", "uploadError"]);
const fileInput = ref(null);
const loadingModal = ref(false);

function uploadImage() {
    fileInput.value.click();
}

async function handleFileSelected(event) {
    loadingModal.value = true;
    const file = event.target.files[0];
    const store = useFileUploadStore();
    try {
        const url = await store.getPreSignedUrl(file);
        await store.putFileUpload(url.presignedURL, file);
        emit("uploadSuccess", url.presignedURL, url.s3path);
    } catch (error) {
        emit("uploadError", error);
    }
    loadingModal.value = false;
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
    cursor: pointer;
}
</style>
