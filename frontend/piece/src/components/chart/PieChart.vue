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
    if (!pieChartInstance) {
        pieChartInstance = new Chart(ctx, {
            type: 'pie',
            data: props.chartData,
            options: {
                responsive: true,
                plugins: {
                    legend: { display: false }
                },
                hover: {
                    mode: 'index',
                    intersect: true,
                    onHover: function(event, chartElement) {
                        if (chartElement.length) {
                            event.native.target.style.cursor = 'pointer';
                        } else {
                            event.native.target.style.cursor = 'default';
                        }
                    }
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
