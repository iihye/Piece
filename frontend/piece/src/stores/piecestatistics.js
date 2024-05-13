// import { defineStore } from 'pinia';
// import { localAxios } from '@/stores/localaxios';

// const axiosInstance = localAxios(import.meta.env.VITE_REST_PIECE_API);

// export const usePieceStatisticsStore = defineStore('pieceStatisticsStore', {
//     state: () => ({
//         statisticsData: null,
//         noData: true,
//     }),
//     getters: {
//         getChartData: (state) => {
//             if (!state.statisticsData) return null;
//             return {
//                 labels: ["영화", "연극/뮤지컬", "콘서트", "기타", "극장"],
//                 datasets: [{
//                     backgroundColor: ["#b094ff", "#efae6d", "#ff9494", "#c4c4c4", "#76aacd"],
//                     data: [
//                         state.statisticsData.movieNumber,
//                         state.statisticsData.musicalNumber,
//                         state.statisticsData.concertNumber,
//                         state.statisticsData.etcNumber,
//                         state.statisticsData.theaterNumber,
//                     ],
//                 }],
//             };
//         }
//     },
//     actions: {
//         async fetchStatistics(viewYear) {
//             this.noData = true;
//             try {
//                 const response = await axiosInstance.get(`/statistics/view/${viewYear}`);
//                 if (response.data.code === "FIND_VIEW_STATISTICS_SUCCESS" && response.data.data) {
//                     this.statisticsData = response.data.data;
//                     this.noData = Object.values(response.data.data).every(x => x === 0);
//                 }
//             } catch (error) {
//                 console.error('Error fetching statistics:', error);
//             }
//         }
//     }
// });

// import { defineStore } from 'pinia';
// import { localAxios } from '@/stores/localaxios';

// export const usePieceStatisticsStore = defineStore('pieceStatistics', {
//   state: () => ({
//     statisticsData: null,
//     noData: true,
//   }),
//   actions: {
//     async fetchStatistics(viewYear) {
//       try {
//         const response = await axios.get(`http://localhost:8000/api/piece/statistics/view/${viewYear}`);
//         if (response.data.code === "FIND_VIEW_STATISTICS_SUCCESS" && response.data.data) {
//           this.statisticsData = response.data.data;
//           this.noData = false;
//         } else {
//           this.statisticsData = null;
//           this.noData = true;
//         }
//       } catch (error) {
//         console.error('Error fetching statistics:', error);
//         this.statisticsData = null;
//         this.noData = true;
//       }
//     }
//   }
// });

// stores/pieceStatistics.js
import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

const baseURL = import.meta.env.VITE_REST_PIECE_API;
console.log('baseURL is ', baseURL);

export const usePieceStatisticsStore = defineStore('piecestatistics', {
    // =========== STATE ===============
    state: () => ({
        statisticsData: {},
        noData: true,
        }),

    
    // =========== ACTION ===============
    actions: {
        async fetchStatistics(viewYear) {
            try {
                console.log('get 요청 : ', `${baseURL}/statistics/view/${viewYear}`);
                const response = await axios.get(`${baseURL}/statistics/view/${viewYear}`);
                console.log('response.data.code: ', response.data.code, ' response.data.data : ', response.data.data);
                if (response.data.code === "FIND_VIEW_STATISTICS_SUCCESS" && Object.keys(response.data.data).length > 0) {
                    this.statisticsData = response.data.data;
                    this.noData = false;
                    console.log('statisticsData: ', this.statisticsData, ' noData: ', this.noData);
                } else {
                    this.statisticsData = {};
                    this.noData = true;
                }
            } catch (error) {
                console.error('에러 :', error);
                this.statisticsData = {};
                this.noData = true;
            }
        }
    }
});