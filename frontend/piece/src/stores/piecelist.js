import { ref, computed } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

export const usePiecelistStore = defineStore(
    "piecelist",
    () => {
        // =========== STATE ===============
        const piecelistList = ref({});
        const piecelistMyList = ref({});
        const piecelistHeartList = ref({});
        const piecelistDetail = ref({});
        const pieceDetailHeart = ref({});

        // =========== GETTER ===============
        const getPiecelistList = computed(() => {
            return piecelistList.value;
        });

        const getPiecelistMyList = computed(() => {
            return piecelistMyList.value;
        });

        const getPiecelistHeartList = computed(() => {
            return piecelistMyList.value;
        });

        const getPiecelistDetail = computed(() => {
            return piecelistDetail.value;
        });

        const getPieceDetailHeart = computed(() => {
            return pieceDetailHeart.value;
        });

        // =========== ACTION ===============
        const findPiecelistList = function () {
            axios({
                url: `${import.meta.env.VITE_REST_PIECE_API}/piecelist`,
                method: "GET",
            })
                .then((res) => {
                    piecelistList.value = res.data.data;
                })
                .catch((err) => {});
        };

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

        const findPiecelistHeartList = function () {
            axios({
                url: `${import.meta.env.VITE_REST_PIECE_API}/piecelist/heart`,
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
                })
                .catch((err) => {});
        };

        const findPieceDetailHeart = function (pieceId) {
            axios({
                url: `${import.meta.env.VITE_REST_PIECE_API}/heart/${pieceId}`,
                method: "GET",
            })
                .then((res) => {
                    pieceDetailHeart.value = res.data.data;
                })
                .catch((err) => {});
        };

        const addPieceDetailHeart = function (pieceId) {
            axios({
                url: `${import.meta.env.VITE_REST_PIECE_API}/heart/${pieceId}`,
                method: "POST",
            })
                .then((res) => {
                    pieceDetailHeart.value = true;
                })
                .catch((err) => {});
        };

        const deletePieceDetailHeart = function (pieceId) {
            axios({
                url: `${import.meta.env.VITE_REST_PIECE_API}/heart/${pieceId}`,
                method: "DELETE",
            })
                .then((res) => {
                    pieceDetailHeart.value = false;
                })
                .catch((err) => {});
        };

        return {
            // state
            piecelistList,
            piecelistMyList,
            piecelistHeartList,
            piecelistDetail,
            pieceDetailHeart,
            // getter
            getPiecelistList,
            getPiecelistMyList,
            getPiecelistHeartList,
            getPiecelistDetail,
            getPieceDetailHeart,
            // action
            findPiecelistList,
            findPiecelistMyList,
            findPiecelistHeartList,
            findPiecelistDetail,
            findPieceDetailHeart,
            addPieceDetailHeart,
            deletePieceDetailHeart,
        };
    },
    { persist: true }
);
