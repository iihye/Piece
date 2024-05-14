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
import { ref } from "vue";
import { useFileUploadStore } from "@/stores/fileupload";
import LoadingModal from "@/components/modal/LoadingModal.vue";

const { roundButtonContent } = defineProps({
    roundButtonContent: String,
});

const emit = defineEmits(["uploadSuccess", "uploadError"]);
const fileInput = ref(null);
const store = useFileUploadStore();
const loadingModal = ref(false);

function uploadImage() {
    fileInput.value.click();
}

// async function handleFileSelected(event) {
//     loadingModal.value = true;
//     const file = event.target.files[0];
//     try {
//         const url = await store.getPreSignedUrl(file);
//         // test
//         console.log('url.presignedURL ', url.presignedURL);
//         console.log('url.s3path: ', url.s3path);
//         await store.putFileUpload(url.presignedURL, file);
//         emit("SUCCESS", url.);
//     } catch (error) {
//         emit("ERROR", error);
//     }
//     loadingModal.value = false;
// }

async function handleFileSelected(event) {
    loadingModal.value = true;
    const file = event.target.files[0];
    console.log('성공');
    try {
        const url = await store.getPreSignedUrl(file);
        await store.putFileUpload(url.presignedURL, file);
        emit("uploadSuccess", url.presignedURL, url.s3path);
    } catch (error) {
        emit("uploadError", error);
        emit("ERROR", error);
    }
    loadingModal.value = false;
}

</script>


<!-- <script setup>
import { ref } from "vue";
import { useFileUploadStore } from "@/stores/fileupload";
import LoadingModal from "@/components/modal/LoadingModal.vue";

const { roundButtonContent } = defineProps({
    roundButtonContent: String,
});

const emit = defineEmits(["uploadSuccess", "uploadError", "SUCCESS", "ERROR"]);
const fileInput = ref(null);
const store = useFileUploadStore();
const loadingModal = ref(false);

function uploadImage() {
    fileInput.value.click();
}

async function handleFileSelected(event) {
    loadingModal.value = true;
    const file = event.target.files[0];
    try {
        const url = await store.getPreSignedUrl(file);
        
        // 백앤드에게서 받아온 presignedURL, S3Path 확인
        console.log('url.presignedURL ', url.presignedURL);
        console.log('s3path: ', url.s3path);
        
        // aws s3 파일 업로드
        await store.putFileUpload(url.presignedURL, file);
        
        emit("SUCCESS", url.s3path);
        emit("uploadSuccess", url.presignedURL);
    } catch (error) {
        emit("ERROR", error);
        emit("uploadError", error);
    }
    loadingModal.value = false;
}
</script> -->

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
