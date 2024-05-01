import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import { localAxios } from '@/stores/localaxios';

const axiosInstance = localAxios();

export const usePieceStore = defineStore(
    "piece",
    () => {
        // =========== STATE ===============

        const pieceValue=ref({});

        // =========== GETTER ===============

        const getpPieceValue = computed(() => {
            return pieceValue.value;
        });

        // =========== SETTER ===============

        const setPieceValue = (data) => {
            pieceValue.value = data;
        };

        // =========== ACTION ===============
        return {
            // state
            pieceValue,
            // getter
            getpPieceValue,
            // setter
            setPieceValue,
            // action
        };
    },
    { persist: true }
);
