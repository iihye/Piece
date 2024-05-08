<template>
    <div class="piecestatisticsview-main-title">어떤 조각을 완성했을까요?</div>
        <year-selector @year-changed="updateData" />
    <pie-chart :data="chartData" />
</template>

<script>
import YearSelector from '@/components/chart/YearSelector.vue'
import PieChart from '@/components/chart/PieChart.vue'

export default {
    components: {
        YearSelector,
        PieChart
    },
    data() {
        return {
            chartData: {
                labels: ["영화", "연극/뮤지컬", "콘서트", "기타"],
                datasets: [{
                    backgroundColor: ["#b094ff", "#76aacd", "#efae6d", "#ff9494"],
                    data: [4, 5, 3, 5]
                }]
            }
        };
    },
    methods: {
        updateData(year) {
            // API 호출 또는 데이터 업데이트 로직 추가
            console.log('Updated year to:', year);
            // 예시 데이터 업데이트 로직
            this.chartData.datasets[0].data = this.chartData.datasets[0].data.map(v => v + 1);
        }
    }
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
</style>
