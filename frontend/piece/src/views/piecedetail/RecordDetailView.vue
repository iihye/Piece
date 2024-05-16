<template>
    <div class="recorddetailview-main-container">
        <div class="recorddetailview-preview-container">
            <!-- image -->
            <div
                class="recorddetailview-image-container"
                v-if="imageUrls.length > 0"
            >
                <!-- prev button -->
                <font-awesome-icon
                    class="recorddetailview-image-button"
                    :icon="['fas', 'angle-left']"
                    style="color: var(--gray2-color)"
                    @click="prev"
                />
                <!-- album -->
                <div class="recorddetailview-image-album">
                    <div class="images">
                        <img
                            class="image"
                            v-for="(imageUrl, index) in imageUrls"
                            :key="index"
                            :src="imageUrl"
                            @click="handleImageClick(imageUrl, index)"
                        />
                    </div>

                    <div
                        v-if="imageUrls.length > 1"
                        class="image-circle-wrapper"
                    >
                        <div
                            class="image-circle"
                            v-for="(imageUrl, index) in imageUrls"
                            :key="index"
                            :class="{ activeImg: index === curPos }"
                        ></div>
                    </div>
                </div>
                <!-- next button -->
                <font-awesome-icon
                    class="recorddetailview-image-button"
                    :icon="['fas', 'angle-right']"
                    style="color: var(--gray2-color)"
                    @click="next"
                />
            </div>

            <div
                v-if="
                    pieceDetailRecord.imgList &&
                    pieceDetailRecord.imgList.length > 0
                "
            ></div>

            <!-- 사진 올리기 버튼 -->
            <div
                class="recorddetailview-file-container"
                v-if="imageUrls.length <= 4"
            >
                <div class="recorddetailview-img-message">
                    사진을 선택해주세요<br />최대 5장까지 추가 가능합니다
                </div>
                <!-- <div
                class="recorddetailview-file-container"
                v-if="
                    pieceDetailRecord.imgList == null ||
                    (pieceDetailRecord.imgList &&
                        pieceDetailRecord.imgList.length <= 4)
                "
            > -->
                <UploadButton
                    roundButtonContent="사진 올리기"
                    @SUCCESS="handleSuccessUpload"
                    @uploadSuccess="handleUpload"
                    @uploadError="handleError"
                    @ERROR="handleErrorUpload"
                    @click="handleUploadClick"
                />
                <!-- <FileUploader
                    class="recorddetailview-file-uploader"
                    @uploaded="handleUpload"
                    @error="handleError"
                    buttonText="사진 올리기"
                /> -->
            </div>
        </div>

        <!-- content -->
        <!-- <input :placeholder="pieceDetailRecord.record" /> -->
        <div class="recorddetailview-content-container">
            <textarea
                class="recorddetailview-main-record"
                :value="record"
                @input="updateRecord($event.target.value)"
                placeholder="여기에 입력해주세요"
            ></textarea>
        </div>

        <SquareButton
            class="recorddetailview-main-button"
            :squareButtonContent="'저장'"
            :squareButtonFunction="handleSuccess"
            :isSquareDisable="true"
        ></SquareButton>

        <!-- modal -->
        <ImageDetailModal
            v-if="isImageModal"
            :imgUrl="imgUrl"
            :imgIndex="imgIndex"
            :handleClick="handleModalClick"
            :handleDeleteClick="handleDeleteClick"
        ></ImageDetailModal>

        <SuccessModal
            v-if="isModal"
            :modalTitle="'기록이 저장되었어요!'"
            :handleSuccessClick="handleRecordSuccess"
        />

        <!-- success upload modal -->
        <SuccessModal
            v-if="successModal"
            :modalTitle="'이미지가 등록되었어요!'"
            :handleSuccessClick="handleSuccessClick"
        />

        <!-- fail modal -->
        <SuccessModal
            v-if="failModal"
            :modalTitle="'다시 시도해주세요!'"
            :handleSuccessClick="handleFailClick"
        />

        <!-- success delete modal -->
        <SuccessModal
            v-if="successDeleteModal"
            :modalTitle="'이미지가 삭제되었어요!'"
            :handleSuccessClick="handleDeleteSuccessClick"
        />
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useCommonStore } from "@/stores/common";
import { usePiecelistStore } from "@/stores/piecelist";
import UploadButton from "@/components/button/UploadButton.vue";
import SquareButton from "@/components/button/SquareButton.vue";
import ImageDetailModal from "@/components/modal/ImageDetailModal.vue";
import SuccessModal from "@/components/modal/SuccessModal.vue";

const commonStore = useCommonStore();
const store = usePiecelistStore();

const router = useRouter();

const pieceDetailRecord = computed(() => store.getPieceDetailRecord);
const isImageModal = ref(false);
const isModal = ref(false);
const imgUrl = ref("");
const imgIndex = ref(0);
const successModal = ref(false);
const failModal = ref(false);
const successDeleteModal = ref(false);

const record = computed(() => store.getPieceDetailRecord.record);
const recordValue = ref("");
const imageUrls = computed(() => store.getImgList);

// dummy data
// const imageUrls = ref([
//     "https://i.ibb.co/grMvZS9/your-image.jpg",
//     "https://i.ibb.co/grMvZS9/your-image.jpg",
//     "https://i.ibb.co/grMvZS9/your-image.jpg",
// ]);

// slider
const curPos = ref(0);
const position = ref(0);
const startX = ref(0);
const endX = ref(0);
let IMAGE_WIDTH = ref(240);
let images = null;

