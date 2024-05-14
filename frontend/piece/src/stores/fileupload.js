import axios from "axios";

const baseURL = import.meta.env.VITE_REST_API;

export const getPreSignedUrl = async (imageName) => {
    // =========== ACTION ===============
    try {
        const baseURL = import.meta.env.VITE_REST_URL
        const presignedURL = await axios.get(`${baseURL}/fileupload/${encodeURIComponent(file.name)}`);
        
        console.log('====test====');
        console.log('presignedURL is ', presignedURL);
        
        return baseURL.data.data;
    } catch (error) {
        console.log('presigned url 받아오기 에러', error);
        throw error;
    }
};