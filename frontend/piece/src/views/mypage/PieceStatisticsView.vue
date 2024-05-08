<template>
    <div class="piecestatisticsview-main-title">어떤 조각을 완성했을까요?</div>
    <year-selector
        class="piecestatisticsview-year-selector"
        @year-changed="updateData"
    />
    <!-- <div class="piecestatisticsview-main-content">김조각님은 17개 조각을 완성했어요</div> -->
    <pie-chart :data="chartData" />

    <!-- 조각이 없을 경우 -->
    <Noitem :content="'완성된 조각이 없어요'"> </Noitem>
    <div v-if="noData" class="consumestatisticsview-main-content">
        완성된 조각이 없어요 ㅜㅜ
        <RouterLink :to="{ name: 'piecemake' }">조각 만들러 가기</RouterLink>
    </div>
</template>

<script>
import YearSelector from "@/components/chart/YearSelector.vue";
import PieChart from "@/components/chart/PieChart.vue";
import SmallButton from "@/components/button/SmallButton.vue";
import NoItem from "@/components/item/NoItem.vue";

export default {
    components: {
        YearSelector,
        PieChart,
    },
    data() {
        return {
            noData: false,
            chartData: {
                labels: ["영화", "연극/뮤지컬", "콘서트", "기타"],
                datasets: [
                    {
                        backgroundColor: [
                            "#b094ff",
                            "#76aacd",
                            "#efae6d",
                            "#ff9494",
                        ],
                        data: [4, 5, 3, 5],
                    },
                ],
            },
        };
    },
};
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
