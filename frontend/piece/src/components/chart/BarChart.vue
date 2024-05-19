<template>
    <div>
        <canvas ref="barChartCanvas"></canvas>
    </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, watch } from 'vue';
import Chart from 'chart.js/auto';
import { defineProps } from 'vue';

const props = defineProps({
    chartData: Object
});

const barChartCanvas = ref(null);
let barChartInstance = null;

const initOrUpdateChart = () => {
    const ctx = barChartCanvas.value.getContext('2d');
    if (barChartInstance) {
        barChartInstance.data = props.chartData;
        barChartInstance.update();
    } else {
        barChartInstance = new Chart(ctx, {
            type: 'bar',
            data: props.chartData,
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                        display: true,
                        grid: { display: false },
                        ticks: {
                            font: {
                                family: 'Regular',  
                                size: 10,         
                                weight: 'bold',  
                                style: 'normal'  
                            }
                        }
                    },
                    x: {
                        grid: { display: false },
                        ticks: {
                            font: {
                                family: 'Regular',  
                                size: 10,         
                                weight: 'bold',  
                                style: 'normal'  
                            }
                        }
                    }
                },
                plugins: {
                    legend: { display: false },
                    title: { display: false },
                    tooltip: {
                        enabled: true,      
                        backgroundColor: 'rgba(0, 0, 0, 0.7)',  
                        titleFont: {
                            family: 'Regular', 
                            size: 14,
                            weight: 'bold',
                            style: 'normal'
                        },
                        bodyFont: {
                            family: 'Regular',  
                            size: 12,
                            weight: 'normal',
                            style: 'normal'
                        },
                        footerFont: {
                            family: 'Regular', 
                            size: 10,
                            weight: 'normal',
                            style: 'normal'
                        },
                        cornerRadius: 4, 
                        xPadding: 10,      
                        yPadding: 10,      
                        displayColors: true  
                    }
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
    if (barChartInstance) {
        barChartInstance.destroy();
    }
});
</script>

<style>
canvas {
    height: 400px;
}
</style>