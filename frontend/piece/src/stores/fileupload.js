import axios from 'axios';

const API_BASE_URL = 'http://localhost:8000/api/piece';

export const getPreSignedUrl = async (imageName) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/upload/${imageName}`);
        console.log('url 받아오기 성공 presigned url : ', response.data.data);
        return response.data.data;
    } catch (error) {
        console.error('url 받아오기 실패', error);
        throw error;
    }
};