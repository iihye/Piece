import { ref, computed } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

export const usePiecelistStore = defineStore(
    "piecelist",
    () => {
        // =========== STATE ===============
        const piecelistMyList = ref({});
        const piecelistDetail = ref({});

        // =========== GETTER ===============
        const getPiecelistMyList = computed(() => {
            return piecelistMyList.value;
        });

        const getPiecelistDetail = computed(() => {
            return piecelistDetail.value;
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

        const findPiecelistDetail = function (pieceId) {
            axios({
                url: `${import.meta.env.VITE_REST_PIECE_API}/pieces/${pieceId}`,
                method: "GET",
            })
                .then((res) => {
                    piecelistDetail.value = res.data.data;
                    console.log(piecelistDetail.value);
                })
                .catch((err) => {});
        };

        return {
            // state
            piecelistMyList,
            piecelistDetail,
            // getter
            getPiecelistMyList,
            getPiecelistDetail,
            // action
            findPiecelistMyList,
            findPiecelistDetail,
        };
    },
    { persist: true }
);
