import { ref, reactive, computed } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

export const usePiecelistStore = defineStore(
    "piecelist",
    () => {
        // =========== STATE ===============
        const piecelistList = ref({});
        const piecelistMyList = ref({});
        const piecelistMyCalendar = ref([]);
        const piecelistHeartList = ref({});
        const piecelistDetail = ref({});
        const pieceDetailHeart = ref({});
        const pieceDetailRecord = ref({});

        const today = ref(new Date());
        const year = ref(today.value.getFullYear());
        const month = ref(today.value.getMonth());
        const state = reactive({
            calendarHeader: "",
            days: [],
        });

        // =========== GETTER ===============
        const getPiecelistList = computed(() => {
            return piecelistList.value;
        });

        const getPiecelistMyList = computed(() => {
            return piecelistMyList.value;
        });

        const getPiecelistMyCalendar = computed(() => {
            return piecelistMyCalendar.value;
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

        const getToday = computed(() => {
            return today.value;
        });

        const getYear = computed(() => {
            return year.value;
        });

        const getMonth = computed(() => {
            return month.value;
        });

        const getState = computed(() => {
            return state;
        });

        const setToday = function (date) {
            today.value = date;
        };

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
                    if (res.data.code === "FIND_MY_PIECE_LIST_SUCCESS") {
                        piecelistMyCalendar.value = res.data.data;
                        calendarImplementation();
                    } else if (res.data.code === "FIND_MY_PIECE_LIST_NULL_SUCCESS") {
                        piecelistMyCalendar.value = [];
                        calendarImplementation();
                    }
                })
                .catch((err) => {});
        };

        const calendarImplementation = function () {
            state.days = [];
            const year = today.value.getFullYear();
            const month = today.value.getMonth();

            const startDayOfTheMonth = new Date(year, month, 1).getDay();
            const endDayOfTheMonth = new Date(year, month + 1, 0).getDate();
            const emptyStartDays = Array(startDayOfTheMonth).fill({
                day: null,
                imageUrl: null,
                imageId: null,
            });
            const daysOfMonth = Array.from({ length: endDayOfTheMonth }, (_, i) => {
                const { frontImg, pieceId } = getImageUrlForDay(i + 1, month, year);
                return {
                    day: i + 1,
                    imageUrl: frontImg,
                    pieceId: pieceId,
                };
            });
            const fullMonth = [...emptyStartDays, ...daysOfMonth];
            const weeks = [];

            while (fullMonth.length > 0) {
                const weekDays = fullMonth.splice(0, 7);
                while (weekDays.length < 7) {
                    weekDays.push({ day: null, imageUrl: null, pieceId: null });
                }
                weeks.push(weekDays);
            }

            state.days = weeks;
            state.calendarHeader = `${year}. ${month + 1}`;
        };

        const getImageUrlForDay = function (day, month, year) {
            const fullDate = `${year}-${String(month + 1).padStart(2, "0")}-${String(day).padStart(2, "0")}`;
            const piece = piecelistMyCalendar.value.find((p) => p.date === fullDate);
            if (piece) {
                return { frontImg: piece.frontImg, pieceId: piece.pieceId };
            } else {
                return { frontImg: null, pieceId: null };
            }
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
            piecelistMyCalendar,
            piecelistHeartList,
            piecelistDetail,
            pieceDetailHeart,
            pieceDetailRecord,
            today,
            year,
            month,
            state,
            // getter
            getPiecelistList,
            getPiecelistMyList,
            getPiecelistMyCalendar,
            getPiecelistHeartList,
            getPiecelistDetail,
            getPieceDetailHeart,
            getPieceDetailRecord,
            getToday,
            getYear,
            getMonth,
            getState,
            setToday,
            // action
            findPiecelistList,
            findPiecelistMyList,
            findPiecelistMyCalendar,
            calendarImplementation,
            getImageUrlForDay,
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
