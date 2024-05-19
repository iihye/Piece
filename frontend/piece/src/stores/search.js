import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useSearchStore = defineStore("search", () => {
  //  =========== STATE ===============

  const searchList = ref([]);
  const mySearchList = ref([]);

  // =========== GETTER ===============

  const getSearchList = computed(() => {
    return searchList.value;
  });

  const getMySearchList = computed(() => {
    return mySearchList.value;
  });

  // =========== ACTION ===============

  const searchPiece = function (keyword) {
    axios({
      url: `${import.meta.env.VITE_REST_PIECE_API}/search/${keyword}`,
      method: "GET",
    })
      .then((res) => {
        searchList.value = res.data.data;
      })
      .catch((err) => {});
  };

  const searchMyPiece = function (keyword) {
    axios({
      url: `${import.meta.env.VITE_REST_PIECE_API}/search/my/${keyword}`,
      method: "GET",
    })
      .then((res) => {
        mySearchList.value = res.data.data;
      })
      .catch((err) => {});
  };

  return {
    // state
    searchList,
    mySearchList,
    // getter
    getSearchList,
    getMySearchList,
    // action
    searchPiece,
    searchMyPiece,
  };
});
