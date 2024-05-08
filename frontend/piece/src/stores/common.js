import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useCommonStore = defineStore("common", () => {
  //  =========== STATE ===============

  const headerType = ref("header1");
  const headerTitle = ref("piece");
  const headerChatName = ref("");
  const headerChatImg = ref("");
  const headerChatCount = ref(0);

  // =========== GETTER ===============

  const getHeaderType = computed(() => {
    console.log("commonStore" + headerType.value);
    return headerType.value;
  });

  const getHeaderTitle = computed(() => {
    console.log("commonStore" + headerTitle.value);
    return headerTitle.value;
  });

  const getHeaderChatName = computed(() => {
    console.log("commonStore" + headerChatName.value);
    return headerChatName.value;
  });

  const getHeaderChatImg = computed(() => {
    console.log("commonStore" + headerChatImg.value);
    return headerChatImg.value;
  });

  const getHeaderChatCount = computed(() => {
    console.log("commonStore" + headerChatCount.value);
    return headerChatCount.value;
  });

  // =========== ACTION ===============

  return {
    headerType,
    headerTitle,
    headerChatCount,
    headerChatName,
    headerChatImg,
    getHeaderType,
    getHeaderTitle,
    getHeaderChatName,
    getHeaderChatImg,
    getHeaderChatCount,
  };
});
