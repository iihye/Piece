<template>
  <div>
    <h1>어떤 조각을 만들까요?</h1>
    <div class="input-div" @drop="handleDrop">
      <p>여기로 이미지를 드래그하거나 <strong>클릭</strong>하세요.</p>
      <input type="file" class="file" @change="handleFileUpload" accept="image/*"/>
    </div>

    <div v-if="imageSrc">
        <VueCropper 
            ref="cropperRef" 
            :src="uploadedImage" 
            :zoomOnWheel="false"
            :initial-aspect-ratio=9/16
            :aspect-ratio=9/16
        />
        <button @click.prevent="cropImage">Crop</button>
    </div>

    <div v-if="croppedImage">
      <img :src="croppedImage" alt="Image Preview" style="max-width: 100%; height: auto;" />
    </div>

    <hr>
    <RouterLink :to="{ name: 'pieceimagecrop' }">사진 올리기</RouterLink>
    <RouterLink :to="{ name: 'pieceinfo' }">사진 다 올리고 다음</RouterLink>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import VueCropper from 'vue-cropperjs';
import 'cropperjs/dist/cropper.css';

const imageSrc = ref(null);
const croppedImage = ref(null);
const uploadedImage = ref('');
const cropperRef = ref(null);

function handleFileUpload(event) {
  const file = event.target.files[0];
  if (file && file.type.startsWith('image/')) {
    const reader = new FileReader();
    reader.onload = e => {
      imageSrc.value = e.target.result;
      uploadedImage.value = e.target.result;
    };
    reader.readAsDataURL(file);
  } else {
    imageSrc.value = null;
    alert('Please upload an image file.');
  }
}

onMounted(() => {
  if (uploadedImage.value && cropperRef.value) {
    cropperRef.value.cropper.replace(uploadedImage.value);
  }
});

const cropImage = () => {
  if (cropperRef.value && cropperRef.value.cropper) {
    croppedImage.value = cropperRef.value.cropper.getCroppedCanvas().toDataURL();
  }
};
</script>


<style></style>
