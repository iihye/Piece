import { ref, reactive, computed } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";
import { useCommonStore } from "@/stores/common";

export const usePiecelistStore = defineStore(
  "piecelist",
  () => {
    const commonStore = useCommonStore();

    // =========== STATE ===============
    const selectedOption = ref("ALL");
    const selectedOptionMyList = ref("ALL");
    const selectedOptionHeartList = ref("ALL");
    const piecelistList = ref({});
    const piecelistListFiltered = ref({});
    const piecelistMyList = ref({});
    const piecelistMyListFiltered = ref({});
    const piecelistMyCalendar = ref([]);
    const piecelistHeartList = ref({});
    const piecelistHeartListFiltered = ref({});
    const piecelistDetail = ref({});
    const pieceDetailHeart = ref({});
    const pieceDetailRecord = ref({});
    const pieceUser = ref({});
    const pieceUserLabel = ref("");
    const isMine = ref(false);
    const pieceDetaiLViewId = ref(0);
    const record = ref("");
    const imgList = ref([]);
    const imgIdList = ref([]);
    const recordImgUrl = ref("");

    const today = ref(new Date());
    const year = ref(today.value.getFullYear());
    const month = ref(today.value.getMonth());
    const state = reactive({
      calendarHeader: "",
      days: [],
    });

    const recentPieceList = ref({});

    // =========== GETTER ===============
    const getSelectOption = computed(() => {
      return selectedOption.value;
    });

    const setSelectOption = function (option) {
      selectedOption.value = option;
    };

    const getSelectOptionMyList = computed(() => {
      return selectedOptionMyList.value;
    });

    const setSelectOptionMyList = function (option) {
      selectedOptionMyList.value = option;
    };

    const getSelectOptionHeartList = computed(() => {
      return selectedOptionHeartList.value;
    });

    const setSelectOptionHeartList = function (option) {
      selectedOptionHeartList.value = option;
    };

    const getPiecelistList = computed(() => {
      return piecelistList.value;
    });

    const getPiecelistListFiltered = computed(() => {
      return piecelistListFiltered.value;
    });

    const getPiecelistMyList = computed(() => {
      return piecelistMyList.value;
    });

    const getPiecelistMyListFiltered = computed(() => {
      return piecelistMyListFiltered.value;
    });

    const getPiecelistMyCalendar = computed(() => {
      return piecelistMyCalendar.value;
    });

    const getPiecelistHeartList = computed(() => {
      return piecelistHeartList.value;
    });

    const getPiecelistHeartListFiltered = computed(() => {
      return piecelistHeartListFiltered.value;
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

    const getPieceUser = computed(() => {
      return pieceUser.value;
    });

    const getPieceUserLabel = computed(() => {
      return pieceUserLabel.value;
    });

    const getIsMine = computed(() => {
      return isMine.value;
    });

    const getPieceDetailViewId = computed(() => {
      return pieceDetaiLViewId.value;
    });

    const setPieceDetailViewId = function (id) {
      pieceDetaiLViewId.value = id;
    };
    const getImgList = computed(() => {
      return imgList.value;
    });

    const getImgIdList = computed(() => {
      return imgIdList.value;
    });

    const setRecordImgUrl = function (url) {
      recordImgUrl.value = url;
    };

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
      year.value = today.value.getFullYear();
      month.value = today.value.getMonth();
    };

    const getRecentPieceList = computed(() => {
      return recentPieceList.value;
    });

    // =========== ACTION ===============

    const findPieceUser = function (userId) {
      axios({
        url: `${import.meta.env.VITE_REST_USER_API}/users/find/${userId}`,
        method: "GET",
      })
        .then((res) => {
          pieceUser.value = res.data;
          if (
            pieceUser.value.labelId !== 0 &&
            pieceUser.value.labelId !== null
          ) {
            findPieceUserLabel(pieceUser.value.labelId);
          }
        })
        .catch((err) => {});
    };

    const findPieceUserLabel = function (labelId) {
      axios({
        url: `${import.meta.env.VITE_REST_PIECE_API}/labels/${labelId}`,
        method: "GET",
      })
        .then((res) => {
          pieceUserLabel.value = res.data.data;
        })
        .catch((err) => {});
    };

    const findPiecelistList = function () {
      axios({
        url: `${import.meta.env.VITE_REST_PIECE_API}/piecelist`,
        method: "GET",
      })
        .then((res) => {
          piecelistList.value = res.data.data;
          piecelistListFiltered.value = computedFilteredList();
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
          piecelistMyListFiltered.value = computedFilteredMyList();
        })
        .catch((err) => {});
    };

    function computedFilteredList() {
      if (selectedOption.value === "ALL") {
        return piecelistList.value;
      } else if (selectedOption.value === "MOVIE") {
        return piecelistList.value.filter(
          (item) => item.performanceType === "MOVIE"
        );
      } else if (selectedOption.value === "THEATER") {
        return piecelistList.value.filter(
          (item) => item.performanceType === "THEATER"
        );
      } else if (selectedOption.value === "MUSICAL") {
        return piecelistList.value.filter(
          (item) => item.performanceType === "MUSICAL"
        );
      } else if (selectedOption.value === "CONCERT") {
        return piecelistList.value.filter(
          (item) => item.performanceType === "CONCERT"
        );
      } else if (selectedOption.value === "ETC") {
        return piecelistList.value.filter(
          (item) => item.performanceType === "ETC"
        );
      } else {
        return [];
      }
    }

    function computedFilteredMyList() {
      if (selectedOptionMyList.value === "ALL") {
        return piecelistMyList.value;
      } else if (selectedOptionMyList.value === "MOVIE") {
        return piecelistMyList.value.filter(
          (item) => item.performanceType === "MOVIE"
        );
      } else if (selectedOptionMyList.value === "THEATER") {
        return piecelistMyList.value.filter(
          (item) => item.performanceType === "THEATER"
        );
      } else if (selectedOptionMyList.value === "MUSICAL") {
        return piecelistMyList.value.filter(
          (item) => item.performanceType === "MUSICAL"
        );
      } else if (selectedOptionMyList.value === "CONCERT") {
        return piecelistMyList.value.filter(
          (item) => item.performanceType === "CONCERT"
        );
      } else if (selectedOptionMyList.value === "ETC") {
        return piecelistMyList.value.filter(
          (item) => item.performanceType === "ETC"
        );
      } else {
        return [];
      }
    }

    function computedFilteredHeartList() {
      if (selectedOptionHeartList.value === "ALL") {
        return piecelistHeartList.value;
      } else if (selectedOptionHeartList.value === "MOVIE") {
        return piecelistHeartList.value.filter(
          (item) => item.performanceType === "MOVIE"
        );
      } else if (selectedOptionHeartList.value === "THEATER") {
        return piecelistHeartList.value.filter(
          (item) => item.performanceType === "THEATER"
        );
      } else if (selectedOptionHeartList.value === "MUSICAL") {
        return piecelistHeartList.value.filter(
          (item) => item.performanceType === "MUSICAL"
        );
      } else if (selectedOptionHeartList.value === "CONCERT") {
        return piecelistHeartList.value.filter(
          (item) => item.performanceType === "CONCERT"
        );
      } else if (selectedOptionHeartList.value === "ETC") {
        return piecelistHeartList.value.filter(
          (item) => item.performanceType === "ETC"
        );
      } else {
        return [];
      }
    }

    const findPiecelistMyCalendar = function (year, month) {
      axios({
        url: `${
          import.meta.env.VITE_REST_PIECE_API
        }/piecelist/my/${year}/${month}`,
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
      const fullDate = `${year}-${String(month + 1).padStart(2, "0")}-${String(
        day
      ).padStart(2, "0")}`;
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
          piecelistHeartList.value = res.data.data;
          piecelistHeartListFiltered.value = computedFilteredHeartList();
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
          findPieceUser(piecelistDetail.value.userId);

          if (localStorage.getItem("userId") == piecelistDetail.value.userId) {
            isMine.value = true;
            findPieceDetailRecord(pieceId);
          } else {
            isMine.value = false;
          }
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
      if (pieceId == null || pieceId == 0) return;
      axios({
        url: `${import.meta.env.VITE_REST_PIECE_API}/pieces/record/${pieceId}`,
        method: "GET",
      })
        .then((res) => {
          pieceDetailRecord.value = res.data.data;
          record.value = pieceDetailRecord.value.record;
          imgList.value = pieceDetailRecord.value.imgList;
          imgIdList.value = pieceDetailRecord.value.imgIdList;
        })
        .catch((err) => {});
    };

    const deletePieceDetail = function (pieceId) {
      axios({
        url: `${import.meta.env.VITE_REST_PIECE_API}/pieces/${pieceId}`,
        method: "DELETE",
      })
        .then((res) => {})
        .catch((err) => {});
    };

    const reviseRecordDetail = function (record) {
      axios({
        url: `${import.meta.env.VITE_REST_PIECE_API}/pieces/record`,
        method: "PUT",
        data: {
          pieceId: pieceDetaiLViewId.value,
          record: record,
        },
      })
        .then((res) => {})
        .catch((err) => {});
    };

    const addRecordImgUrl = function (pieceId, url) {
      axios({
        url: `${import.meta.env.VITE_REST_PIECE_API}/pieces/record/image`,
        method: "POST",
        data: {
          pieceId: pieceId,
          s3path: url,
        },
      })
        .then((res) => {
          findPieceDetailRecord(pieceId);
        })
        .catch((err) => {});
    };

    const deleteRecordImgUrl = function (pieceId, pieceimageid) {
      axios({
        url: `${
          import.meta.env.VITE_REST_PIECE_API
        }/pieces/record/image/${pieceimageid}`,
        method: "DELETE",
      })
        .then((res) => {
          findPieceDetailRecord(pieceId);
        })
        .catch((err) => {});
    };

    const findRecentPieceList = function () {
      axios({
        url: `${import.meta.env.VITE_REST_PIECE_API}/piecelist/recent`,
        method: "GET",
      })
        .then((res) => {
          recentPieceList.value = res.data.data;
          console.log(recentPieceList.value);
        })
        .catch((err) => {});
    };

    return {
      // state
      selectedOption,
      selectedOptionMyList,
      selectedOptionHeartList,
      piecelistList,
      piecelistListFiltered,
      piecelistMyList,
      piecelistMyListFiltered,
      piecelistMyCalendar,
      piecelistHeartList,
      piecelistHeartListFiltered,
      piecelistDetail,
      pieceDetailHeart,
      pieceDetailRecord,
      pieceUser,
      pieceUserLabel,
      isMine,
      pieceDetaiLViewId,
      imgList,
      imgIdList,
      recordImgUrl,
      today,
      year,
      month,
      state,
      recentPieceList,
      // getter
      getSelectOption,
      setSelectOption,
      getSelectOptionMyList,
      setSelectOptionMyList,
      getSelectOptionHeartList,
      setSelectOptionHeartList,
      getPiecelistList,
      getPiecelistListFiltered,
      getPiecelistMyList,
      getPiecelistMyListFiltered,
      getPiecelistMyCalendar,
      getPiecelistHeartList,
      getPiecelistHeartListFiltered,
      getPiecelistDetail,
      getPieceDetailHeart,
      getPieceDetailRecord,
      getPieceUser,
      getPieceUserLabel,
      getIsMine,
      getPieceDetailViewId,
      setPieceDetailViewId,
      getImgList,
      getImgIdList,
      setRecordImgUrl,
      getToday,
      getYear,
      getMonth,
      getState,
      setToday,
      getRecentPieceList,
      // action
      findPieceUser,
      findPieceUserLabel,
      findPiecelistList,
      findPiecelistMyList,
      computedFilteredList,
      computedFilteredMyList,
      computedFilteredHeartList,
      findPiecelistMyCalendar,
      calendarImplementation,
      getImageUrlForDay,
      findPiecelistHeartList,
      findPiecelistDetail,
      findPieceDetailHeart,
      addPieceDetailHeart,
      deletePieceDetailHeart,
      findPieceDetailRecord,
      deletePieceDetail,
      reviseRecordDetail,
      addRecordImgUrl,
      deleteRecordImgUrl,
      findRecentPieceList,
    };
  },
  { persist: true }
);
