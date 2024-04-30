import { ref, computed } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

export const useUserStore = defineStore(
    "user",
    () => {
        // =========== STATE ===============

        const mypageLabelList = ref({});
        const mypageLabelWear = ref({});
        const mypageLabelWearoff = ref();

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

        // =========== ACTION ===============

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
                url: `${
                    import.meta.env.VITE_REST_PIECE_API
                }/mylabels/${labelId}`,
                method: "PUT",
            })
                .then((res) => {
                    findMypageLabelList();
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
                })
                .catch((err) => {});
        };

        const checkMypageLabelWear = function () {
            setMypagelabelWearoff(
                Object.values(mypageLabelList.value).every(
                    (item) => !item.wearLabels
                )
            );
        };

        return {
            // state
            mypageLabelList,
            mypageLabelWear,
            mypageLabelWearoff,
            // getter
            getMypageLabelList,
            getMypageLabelWear,
            getMypageLabelWearoff,
            setMypagelabelWearoff,
            // action
            findMyPageLabelList: findMypageLabelList,
            addMypageLabelWear,
            deleteMypageLabelWear,
            checkMypageLabelWear,
        };
    },
    { persist: true }
);
