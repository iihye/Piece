import { ref, computed } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

export const usePiecelistStore = defineStore(
    "piecelist",
    () => {
        // =========== STATE ===============
        const piecelistMyList = ref({});

        // =========== GETTER ===============
        const getPiecelistMyList = computed(() => {
            return piecelistMyList.value;
        });

        // =========== ACTION ===============
        const findPiecelistMyList = function () {
            axios({
                url: `${import.meta.env.VITE_REST_PIECE_API}/piecelist/my`,
                method: "GET",
            })
                .then((res) => {
                    piecelistMyList.value = res.data.data;
                })
                .catch((err) => {});
        };

        return {
            // state
            piecelistMyList,
            // getter
            getPiecelistMyList,
            // action
            findPiecelistMyList,
        };
    },
    { persist: true }
);