const prev = () => {
    if (curPos.value > 0) {
        position.value += IMAGE_WIDTH.value;
        images.style.transform = `translateX(${position.value}px)`;
        curPos.value--;
    }
};

const next = () => {
    if (curPos.value < imageUrls.value.length - 1) {
        position.value -= IMAGE_WIDTH.value;
        images.style.transform = `translateX(${position.value}px)`;
        curPos.value++;
    }
};

const handleImageClick = (imageUrl, index) => {
    imgUrl.value = imageUrl;
    imgIndex.value = index;
    isImageModal.value = true;
};

const touchStart = (event) => {
    startX.value = event.touches[0].pageX;
};

const touchEnd = (event) => {
    endX.value = event.changedTouches[0].pageX;
    if (startX.value > endX.value) next();
    else prev();
};

// upload
function handleUpload(url) {
    profileImage.value = url;
    successModal.value = true;
}

function handleError(error) {
    console.error("업로드 실패", error);
    failModal.value = true;
}

function handleSuccessUpload() {
    consoles.log("success");
    try {
        console.log("사진 업로드");
        // TODO: s3 path 받아오기
        successModal.value = true;
        // TODO: image 등록
    } catch (error) {
        console.log("사진 업로드 실패");
        failModal.value = true;
    }
}

function handleErrorUpload() {
    console.log("error");
}

function handleUploadClick() {
    console.log("upload click");
}

// record
const updateRecord = (value) => {
    recordValue.value = value;
};

// button
const handleSuccess = () => {
    if (recordValue.value === "") {
        router.go(-1);
    } else {
        store.reviseRecordDetail(recordValue.value);
        isModal.value = true;
    }
};

// modal
const handleModalClick = () => {
    isImageModal.value = false;
};

const handleDeleteClick = () => {
    isImageModal.value = false;
    try {
        console.log("사진 삭제 성공");
        // TODO: s3 pathfh image 삭제
        successDeleteModal.value = true;
    } catch (error) {
        console.log("사진 삭제 실패");
        failModal.value = true;
    }
};

const handleRecordSuccess = () => {
    router.go(-1);
};

const handleSuccessClick = () => {
    successModal.value = false;
};

const handleFailClick = () => {
    failModal.value = false;
};

const handleDeleteSuccessClick = () => {
    successDeleteModal.value = false;
};

onMounted(async () => {
    commonStore.headerTitle = "자세히 기록하기";
    commonStore.headerType = "header2";

    const pieceId = store.getPieceDetailViewId;
    if (pieceId == null || pieceId == 0) {
        router.go(-1);
    }

    // slider
    if (imageUrls.value.length > 0) {
        images = document.querySelector(".images");
        images.addEventListener("touchstart", touchStart);
        images.addEventListener("touchend", touchEnd);
    }
});
</script>

<style>
.recorddetailview-main-container {
    width: 100%;
    height: calc(100vh - 7.25rem);
}

.recorddetailview-preview-container {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    width: 100%;
    height: 24rem;
    background-color: var(--sub2-color);
}

.recorddetailview-content-container {
    height: calc(100vh - 7.25rem - 24rem - 6rem);
    padding: 1rem;
    width: calc(100% - 2rem);
}

/* image */
.recorddetailview-image-container {
    display: flex;
    justify-content: center;
    align-items: center;
}

/* 사진 있을 때 */
.recorddetailview-image-button {
    margin: 1rem;
}

.recorddetailview-image-button:hover {
    cursor: pointer;
}

/* album */
.recorddetailview-image-album {
    width: 15rem;
    height: 15rem;
    min-width: 15rem;
    min-height: 15rem;
    overflow: hidden;
}

.images {
    position: relative;
    display: flex;
    height: auto;
    transition: transform 0.5s;
}

.image {
    width: 15rem;
    height: 15rem;
}

.image:hover {
    cursor: pointer;
}

.image-circle-wrapper {
    display: flex;
    position: absolute;
    left: 50%;
    transform: translate(-50%, -18px);
}

.image-circle {
    width: 8px;
    height: 8px;
    border-radius: 50%;
    background-color: var(--white-color);
    border: 1px solid var(--gray-color);
    margin-right: 12px;
}

.image-circle:last-child {
    margin-right: 0;
}

.image-circle.activeImg {
    background-color: var(--gray2-color);
}

/* 사진 없을 때 */
.recorddetailview-img-message {
    font-family: "Regular";
    font-size: 1rem;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: cetner;
    margin-top: 0.6rem;
    margin-bottom: 0.6rem;
    user-select: none;
}

/* 사진 올리기 */
.recorddetailview-file-uploader {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin-top: 1rem;
}

/* content */
.recorddetailview-main-record {
    width: 100%;
    height: calc(100vh - 7.25rem - 24rem - 6rem);
    font-family: "Regular";
    font-size: 1rem;
    line-height: 1.4rem;
    border: 0px;
    outline: 0px;
    resize: none;
    display: block;
}

.recorddetailview-main-record::-webkit-scrollbar {
    width: 0.2rem;
}

.recorddetailview-main-record::-webkit-scrollbar-thumb {
    background-color: var(--gray2-color);
    border-radius: 1rem;
}

.recorddetailview-main-record::-webkit-scrollbar-track {
    background-color: var(--gray-color);
    border-radius: 1rem;
}

.recorddetailview-main-button {
    position: fixed;
    bottom: 4rem;
}
</style>
