<template>
    <div class="piecestatisticsview-main-title">어떤 조각을 완성했을까요?</div>
    <year-selector class="piecestatisticsview-year-selector"
    @year-changed="updateChartData" />
    <pie-chart :chartData="chartData" />
    <hr />
  <div>
    <div v-for="(amount, index) in chartData.datasets[0].data" :key="index" class="monthly-consumption">
        <div v-if="amount.toLocaleString() != 0" class="monthly-consumption">
            <div class="color-box" :style="{ backgroundColor: chartData.datasets[0].backgroundColor[index] }"></div>
            <span class="label">{{ chartData.labels[index] }}</span>
            <span class="amount">{{ amount.toLocaleString() }}회</span>
        </div>
    </div>
  </div>
    <div v-if="noData" class="no-data-content">완성된 조각이 없어요
        <RouterLink :to="{ name: 'piecemake' }">조각 만들러 가기</RouterLink>
    </div>
    <div v-else class="statistics-details">
    </div>
  </template>
  
  <script setup>
  import { computed, onMounted } from 'vue';
  import { usePieceStatisticsStore } from '@/stores/piecestatistics';
  import YearSelector from "@/components/chart/YearSelector.vue";
  import PieChart from "@/components/chart/PieChart.vue";
  
  const { chartData, noData, updateData } = usePieceStatisticsStore();

  const updateChartData = (year) => {
      updateData(year);
  };
  
  const displayChartData = computed(() => chartData);
  </script>
  
  <style scoped>
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
  
  .no-data-content {
      color: var(--main-color);
      text-align: center;
      font-size: 18px;
      margin-top: 20px;
  }
  
  .color-box {
      width: 24px;
      height: 24px;
      border-radius: 50%;
      margin-right: 10px;
  }
  
  .label-text {
      flex-grow: 1;
      text-align: left;
      font-family: "Medium";
      font-size: 1rem;
  }
  
  .label-count {
      white-space: nowrap;
      font-family: "Regular";
      font-size: 1rem;
  }

  .monthly-consumption {
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

.amount {
    white-space: nowrap;
}
  </style>