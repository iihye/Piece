<template>
    <div class="profileimgview-main-container">
        <div class="profileimgview-main-title">
            프로필 이미지를 등록해주세요
        </div>
        <div class="profileimgview-main-content">
            나를 표현하는 프로필 이미지를 등록해주세요
        </div>

        <!-- image -->
        <div class="profileimgview-preview-container">
            <div class="profileimgview-preview-image-container">
                <img
                    v-if="
                        loginUserInfo.profileImage === null ||
                        loginUserInfo.profileImage === ''
                    "
                    class="profileimgview-preview-img"
                    src="@/assets/defaultprofile.png"
                />
                <img
                    v-else
                    class="profileimgview-preview-img"
                    :src="loginUserInfo.profileImage"
                />
            </div>
        </div>

        <!-- 업로드 버튼 -->
        <div class="profileimgview-upload-container">
            <UploadButton
                class="profileimgview-upload-button"
                roundButtonContent="사진 올리기"
                @uploadSuccess="handleUpload"
                @uploadError="handleError"
            />
        </div>

        <!-- 삭제 버튼 -->
        <delete-image-button
            class="profileimgview-delete-button"
            v-if="loginUserInfo.profileImage"
            roundButtonContent="사진 삭제하기"
            @click="deleteImage"
        />

        <!-- success modal -->
        <SuccessModal
            v-if="successModal"
            :modalTitle="'프로필 이미지가 등록되었어요!'"
            :handleSuccessClick="handleSuccessClick"
        />

        <!-- fail modal -->
        <SuccessModal
            v-if="failModal"
            :modalTitle="'다시 시도해주세요!'"
            :handleSuccessClick="handleFailClick"
        />
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useCommonStore } from "@/stores/common";
import { useUserStore } from "@/stores/user";
import { useFileUploadStore } from "@/stores/fileupload";
import SuccessModal from "@/components/modal/SuccessModal.vue";
import UploadButton from "@/components/button/UploadButton.vue";
import router from "@/router";
import DeleteImageButton from "@/components/button/DeleteImageButton.vue";

const commonStore = useCommonStore();
const loginUserInfo = computed(() => commonStore.getLoginUserInfo);
const successModal = ref(false);
const failModal = ref(false);

const fileUploadStore = useFileUploadStore();
const userStore = useUserStore();

// upload image
async function handleUpload(url, s3path) {
    userStore.updateProfileImage(s3path);

    // ==========================================
    // successModal.value = true;
    // ==========================================

    commonStore.findLoginUserInfo();
}

function handleError(error) {
    // console.error("업로드 실패:", error);
    failModal.value = true;
}

// delete image
const deleteImage = async () => {
    try {
        await fileUploadStore.deleteProfileImage(); // 이미지 삭제 요청
        userStore.updateProfileImage(""); // 로컬 사용자 정보 업데이트
        await commonStore.findLoginUserInfo(); // 사용자 정보 다시 로드
        successModal.value = true;
    } catch (error) {
        // console.error("이미지 삭제 과정에서 오류 발생:", error);
        failModal.value = true;
    }
};

// modal
const handleSuccessClick = () => {
    successModal.value = false;
    router.go(-1);
};

const handleFailClick = () => {
    failModal.value = false;
    router.go(-1);
};

onMounted(async () => {
    commonStore.headerTitle = "프로필 이미지 수정";
    commonStore.headerType = "header2";
    await commonStore.findLoginUserInfo();
});
</script>

<style>
.profileimgview-main-container {
    display: flex;
    flex-direction: column;
    height: 100%;
    padding-top: 1rem;
    margin: 0 1rem 0 1rem;
}

.profileimgview-main-title {
    font-family: "Bold";
    font-size: 1.6rem;
    color: var(--black-color);
    margin: 0 0 0.6rem 0;
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

/* .profileimgeview-sub-container {
} */

.profileimgview-img-container {
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 50%;
    margin: 2rem 0 2rem 0;
}

.profileimgview-img-background {
    position: relative;
    width: 10rem;
    height: 10rem;
    border-radius: 50%;
    background-color: var(--gray-color);
}

.profileimgview-img-icon {
    width: 10rem;
    height: 10rem;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}

.profileimgview-img-img {
    width: 10rem;
    height: 10rem;
    border: 0.15rem solid var(--gray-color);
    border-radius: 50%;
}

.profileimgview-file-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin-top: 1rem;
}

/* preview */
.profileimgview-preview-container {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 10rem;
    margin-bottom: 2rem;
    user-select: none;
}

.profileimgview-preview-image-container {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 10rem;
    height: 10rem;
}

.profileimgview-preview-img {
    width: 10rem;
    height: 10rem;
    border: 1px solid var(--gray-color);
    border-radius: 50%;
    object-fit: cover;
    background-color: var(--white-color);
}

/* button */
.profileimgview-upload-container {
    margin-bottom: 0.6rem;
}
</style>
