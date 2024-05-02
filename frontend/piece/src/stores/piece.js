import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import { localAxios } from '@/stores/localaxios';

const axiosInstance = localAxios();

export const usePieceStore = defineStore('piece', {
    state: () => ({
        pieceValue: {
            type: null,
            name: '',
            date: null,
            time: null,
            cast: '',
            location: '',
            director: '',
            seat: '',
            price: '',
        },
    }),
    getters: {
        getPieceValue: (state) => state.pieceValue
    },
    actions: {
        setPieceValue(key, value) {
            this.pieceValue[key] = value;
        }
    }
},
{persist: true});