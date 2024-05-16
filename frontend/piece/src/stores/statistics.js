import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useStatisticsStore = defineStore('statistics', () => {
    const chartData = ref({
        labels: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
        datasets: [{
            backgroundColor: "#ffe3e1",
            data: Array(12).fill(0)
        }]
    });
    const noData = ref(false);

    const updateData = async (consumptionYear) => {
        try {
            console.log('enter useStatisticStore');
            const response = await axios.get(`${import.meta.env.VITE_REST_PIECE_API}/statistics/consumption/${consumptionYear}`);
            const responseData = response.data;
            console.log('responseData: ', responseData);

            if (responseData.code === "FIND_CONSUMPTIONS_SUCCESS" && responseData.data.length > 0) {
                noData.value = false;

                const monthlyData = Array(12).fill(0);

                responseData.data.forEach(item => {
                    monthlyData[item.consumptionMonth - 1] = item.consumptionMoney;
                });

                chartData.value.datasets[0].data = monthlyData;
            } else {
                noData.value = true;
                chartData.value.datasets[0].data.fill(0);
            }
        } catch (error) {
            console.error('Error fetching consumption data:', error);
            noData.value = true;
            chartData.value.datasets[0].data.fill(0);
        }
    };

    return {
        chartData,
        noData,
        updateData
    };
});