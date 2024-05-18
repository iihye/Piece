import { ref, computed } from "vue";
import { defineStore } from 'pinia';
import axios from 'axios';

export const usePieceStatisticsStore = defineStore('pieceStatistics', () => {
    const chartData = ref({
        labels: ["영화", "뮤지컬", "콘서트", "기타", "극장"],
        datasets: [{
            backgroundColor: ["#b094ff", "#efae6d", "#ff9494", "#c4c4c4", "#76aacd"],
            data: []
        }]
    });

    const noData = ref(false);
    const getNoData = computed(() => noData.value);

    const updateData = async (viewYear) => {
        try {
            const response = await axios.get(`${import.meta.env.VITE_REST_PIECE_API}/statistics/view/${viewYear}`);
            const responseData = response.data;

            if (responseData.code === "FIND_VIEW_STATISTICS_SUCCESS" && responseData.data) {
                const statisticsData = responseData.data;

                // 모든 값이 0인지 확인
                const allZero = Object.values(statisticsData).slice(1).every(value => value === 0);

                if (allZero) {
                    noData.value = true;
                    chartData.value.datasets[0].data.fill(0);
                } else {
                    noData.value = false;
                    chartData.value.datasets[0].data = [
                        statisticsData.movieNumber,
                        statisticsData.musicalNumber,
                        statisticsData.concertNumber,
                        statisticsData.etcNumber,
                        statisticsData.theaterNumber
                    ];
                }
            } else {
                noData.value = true;
                chartData.value.datasets[0].data.fill(0);
            }
        } catch (error) {
            console.error('조각 내역 받아오기 에러 ', error);
            noData.value = true;
            chartData.value.datasets[0].data.fill(0);
        }
    };

    return {
        chartData,
        noData,
        updateData,
        getNoData
    };
});
