<!-- ToDo : 값이 0이면 표시 x -->
<!-- ToDo : 값이 없으면 차트도 보여주지 말고 소비한 내용이 없어요 띄우기 -->
<template>
    <div class="consumestatisticsview-main-title">조각으로 분석한 소비 통계예요</div>
    <YearSelector @year-changed="showBarChartData" />
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

<script setup>
import { useStatisticsStore } from '@/stores/statistics';
import YearSelector from "@/components/chart/YearSelector.vue";
import BarChart from "@/components/chart/BarChart.vue";

const statisticsStore = useStatisticsStore();
const { chartData, noData, updateData } = statisticsStore;

const showBarChartData = async (year) => {
    try {
        // console.log('selected year : ', year);
        await updateData(year);
    } catch (error) {
        console.error("지출 내역 불러오기 실패", error);
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
