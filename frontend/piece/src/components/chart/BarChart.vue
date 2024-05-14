<template>
    <div>
        <canvas ref="barChartCanvas"></canvas>
    </div>
</template>

<script setup>
import { onMounted, onUnmounted, ref, watch } from 'vue';
import Chart from 'chart.js/auto';
import { defineProps } from 'vue';

const props = defineProps({
    chartData: Object
});

const barChartCanvas = ref(null);
let chartInstance = null;

const initOrUpdateChart = () => {
    const ctx = barChartCanvas.value.getContext('2d');
    if (chartInstance) {
        chartInstance.data = props.chartData; 
        chartInstance.update();
    } else {
        chartInstance = new Chart(ctx, {
            type: 'bar',
            data: props.chartData,
            options: {
                scales: {
                    y: { beginAtZero: true, display: true },
                    x: { grid: { display: false } }
                },
                plugins: {
                    legend: { display: false },
                    title: { display: false }
                },
                responsive: true,
                maintainAspectRatio: false,
                layout: {
                    padding: { left: 0, right: 0, top: 0, bottom: 0 }
                },
                elements: {
                    bar: { borderRadius: 10 }
                }
            }
        });
    }
};

onMounted(() => {
    initOrUpdateChart();
});

watch(() => props.chartData, initOrUpdateChart, { deep: true });

onUnmounted(() => {
    if (chartInstance) {
        chartInstance.destroy();
    }
});
</script>

<style>
canvas {
    height: 400px;
}
</style>