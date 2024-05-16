<!-- <template>
    <div class="piecestatisticsview-main-title">어떤 조각을 완성했을까요?</div>
    <year-selector
        class="piecestatisticsview-year-selector"
        @year-changed="updateData"
    />
    <pie-chart :data="chartData" />

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
                labels: ["영화", "뮤지컬", "콘서트", "기타", "연극"],
                datasets: [
                    {
                        backgroundColor: [
                            "#b094ff",
                            "#efae6d",
                            "#ff9494",
                            "#c4c4c4",
                            "#76aacd",
                        ],
                        data: [4, 5, 3, 5],
                    },
                ],
            },
        };
    },
};
</script> -->


<!-- <template>
    <div class="piecestatisticsview-main-title">어떤 조각을 완성했을까요?</div>
    <YearSelector @year-changed="fetchStatistics" />
    <PieChart :data="chartData" v-if="!noData" />
    <NoItem v-if="noData" content="완성된 조각이 없어요" />
  </template>
  
  <script setup>
  import { computed } from 'vue';
  import { usePieceStatisticsStore } from '@/stores/pieceStatistics.js';
  import YearSelector from "@/components/chart/YearSelector.vue";
  import PieChart from "@/components/chart/PieChart.vue";
  import NoItem from "@/components/item/NoItem.vue";
  
  const { fetchStatistics, statisticsData, noData } = usePieceStatisticsStore();
  const currentYear = new Date().getFullYear();
  
  fetchStatistics(currentYear);
  
  const chartData = computed(() => ({
    labels: ["영화", "뮤지컬", "콘서트", "기타", "연극"],
    datasets: [{
      backgroundColor: ["#b094ff", "#efae6d", "#ff9494", "#c4c4c4", "#76aacd"],
      data: [
        statisticsData.movieNumber || 0,
        statisticsData.musicalNumber || 0,
        statisticsData.concertNumber || 0,
        statisticsData.etcNumber || 0,
        statisticsData.theaterNumber || 0
      ]
    }]
  }));
  </script>
   -->

   <template>
    <div class="piecestatisticsview-main-title">어떤 조각을 완성했을까요?</div>
    <year-selector class="piecestatisticsview-year-selector" @year-changed="updateChartData" />
    <pie-chart :chartData="chartData" />
    <no-item v-if="noData" content="완성된 조각이 없어요"/>
  </template>
  
  <script setup>
  import { computed } from 'vue';
  import { usePieceStatisticsStore } from '@/stores/piecestatistics';
  import YearSelector from "@/components/chart/YearSelector.vue";
  import PieChart from "@/components/chart/PieChart.vue";
  import NoItem from "@/components/item/NoItem.vue";
  
  const { chartData, noData, updateData } = usePieceStatisticsStore();
  
  const updateChartData = (year) => {
      updateData(year);
  };
  
  const displayChartData = computed(() => chartData);
  </script>
  

<!-- <template>
    <div class="piecestatisticsview-main-title">어떤 조각을 완성했을까요?</div>
    <year-selector class="piecestatisticsview-year-selector" @year-changed="fetchStatistics" />
    <pie-chart :data="chartData" />
    <NoItem v-if="noData" content="완성된 조각이 없어요"/>
</template>

<script setup>
import { usePieceStatisticsStore } from '@/stores/piecestatistics.js';
import YearSelector from "@/components/chart/YearSelector.vue";
import PieChart from "@/components/chart/PieChart.vue";

const pieceStatisticsStore = usePieceStatisticsStore();
const { chartData, noData, updateData } = pieceStatisticsStore;

const showPieChartData = async (year) => {
    try {
        // console.log('selected year : ', year);
        await updateData(year);
    } catch (error) {
        console.error("통계 내역 불러오기 실패", error);
    }
};

    
    
</script> -->


  
<!-- <template>
    <div class="piecestatisticsview-main-title">어떤 조각을 완성했을까요?</div>
    <year-selector class="piecestatisticsview-year-selector" @year-changed="fetchStatistics" />
    <pie-chart v-if="!noData" :data="chartData" />
    <NoItem v-if="noData" content="완성된 조각이 없어요"/>
  </template>
  
  <script setup>
  import { computed } from 'vue';
  import { usePieceStatisticsStore } from '@/stores/piecestatistics.js';
  import YearSelector from "@/components/chart/YearSelector.vue";
  import PieChart from "@/components/chart/PieChart.vue";
  import NoItem from "@/components/item/NoItem.vue";
  console.log('script setup');

  const { fetchStatistics, statisticsData, noData } = usePieceStatisticsStore();
  
  const chartData = computed(() => {
      if (noData.value) {
          return {
              labels: ["영화", "뮤지컬", "콘서트", "기타", "연극"],
              datasets: [{
                  backgroundColor: ["#b094ff", "#efae6d", "#ff9494", "#c4c4c4", "#76aacd"],
                  data: [0, 0, 0, 0, 0]
              }]
          };
      }
    console.log('enter computed');

      return {
          labels: ["영화", "뮤지컬", "콘서트", "기타", "연극"],
          datasets: [{
              backgroundColor: ["#b094ff", "#efae6d", "#ff9494", "#c4c4c4", "#76aacd"],
              data: [
                  statisticsData.movieNumber || 0,
                  statisticsData.musicalNumber || 0,
                  statisticsData.concertNumber || 0,
                  statisticsData.etcNumber || 0,
                  statisticsData.theaterNumber || 0
              ]
          }]
      };
  });
  </script>
   -->

  

<!-- <template>
    <div class="piecestatisticsview-main-title">어떤 조각을 완성했을까요?</div>
    <YearSelector @year-changed="fetchStatistics" />
    <PieChart :data="chartData" />
    <NoItem v-if="noData" content="완성된 조각이 없어요" />
  </template>
  
  <script setup>
  import { computed } from 'vue';
  import { usePieceStatisticsStore } from '@/stores/pieceStatisticsStore';
  import YearSelector from "@/components/chart/YearSelector.vue";
  import PieChart from "@/components/chart/PieChart.vue";
  import NoItem from "@/components/item/NoItem.vue";
  
  const { fetchStatistics, statisticsData, noData } = usePieceStatisticsStore();
  const currentYear = new Date().getFullYear();
  
  // Fetch data when component is first loaded
  fetchStatistics(currentYear);
  
console.log('statisticsData: ', statisticsData);

  const chartData = computed(() => {
    // if (!noData.value && statisticsData.value) {
        if (!noData.value && statisticsData) {
      return {
        labels: ["영화", "뮤지컬", "콘서트", "기타", "연극"],
        datasets: [{
            backgroundColor: [
                            "#b094ff",
                            "#efae6d",
                            "#ff9494",
                            "#c4c4c4",
                            "#76aacd",
                        ],
          data: [
            statisticsData.value.movieNumber,
            statisticsData.value.musicalNumber,
            statisticsData.value.concertNumber,
            statisticsData.value.etcNumber
          ],
        }],
      };
    }
    return null;
  });
  </script>
   -->

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