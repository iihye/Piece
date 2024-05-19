<template>
    <div class="piecesaveview-container">
        <!-- title -->
        <div class="piecesaveview-main-title">조각이 완성되었어요!</div>
        <div class="piecesaveview-main-content">
            완성된 조각의 앞면과 뒷면을 확인해보세요
        </div>

        <div class="piecesaveview-detail-container">
            <PieceDetailView
                :frontImg="pieceStore.getBase64FrontImage"
                :backImg="pieceStore.getBase64BackImage"
                :title="pieceStore.title"
            />
        </div>

        <!-- button -->
        <RoundButton
            class="piecesaveview-button-container"
            :roundButtonContent="'저장하기'"
            :isRoundDisable="true"
            :roundButtonFunction="savePiece"
        />
    </div>

    <!-- modal -->
    <LoadingModal
        v-if="isLoading"
        :modalTitle="'조각을 만들고 있어요'"
        :modalContent="'파일 업로드 중이에요<br>조금만 기다려주세요'"
    />

    <ImageSuccessModal
        v-if="fileUploadSuccessModal"
        :modalTitle="'조각이 생성되었습니다!'"
        :handleSuccessClick="handleUploadSuccess"
    />
    <ImageSuccessModal
        v-if="fileUploadFailModal"
        :modalTitle="'조각 생성 중 에러가 발생했습니다.'"
        :handleSuccessClick="handleUploadFail"
    />
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import { usePieceStore } from "@/stores/piece.js";
import PieceDetailView from "@/components/item/PieceDetailItem.vue";
import ImageSuccessModal from "@/components/modal/ImageSuccessModal.vue";
import { useCommonStore } from "@/stores/common.js";
import { useFileUploadStore } from "@/stores/fileupload";
import RoundButton from "@/components/button/RoundButton.vue";
import LoadingModal from "@/components/modal/LoadingModal.vue";
import JSConfetti from 'js-confetti';

const pieceStore = usePieceStore();
const commonStore = useCommonStore();
const fileUploadStore = useFileUploadStore();
const router = useRouter();
const jsConfetti = new JSConfetti();

const pieceValue = pieceStore.pieceValue;

const isLoading = ref(false);
const fileUploadSuccessModal = ref(false);
const fileUploadFailModal = ref(false);

const savePiece = async () => {
    try {
        isLoading.value = true; // 로딩 시작

        const frontFilename = `${pieceValue.title}_${
            commonStore.getLoginUser
        }_${Date.now()}_front.png`;
        const backFilename = `${pieceValue.title}_${
            commonStore.getLoginUser
        }_${Date.now()}_back.png`;

        const frontFile = base64toFile(
            pieceStore.getBase64FrontImage,
            frontFilename
        );
        const backFile = base64toFile(
            pieceStore.getBase64BackImage,
            backFilename
        );

        const frontFileUrl = await fileUploadStore.getPreSignedUrl(frontFile);
        const backFileUrl = await fileUploadStore.getPreSignedUrl(backFile);

        await fileUploadStore.putFileUpload(
            frontFileUrl.presignedURL,
            frontFile
        );
        await fileUploadStore.putFileUpload(backFileUrl.presignedURL, backFile);

        pieceValue.imageFront = frontFileUrl.s3path;
        pieceValue.imageBack = backFileUrl.s3path;

        await pieceStore.savePiece();
        isLoading.value = false; // 로딩 종료
        fileUploadSuccessModal.value = true;

        // 상태초기화
        pieceStore.resetPieceValue();
    } catch (error) {
        console.error("사진 저장 중 에러 발생 :", error);
        isLoading.value = false; // 로딩 종료
        fileUploadFailModal.value = true;
    }
};

function base64toFile(base_data, filename) {
    var arr = base_data.split(","),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]),
        n = bstr.length,
        u8arr = new Uint8Array(n);

    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }

    return new File([u8arr], filename, { type: mime });
}

const handleUploadSuccess = () => {
    fileUploadSuccessModal.value = false;
    router.push({ name: "piecelistmain" });
};
const handleUploadFail = () => {
    fileUploadFailModal.value = false;
};

watch(fileUploadSuccessModal, (newValue) => {
  if (newValue) {
    triggerConfetti();
  }
});

const triggerConfetti = () => {
  jsConfetti.addConfetti({
    emojis: ['🎉', '🎊', '😊', '😍', '🎂', '🍰', '🧁'],
    emojiSize: 50,
    confettiNumber: 50,
    confettiRadius: 6,
    scatterDirection: 'up'
  });
};

onMounted(async () => {
    commonStore.headerTitle = "조각 만들기";
    commonStore.headerType = "header2";
    commonStore.setProgress(6);
});
</script>

<style>
.piecesaveview-container {
    display: flex;
    flex-direction: column;
    height: calc(100vh - 9.25rem);
    padding-top: 1rem;
    padding-bottom: 1rem;
    margin: 0 1rem 0 1rem;
}

/* title */
.piecesaveview-main-title {
    font-family: "Bold";
    font-size: 1.6rem;
    color: var(--black-color);
    margin: 0 0 0.4rem 0;
    user-select: none;
}

.piecesaveview-main-content {
    font-family: "Regular";
    font-size: 1rem;
    color: var(--gray2-color);
    margin-bottom: 1rem;
    display: flex;
    justify-content: left;
    align-items: center;
    user-select: none;
}

/* detail */
.piecesaveview-detail-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
    height: 468px;
}

/* button */
.piecesaveview-button-container {
    position: fixed;
    left: 0;
    right: 0;
    bottom: 4rem;
    text-align: center;
}
</style>
