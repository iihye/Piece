<!-- ToDo : 값이 0이면 표시 x -->
<!-- ToDo : 값이 없으면 차트도 보여주지 말고 소비한 내용이 없어요 띄우기 -->
<template>
    <div class="consumestatisticsview-main-title">조각으로 분석한 소비 통계예요</div>
    <YearSelector @year-changed="updateData" />
    <BarChart :chartData="chartData" />
    <hr />
    <div v-for="(amount, index) in chartData.datasets[0].data" :key="index" class="monthly-consumption">
        <span>{{ chartData.labels[index] }}</span>
        <span class="amount">{{ amount.toLocaleString() }}원</span>
    </div>
    <div v-if="noData" class="consumestatisticsview-main-content">소비한 내용이 없어요 ㅜㅜ
        <RouterLink :to="{ name: 'piecemake' }">조각 만들러 가기</RouterLink>
    </div>
</template>

<!-- <template>
    <div class="consumestatisticsview-main-title">조각으로 분석한 소비 통계예요</div>
    <YearSelector @year-changed="updateData" />
    <BarChart :chartData="chartData" />
    <hr />
    <div class="consumestatisticsview-main-content">
        <NoItem v-if="noData" content="소비한 내용이 없어요 ㅜㅜ"  />
        <RouterLink :to="{ name: 'piecemake' }">조각 만들러 가기</RouterLink>
    </div>
    <div v-for="(amount, index) in chartData.datasets[0].data" :key="index" class="monthly-consumption">
        <span>{{ chartData.labels[index] }}</span>
        <span class="amount">{{ amount.toLocaleString() }}원</span>
    </div>
</template> -->


<script setup>
import { ref } from 'vue';
import axios from 'axios';
import YearSelector from "@/components/chart/YearSelector.vue";
import BarChart from "@/components/chart/BarChart.vue";

const chartData = ref({
    labels: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
    datasets: [{
        backgroundColor: "#ffe3e1",
        data: Array(12).fill(0)  // 초기 데이터를 0으로 설정
    }]
});
const noData = ref(false);

const updateData = async (consumptionYear) => {
    try {
        const response = await axios.get(`http://localhost:8000/api/piece/statistics/consumption/${consumptionYear}`);
        const responseData = response.data;

        if (responseData.code === "FIND_CONSUMPTIONS_SUCCESS" && responseData.data.length > 0) {
            noData.value = false;

            const monthlyData = Array(12).fill(0);

            responseData.data.forEach(item => {
                monthlyData[item.consumptionMonth - 1] = item.consumptionMoney;
            });

            chartData.value.datasets[0].data = monthlyData;
        } else {
            // 데이터가 없는 경우
            noData.value = true;
            chartData.value.datasets[0].data.fill(0); // 데이터가 없으면 0
        }
    } catch (error) {
        console.error('Error fetching consumption data:', error);
        noData.value = true;
        chartData.value.datasets[0].data.fill(0); // 에러 발생 시 0
    }
};

</script>



<style scoped>
.consumestatisticsview-main-title {
    font-size: 1.6rem;
    color: var(--black-color);
    margin-bottom: 2rem;
    user-select: none;
}

.monthly-consumption {
    display: flex;
    justify-content: space-between;
    font-size: 1rem;
    margin-bottom: 0.5rem;
}

.amount {
    margin-left: auto;
}

.consumestatisticsview-main-content {
    font-size: 1rem;
    color: var(--gray2-color);
    margin-bottom: 1rem;
    display: flex;
    justify-content: left;
    align-items: center;
    user-select: none;
}
</style>
