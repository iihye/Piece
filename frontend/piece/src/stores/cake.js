import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useCakeStore = defineStore('cake', () => {
    const selectedOptionCakeList = ref("ALL");
    const cakeList = ref([]);
    const cakeListFiltered = ref([]);
    const isLoading = ref(false);
    const nextPageUrl = ref(null);
    const selectedMovie = ref(null);

    const getCakeList = computed(() => cakeList.value);
    const getCakeListFiltered = computed(() => cakeListFiltered.value);
    const getSelectOptionCakeList = computed(() => selectedOptionCakeList.value);
    const getSelectedMovie = computed(() => selectedMovie.value);

    const setSelectOptionCakeList = (option) => {
        selectedOptionCakeList.value = option;
    };

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

        let url = `${import.meta.env.VITE_REST_PIECE_API}/cultures?pageSize=${pageSize}&cultureType`;

        if (cultureType) {
            url += `=${cultureType}`;
        }

        isLoading.value = true;
        try {
            const res = await axios.get(url);
            if (shuffle) {
                cakeList.value = shuffleArray(res.data.data.dataList);
            } else {
                cakeList.value = res.data.data.dataList;
            }
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

    const fetchMovieDetails = async (movieId) => {
        try {
            const res = await axios.get(`${import.meta.env.VITE_REST_PIECE_API}/cultures/tmdb/${movieId}`);
            if (res.data.code === "FIND_TMDB_CULTURE_SUCCESS") {
                selectedMovie.value = res.data.data;
            }
        } catch (err) {
            console.error("Failed to fetch movie details", err);
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
        nextPageUrl,
        selectedMovie,
        getCakeList,
        getCakeListFiltered,
        getSelectOptionCakeList,
        getSelectedMovie,
        setSelectOptionCakeList,
        findCakeList,
        loadMoreCakes,
        fetchMovieDetails,
    };
});
