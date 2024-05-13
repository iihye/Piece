<template>
    <div class="pieceimageview-container">
        <!-- title -->
        <div class="pieceimageview-main-title">어떤 조각을 만들까요?</div>
        <div class="pieceimageview-main-content">
            제목으로 검색하거나 직접 사진을 올려주세요
        </div>

        <!-- search -->
        <div class="pieceimageview-search-area">
            <TextInput
                class="pieceimageview-search-input"
                placeholder="포스터를 찾아보세요!"
                v-model="searchQuery"
                @focus="handleFocus"
                @update:modelValue="(value) => (searchQuery = value)"
            />
            <InputPreview
                class="pieceimageview-search-preview"
                :searchQuery="searchQuery"
                :searchResults="searchResults"
                :isFocused="isFocused"
                @select="handleSelect"
            />
        </div>

        <!-- upload -->
        <div class="pieceimageview-upload-container">
            <div
                class="pieceimageview-upload-area"
                @click="triggerFileInput"
                :class="{ 'no-click': imageSrc }"
            >
                <input
                    type="file"
                    @change="handleFileUpload"
                    accept="image/*"
                    ref="fileInput"
                    style="display: none"
                />
                <div class="pieceimageview-explain" v-if="!imageSrc">
                    <font-awesome-icon
                        class="pieceimageview-explain-icon"
                        :icon="['fas', 'image']"
                        style="color: var(--main-color)"
                    />
                    <div class="pieceimageview-explain-strong">
                        조각에 넣을 사진을 선택해주세요
                    </div>
                    <div class="pieceimageview-explain-regular">
                        사진 선택은 필수예요
                    </div>
                </div>
                <!-- image -->
                <div v-if="imageSrc">
                    <VueCropper
                        class="pieceImageView-crop-area"
                        ref="cropperRef"
                        :src="uploadedImage"
                        :zoomOnWheel="false"
                        :viewMode="2"
                        :background="false"
                        :autoCropArea="1"
                        :initial-aspect-ratio="7 / 10"
                        :aspect-ratio="7 / 10"
                        @crop="debouncedCropImage"
                    />
                </div>
            </div>
        </div>

        <!-- image reset -->
        <SmallButton
            class="piecemake-button-reset"
            v-if="imageSrc"
            @click.prevent="resetImage"
            :smallButtonContent="'이미지 초기화'"
        />

        <!-- next -->
        <div class="piecemake-button-container">
            <RouterLink :to="{ name: 'pieceinfo' }">
                <RoundButton
                    :roundButtonContent="'다음'"
                    :isRoundDisable="isRoundDisable"
                ></RoundButton>
            </RouterLink>
        </div>
    </div>
</template>

<script setup>
import { ref, watch } from "vue";
import VueCropper from "vue-cropperjs";
import "cropperjs/dist/cropper.css";
import { usePieceStore } from "@/stores/piece.js";
import RoundButton from "@/components/button/RoundButton.vue";
import SmallButton from "@/components/button/SmallButton.vue";
import TextInput from "@/components/text/OnlyInput.vue";
import InputPreview from "@/components/text/InputPreview.vue";

const pieceStore = usePieceStore();

const imageSrc = ref(null);
const croppedImage = ref(null);
const uploadedImage = ref(null);
const originalImage = ref(null);
const cropperRef = ref(null);
const fileInput = ref(null);
const isRoundDisable = ref(false);

const searchResults = ref([]);
const searchQuery = ref("");
const isFocused = ref(false);

watch(searchQuery, (newValue) => {
    if (newValue.length >= 2) {
        searchMovieDebouncing(newValue);
    } else {
        searchResults.value = [];
    }
});

const getFetchImageFromUrl = async (imageUrl) => {
    const data = await pieceStore.fetchImage(imageUrl);
    if (imageUrl) {
        // searchResults.value = data;
        imageSrc.value = data;
        uploadedImage.value = data;
        originalImage.value = data;
    }
};

// function getFetchImageFromUrl(imageUrl) {
//     imageSrc.value = imageUrl;
//     uploadedImage.value = imageUrl;
//     originalImage.value = imageUrl;
// }

