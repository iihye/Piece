<template>
    <div class="piecefrontview-main-container">
        <!-- title -->
        <div class="piecefrontview-main-title">
            모양 레이아웃을 선택해주세요
        </div>
        <div class="piecefrontview-main-content">
            조각의 앞면을 완성해주세요
        </div>

        <!-- choice -->
        <div class="piecefrontview-sub-container">
            <div class="piecefrontview-choice">
                <img class="piecefrontview-layout" v-for="layout in layouts" :key="layout" :src="layout" width="36"
                    height="56" @click="changeLayout(layout)" />
            </div>
            <canvas id="canvas" class="piecefrontview-canvas" width="896" height="1280"></canvas>
        </div>

        <!-- button -->
        <div class="piecemake-button-container">
            <RoundButton :roundButtonContent="'확인'" :roundButtonFunction="next" :isRoundDisable="isRoundDisable">
            </RoundButton>
        </div>
    </div>
</template>

<script setup>
import { onMounted, ref, watchEffect } from 'vue';
import Layout1 from '@/assets/Layout1.png';
import Layout2 from '@/assets/Layout2.png';
import Layout3 from '@/assets/Layout3.png';
import RoundButton from '@/components/button/RoundButton.vue';
import router from '@/router';
import { usePieceMakeStore } from "@/stores/piecemake";
import { useCommonStore } from "@/stores/common";
import { usePieceStore } from "@/stores/piece";

const commonStore = useCommonStore();
const makeStore = usePieceMakeStore();
const pieceStore = usePieceStore();

const layout1 = Layout1;
const layout2 = Layout2;
const layout3 = Layout3;

const layouts = [layout1, layout2, layout3];
const selectedLayout = ref(layout1); // 기본 선택된 레이아웃
const poster = pieceStore.croppedImageValue;

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
        context.drawImage(firstImage, 0, 0, 896, 1280);

        // 두 번째 이미지 로드 및 그리기
        var secondImage = new Image();
        secondImage.src = poster;
        secondImage.onload = function () {
            context.globalCompositeOperation = "source-in";
            context.drawImage(secondImage, 0, 0, 896, 1280);
            context.globalCompositeOperation = "source-over";
        };
    };
}

onMounted(() => {
    makeStore.selectedLayout = layout1;
    drawCanvas(); // 컴포넌트가 마운트된 후 최초로 캔버스 그리기
    watchEffect(drawCanvas); // 선택된 레이아웃이 변경될 때마다 캔버스 다시 그리기

    commonStore.headerTitle = "조각 만들기";
    commonStore.headerType = "header2";
    commonStore.setProgress(4);
});

const isRoundDisable = ref(true);

const next = () => {
    const canvas = document.getElementById("canvas");
    const imagefile = canvas.toDataURL();
    pieceStore.setBase64FrontImage(imagefile);
    router.push('/piece/background');
}
</script>

<style scoped>
.piecefrontview-main-container {
    display: flex;
    flex-direction: column;
    height: calc(100vh - 9.25rem);
    padding-top: 1rem;
    padding-bottom: 1rem;
    margin: 0 1rem 0 1rem;
}

/* title */
.piecefrontview-main-title {
    font-family: "Bold";
    font-size: 1.6rem;
    color: var(--black-color);
    margin: 0 0 0.4rem 0;
    user-select: none;
}

.piecefrontview-main-content {
    font-family: "Regular";
    font-size: 1rem;
    color: var(--gray2-color);
    margin-bottom: 1rem;
    display: flex;
    justify-content: left;
    align-items: center;
    user-select: none;
}

/* choice */
.piecefrontview-sub-container {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.piecefrontview-choice {
    display: flex;
    justify-content: center;
    align-items: center;
    border: 1px solid var(--main-color);
    width: 20rem;
    padding: 0.2rem 0 0.2rem 0;
    margin-bottom: 1rem;
}

.piecefrontview-layout {
    background-color: transparent;
    border: 1px solid black;
    margin: 0 0.2rem 0 0.2rem;
}

.piecefrontview-canvas {
    width: 100%;
    height: 468px;
}

/* button */
.piecemake-button-container {
    position: fixed;
    left: 0;
    right: 0;
    bottom: 4rem;
    text-align: center;
}
</style>
