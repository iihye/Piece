<template>
    <div class="piecefront-container">
        <div class="piecefront-title"> 모양 레이아웃을 선택해주세요</div>
        <div class="piecefront-choice">
            <img v-for="layout in layouts" :key="layout" :src="layout" width="36" height="56" class="piecefront-layout"
                @click="changeLayout(layout)" />
        </div>
        <canvas id="canvas" class="piecefront-canvas" width="284" height="468"></canvas>
        <RoundButton :roundButtonContent="'확인'" :roundButtonFunction="next" :isRoundDisable="isRoundDisable"
            class="piecefront-button">
        </RoundButton>

    </div>

</template>

<script setup>
import { onMounted, ref, watchEffect } from 'vue';
import testLayout from '@/assets/testlayout.svg';
import RoundButton from '@/components/button/RoundButton.vue';
import router from '@/router';
import { usePieceMakeStore } from "@/stores/piecemake";
import { useCommonStore } from '@/stores/common';

const commonStore = useCommonStore();
const makeStore = usePieceMakeStore();

const layout1 = testLayout;
const layout2 = testLayout;
const layout3 = testLayout;

const poster = 'https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fmovie-phinf.pstatic.net%2F20210201_56%2F16121602734716k8oV_JPEG%2Fmovie_image.jpg';

const layouts = [layout1, layout2, layout3];
const selectedLayout = ref(layout1); // 기본 선택된 레이아웃



function changeLayout(layout) {
    selectedLayout.value = layout; // 선택된 레이아웃 변경
    makeStore.selectedLayout = layout;
}

function drawCanvas() {
    const canvas = document.getElementById("canvas");
    if (!canvas) return; // canvas 요소가 없으면 함수를 종료
    const context = canvas.getContext("2d");
    context.clearRect(0, 0, canvas.width, canvas.height); // 캔버스 지우기

    // 첫 번째 이미지 로드 및 그리기
    var firstImage = new Image();
    firstImage.src = selectedLayout.value;
    firstImage.onload = function () {
        context.drawImage(firstImage, 0, 0, 284, 468);

        // 두 번째 이미지 로드 및 그리기
        var secondImage = new Image();
        secondImage.src = poster;
        secondImage.onload = function () {
            context.globalCompositeOperation = 'source-in';
            context.drawImage(secondImage, 0, 0, 284, 468);
            context.globalCompositeOperation = 'source-over';
        };
    };
}

onMounted(() => {
    makeStore.selectedLayout = layout1;
    drawCanvas(); // 컴포넌트가 마운트된 후 최초로 캔버스 그리기
    watchEffect(drawCanvas); // 선택된 레이아웃이 변경될 때마다 캔버스 다시 그리기
    commonStore.headerTitle = '조각만들기';
    commonStore.headerType = 'header2';
});

const isRoundDisable = ref(true);

const next = () => {
    router.push('/piece/back');
}
</script>


<style scoped>
.piecefront-container {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.piecefront-title {
    font-size: 1.6rem;
    font-family: "Bold";
}

.piecefront-choice {
    border: 1px solid var(--main-color);
    width: 20rem;
}

.piecefront-layout {
    background-color: transparent;
    border: 1px solid black;
}
</style>
