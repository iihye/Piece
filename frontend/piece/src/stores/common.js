import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useCommonStore = defineStore("common", () => {
    //  =========== STATE ===============

    const headerType = ref("header1");
    const headerTitle = ref("piece");
    const headerChatName = ref("");
    const headerChatImg = ref("");
    const headerChatCount = ref(0);
    const isLogin = ref(false);
    const loginUser = ref("");
    const loginUserInfo = ref({});
    const loginUserLabel = ref("");

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

    const getIsLogin = computed(() => {
        return isLogin.value;
    });

    const getLoginUser = computed(() => {
        return loginUser.value;
    });

    const getLoginUserInfo = computed(() => {
        return loginUserInfo.value;
    });

    const getLoginUserLabel = computed(() => {
        return loginUserLabel.value;
    });

    // =========== ACTION ===============

    const findLoginUserInfo = function () {
        const userId = localStorage.getItem("userId");
        axios({
            url: `${import.meta.env.VITE_REST_USER_API}/users/find/${userId}`,
            method: "GET",
        })
            .then((res) => {
                loginUserInfo.value = res.data;
                if (loginUserInfo.value.labelId !== null) {
                    findUserLabel();
                } else {
                    loginUserLabel.value = "";
                }
            })
            .catch((err) => {});
    };

    const findUserLabel = function () {
        const userId = localStorage.getItem("userId");
        axios({
            url: `${import.meta.env.VITE_REST_PIECE_API}/labels/${
                loginUserInfo.value.labelId
            }`,
            method: "GET",
        })
            .then((res) => {
                loginUserLabel.value = res.data.data;
            })
            .catch((err) => {});
    };

    return {
        // state
        headerType,
        headerTitle,
        headerChatCount,
        headerChatName,
        headerChatImg,
        isLogin,
        loginUser,
        loginUserInfo,
        loginUserLabel,
        // getter
        getHeaderType,
        getHeaderTitle,
        getHeaderChatName,
        getHeaderChatImg,
        getHeaderChatCount,
        getIsLogin,
        getLoginUser,
        getLoginUserInfo,
        getLoginUserLabel,
        // action
        findLoginUserInfo,
        findUserLabel,
    };
});
