<template>
    <div class="consumestatisticsview-main-title">조각으로 분석한 소비 통계예요</div>
    <YearSelector @year-changed="showBarChartData" />
    <BarChart :chartData="chartData" />
    <hr />
    <div v-for="(amount, index) in chartData.datasets[0].data" :key="index" class="monthly-consumption">
        <div v-if="amount.toLocaleString() != 0" class="consumption-row">
            <span class="label">{{ chartData.labels[index] }}</span>
            <span class="amount">{{ amount.toLocaleString() }}원</span>
        </div>
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
        await updateData(year);
    } catch (error) {
        // console.error("지출 내역 불러오기 실패", error);
    }
};
</script>


<style>
.consumestatisticsview-main-title {
    font-size: 1.6rem;
    color: var(--black-color);
    margin-bottom: 2rem;
    user-select: none;
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

.monthly-consumption .consumption-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 8px 0;
}

.monthly-consumption .label {
    flex-grow: 0;
    flex-shrink: 0;
}

.monthly-consumption .amount {
    flex-grow: 0;
    flex-shrink: 0;
    text-align: right;
}
</style>
