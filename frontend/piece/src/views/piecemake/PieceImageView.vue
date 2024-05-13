<template>
  <div class="pieceimageview-container">
    <h1>어떤 조각을 만들까요?</h1>
    <div class="pieceimageview-search-area">
      <TextInput
        placeholder="영화명을 검색해보세요!"
        v-model="searchQuery"
        @focus="handleFocus"
        @update:modelValue="value => searchQuery = value"
      />
      <InputPreview
        :searchQuery="searchQuery"
        :searchResults="searchResults"
        :isFocused="isFocused"
        @select="handleSelect"
      />
    </div>

    <div class="pieceimageview-upload-area" @click="triggerFileInput" :class="{ 'no-click': imageSrc }">
      <input type="file" @change="handleFileUpload" accept="image/*" ref="fileInput" style="display: none;" />
      <div class="pieceimageview-explain" v-if="!imageSrc">
        <font-awesome-icon :icon="['fas', 'image']" style="color : var(--red-color)"/>
        <p class="pieceimageview-explain-strong">조각에 넣을 사진을 선택해주세요</p>
        <p>사진 선택은 필수에요</p>
      </div>
      <div v-if="imageSrc">
        <VueCropper 
          ref="cropperRef" 
          :src="uploadedImage" 
          :zoomOnWheel="false"
          :viewMode="2"
          :background="false"
          :autoCropArea="1"
          :initial-aspect-ratio="7/10"
          :aspect-ratio="7/10"
          @crop="cropImage"
        />
      </div>
    </div>
    <SmallButton v-if="imageSrc" @click.prevent="resetImage" :smallButtonContent="'이미지초기화'"/>

    <div class="piecemake-button-container">
      <RouterLink :to="{ name: 'pieceinfo' }">
        <RoundButton :roundButtonContent="'다음'" :isRoundDisable="isRoundDisable"></RoundButton>
      </RouterLink>
    </div> 
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import VueCropper from 'vue-cropperjs';
import 'cropperjs/dist/cropper.css';
import { usePieceStore } from '@/stores/piece.js';
import RoundButton from '@/components/button/RoundButton.vue';
import SmallButton from '@/components/button/SmallButton.vue';
import TextInput from '@/components/text/OnlyInput.vue';
import InputPreview from '@/components/text/InputPreview.vue';

const pieceStore = usePieceStore();

const imageSrc = ref(null);
const croppedImage = ref(null);
const uploadedImage = ref(null); 
const originalImage = ref(null);
const cropperRef = ref(null);
const fileInput = ref(null);
const isRoundDisable = ref(false);

const searchResults = ref([]);
const searchQuery = ref('');
const isFocused = ref(false);

watch(searchQuery, (newValue) => {
  if (newValue.length >= 2) {
    searchMovieDebouncing(newValue);
  } else {
    searchResults.value = [];
  }
});

const getFetchImageFromUrl = (async (imageUrl) => {
  const data = await pieceStore.fetchImage(imageUrl);
  if (imageUrl) {
    // searchResults.value = data;
    imageSrc.value = data;
    uploadedImage.value = data;
    originalImage.value = data;
  }
})

const searchMovieDebouncing = debounce(async (query) => {
  const data = await pieceStore.getSearchMovieList(query);
  if (data) {
    searchResults.value = data;
  }
}, 250)

const emit = defineEmits(['select']);
const hover = ref(false);

function handleSelect(item) {
  getFetchImageFromUrl(item.poster_path);
  handleBlur();
  emit('select', item);
}

async function fetchImageFromUrl(imageUrl) {
  try {
    const blob = await imageUrl.blob();
    const reader = new FileReader();
    reader.onload = e => {
      imageSrc.value = e.target.result;
      uploadedImage.value = e.target.result;
      originalImage.value = e.target.result;
    };
    reader.readAsDataURL(blob);
  } catch (error) {
    console.error('Failed to fetch image:', error);
  }
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
  if (file.type.startsWith('image/')) {
    const reader = new FileReader();
    reader.onload = e => {
      imageSrc.value = e.target.result;
      uploadedImage.value = e.target.result;
      originalImage.value = e.target.result;
    };
    reader.readAsDataURL(file);
  } else {
    alert('Please upload an image file.');
  }
}

const cropImage = () => {
  if (cropperRef.value && cropperRef.value.cropper) {
    croppedImage.value = cropperRef.value.cropper.getCroppedCanvas().toDataURL();
    uploadedImage.value = croppedImage.value;
  }
};

const resetImage = () => {
  imageSrc.value = null;
  croppedImage.value = null;
  uploadedImage.value = '';
  originalImage.value = '';
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
  if (!event.target.closest('.pieceimageview-container')) {
    handleBlur();
  }
};

document.addEventListener('click', handleDocumentClick);
</script>

<style scoped>
.pieceimageview-container {
  max-width: 50rem;
  margin: auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.pieceimageview-explain {
  text-align: center;
}

.pieceimageview-explain-strong{
  font-family: "Semi";
  font-size: 1.4rem;
  color: (--black-color);
}

.pieceimageview-upload-area {
  width: 18.75rem;
  height: 26.25rem;
  border: 0.125rem dashed var(--gray-color);
  padding: 1.25rem;
  text-align: center;
  margin-bottom: 1.25rem;
  cursor: pointer;
  position: relative;
  
  display: flex;
  align-items: center;
}

.pieceimageview-upload-area.no-click {
  cursor: default;
}

.cropper-view-box,
.cropper-face,
.cropper-line,
.cropper-point {
  display: none;
}

.piecemake-button-container {
  position: fixed;
  left: 0;
  right: 0;
  bottom: 4rem;
  text-align: center;
}

.pieceimageview-search-area {
  width: 15rem;
}
</style>
