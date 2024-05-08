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
        return headerType.value;
    });

    const getHeaderTitle = computed(() => {
        return headerTitle.value;
    });

    const getHeaderChatName = computed(() => {
        return headerChatName.value;
    });

    const getHeaderChatImg = computed(() => {
        return headerChatImg.value;
    });

    const getHeaderChatCount = computed(() => {
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
