import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import { localAxios } from '@/stores/localaxios';

const axiosInstance = localAxios(import.meta.env.VITE_REST_PIECE_API);

export const usePieceStore = defineStore('piece', {
    state: () => ({
        pieceValue: {
            performanceType: null,
            title: '',
            date: null,
            time: null,
            cast: '',
            address: '',
            supervision: '',
            seat: '',
            price: 0,
            score: 0,
            comment: '',

            // 아래는 더미데이터
            openYn: 'Y',
            imageFront: 'if',
            imageBack: 'ib',
            genre: 'FEAR',
        },
    }),
    getters: {
        getPieceValue: (state) => state.pieceValue
    },
    actions: {
        setPieceValue(key, value) {
            this.pieceValue[key] = value;
        },
        async savePiece() {
            try {
                const response = await axiosInstance.post('/pieces', this.pieceValue);
                // console.log('Piece saved:', response.data);
                return response.data;
            } catch (error) {
                console.error('에러: ', error);
            }
        }
    }
},
{persist: true});