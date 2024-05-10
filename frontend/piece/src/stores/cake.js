import { ref, reactive, computed } from "vue";
import { defineStore } from "pinia";
import router from "@/router";
import axios from "axios";

export const useCakeStore = defineStore(
    "cake",
    () => {
        // =========== STATE ===============
        const selectedOptionCakeList = ref("ALL");
        const cakeList = ref({});
        const cakeListFiltered = ref({});

        // =========== GETTER ===============
        const getSelectOptionCakeList = computed(
            () => selectedOptionCakeList.value
        );

        const setSelectOptionCakeList = (option) => {
            selectedOptionCakeList.value = option;
        };

        const getCakeList = computed(() => cakeList.value);

        const getCakeListFiltered = computed(() => cakeListFiltered.value);

        // =========== ACTION ===============
        const findCakeList = function (cultureType, pageSize) {
            if (cultureType === "ALL") {
                cultureType = null;
            }

            let url = `${
                import.meta.env.VITE_REST_PIECE_API
            }/cultures?pageSize=${pageSize}&cultureType`;

            if (cultureType) {
                url += `=${cultureType}`;
            }

            axios({
                url: url,
            })
                .then((res) => {
                    cakeList.value = res.data.data.dataList;
                    const nextpage = res.data.data.nextPage;
                    cakeListFiltered.value = computedFilteredCakeList();
                })
                .catch((err) => {});
        };

        function computedFilteredCakeList() {
            if (selectedOptionCakeList.value === "ALL") {
                return cakeList.value;
            } else if (selectedOptionCakeList.value === "MOVIE") {
                return cakeList.value.filter(
                    (item) => item.cultureType === "MOVIE"
                );
            } else if (selectedOptionCakeList.value === "THEATER") {
                return cakeList.value.filter(
                    (item) => item.cultureType === "THEATER"
                );
            } else if (selectedOptionCakeList.value === "MUSICAL") {
                return cakeList.value.filter(
                    (item) => item.cultureType === "MUSICAL"
                );
            } else if (selectedOptionCakeList.value === "CONCERT") {
                return cakeList.value.filter(
                    (item) => item.cultureType === "CONCERT"
                );
            } else if (selectedOptionCakeList.value === "ETC") {
                return cakeList.value.filter(
                    (item) => item.cultureType === "ETC"
                );
            } else {
                return [];
            }
        }

        return {
            // state
            selectedOptionCakeList,
            cakeList,
            cakeListFiltered,
            // getter
            getSelectOptionCakeList,
            setSelectOptionCakeList,
            getCakeList,
            getCakeListFiltered,
            // action
            findCakeList,
            computedFilteredCakeList,
        };
    },
    { persist: true }
);
