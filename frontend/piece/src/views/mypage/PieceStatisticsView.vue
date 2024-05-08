<template>
    <div class="piecestatisticsview-main-title">어떤 조각을 완성했을까요?</div>
    <year-selector @year-changed="updateData" />
    <!-- <div class="piecestatisticsview-main-content">김조각님은 17개 조각을 완성했어요</div> -->
    <pie-chart :data="chartData" />

    <!-- 조각이 없을 경우 -->
    <div v-if="noData" class="consumestatisticsview-main-content">완성된 조각이 없어요 ㅜㅜ
        <RouterLink :to="{ name: 'piecemake' }">조각 만들러 가기</RouterLink>
    </div>
</template>

<script>
import YearSelector from '@/components/chart/YearSelector.vue'
import PieChart from '@/components/chart/PieChart.vue'
import SmallButton from '@/components/button/SmallButton.vue'

export default {
    components: {
        YearSelector,
        PieChart
    },
    data() {
        return {
            noData: false,
            chartData: {
                labels: ["영화", "연극/뮤지컬", "콘서트", "기타"],
                datasets: [{
                    backgroundColor: ["#b094ff", "#76aacd", "#efae6d", "#ff9494"],
                    data: [4, 5, 3, 5]
                }]
            }
        };
    },
}
</script>

<style>
.piecestatisticsview-main-title {
    font-family: "Semi";
    font-size: 1.4rem;
    color: var(--black-color);
    margin-bottom: 0.6rem;
    user-select: none;
}

.piecestatisticsview-main-content {
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
</style>