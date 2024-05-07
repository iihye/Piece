<template>
    <div>
        <input type="file" @change="handleFileUpload">
        <button @click="uploadImage">Upload Image</button>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
        file: null,
        imageUrl: '',
        };
    },
    methods: {
        handleFileUpload(event) {
        this.file = event.target.files[0];
        },
        async uploadImage() {
        // 1. 백엔드에게 get 요청을 보내서 url을 응답받기
        try {
            const response = await axios.get(`/upload/${this.file.name}`);
            this.imageUrl = response.data.url;
            console.log("url is : ", url);
        } catch (error) {
            console.error('Error getting pre-signed URL:', error);
            return;
        }

        // 2. 응답받은 url에 put 요청으로 이미지 업로드하기
        try {
            await axios.put(this.imageUrl, this.file, {
                headers: {
                    'Content-Type': this.file.type,
                    },
                });
                console.log('이미지 업로드 성공');
        } catch (error) {
            console.error('이미지 업로드 실패 ㅜ', error);
            }
        },
    },
};
</script>