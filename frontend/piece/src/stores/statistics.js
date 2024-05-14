// import { defineStore } from 'pinia';
// import { localAxios } from '@/stores/localaxios';

// const axiosInstance = localAxios(import.meta.env.VITE_REST_PIECE_API);

// export const useStatisticsStore = defineStore('statistics', {
//     state: () => ({
//         statisticsData: null,
//         noData: true,
//     }),
//     getters: {
//         getStatisticsData: (state) => state.statisticsData,
//         hasNoData: (state) => state.noData
//     },
//     actions: {
//         async fetchStatistics(consumptionYear) {
//             try {
//                 const response = await axiosInstance.get(`/statistics/consumption/${consumptionYear}`);
//                 console.log('get response is : ', response.data.data);
//                 if (response.data.data === "FIND_CONSUMPTIONS_SUCCESS" && response.data.data.length > 0) {
//                     this.statisticsData = response.data.data;
//                     this.noData = false;
//                 } else {
//                     this.statisticsData = null;
//                     this.noData = true;
//                 }
//             } catch (error) {
//                 console.error('Error fetching statistics data:', error);
//                 this.noData = true;
//                 this.statisticsData = null;
//             }
//         }
//     }
// });

// import { defineStore } from 'pinia';
// import { localAxios } from '@/stores/localaxios';

// const axiosInstance = localAxios(import.meta.env.VITE_REST_PIECE_API);

// console.log('statistics is');
// export const useStatisticsStore = defineStore('statistics', {
//     state: () => ({
//         statisticsData: [],
//         noData: true,
//     }),
//     getters: {
//         getStatisticsData: (state) => state.statisticsData,
//         hasNoData: (state) => state.noData
//     },
//     actions: {
//         async fetchStatistics(consumptionYear) {
//             try {
//                 const response = await axiosInstance.get(`/statistics/consumption/${consumptionYear}`);
//                 const responseData = response.data;
//                 console.log('getStatisticsData is : ', state.statisticsData, ' hasNoData', state.noData);
//                 console.log('responseData is ', response.data);

//                 if (responseData.code === "FIND_CONSUMPTIONS_SUCCESS" && responseData.data.length > 0) {
//                     noData.value = false;
        
//                     const monthlyData = Array(12).fill(0);
        
//                     responseData.data.forEach(item => {
//                         monthlyData[item.consumptionMonth - 1] = item.consumptionMoney;
//                     });
        
//                     chartData.value.datasets[0].data = monthlyData;
//                 } else {
//                     // 데이터가 없는 경우
//                     noData.value = true;
//                     chartData.value.datasets[0].data.fill(0); // 데이터가 없으면 0
//                 }
//             } catch (error) {
//                 console.error('Error fetching consumption data:', error);
//                 noData.value = true;
//                 chartData.value.datasets[0].data.fill(0); // 에러 발생 시 0
//             }
//         }
//     }
// });