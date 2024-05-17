<template>
    <div class="piecestatisticsview-main-title">어떤 조각을 완성했을까요?</div>
    <year-selector class="piecestatisticsview-year-selector"
    @year-changed="updateChartData" />
    <pie-chart :chartData="chartData" />
    <hr />
    <div v-for="(amount, index) in chartData.datasets[0].data" :key="index" class="monthly-consumption">
        <span>{{ chartData.labels[index] }}</span>
        <span class="amount">{{ amount.toLocaleString() }}회</span>
    </div>
    <div v-if="noData" class="no-data-content">완성된 조각이 없어요
        <RouterLink :to="{ name: 'piecemake' }">조각 만들러 가기</RouterLink>
    </div>
    <div v-else class="statistics-details">
      <ul>
        <li v-for="(amount, index) in chartData.datasets[0].data" :key="index">
          <span class="color-box" :style="{ backgroundColor: chartData.datasets[0].backgroundColor[index] }"></span>
          {{ chartData.labels[index] }}: {{ amount }} 회
        </li>
      </ul>
    </div>
  </template>
  
  <script setup>
  import { computed } from 'vue';
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
      color: #ff6b6b;
      text-align: center;
      font-size: 18px;
      margin-top: 20px;
  }
  
  .statistics-details ul {
      list-style-type: none;
      padding: 0;
      margin-top: 20px;
  }
  
  .statistics-details li {
      display: flex;
      align-items: center;
      background: #f0f0f0;
      margin: 10px 0;
      padding: 10px;
      border-radius: 5px;
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
  </style>