const searchMovieDebouncing = debounce(async (query) => {
    const data = await pieceStore.getSearchMovieList(query);
    if (data) {
        searchResults.value = data;
    }
}, 250);

const emit = defineEmits(["select"]);

function handleSelect(item) {
    resetImage();
    getFetchImageFromUrl(item.poster_path);
    handleBlur();
    emit("select", item);
}

function triggerFileInput() {
    if (!imageSrc.value) {
        fileInput.value.click();
    }
}

const handleFocus = () => {
    isFocused.value = true;
};

const handleBlur = () => {
    isFocused.value = false;
};

function handleFileUpload(event) {
    const file = event.target.files[0];
    if (file.type.startsWith("image/")) {
        const reader = new FileReader();
        reader.onload = (e) => {
            imageSrc.value = e.target.result;
            uploadedImage.value = e.target.result;
            originalImage.value = e.target.result;
        };
        reader.readAsDataURL(file);
    } else {
        alert("Please upload an image file.");
    }
}

const cropImage = () => {
    if (cropperRef.value && cropperRef.value.cropper) {
        croppedImage.value = cropperRef.value.cropper
            .getCroppedCanvas()
            .toDataURL();
        uploadedImage.value = croppedImage.value;
    }
};

const debouncedCropImage = debounce(cropImage, 100);

const resetImage = () => {
    imageSrc.value = null;
    croppedImage.value = null;
    uploadedImage.value = "";
    originalImage.value = "";
};

watch(uploadedImage, (newValue) => {
    isRoundDisable.value = Boolean(newValue);
});

// debounce
function debounce(func, delay) {
    let timeoutId;

    return function (...args) {
        clearTimeout(timeoutId);
        timeoutId = setTimeout(() => {
            func.apply(this, args);
        }, delay);
    };
}

const handleDocumentClick = (event) => {
    if (!event.target.closest(".pieceimageview-container")) {
        handleBlur();
    }
};

document.addEventListener("click", handleDocumentClick);
</script>

<style>
.pieceimageview-container {
    display: flex;
    flex-direction: column;
    height: calc(100vh - 9.25rem);
    padding-top: 1rem;
    padding-bottom: 1rem;
    margin: 0 1rem 0 1rem;
}

/* title */
.pieceimageview-main-title {
    font-family: "Bold";
    font-size: 1.6rem;
    color: var(--black-color);
    margin: 0 0 0.4rem 0;
    user-select: none;
}

.pieceimageview-main-content {
    font-family: "Regular";
    font-size: 1rem;
    color: var(--gray2-color);
    margin-bottom: 1rem;
    display: flex;
    justify-content: left;
    align-items: center;
    user-select: none;
}

/* search */
.pieceimageview-search-area {
    width: 15rem;
    margin-bottom: 1rem;
    align-self: end;
}

/* upload */
.pieceimageview-upload-container {
    display: flex;
    justify-content: center;
    align-items: center;
}

.pieceimageview-upload-area {
    width: 100%;
    height: 25.25rem;
    border: 0.125rem dashed var(--gray-color);
    text-align: center;
    margin-bottom: 1.25rem;
    cursor: pointer;
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
}

.pieceimageview-upload-area.no-click {
    cursor: default;
}

/* explain */
.pieceimageview-explain {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
}

.pieceimageview-explain-icon {
    margin-bottom: 0.6rem;
}

.pieceimageview-explain-strong {
    font-family: "Semi";
    font-size: 1.2rem;
    color: var(--black-color);
    margin-bottom: 0.6rem;
}

.pieceimageview-explain-regular {
    font-family: "Regular";
    font-size: 1rem;
    color: var(--black-color);
    margin-bottom: 0.6rem;
}

/* image */
.pieceImageView-crop-area {
    width: 100%;
    height: 25.25rem;
    object-fit: cover;
}

.cropper-face,
.cropper-line,
.cropper-point {
    background-color: var(--main-color) !important;
}

.cropper-view-box {
    outline: var(--main-color) !important;
}

/* reset */
.piecemake-button-reset {
    display: flex;
    justify-content: center;
    align-items: center;
}

/* button */
.piecemake-button-container {
    position: fixed;
    left: 0;
    right: 0;
    bottom: 3.75rem;
    margin-bottom: 0.6rem;
    text-align: center;
}
</style>
