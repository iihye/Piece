<template>
    <div class="consumestatisticsview-main-container">
        <div class="consumestatisticsview-main-title">조각으로 분석한 소비 통계예요</div>
        <YearSelector class="consumestatisticsview-year-selector" @year-changed="showBarChartData" />
        <!-- no data -->
        <div v-if="noData" class="consumestatisticsview-main-content-container">
            <NoItem 
                class="consumestatisticsview-notiem-container" 
                content = "소비한 조각이 없어요">
            </NoItem>
            <div class="button-container">
                <RouterLink :to="{ name: 'piecemake' }">
                    <SmallButton smallButtonContent="조각 만들기"/>
                </RouterLink>
            </div>
        </div>
        <!-- chart -->
        <div v-else>
            <BarChart :chartData="chartData" />
            <hr />
            <div>
                <div v-for="(amount, index) in chartData.datasets[0].data" :key="index" class="monthly-consumption">
                    <div v-if="amount.toLocaleString() != 0" class="consumption-row">
                        <span class="label">{{ chartData.labels[index] }}</span>
                        <span class="amount">{{ amount.toLocaleString() }}원</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>


<script setup>
import { ref, computed, onMounted } from "vue";
import { useStatisticsStore } from '@/stores/statistics';
import YearSelector from "@/components/chart/YearSelector.vue";
import BarChart from "@/components/chart/BarChart.vue";
import { useCommonStore } from "@/stores/common";
import SmallButton from "@/components/button/SmallButton.vue"
import NoItem from "@/components/item/NoItem.vue";

const statisticsStore = useStatisticsStore();
const commonStore = useCommonStore();
const { chartData, updateData } = statisticsStore;

const noData = computed(() => statisticsStore.getNoData);
const currentYear = new Date().getFullYear();
const year = ref(currentYear);


const showBarChartData = async (getYear) => {
    try {
        await updateData(getYear);
    } catch (error) {
        // console.error("지출 내역 불러오기 실패", error);
    }
};

onMounted(async () => {
    commonStore.headerTitle = "소비 통계";
    commonStore.headerType = "header2";

    await updateData(year.value);
});

</script>

<style>
.consumestatisticsview-main-container {
    display: flex;
    flex-direction: column;
    height: calc(100vh - 8.25rem);
    padding-top: 1rem;
    margin: 0 1rem 0 1rem;
}

.consumestatisticsview-main-title {
    font-family: "Bold";
    font-size: 1.6rem;
    color: var(--black-color);
    margin: 0 0 1rem 0;
    user-select: none;
}

.piecestatisticsview-year-selector {
    margin-bottom: 1rem;
}

.amount {
    margin-left: auto;
}

.consumestatisticsview-main-content-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100%;
    text-align: center;
}

.consumestatisticsview-notiem-container{
    display: flex;
    height: 50%;
}

.consumestatisticsview-main-content {
    font-family: "Regular";
    font-size: 1rem;
    color: var(--gray2-color);
    margin: 0;
    margin-bottom: 1rem;
    display: flex;
    justify-content: left;
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
    padding: 8px 0;
}

.monthly-consumption .label {
    flex-grow: 0;
    flex-shrink: 0;
    color: var(--main-color)
}

.monthly-consumption .amount {
    flex-grow: 0;
    flex-shrink: 0;
    text-align: right;
}
</style>
