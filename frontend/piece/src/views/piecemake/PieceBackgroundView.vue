<template>
    <div class="piecebackview-main-container">
        <!-- title -->
        <div class="piecebackview-main-title">배경을 선택해주세요</div>
        <div class="piecebackview-main-content">조각의 뒷면을 완성해주세요</div>

        <!-- choice -->
        <div class="piecebackview-sub-container">
            <div class="piecebackview-colorchoice">
                <div
                    v-for="color in colors"
                    :key="color"
                    class="piecebackview-color"
                    @click="changeColor(color)"
                    :style="{ backgroundColor: color }"
                ></div>
            </div>
            <canvas
                id="canvas"
                class="piecebackview-canvas"
                width="896"
                height="1280"
            ></canvas>
        </div>

        <!-- button -->
        <div class="piecebackview-button-container">
            <RoundButton
                :roundButtonContent="'확인'"
                :roundButtonFunction="next"
                :isRoundDisable="isRoundDisable"
                class="piecebackview-button"
            >
            </RoundButton>
        </div>
    </div>

    <!-- modal -->
    <LoadingModal
        v-if="!backImageValue"
        :modalTitle="'이미지를 생성하고 있어요'"
        :modalContent="'DALL·E가 열심히 만들고 있어요<br>조금만 기다려주세요'"
        class="piecebackview-loading"
    ></LoadingModal>
</template>

<script setup>
import { onMounted, ref, computed, watch, watchEffect } from "vue";
import { useCommonStore } from "@/stores/common";
import { usePieceMakeStore } from "@/stores/piecemake";
import { usePieceStore } from "@/stores/piece";
import RoundButton from "@/components/button/RoundButton.vue";
import LoadingModal from "@/components/modal/LoadingModal.vue";
import router from "@/router";
import "@/components/css/color.css";

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
    color1,
    color2,
    color3,
    color4,
    color5,
    color6,
    color7,
    color8,
    color9,
];

const selectedColor = ref(color1);

const changeColor = (color) => {
    selectedColor.value = color;
};

watch(
    selectedColor,
    () => {
        drawCanvas();
    },
    { immediate: true }
);

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

            context.drawImage(
                createImage,
                0,
                0,
                canvas.width,
                (canvas.height * 2) / 3
            );

            drawTextBox(
                context,
                pieceStore.pieceValue,
                0,
                (canvas.height * 2) / 3,
                canvas
            );

            // applyFadeInGradient(context, canvas.height * 2 / 3);

            applyGradient(
                context,
                (canvas.height * 2) / 3 - 150,
                (canvas.height * 2) / 3
            );

            context.globalCompositeOperation = "destination-in";

            context.drawImage(layoutImage, 0, 0, canvas.width, canvas.height);

            context.globalCompositeOperation = "source-over";
        };
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

function drawTextBox(context, content, x, y) {
    if (!context) return;
    // const padding = 0;
    const textHeight = 426; // 텍스트 박스의 높이
    context.fillStyle = selectedColor.value; // 텍스트 박스 배경색
    context.fillRect(x, y, 896, textHeight);

    context.font = "40px Bold";
    context.fillStyle = "black";
    context.fillText(content.title, 100, y + 60);

    // 별 그리기
    context.font = "40px Bold";
    let stars = "";
    for (let i = 0; i < 5; i++) {
        stars += i < content.rating ? "★" : "☆";
    }
    context.fillText(stars, 650, y + 60);

    context.font = "30px Medium";
    context.fillStyle = "black";
    context.fillText(content.date + " " + content.time, 100, y + 120);

    context.font = "30px Medium";
    context.fillStyle = "black";
    const reviewLines = wrapText(context, content.comment, 696);
    let reviewY = y + 180;
    reviewLines.forEach((line) => {
        context.fillText(line, 100, reviewY);
        reviewY += 40; // 줄 간격(lineHeight)을 40으로 조정
    });

    // 텍스트 줄 바꿈 함수
    function wrapText(context, text, maxWidth) {
        if (!text) return []; // 텍스트가 비어있는 경우를 대비한 검사

        const words = text.split(" ");
        let line = "";
        const lines = [];

        words.forEach((word) => {
            const testLine = line + word + " ";
            const metrics = context.measureText(testLine);
            const testWidth = metrics.width;
            if (testWidth > maxWidth && line !== "") {
                lines.push(line.trim()); // 마지막 공백 제거 후 줄 추가
                line = word + " "; // 새 줄 시작
            } else {
                line = testLine;
            }
        });

        if (line !== "") lines.push(line.trim()); // 마지막 줄 추가 (마지막 공백 제거)
        return lines;
    }

    context.font = "24px Semi";
    context.fillStyle = rootStyle.getPropertyValue("--gray2-color").trim();
    context.fillText("piece", 420, 1200);
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
    pieceStore.imageBack = imagefile;
    router.push("/piece/save");
};

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
