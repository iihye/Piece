import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useCakeStore = defineStore('cake', () => {
    const selectedOptionCakeList = ref("ALL");
    const cakeList = ref([]);
    const cakeListFiltered = ref([]);
    const selectedMovie = ref({});
    const selectedConcert = ref({});
    const isLoading = ref(false);
    const nextPageUrl = ref(null);

    const getCakeList = computed(() => cakeList.value);
    const getCakeListFiltered = computed(() => cakeListFiltered.value);
    const getSelectOptionCakeList = computed(() => selectedOptionCakeList.value);
    const getSelectedMovie = computed(() => selectedMovie.value);
    const getSelectedConcert = computed(() => selectedConcert.value);

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

    const findCakeList = async (cultureType, pageSize, shuffle = false) => {
        if (cultureType === "ALL") {
            cultureType = null;
        }
    
        let url = `${import.meta.env.VITE_REST_PIECE_API}/cultures?pageSize=${pageSize}`;
        if (cultureType) {
            url += `&cultureType=${cultureType}`;
        }
    
        isLoading.value = true;
        try {
            const res = await axios.get(url);
            cakeList.value = shuffle ? shuffleArray(res.data.data.dataList) : res.data.data.dataList;
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
            cakeList.value = [...cakeList.value, ...res.data.data.dataList];
            nextPageUrl.value = res.data.data.nextPage;
            cakeListFiltered.value = computedFilteredCakeList();
        } catch (err) {
            console.error(err);
        } finally {
            isLoading.value = false;
        }
    };

    const fetchTmdbDetails = async (code) => {
        try {
            const response = await axios.get(`${import.meta.env.VITE_REST_PIECE_API}/cultures/tmdb/${code}`);
            if (response.data.code === "FIND_TMDB_CULTURE_SUCCESS") {
                selectedMovie.value = response.data.data;
            } else {
                console.error('Failed to fetch TMDB details:', response.data);
                selectedMovie.value = null;
            }
        } catch (error) {
            console.error("TMDB 상세정보 가져오기 실패", error);
            selectedMovie.value = null;
            throw error;
        }
    };
    
    const fetchKopisDetails = async (code) => {
        try {
            const response = await axios.get(`${import.meta.env.VITE_REST_PIECE_API}/cultures/kopis/${code}`);
            if (response.data.code === "FIND_KOPIS_CULTURE_SUCCESS") {
                selectedConcert.value = response.data.data;
            } else {
                console.error('Failed to fetch KOPIS details:', response.data);
                selectedConcert.value = null;
            }
        } catch (error) {
            console.error("KOPIS 상세정보 가져오기 실패", error);
            selectedConcert.value = null;
            throw error;
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
        selectedMovie,
        selectedConcert,
        isLoading,
        getCakeList,
        getCakeListFiltered,
        getSelectOptionCakeList,
        getSelectedMovie,
        getSelectedConcert,
        setSelectOptionCakeList,
        findCakeList,
        loadMoreCakes,
        fetchTmdbDetails,
        fetchKopisDetails,
    };
});
