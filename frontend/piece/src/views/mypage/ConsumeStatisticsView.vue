<template>
    <div class="consumestatisticsmain">
        <div class="consumestatisticsmain-title">조각으로 분석한 소비 통계예요</div>

        <div class="year-selector">
        </div>

        <pie-chart v-if="hasData" :data="chartData" />
        <div v-else class="no-data">소비한 내용이 없어요 (그림)</div>

        <div class="total-spent">김조각님은 1,563,000원을 소비했어요</div>

        <div v-for="(expense, month) in monthlyExpenses" :key="month" class="monthly-expense">
            {{ month }}월 {{ expense.toLocaleString() }}원
        </div>
    </div>
</template>

<script setup>

</script>

<style>
.consumestatisticsmain-title {
    font-size: 1rem;
    color: var(--black-color);
}

</style>
