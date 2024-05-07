import axios from 'axios';

const API_BASE_URL = 'http://localhost:8000/api/piece';

export const getPreSignedUrl = async (imageName) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/upload/${imageName}`);
        console.error('presigned url is : ', response.data.data);
        return response.data.data;
    } catch (error) {
        console.error('Error fetching pre-signed URL', error);
        throw error;
    }
};
