import axios from 'axios';

const API_BASE_URL = 'http://localhost:8000';

export const getPreSignedUrl = async (imageName) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/upload/${imageName}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching pre-signed URL', error);
        throw error;
    }
};
