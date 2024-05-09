import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const usePieceMakeStore = defineStore("piecemake", () => {
  // =========== STATE ===============

  const selectedLayout = ref("");

  // =========== GETTER ===============

  const getSelectedLayout = computed(() => {
    return selectedLayout.value;
  });

  // =========== ACTION ===============

  return {
    // state
    selectedLayout,
    // getter
    getSelectedLayout,
    // action
  };
});
