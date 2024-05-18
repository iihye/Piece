import { ref, computed } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";
import { useCommonStore } from "@/stores/common";
import { isTutorialModal } from "@/stores/util";

export const useUserStore = defineStore(
  "user",
  () => {
    const commonStore = useCommonStore();

    // =========== STATE ===============

    const mypageLabelList = ref({});
    const mypageLabelWear = ref({});
    const mypageLabelWearoff = ref();
    const nicknameMessage = ref("3자 이상 10자 이내의 한글, 영문만 가능해요");
    const isNickname = ref(false);
    const isTutorial = ref(false);

    // =========== GETTER ===============

    const getMypageLabelList = computed(() => {
      return mypageLabelList.value;
    });

    const getMypageLabelWear = computed(() => {
      return mypageLabelWear.value;
    });

    const getMypageLabelWearoff = computed(() => {
      return mypageLabelWearoff.value;
    });

    const setMypagelabelWearoff = (data) => {
      mypageLabelWearoff.value = data;
    };

    const getNicknameMessage = computed(() => {
      return nicknameMessage.value;
    });

    const setNicknameMessage = (data) => {
      nicknameMessage.value = data;
    };

    const getIsNickname = computed(() => {
      return isNickname.value;
    });

    const getIsTutorial = computed(() => {
      return isTutorial.value;
    });

    // =========== ACTION ===============

    const checkMypageLabelList = function () {
      axios({
        url: `${import.meta.env.VITE_REST_PIECE_API}/mylabels/check`,
        method: "GET",
      })
        .then((res) => {
          findMypageLabelList();
        })
        .catch((err) => {});
    };

    const findMypageLabelList = function () {
      axios({
        url: `${import.meta.env.VITE_REST_PIECE_API}/mylabels`,
        method: "GET",
      })
        .then((res) => {
          mypageLabelList.value = res.data.data;
          checkMypageLabelWear();
        })
        .catch((err) => {});
    };

    const addMypageLabelWear = function (labelId) {
      axios({
        url: `${import.meta.env.VITE_REST_PIECE_API}/mylabels/${labelId}`,
        method: "PUT",
      })
        .then((res) => {
          findMypageLabelList();
          commonStore.findLoginUserInfo();
        })
        .catch((err) => {});
    };

    const deleteMypageLabelWear = function () {
      axios({
        url: `${import.meta.env.VITE_REST_PIECE_API}/mylabels/wearoff`,
        method: "PUT",
      })
        .then((res) => {
          findMypageLabelList();
          commonStore.findLoginUserInfo();
        })
        .catch((err) => {});
    };

    const checkMypageLabelWear = function () {
      setMypagelabelWearoff(
        Object.values(mypageLabelList.value).every((item) => !item.wearLabels)
      );
    };

    const changeMypageNickname = function (userId, nickname) {
      axios({
        url: `${import.meta.env.VITE_REST_USER_API}/users/${userId}/nickname`,
        method: "PUT",
        data: {
          newNickname: nickname,
        },
      })
        .then((res) => {})
        .catch((err) => {});
    };

    const changeMypagePassword = function (userId, password) {
      axios({
        url: `${import.meta.env.VITE_REST_USER_API}/users/${userId}/password`,
        method: "PUT",
        data: {
          newPassword: password,
        },
      })
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {});
    };

    const checkNickname = function (nickname) {
      axios({
        url: `${
          import.meta.env.VITE_REST_USER_API
        }/users/check-nickname?nickname=${nickname}`,
        method: "GET",
      })
        .then((res) => {
          nicknameMessage.value = res.data.message;
          if (res.data.code === "CHECK_NICKNAME_GOOD") {
            isNickname.value = true;
          } else {
            isNickname.value = false;
          }
        })
        .catch((err) => {});
    };

    const checkTutorial = function () {
      axios({
        url: `${import.meta.env.VITE_REST_USER_API}/users/tutorial`,
        method: "PUT",
      })
        .then((res) => {})
        .catch((err) => {});
    };

    const readTutorial = function () {
      axios({
        url: `${import.meta.env.VITE_REST_USER_API}/users/istutorial`,
        method: "GET",
      })
        .then((res) => {
          isTutorial.value = res.data.data;
          if (isTutorial.value === false) {
            isTutorialModal.value = false;
          } else {
            isTutorialModal.value = true;
          }
        })
        .catch((err) => {});
    };

    return {
      // state
      mypageLabelList,
      mypageLabelWear,
      mypageLabelWearoff,
      nicknameMessage,
      isNickname,
      isTutorial,
      // getter
      getMypageLabelList,
      getMypageLabelWear,
      getMypageLabelWearoff,
      setMypagelabelWearoff,
      getNicknameMessage,
      setNicknameMessage,
      getIsNickname,
      getIsTutorial,
      // action
      checkMypageLabelList,
      findMypageLabelList,
      addMypageLabelWear,
      deleteMypageLabelWear,
      checkMypageLabelWear,
      changeMypageNickname,
      changeMypagePassword,
      checkNickname,
      checkTutorial,
      readTutorial,
    };
  },
  { persist: true }
);
