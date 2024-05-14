import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const usePieceMakeStore = defineStore("piecemake", () => {
  // =========== STATE ===============

  const selectedLayout = ref("");
  const backImage = ref("");

  // =========== GETTER ===============

  const getSelectedLayout = computed(() => {
    return selectedLayout.value;
  });
  const getBackImage = computed(() => {
    return backImage.value;
  });

  // =========== ACTION ===============

  const makeImage = function (image) {
    axios({
      url: `${import.meta.env.VITE_REST_PIECE_API}/make`,
      method: "POST",
      data: image,
    })
      .then((res) => {
        backImage.value = res.data.data;
      })
      .catch((err) => {});
  };

  return {
    // state
    selectedLayout,
    backImage,
    // getter
    getSelectedLayout,
    getBackImage,
    // action
    makeImage,
  };
});
