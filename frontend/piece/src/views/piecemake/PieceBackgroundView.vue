<template>
    <div class="piecebackview-main-container">
        <!-- title -->
        <div class="piecebackview-main-title">배경을 선택해주세요</div>
        <div class="piecebackview-main-content">조각의 뒷면을 완성해주세요</div>

        <!-- choice -->
        <div class="piecebackview-sub-container">
            <div class="piecebackview-colorchoice">
                <div v-for="color in colors" :key="color" class="piecebackview-color" @click="changeColor(color)"
                    :style="{ backgroundColor: color }"></div>
            </div>
            <canvas id="canvas" class="piecebackview-canvas" width="896" height="1280"></canvas>
        </div>

        <!-- button -->
        <div class="piecebackview-button-container">
            <RoundButton :roundButtonContent="'확인'" :roundButtonFunction="next" :isRoundDisable="isRoundDisable"
                class="piecebackview-button">
            </RoundButton>
        </div>
    </div>

    <!-- modal -->
    <LoadingModal v-if="!backImageValue" :modalTitle="'이미지를 생성하고 있어요'"
        :modalContent="'DALL·E가 열심히 만들고 있어요<br>조금만 기다려주세요'" class="piecebackview-loading"></LoadingModal>
</template>

<script setup>
import { onMounted, ref, computed, watch, watchEffect } from "vue";
import { useCommonStore } from "@/stores/common";
import { usePieceMakeStore } from "@/stores/piecemake";
import { usePieceStore } from "@/stores/piece";
import router from "@/router";
import "@/components/css/color.css";
import RoundButton from "@/components/button/RoundButton.vue";
import LoadingModal from "@/components/modal/LoadingModal.vue";
import Layout1 from '@/assets/Layout1.png';
import Layout2 from '@/assets/Layout2.png';
import Layout3 from '@/assets/Layout3.png';
import starImg from '@/assets/star.png';
import pieceLogo from '@/assets/piece.png';

// 스토어
const commonStore = useCommonStore();
const makeStore = usePieceMakeStore();
const pieceStore = usePieceStore();

// 색상버튼
const rootStyle = getComputedStyle(document.documentElement);
const color1 = rootStyle.getPropertyValue("--back1-color").trim();
const color2 = rootStyle.getPropertyValue("--back2-color").trim();
const color3 = rootStyle.getPropertyValue("--back3-color").trim();
const color4 = rootStyle.getPropertyValue("--back4-color").trim();
const color5 = rootStyle.getPropertyValue("--back5-color").trim();
const color6 = rootStyle.getPropertyValue("--back6-color").trim();
const color7 = rootStyle.getPropertyValue("--back7-color").trim();
const color8 = rootStyle.getPropertyValue("--back8-color").trim();
const color9 = rootStyle.getPropertyValue("--back9-color").trim();

const colors = [
    color1, color2, color3, color4, color5, color6, color7, color8, color9,];

const selectedColor = ref(color1);

const changeColor = (color) => {
    selectedColor.value = color;
};

watch(selectedColor, () => {
    drawCanvas();
}, { immediate: true });

// 레이아웃
const selectedLayout = computed(() => makeStore.getSelectedLayout);

// 뒷면이미지
const backImage = computed(() => makeStore.getBackImage);
const backImageValue = ref("");

watch(
    backImage,
    (newImage) => {
        if (newImage) {
            backImageValue.value = newImage;
            drawCanvas();
        }
    },
    { immediate: true }
);

// 캔버스
function drawCanvas() {
    const canvas = document.getElementById("canvas");
    if (!canvas) return;
    const context = canvas.getContext("2d");
    context.clearRect(0, 0, canvas.width, canvas.height);

    var layoutImage = new Image();
    layoutImage.src = selectedLayout.value; // 레이아웃 이미지 경로
    layoutImage.onload = function () {
        context.drawImage(layoutImage, 0, 0, canvas.width, canvas.height);

        var createImage = new Image();
        createImage.src = backImage.value; // 뒷면 이미지 경로
        createImage.onload = function () {
            context.globalCompositeOperation = "source-over";
            context.drawImage(createImage, -64, -64, createImage.width, createImage.height);

            let textBoxStart = 896;
            switch (layoutImage.src.split("/").pop()) {
                case "Layout1.png":
                    textBoxStart = 856;
                    drawTextBoxForLayout1(context, pieceStore.pieceValue, 0, textBoxStart);
                    break;
                case "Layout2.png":
                    textBoxStart = 800;
                    drawTextBoxForLayout2(context, pieceStore.pieceValue, 0, 800);
                    break;
                case "Layout3.png":
                    textBoxStart = 820;
                    drawTextBoxForLayout3(context, pieceStore.pieceValue, 0, 820);
                    break;
            }

            applyGradient(context, textBoxStart - 300, textBoxStart);

            context.globalCompositeOperation = "destination-in";

            context.drawImage(layoutImage, 0, 0, canvas.width, canvas.height);

            context.globalCompositeOperation = "source-over";
        };
    };
}

