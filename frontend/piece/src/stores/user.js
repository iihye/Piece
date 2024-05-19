import { ref, computed } from "vue";
import { defineStore } from "pinia";
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
        const heartStates = ref({});
        const isTutorial = ref(false);

        // =========== GETTER ===============
        const getMypageLabelList = computed(() => mypageLabelList.value);
        const getMypageLabelWear = computed(() => mypageLabelWear.value);
        const getMypageLabelWearoff = computed(() => mypageLabelWearoff.value);
        const getNicknameMessage = computed(() => nicknameMessage.value);
        const getIsNickname = computed(() => isNickname.value);
        const getHeartState = (cultureId) => heartStates.value[cultureId];
        const getIsTutorial = computed(() => {
            return isTutorial.value;
        });

        // =========== ACTION ===============
        const setMypagelabelWearoff = (data) => {
            mypageLabelWearoff.value = data;
        };

        const setNicknameMessage = (data) => {
            nicknameMessage.value = data;
        };

        const setIsNickname = (data) => {
            isNickname.value = data;
        };

        const setHeartState = (cultureId, state) => {
            heartStates.value[cultureId] = state;
        };

        const checkMypageLabelList = () => {
            axios.get(`${import.meta.env.VITE_REST_PIECE_API}/mylabels/check`)
            .then(() => findMypageLabelList())
            .catch((err) => {});
        };

        const findMypageLabelList = () => {
            axios.get(`${import.meta.env.VITE_REST_PIECE_API}/mylabels`)
            .then((res) => {
            mypageLabelList.value = res.data.data;
            checkMypageLabelWear();
            })
            .catch((err) => {});
        };

        const addMypageLabelWear = (labelId) => {
            axios.put(`${import.meta.env.VITE_REST_PIECE_API}/mylabels/${labelId}`)
            .then(() => {
                findMypageLabelList();
                commonStore.findLoginUserInfo();
            })
            .catch((err) => {});
        };

        const deleteMypageLabelWear = () => {
            axios.put(`${import.meta.env.VITE_REST_PIECE_API}/mylabels/wearoff`)
            .then(() => {
                findMypageLabelList();
                commonStore.findLoginUserInfo();
            })
            .catch((err) => {});
        };  

        const checkMypageLabelWear = () => {
            setMypagelabelWearoff(
                Object.values(mypageLabelList.value).every((item) => !item.wearLabels)
            );
        };

        const changeMypageNickname = (userId, nickname) => {
            axios.put(`${import.meta.env.VITE_REST_USER_API}/users/${userId}/nickname`, { newNickname: nickname })
            .catch((err) => {});
        };

        const changeMypagePassword = (userId, password) => {
            axios.put(`${import.meta.env.VITE_REST_USER_API}/users/${userId}/password`, { newPassword: password })
            .catch((err) => {});
        };

        const checkNickname = (nickname) => {
            axios.get(`${import.meta.env.VITE_REST_USER_API}/users/check-nickname?nickname=${nickname}`)
            .then((res) => {
                nicknameMessage.value = res.data.message;
                isNickname.value = res.data.code === "CHECK_NICKNAME_GOOD";
            })
            .catch((err) => {});
        };

        const checkTutorial = () => {
            axios.put(`${import.meta.env.VITE_REST_USER_API}/users/tutorial`)
            .catch((err) => {});
        };

        async function updateProfileImage(s3path) {
            try {
                console.log("Sending s3path as body:", s3path);
                await axios.put(`${import.meta.env.VITE_REST_USER_API}/users/save/profileImage`, { s3path });
                console.log("파일경로 저장 성공!");
            } catch (error) {
                console.error("파일경로 저장 실패", error.message);
            }
            }
            
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
            heartStates,
            isTutorial,
            // getter
            getMypageLabelList,
            getMypageLabelWear,
            getMypageLabelWearoff,
            setMypagelabelWearoff,
            getNicknameMessage,
            setNicknameMessage,
            getIsNickname,
            setIsNickname,
            getHeartState,
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
            updateProfileImage,
            setHeartState,
            readTutorial,
        };
    },
    { persist: true }
);
