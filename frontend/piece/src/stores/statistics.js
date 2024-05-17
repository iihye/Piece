import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useStatisticsStore = defineStore('statistics', () => {
    const chartData = ref({
        labels: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
        datasets: [{
            backgroundColor: "#ff9494",
            data: Array(12).fill(0)
        }]
    });
    const noData = ref(false);
    const getNoData = computed(() => {
        return noData.value;
    });

    const updateData = async (consumptionYear) => {
        try {
            const response = await axios.get(`${import.meta.env.VITE_REST_PIECE_API}/statistics/consumption/${consumptionYear}`);
            const responseData = response.data;

            if (responseData.code === "FIND_CONSUMPTIONS_SUCCESS" && responseData.data != null) {
                noData.value = false;
                // console.log('if문 안 nodata: ', noData.value);

                const monthlyData = Array(12).fill(0);

                responseData.data.forEach(item => {
                    monthlyData[item.consumptionMonth - 1] = item.consumptionMoney;
                    // console.log(monthlyData[item.consumptionMonth - 1], item.consumptionMoney);
                });

                chartData.value.datasets[0].data = monthlyData;
            } else {
                noData.value = true;
                // console.log('nodata: ', noData.value);
                chartData.value.datasets[0].data.fill(0);
            }
        } catch (error) {
            // console.error('지출 데이터 받아오기 에러', error);
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