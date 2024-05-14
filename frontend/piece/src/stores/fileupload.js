import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useFileUploadStore = defineStore ('fileupload', () => {
    // =========== ACTION ===============
    const getPreSignedUrl = async (file) => {
        try {

            const response = await axios.get(`${import.meta.env.VITE_REST_PIECE_API}/fileupload/${encodeURIComponent(file.name)}`);
            console.log('resonse is ', response);
            const presignedURL = response.data.data;
            console.log('presignedURL is ', presignedURL);
        
            return presignedURL;
        } catch (error) {
            console.log('presigned url 받아오기 에러', error);
            throw error;
        }
    }

    async function putFileUpload(presignedURL, file) {
        try {
            const response = await axios.put(presignedURL, file, {
                headers: {
                    "Content-Type": file.type,
                    Authorization: undefined, // Authorization 헤더 제외
                },
            });
            console.log('파일 업로드 성공!');
        } catch (error) {
            console.error('파일 업로드 실패', error.message);
        }
    }

    return {
        getPreSignedUrl,
        putFileUpload,
    };
});