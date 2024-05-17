import { defineStore } from 'pinia';
import axios from 'axios';

export const usePieceStatisticsStore = defineStore('pieceStatistics', {
    state: () => ({
        statisticsData: null,
        noData: true,
        chartData: {
            labels: ["영화", "뮤지컬", "콘서트", "기타", "극장"],
            datasets: [{
                backgroundColor: ["#b094ff", "#efae6d", "#ff9494", "#c4c4c4", "#76aacd"],
                data: [],
                // hoverBackgroundColor: "#fff",
                hoverBorderColor: "#ff4f4f;",
                hoverBorderWidth: 10
            }]
        }
    }),
    actions: {
        async updateData(viewYear) {
            try {
                const response = await axios.get(`${import.meta.env.VITE_REST_PIECE_API}/statistics/view/${viewYear}`);
                if (response.data.code === "FIND_VIEW_STATISTICS_SUCCESS" && response.data.data) {
                    this.statisticsData = response.data.data;
                    this.chartData.datasets[0].data = [
                        this.statisticsData.movieNumber,
                        this.statisticsData.musicalNumber,
                        this.statisticsData.concertNumber,
                        this.statisticsData.etcNumber,
                        this.statisticsData.theaterNumber,
                    ];
                    this.noData = false;
                } else {
                    this.noData = true;
                    this.chartData.datasets[0].data.fill(0);
                }
            } catch (error) {
                console.error('Error fetching statistics:', error);
                this.noData = true;
                this.chartData.datasets[0].data.fill(0);
            }
        }
    }
});