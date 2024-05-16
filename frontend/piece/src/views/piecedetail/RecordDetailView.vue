<template>
    <div class="recorddetailview-main-container">
        <div class="recorddetailview-preview-container">
            <!-- image -->
            <div
                class="recorddetailview-image-container"
                v-show="imageUrls.length > 0"
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
                    @uploadSuccess="handleUpload"
                    @uploadError="handleError"
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

// images
const imgUrl = ref("");
const imgIndex = ref(0);
const selectImgIndex = ref(0);
const imageUrls = computed(() => store.getImgList);
const imageIdList = computed(() => store.getImgIdList);

// record
const record = computed(() => store.getPieceDetailRecord.record);
const recordValue = ref("");

// modal
const isImageModal = ref(false);
const isModal = ref(false);
const successModal = ref(false);
const failModal = ref(false);
const successDeleteModal = ref(false);

// slider
const curPos = ref(0);
const position = ref(0);
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
    selectImgIndex.value = imageIdList.value[index];
    isImageModal.value = true;
};

// upload
async function handleUpload(url, s3path) {
    store.addRecordImgUrl(store.getPieceDetailViewId, s3path);
    successModal.value = true;

    imgUrl.value = "";
    selectImgIndex.value = 0;
    position.value = 0;
    images.style.transform = `translateX(0px)`;
    curPos.value = 0;
}

function handleError(error) {
    failModal.value = true;
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
// image detail
const handleModalClick = () => {
    isImageModal.value = false;
};

async function handleDeleteClick() {
    isImageModal.value = false;
    await store.deleteRecordImgUrl(
        store.getPieceDetailViewId,
        selectImgIndex.value
    );

    successDeleteModal.value = true;

    imgUrl.value = "";
    selectImgIndex.value = 0;
    position.value = 0;
    images.style.transform = `translateX(0px)`;
    curPos.value = 0;
}

// record
const handleRecordSuccess = () => {
    router.go(-1);
};

// upload
const handleSuccessClick = () => {
    successModal.value = false;
};

const handleFailClick = () => {
    failModal.value = false;
};

// image detail
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
