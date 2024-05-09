<template>

    <div class="piecebackground-container">
        <div class="piecebackground-title">
            배경을 선택해주세요
        </div>
        <div class="piecebackground-colorchoice">
            <div v-for="color in colors" :key="color" class="piecebackground-color" @click="changeColor(color)"
                :style="{ backgroundColor: color }"></div>
        </div>
        <canvas id="canvas" class="piecefront-canvas" width="284" height="468"></canvas>
    </div>
    <RoundButton :roundButtonContent="'확인'" :roundButtonFunction="next" :isRoundDisable="isRoundDisable"
        class="piecefront-button">
    </RoundButton>
</template>

<script setup>
import { onMounted, ref, computed, watch } from 'vue';
// import { useCommonStore } from '@/stores/common';
import { usePieceMakeStore } from '@/stores/piecemake';
import RoundButton from '@/components/button/RoundButton.vue';
import router from '@/router';
import '@/components/css/color.css';

// const commonStore = useCommonStore();
const makeStore = usePieceMakeStore();

const rootStyle = getComputedStyle(document.documentElement);

const color1 = rootStyle.getPropertyValue('--back1-color').trim();
const color2 = rootStyle.getPropertyValue('--back2-color').trim();
const color3 = rootStyle.getPropertyValue('--back3-color').trim();
const color4 = rootStyle.getPropertyValue('--back4-color').trim();
const color5 = rootStyle.getPropertyValue('--back5-color').trim();
const color6 = rootStyle.getPropertyValue('--back6-color').trim();
const color7 = rootStyle.getPropertyValue('--back7-color').trim();
const color8 = rootStyle.getPropertyValue('--back8-color').trim();
const color9 = rootStyle.getPropertyValue('--back9-color').trim();
const color10 = rootStyle.getPropertyValue('--white-color').trim();


const colors = [color1, color2, color3, color4, color5, color6, color7, color8, color9, color10];
const selectedColor = ref(color1); // 기본 색상

const changeColor = (color) => {
    selectedColor.value = color;
};

const selectedLayout = computed(() => makeStore.getSelectedLayout);

const backImage = 'https://search.pstatic.net/common?quality=75&direct=true&src=https%3A%2F%2Fmovie-phinf.pstatic.net%2F20210209_158%2F1612851946952VCyho_JPEG%2Fmovie_image.jpg';

watch(selectedColor, () => {
    drawCanvas();
}, { immediate: true });


function drawCanvas() {
    const canvas = document.getElementById("canvas");
    if (!canvas) return;
    const context = canvas.getContext("2d");
    context.clearRect(0, 0, canvas.width, canvas.height);

    var layoutImage = new Image();
    layoutImage.src = selectedLayout.value;  // 레이아웃 이미지 경로
    layoutImage.onload = function () {
        context.drawImage(layoutImage, 0, 0, canvas.width, canvas.height);

        var createImage = new Image();
        createImage.src = backImage;  // 포스터 이미지 경로
        createImage.onload = function () {

            context.globalCompositeOperation = 'source-over';

            context.drawImage(createImage, 0, 0, canvas.width, canvas.height * 2 / 3);

            drawTextBox(context, "여기에 불러온 정보를 표시합니다.", 0, canvas.height * 2 / 3, canvas);

            // applyFadeInGradient(context, canvas.height * 2 / 3);

            applyGradient(context, canvas.height * 2 / 3 - 70, canvas.height * 2 / 3);

            context.globalCompositeOperation = 'destination-in';

            context.drawImage(layoutImage, 0, 0, canvas.width, canvas.height);

            context.globalCompositeOperation = 'source-over';

        };
    };
}

function applyGradient(context, startY, endY) {
    // let gradient = context.createLinearGradient(0, startY, 0, endY);
    // gradient.addColorStop(0, 'rgba(0,0,0,0)'); // 투명
    // gradient.addColorStop(1, 'rgba(0, 0, 0, 0.8)'); // 불투명

    // // context.globalCompositeOperation = 'destination-over';
    // context.fillStyle = gradient;
    // context.fillRect(0, startY, context.canvas.width, endY - startY);

    const rgbaColor = hexToRGBA(selectedColor.value, 0.8);
    const gradient = context.createLinearGradient(0, startY, 0, endY);
    gradient.addColorStop(0, 'rgba(0,0,0,0)'); // 완전 투명
    gradient.addColorStop(1, rgbaColor); // 선택한 색상의 불투명도 0.8

    context.fillStyle = gradient;
    context.fillRect(0, startY, context.canvas.width, endY - startY);
}

function hexToRGBA(hex, opacity) {
    let r = parseInt(hex.slice(1, 3), 16),
        g = parseInt(hex.slice(3, 5), 16),
        b = parseInt(hex.slice(5, 7), 16);

    return `rgba(${r}, ${g}, ${b}, ${opacity})`;
}

function drawTextBox(context, text, x, y) {
    if (!context) return;
    // const padding = 0;
    const textHeight = 156;  // 텍스트 박스의 높이
    context.fillStyle = selectedColor.value;  // 텍스트 박스 배경색
    context.fillRect(x, y, 284, textHeight);

    context.font = '16px Arial';
    context.fillStyle = 'black';
    context.fillText(text, 30, y + 30);  // 텍스트를 텍스트 박스 내에 그립니다.
}

onMounted(() => {
    drawCanvas(); // 컴포넌트가 마운트된 후 최초로 캔버스 그리기
    // commonStore.headerTitle = '조각만들기';
    // commonStore.headerType = 'header2';
});

const isRoundDisable = ref(true);

const next = () => {
    router.push('/piece/save');
}


</script>

<style scoped>
.piecebackground-color {
    width: 2rem;
    height: 2rem;
    border-radius: 50%;
    border: 1px solid var(--gray-color);
    display: inline-block;
    cursor: pointer;
}

.piecebackground-title {
    font-size: 2rem;
    font-family: "Bold";
}
</style>