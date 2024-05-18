import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useCakeStore = defineStore('cake', () => {
    const selectedOptionCakeList = ref("ALL");
    const cakeList = ref([]);
    const cakeListFiltered = ref([]);
    const isLoading = ref(false);
    const nextPageUrl = ref(null);

    const getCakeList = computed(() => cakeList.value);
    const getCakeListFiltered = computed(() => cakeListFiltered.value);
    const getSelectOptionCakeList = computed(() => selectedOptionCakeList.value);

    const setSelectOptionCakeList = (option) => {
        selectedOptionCakeList.value = option;
    };

    // 랜덤으로 보여주기
    const shuffleArray = (array) => {
        for (let i = array.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [array[i], array[j]] = [array[j], array[i]];
        }
        return array;
    };

    const findCakeList = async (cultureType, pageSize) => {
        if (cultureType === "ALL") {
            cultureType = null;
        }

        // 무한스크롤
        let url = `${import.meta.env.VITE_REST_PIECE_API}/cultures?pageSize=${pageSize}&cultureType`;

        if (cultureType) {
            url += `=${cultureType}`;
        }

        isLoading.value = true;
        try {
            const res = await axios.get(url);
            cakeList.value = shuffleArray(res.data.data.dataList);
            nextPageUrl.value = res.data.data.nextPage;
            cakeListFiltered.value = computedFilteredCakeList();
        } catch (err) {
            console.error(err);
        } finally {
            isLoading.value = false;
        }
    };

    const loadMoreCakes = async () => {
        if (!nextPageUrl.value || isLoading.value) return;
        
        isLoading.value = true;
        try {
            const res = await axios.get(nextPageUrl.value);
            cakeList.value = [...cakeList.value, ...shuffleArray(res.data.data.dataList)];
            nextPageUrl.value = res.data.data.nextPage;
            cakeListFiltered.value = computedFilteredCakeList();
        } catch (err) {
            console.error(err);
        } finally {
            isLoading.value = false;
        }
    };

    function computedFilteredCakeList() {
        if (selectedOptionCakeList.value === "ALL") {
            return cakeList.value;
        } else {
            return cakeList.value.filter((item) => item.cultureType === selectedOptionCakeList.value);
        }
    }

    return {
        selectedOptionCakeList,
        cakeList,
        cakeListFiltered,
        isLoading,
        getCakeList,
        getCakeListFiltered,
        getSelectOptionCakeList,
        setSelectOptionCakeList,
        findCakeList,
        loadMoreCakes,
    };
});
