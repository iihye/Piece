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
        isPrivate: false,
        openYn: 'Y',
        imageFront: '',
        imageBack: '',
        genre: 'FEAR'
    });

    const croppedImageValue = ref('');
    const base64FrontImage = ref('');
    const base64BackImage = ref('');

    // Getters
    const getCroppedImageValue = computed(() => croppedImageValue.value);
    const getPieceValue = computed(() => pieceValue.value);
    const getBase64FrontImage = computed(() => base64FrontImage.value);
    const getBase64BackImage = computed(() => base64BackImage.value);

    // Actions
    function setPieceValue(key, value) {
      pieceValue.value[key] = value;
    }

    function setCroppedImageValue(value) {
      croppedImageValue.value = value;
    }

    function setBase64FrontImage(value) {
      base64FrontImage.value = value;
    }

    function setBase64BackImage(value) {
      base64BackImage.value = value;
    }

    function resetPieceValue() {
      pieceValue.value = {
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
          isPrivate: false,
          openYn: 'Y',
          imageFront: '',
          imageBack: '',
          genre: 'FEAR'
      };
      croppedImageValue.value = '';
      base64FrontImage.value = '';
      base64BackImage.value = '';
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
        getBase64FrontImage,
        getBase64BackImage,
        getCroppedImageValue,
        setPieceValue,
        setCroppedImageValue,
        savePiece,
        getSearchMovieList,
        fetchImage,
        setBase64FrontImage,
        setBase64BackImage,
        resetPieceValue,
    };
},
{
    persist: true
});
