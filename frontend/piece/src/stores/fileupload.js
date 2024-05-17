import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useFileUploadStore = defineStore("fileupload", () => {
    // =========== ACTION ===============
    const getPreSignedUrl = async (file) => {
        console.log("=====getPreSignedUrl=====");
        try {
            // console.log('fileName is ', file.name);
            const response = await axios.get(
                `${
                    import.meta.env.VITE_REST_PIECE_API
                }/fileupload/${encodeURIComponent(file.name)}`
            );
            const presignedURL = response.data.data[0];
            const s3path = response.data.data[1];

            // console.log('presignedURL is ', presignedURL, ' and s3path is ', s3path);

            const urlands3path = {
                presignedURL: presignedURL,
                s3path: s3path,
            };
            console.log("presinged url 받아오기 성공");
            console.log("presigned url: ", presignedURL);
            console.log("s3path: ", s3path);
            console.log("response: ", response);
            console.log("==========================");

            return urlands3path;
        } catch (error) {
            console.log("presigned url 받아오기 에러", error);
            console.log("==========================");
            throw error;
        }
    };

    // =========== AWS ===============
    async function putFileUpload(presignedURL, file) {
        console.log("=====putFileUpload=====");
        try {
            console.log("presignedURL: ", presignedURL);
            const response = await axios.put(presignedURL, file, {
                headers: {
                    "Content-Type": file.type,
                    Authorization: undefined, // Authorization 헤더 제외
                },
            });
            console.log("파일 업로드 성공!");
            console.log("response: ", response);
            console.log("==========================");
        } catch (error) {
            console.error("파일 업로드 실패", error);
            console.log("===================S=======");
        }
    }

    // =========== DELETE USER ===============
    async function deleteProfileImage() {
        try {
            const response = await axios.delete(
                `${
                    import.meta.env.VITE_REST_USER_API
                }/users/delete/profileImage`
            );
            return response.data;
        } catch (error) {
            console.error("이미지 삭제 실패:", error.response.data);
            throw error;
        }
    }

    // =========== DELETE PIECE ===============
    async function deletePieceImage() {
        try {
            const response = await axios.delete(
                `${import.meta.env.VITE_REST_PIECE_API}/delete/profileImage`
            );
            return response.data;
        } catch (error) {
            console.error("이미지 삭제 실패:", error.response.data);
            throw error;
        }
    }

    return {
        getPreSignedUrl,
        putFileUpload,
    };
});
