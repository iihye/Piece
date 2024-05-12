import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import { localAxios } from '@/stores/localaxios';
import axios from 'axios';

const axiosInstance = localAxios(import.meta.env.VITE_REST_PIECE_API);

export const usePieceStore = defineStore('piece', () => {
    // state
    const pieceValue = ref({
        performanceType: null,
        title: null,
        date: null,
        time: null,
        cast: null,
        address: null,
        supervision: null,
        seat: null,
        price: 0,
        score: 0,
        comment: null,
        openYn: 'Y',
        imageFront: 'if',
        imageBack: 'ib',
        genre: 'FEAR'
    });

    const croppedImageValue = ref('');

    // Getters
    const getPieceValue = computed(() => pieceValue.value);

    // Actions
    function setPieceValue(key, value) {
        pieceValue.value[key] = value;
    }

    function setCroppedImageValue(value) {
        croppedImageValue.value = value;
    }

    async function savePiece() {
        try {
            const response = await axiosInstance.post('/pieces', pieceValue.value);
            // console.log('Piece saved:', response.data);
            return response.data;
        } catch (error) {
            console.error('에러: ', error);
            return null;
        }
    }

    const getSearchMovieList = async (query) => {
        try {
          const response = await axios.get(`${import.meta.env.VITE_REST_PIECE_API}/cultures/tmdb/search?movieName=${query}`);
          return response.data.data || [];
        } catch (error) {
          console.error('Search failed:', error);
          return []; 
        }
      };

    const fetchImage = async (imageUrl) => {
        try {
          const response = await axios.get(`${import.meta.env.VITE_REST_PIECE_API}/cultures/image?imageUrl=${imageUrl}`);
          return response.data.data;
        } catch (error) {
          console.error('fetch failed:', error);
          return []; 
        }
      };

    return {
        pieceValue,
        croppedImageValue,
        getPieceValue,
        setPieceValue,
        setCroppedImageValue,
        savePiece,
        getSearchMovieList,
        fetchImage,
    };
},
{
    persist: true
});
