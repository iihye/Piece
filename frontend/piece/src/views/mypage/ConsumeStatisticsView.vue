<!-- <template>
    <div class="consumestatisticsview-main-title">조각으로 분석한 소비 통계예요</div>
    <year-selector @year-changed="updateData" />
    <bar-chart :chartData="chartData" />

    <div v-if="noData" class="consumestatisticsview-main-content">소비한 내용이 없어요 ㅜㅜ</div>
    <RouterLink :to="{ name: 'piecemake' }">조각 만들러 가기</RouterLink>
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
            noData: false,
            chartData: {
                labels: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
                datasets: [{
                    backgroundColor: "#ffe3e1",
                    data: [13000, 27000, 8000, 0, 0, 0, 0, 0, 0, 0, 0, 0]
                }]
            }
        };
    },
    methods: {
        updateData(year) {
            console.log('Updated year to:', year);
            this.noData = this.chartData.datasets[0].data.every(v => v === 0);
        }
    }
}
</script>

<style>
.consumestatisticsview-main-title {
    font-family: "Semi";
    font-size: 1.4rem;
    color: var(--black-color);
    margin-bottom: 0.6rem;
    user-select: none;
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
</style> -->

<template>
    <div class="consumestatisticsview-main-title">조각으로 분석한 소비 통계예요</div>
    <year-selector @year-changed="updateData" />
    <bar-chart :chartData="chartData" />

    <hr/>

    <!-- 월별 소비 내역 표시 -->
    <div v-for="(amount, index) in chartData.datasets[0].data" :key="index" class="monthly-consumption">
      <span>{{ chartData.labels[index] }}</span>
      <span class="amount">{{ amount.toLocaleString() }}원</span>
    </div>
  
    <!-- 소비가 없을 경우 -->
    <div v-if="noData" class="consumestatisticsview-main-content">소비한 내용이 없어요 ㅜㅜ</div>
    <RouterLink :to="{ name: 'piecemake' }">조각 만들러 가기</RouterLink>
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
              noData: false,
              chartData: {
                  labels: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
                  datasets: [{
                      backgroundColor: "#ffe3e1",
                      data: [13000, 27000, 8000, 0, 0, 0, 0, 0, 0, 0, 0, 0]
                  }]
              }
          };
      },
      methods: {
          updateData(year) {
              console.log('Updated year to:', year);
              this.noData = this.chartData.datasets[0].data.every(v => v === 0);
          }
      }
  }
  </script>
  
  <style>
  .consumestatisticsview-main-title {
      font-family: "Semi";
      font-size: 1.4rem;
      color: var(--black-color);
      margin-bottom: 0.6rem;
      user-select: none;
  }
  
  .monthly-consumption {
      display: flex;
      justify-content: space-between; /* 금액 정보를 양 끝으로 정렬 */
      font-family: "Regular";
      font-size: 1rem;
      margin-bottom: 0.5rem;
  }
  
  .amount {
      margin-left: auto; /* 금액을 오른쪽으로 정렬 */
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
  </style>
  