function drawTextBoxForLayout1(context, content, x, y) {
    if (!context) return;
    // const padding = 0;
    context.fillStyle = selectedColor.value; // 텍스트 박스 배경색
    context.fillRect(x, y, 896, canvas.height - y);

    context.font = "40px Bold";
    context.fillStyle = "black";

    const titleLines = wrapText(context, content.title, 696);
    let titleY = y + 60;
    titleLines.forEach((line) => {
        context.fillText(line, 100, titleY);
        titleY += 40; // 줄 간격(lineHeight)을 40으로 조정
    });

    const starImage = new Image();
    starImage.src = starImg; // 별 이미지 경로 설정
    starImage.onload = () => {
        drawStar(context, content.score, 280, y - 80, 40, 40, 896);
    };


    context.font = "30px Medium";
    context.fillStyle = "black";
    const displayTime = content.time ? content.time : '';
    context.fillText(content.date + " " + displayTime, 100, y + 150);

    context.font = "30px Medium";
    context.fillStyle = "black";
    const reviewLines = wrapText(context, content.comment, 696);
    let reviewY = y + 200;
    reviewLines.forEach((line) => {
        context.fillText(line, 100, reviewY);
        reviewY += 40; // 줄 간격(lineHeight)을 40으로 조정
    });

    // piece 로고
    const logoImage = new Image();
    logoImage.src = pieceLogo;
    logoImage.onload = () => {
        context.drawImage(logoImage, 398, 1180, 100, 50);
    };
}

function drawTextBoxForLayout2(context, content, x, y) {
    if (!context) return;
    // const padding = 0;
    context.fillStyle = selectedColor.value; // 텍스트 박스 배경색
    context.fillRect(x, y, 896, canvas.height - y);

    context.font = "40px Bold";
    context.fillStyle = "black";

    const titleLines = wrapText(context, content.title, 696);
    let titleY = y + 100;
    titleLines.forEach((line) => {
        context.fillText(line, 100, titleY);
        titleY += 40; // 줄 간격(lineHeight)을 40으로 조정
    });

    const starImage = new Image();
    starImage.src = starImg; // 별 이미지 경로 설정
    starImage.onload = () => {
        drawStar(context, content.score, 220, y - 80, 40, 40, 896);
    };


    context.font = "30px Medium";
    context.fillStyle = "black";
    const displayTime = content.time ? content.time : '';
    context.fillText(content.date + " " + displayTime, 100, y + 190);

    context.font = "30px Medium";
    context.fillStyle = "black";
    const reviewLines = wrapText(context, content.comment, 696);
    let reviewY = y + 240;
    reviewLines.forEach((line) => {
        context.fillText(line, 100, reviewY);
        reviewY += 40; // 줄 간격(lineHeight)을 40으로 조정
    });

    // piece 로고
    const logoImage = new Image();
    logoImage.src = pieceLogo;
    logoImage.onload = () => {
        context.drawImage(logoImage, 398, 1170, 100, 50);
    };
}

function drawTextBoxForLayout3(context, content, x, y) {
    if (!context) return;
    // const padding = 0;
    context.fillStyle = selectedColor.value; // 텍스트 박스 배경색
    context.fillRect(x, y, 896, canvas.height - y);

    context.font = "40px Bold";
    context.fillStyle = "black";

    const titleLines = wrapText(context, content.title, 696);
    let titleY = y + 70;
    titleLines.forEach((line) => {
        context.fillText(line, 100, titleY);
        titleY += 40; // 줄 간격(lineHeight)을 40으로 조정
    });

    const starImage = new Image();
    starImage.src = starImg; // 별 이미지 경로 설정
    starImage.onload = () => {
        drawStar(context, content.score, 280, y - 80, 40, 40, 896);
    };


    context.font = "30px Medium";
    context.fillStyle = "black";
    const displayTime = content.time ? content.time : '';
    context.fillText(content.date + " " + displayTime, 100, y + 160);

    context.font = "30px Medium";
    context.fillStyle = "black";
    const reviewLines = wrapText(context, content.comment, 686);
    let reviewY = y + 210;
    reviewLines.forEach((line) => {
        context.fillText(line, 100, reviewY);
        reviewY += 40; // 줄 간격(lineHeight)을 40으로 조정
    });

    // piece 로고
    const logoImage = new Image();
    logoImage.src = pieceLogo;
    logoImage.onload = () => {
        context.drawImage(logoImage, 398, 1150, 100, 50);
    };
}

