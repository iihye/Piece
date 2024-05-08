<template>
    <div class="consumestatisticsview-main-title">조각으로 분석한 소비</div>
    <year-selector @year-changed="updateData" />
    <pie-chart :data="chartData" />
</template>

<script>
import YearSelector from '@/components/chart/YearSelector.vue'
import BarChart from '@/components/chart/BarChart.vue';

export default {
    components: {
        YearSelector,
        BarChart
    },
    data() {
        return {
            chartData: {
                labels: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
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
