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

        // =========== GETTER ===============

        const getMypageLabelList = computed(() => {
            return mypageLabelList.value;
        });

        const getMypageLabelWear = computed(() => {
            return mypageLabelWear.value;
        });

        // =========== ACTION ===============

        const findMypageLabelList = function () {
            axios({
                url: `${import.meta.env.VITE_REST_PIECE_API}/mylabels`,
                method: "GET",
            })
                .then((res) => {
                    mypageLabelList.value = res.data.data;
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

        return {
            // state
            mypageLabelList,
            mypageLabelWear,
            // getter
            getMypageLabelList,
            getMypageLabelWear,
            // action
            findMyPageLabelList: findMypageLabelList,
            addMypageLabelWear,
        };
    },
    { persist: true }
);
