<template>
    <div class="container">
        <h1>프로필 이미지 변경</h1>
        <div class="image-container">
            <img :src="profileImage" alt="프로필 이미지">
        </div>
        <input type="file" @change="uploadImageToS3" style="display: none" ref="fileInput">
        <button class="button" @click="triggerFileUpload">사진 업로드</button>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { getPreSignedUrl } from '@/stores/fileupload';  // getPreSignedUrl 함수를 가져옴

const profileImage = ref('@/assets/logo.png');
const fileInput = ref(null);

function triggerFileUpload() {
    fileInput.value.click();
}

async function uploadImageToS3(event) {
    const file = event.target.files[0];
    if (!file) return;

    const fileName = encodeURIComponent(file.name);  // 파일 이름을 URL 안전 문자열로 인코딩

    try {
        const { url } = await getPreSignedUrl(fileName);  // presigned URL을 받아온다

        // Presigned URL을 사용해서 S3에 파일 업로드
        const uploadResponse = await axios.put(url, file, {
        headers: {
            'Content-Type': file.type
            }
        });

      if (uploadResponse.status === 200) {  // 성공
        profileImage.value = URL.createObjectURL(file);
        console.log('S3 올리기 성공했슴. 확인해보삼');
        }
    } catch (error) {
        console.error('S3 올리기 실패 ㅜ', error);
    }
}
</script>

<style scoped>
.container {
    text-align: center;
    background: #f8f8f8;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0,0,0,0.1);
}

h1 {
    color: #333;
}

.image-container {
    margin: 20px 0;
    border-radius: 50%;
    width: 150px;
    height: 150px;
    overflow: hidden;
    display: inline-block;
    background: #eee;
}

.image-container img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.button {
    background-color: #f06767;
    color: white;
    border: none;
    padding: 10px 20px;
    cursor: pointer;
    border-radius: 5px;
    font-size: 16px;
}

.button:hover {
    background-color: #e05656;
}
</style>