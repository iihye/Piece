<template>
    <div>
        <canvas ref="canvas" :style="{ width: '100%', maxWidth: '600px' }"></canvas>
    </div>
</template>

<script>
import Chart from 'chart.js/auto';

export default {
    name: 'PieChart',
    props: ['data'], // 데이터 prop 추가
    mounted() {
        this.createChart();
    },
    methods: {
        createChart() {
            const ctx = this.$refs.canvas.getContext('2d');
            new Chart(ctx, {
                type: 'pie',
                data: this.data, // 받은 데이터를 사용하여 차트 생성
            });
        }
    }
};
</script>
