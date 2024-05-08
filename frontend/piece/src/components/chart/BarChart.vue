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
                                display: false // x축의 그리드 라인을 숨깁니다.
                            }
                        }
                    },
                    plugins: {
                        legend: {
                            display: false // 범례를 숨깁니다.
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
                            borderRadius: 10 // 바의 모서리를 둥글게 합니다.
                        }
                    },
                    // 배경을 투명하게 설정하여 없앱니다.
                    plugins: {
                        backgroundColor: 'rgba(0, 0, 0, 0)' 
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
