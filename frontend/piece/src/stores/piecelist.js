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
        const pieceListMyCalendar = ref([]);
        const piecelistHeartList = ref({});
        const piecelistDetail = ref({});
        const pieceDetailHeart = ref({});
        const pieceDetailRecord = ref({});

        // =========== GETTER ===============
        const getPiecelistList = computed(() => {
            return piecelistList.value;
        });

        const getPiecelistMyList = computed(() => {
            return piecelistMyList.value;
        });

        const getPiecelistMyCalendar = computed(() => {
            return pieceListMyCalendar.value;
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

        const getPieceDetailRecord = computed(() => {
            return pieceDetailRecord.value;
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

        const findPiecelistMyCalendar = function (year, month) {
            axios({
                url: `${import.meta.env.VITE_REST_PIECE_API}/piecelist/my/${year}/${month}`,
                method: "GET",
            })
                .then((res) => {
                    pieceListMyCalendar.value = res.data.data;
                    console.log(pieceListMyCalendar.value);
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

        const findPieceDetailRecord = function (pieceId) {
            axios({
                url: `${import.meta.env.VITE_REST_PIECE_API}/pieces/record/${pieceId}`,
                method: "GET",
            })
                .then((res) => {
                    pieceDetailRecord.value = res.data.data;
                    console.log(pieceDetailRecord.value);
                })
                .catch((err) => {});
        };

        return {
            // state
            piecelistList,
            piecelistMyList,
            pieceListMyCalendar,
            piecelistHeartList,
            piecelistDetail,
            pieceDetailHeart,
            pieceDetailRecord,
            // getter
            getPiecelistList,
            getPiecelistMyList,
            getPiecelistMyCalendar,
            getPiecelistHeartList,
            getPiecelistDetail,
            getPieceDetailHeart,
            getPieceDetailRecord,
            // action
            findPiecelistList,
            findPiecelistMyList,
            findPiecelistMyCalendar,
            findPiecelistHeartList,
            findPiecelistDetail,
            findPieceDetailHeart,
            addPieceDetailHeart,
            deletePieceDetailHeart,
            findPieceDetailRecord,
        };
    },
    { persist: true }
);