// 텍스트 줄바꿈 함수
function wrapText(context, text, maxWidth) {
    let line = "";
    const lines = [];

    // 글자 단위로 반복
    for (let i = 0; i < text.length; i++) {
        let char = text[i];
        const testLine = line + char;

        // 텍스트의 너비를 측정
        const metrics = context.measureText(testLine);
        const testWidth = metrics.width;

        if (testWidth > maxWidth && line !== "") {
            lines.push(line);  // 현재 줄을 lines 배열에 추가
            line = char;  // 새 줄 시작
        } else {
            line += char;  // 현재 줄에 글자 추가
        }
    }

    // 마지막 줄 추가
    if (line !== "") lines.push(line);
    return lines;
}

// // 별 그리기
// function drawStar(context, score, x, y, width, height, totalWidth) {
//     const starImage = new Image();
//     starImage.src = starImg;

//     // 별 사이 간격
//     const spacing = 5;

//     // 별 전체 그룹의 너비를 계산
//     const totalStarsWidth = score * width + (score - 1) * spacing;

//     // 시작 x 위치를 중앙 정렬로 조정
//     const startX = x + (totalWidth - totalStarsWidth) / 2;

//     starImage.onload = () => {
//         for (let i = 0; i < score; i++) {
//             context.drawImage(starImage, startX + i * (width + spacing), y, width, height);
//         }
//     };
// }

// 별 그리기
function drawStar(context, score, x, y, width, height, totalWidth) {
    const starImage = new Image();
    starImage.src = starImg;

    // 별 사이 간격
    const spacing = 5;

    // 별 전체 그룹의 너비를 계산
    const totalStarsWidth = score * width + (score - 1) * spacing;

    // 시작 x 위치를 중앙 정렬로 조정
    const startX = x + (totalWidth - totalStarsWidth) / 2;

    starImage.onload = () => {
        for (let i = 0; i < score; i++) {
            context.drawImage(starImage, startX + i * (width + spacing), y, width, height);
        }
    };
}

function applyGradient(context, startY, endY) {
    const rgbaColor = hexToRGBA(selectedColor.value, 0.8);
    const gradient = context.createLinearGradient(0, startY, 0, endY);
    gradient.addColorStop(0, "rgba(0,0,0,0)"); // 완전 투명
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

onMounted(() => {
    drawCanvas(); // 컴포넌트가 마운트된 후 최초로 캔버스 그리기
    watchEffect(drawCanvas);
    // 헤더정보 입력
    commonStore.headerTitle = "조각 만들기";
    commonStore.headerType = "header2";
});

// 다음단계버튼
const next = () => {
    const imagefile = canvas.toDataURL();
    pieceStore.setBase64BackImage(imagefile);
    console.log(imagefile);
    router.push('/piece/save');
}

const isRoundDisable = ref(true);
</script>

<style scoped>
.piecebackview-main-container {
    display: flex;
    flex-direction: column;
    height: calc(100vh - 9.25rem);
    padding-top: 1rem;
    padding-bottom: 1rem;
    margin: 0 1rem 0 1rem;
}

/* title */
.piecebackview-main-title {
    font-family: "Bold";
    font-size: 1.6rem;
    color: var(--black-color);
    margin: 0 0 0.4rem 0;
    user-select: none;
}

.piecebackview-main-content {
    font-family: "Regular";
    font-size: 1rem;
    color: var(--gray2-color);
    margin-bottom: 1rem;
    display: flex;
    justify-content: left;
    align-items: center;
    user-select: none;
}

/*  */
.piecebackview-colorchoice {
    display: flex;
    justify-content: space-evenly;
    margin-bottom: 1rem;
}

.piecebackview-color {
    width: 2rem;
    height: 2rem;
    border-radius: 50%;
    border: 1px solid var(--gray-color);
    display: inline-block;
    cursor: pointer;
}

.piecebackview-canvas {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 30.375rem;
}

/* button */
.piecebackview-button-container {
    position: fixed;
    left: 0;
    right: 0;
    bottom: 4rem;
    text-align: center;
}
</style>
