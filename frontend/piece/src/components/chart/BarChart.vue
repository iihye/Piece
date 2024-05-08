<template>
    <div>
        <canvas ref="barChartCanvas"></canvas>
    </div>
</template>

<script>
import Chart from 'chart.js/auto';

export default {
    name: 'BarChart',
    props: {
        chartData: {
            type: Object,
            required: true
        }
    },
    mounted() {
        this.createBarChart();
    },
    methods: {
        createBarChart() {
            const ctx = this.$refs.barChartCanvas.getContext('2d');
            new Chart(ctx, {
                type: 'bar',
                data: this.chartData,
                options: {
                    scales: {
                        y: {
                            display: false
                        },
                        x: {
                            grid: {
                                display: false
                            }
                        }
                    },
                    plugins: {
                        legend: {
                            display: false
                        },
                        title: {
                            display: false
                        }
                    },
                    responsive: true,
                    maintainAspectRatio: false,
                    layout: {
                        padding: {
                            left: 0,
                            right: 0,
                            top: 0,
                            bottom: 0
                        }
                    },
                    elements: {
                        bar: {
                            borderRadius: 10
                        }
                    }
                }
            });
        }
    }
}
</script>

<style scoped>
canvas {
    height: 400px;
}
</style>


<style scoped>
canvas {
    height: 400px;
}
</style>
