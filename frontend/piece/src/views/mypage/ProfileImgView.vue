<template>
    <div class="profileimgview-main-container">
        <div class="profileimgview-main-title">
            프로필 이미지를 등록해주세요
        </div>
        <div class="profileimgview-main-content">
            나를 표현하는 프로필 이미지를 등록해주세요
        </div>

        <div class="profileimgview-sub-container">
            <div
                v-if="!loginUserInfo.profileImage"
                class="profileimgview-icon-container"
            >
                <div class="profileimgview-icon-background"></div>
                <font-awesome-icon
                    class="profileimgview-icon-icon"
                    :icon="['fas', 'user']"
                    style="color: var(--gray2-color); width: 4rem; height: 4rem"
                />
            </div>

            <div
                v-if="loginUserInfo.profileImage"
                class="profileimgview-img-container"
            >
                <img
                    v-if="loginUserInfo.profileImage"
                    class="profileimgview-img-img"
                    :src="loginUserInfo.profileImage"
                    alt="프로필 이미지"
                />
            </div>
        </div>

        <!-- <UploadButton
            class="profileimgview-file-container"
            @uploaded="handleUpload"
            @error="handleError"
            buttonText="사진 올리기"
        /> -->

        <!-- 업로드 버튼 -->
        <!-- <UploadButton
            roundButtonContent="사진 올리기"
            @uploadSuccess="handleUpload"
            @uploadError="handleError"
            @SUCCESS="handleSuccessUpload"
            @ERROR="handleErrorUpload"
            @click="handleUploadClick"
        /> -->

                <!-- 업로드 버튼 -->
        <UploadButton
            roundButtonContent="사진 올리기"
            @SUCCESS="handleSuccessUpload"
            @uploadSuccess="handleUpload"
            @uploadError="handleError"
            @ERROR="handleErrorUpload"
            @click="handleUploadClick"
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


const commonStore = useCommonStore();
const loginUserInfo = computed(() => commonStore.getLoginUserInfo);
const successModal = ref(false);
const failModal = ref(false);

// 추가
const fileUploadStore = useFileUploadStore();
const userStore = useUserStore();
const profileImage = ref("");

// async function handleUpload(url) {
//     console.log("handleUpload");
//     profileImage.value = url;

//     await commonStore.findLoginUserInfo();
// }


// ProfileImgView.vue
const handleSuccessUpload = async (s3path) => {
    console.log("받은 S3 경로:", s3path);
    try {
        await fileUploadStore.putUserS3FilePath(s3path); // s3path를 백엔드로 전송
        console.log("S3 경로 저장 성공.");
        successModal.value = true;
        // 밑에줄 추가
        userStore.updateProfileImage(s3path);
        await commonStore.findLoginUserInfo(); // 사용자 정보 업데이트
    } catch (error) {
        console.error("S3 경로 저장 실패:", error);
        failModal.value = true;
    }
};


async function handleUpload(url, s3path) {

    profileImage.value = url;
    // commonStore.updateProfileImage(s3path);
    userStore.updateProfileImage(s3path)

    successModal.value = true;
    commonStore.findLoginUserInfo();
}

// const handleSuccessUpload = () => {
//     successModal.value = true;
//     commonStore.findLoginUserInfo();
// };


const handleSuccessClick = () => {
    successModal.value = false;
    router.go(-1);
};


function handleError(error) {
    console.error("업로드 실패:", error);
    failModal.value = true;
}

// const handleSuccessUpload = () => {
//     console.log('handleSuccessUpload');
//     useFileUploadStore.putUserS3FilePath(s3path);
//     successModal.value = true;
//     commonStore.findLoginUserInfo();
// };



const handleFailClick = () => {
    failModal.value = false;
    router.go(-1);
};


onMounted(async () => {
    commonStore.headerTitle = "프로필 이미지 수정";
    commonStore.headerType = "header2";

    // 추가
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

/* icon */
.profileimgview-icon-container {
    position: relative;
    width: 10rem;
    height: 10rem;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-right: 0.4rem;
}

.profileimgview-icon-background {
    position: absolute;
    top: 0;
    left: 0;
    width: 10rem;
    height: 10rem;
    border-radius: 50%;
    background-color: var(--gray-color);
}

.profileimgview-icon-container
    > .profileimgview-icon-background
    + .profileimgview-icon-icon {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}

.profileimgview-sub-container {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}
</style>
