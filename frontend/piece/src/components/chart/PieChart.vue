<template>
    <div>
        <canvas ref="pieChartCanvas"></canvas>
    </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, watch } from 'vue';
import Chart from "chart.js/auto";
import { defineProps } from 'vue';

const props = defineProps({
    chartData: Object
});

const pieChartCanvas = ref(null);
let pieChartInstance = null;

const initOrUpdateChart = () => {
    const ctx = pieChartCanvas.value.getContext('2d');
    if (pieChartInstance) {
        pieChartInstance.data = props.chartData;
        pieChartInstance.update();
    } else {
        pieChartInstance = new Chart(ctx, {
            type: 'pie',
            data: props.chartData,
            options: {
                responsive: true,
                plugins: {
                    legend: { display: false }
                }
            }
        });
    }
};

onMounted(() => {
    initOrUpdateChart();
});


watch(() => props.chartData, (newData) => {
    if (pieChartInstance) {
        pieChartInstance.data = newData;
        pieChartInstance.update();
    }
}, { deep: true });

onUnmounted(() => {
    if (pieChartInstance) {
        pieChartInstance.destroy();
    }
});
</script>

<style>
.chart-labels {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 10px;
}

.label-item {
    display: flex;
    align-items: center;
    width: 100%;
    justify-content: space-between;
    margin-bottom: 10px;
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