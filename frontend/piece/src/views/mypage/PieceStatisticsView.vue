<template>
    <div class="piecestatisticsview-main-title">어떤 조각을 완성했을까요?</div>
    <YearSelector class="piecestatisticsview-year-selector" @year-changed="showPieChartData" />
    <PieChart :chartData="chartData" />
    <hr />
    <div>
        <div v-for="(amount, index) in chartData.datasets[0].data" :key="index" class="monthly-consumption">
            <div v-if="amount != 0" class="consumption-row">
                <div class="color-box" :style="{ backgroundColor: chartData.datasets[0].backgroundColor[index] }"></div>
                <span class="label">{{ chartData.labels[index] }}</span>
                <span class="amount">{{ amount.toLocaleString() }}회</span>
            </div>
        </div>
    </div>
    <div v-if="noData" class="piecestatisticsview-main-content-container">
        <div class="piecestatisticsview-main-content">
            소비한 내용이 없어요 ㅜㅜ
        </div>
        <div class="button-container">
            <RouterLink :to="{ name: 'piecemake' }">
                <SmallButton smallButtonContent="조각 만들기" />
            </RouterLink>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { usePieceStatisticsStore } from '@/stores/piecestatistics';
import YearSelector from "@/components/chart/YearSelector.vue";
import PieChart from "@/components/chart/PieChart.vue";
import { useCommonStore } from "@/stores/common";
import SmallButton from "@/components/button/SmallButton.vue";

const pieceStatisticsStore = usePieceStatisticsStore();
const commonStore = useCommonStore();
const { chartData, updateData } = pieceStatisticsStore;

const noData = computed(() => pieceStatisticsStore.getNoData);
const currentYear = new Date().getFullYear();
const year = ref(currentYear);

const showPieChartData = async (selectedYear) => {
    try {
        await updateData(selectedYear);
    } catch (error) {
        console.error("조각 내역 불러오기 실패", error);
    }
};

onMounted(async () => {
    commonStore.headerTitle = "조각 통계";
    commonStore.headerType = "header2";

    await updateData(year.value);
});
</script>

<style>
.piecestatisticsview-main-title {
    font-family: "Bold";
    font-size: 1.6rem;
    color: var(--black-color);
    margin: 0 0 2rem 0;
    user-select: none;
}

.piecestatisticsview-year-selector {
    margin-bottom: 1rem;
}

.amount {
    white-space: nowrap;
}

.piecestatisticsview-main-content-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100%;
    text-align: center;
}

.piecestatisticsview-main-content {
    font-size: 1rem;
    color: var(--gray2-color);
    margin-bottom: 1rem;
    display: flex;
    justify-content: center;
    align-items: center;
    user-select: none;
}

.button-container {
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
}

.button-container button:hover {
    cursor: pointer;
}

.monthly-consumption .consumption-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    margin-bottom: 10px;
}

.color-box {
    width: 24px;
    height: 24px;
    border-radius: 50%;
    margin-right: 10px;
}

.label {
    flex-grow: 1;
}
</style>
