import { ref, computed } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

export const useUserStore = defineStore(
    "user",
    () => {
        // =========== STATE ===============

        const mypageLabelList = ref({});

        // =========== GETTER ===============

        const getMypageLabelList = computed(() => {
            return mypageLabelList.value;
        });

        // =========== ACTION ===============

        const findMyPageLabelList = function () {
            axios({
                url: `${import.meta.env.VITE_REST_PIECE_API}/mylabels`,
                method: "GET",
            })
                .then((res) => {
                    // console.log(res.data.data);
                    mypageLabelList.value = res.data.data;
                    // console.log(myLabelList.value);
                })
                .catch((err) => {});
        };

        return {
            mypageLabelList,
            getMypageLabelList,
            findMyPageLabelList,
        };
    },
    { persist: true }
);
