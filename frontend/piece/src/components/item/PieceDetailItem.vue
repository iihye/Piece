<template>
    <div class="pieceDetailItem-main-container">
        <div
            class="pieceDetailView-flip-card"
            @click="handleImageClick"
            @mousemove="handleMouseMove"
            @mouseleave="handleMouseOut"
            @touchstart="handleTouchStart"
            @touchmove="handleTouchMove"
            @touchend="handleMouseOut"
        >
            <div
                class="pieceDetailView-flip-card-inner"
                :class="{ 'is-flipped': imgFrontBack }"
            >
                <!-- Front face -->
                <div class="pieceDetailView-flip-card-front">
                    <img
                        class="pieceDetailView-image-item"
                        :src="frontImg"
                        :alt="title"
                    />
                </div>
                <!-- Back face -->
                <div class="pieceDetailView-flip-card-back">
                    <img
                        class="pieceDetailView-image-item"
                        :src="backImg"
                        :alt="title"
                    />
                </div>
            </div>
        </div>
        <div v-if="imgFrontBack" class="pieceDetailView-image-message">
            클릭해서 앞면을 확인해보세요
        </div>
        <div v-else class="pieceDetailView-image-message">
            클릭해서 뒷면을 확인해보세요
        </div>
    </div>
</template>

<script setup>
import { ref, defineProps } from "vue";
import ToastSuccessModal from "@/components/modal/ToastSuccessModal.vue";

const imgFrontModal = ref(false);
const imgBackModal = ref(false);
let resetTimeout = null;

const props = defineProps({
    frontImg: String,
    backImg: String,
    title: String,
});

const imgFrontBack = ref(false);
const isFading = ref(false);
let touchStartX = 0;
let touchStartY = 0;

function handleImageClick() {
    imgFrontBack.value = !imgFrontBack.value;
    toggleModal();
}

function toggleModal() {
    if (imgFrontBack.value) {
        imgFrontModal.value = true;
        toggleFade();
    } else {
        imgBackModal.value = true;
        toggleFade();
    }
}

function toggleFade() {
    isFading.value = true;
    setTimeout(() => {
        isFading.value = false;
        imgFrontModal.value = false;
        imgBackModal.value = false;
    }, 1000);
}

function handleMouseMove(event) {
    const rect = event.currentTarget.getBoundingClientRect();
    const mouseX = ((event.clientX - rect.left) / rect.width) * 100;
    const mouseY = ((event.clientY - rect.top) / rect.height) * 100;
    event.currentTarget.style.setProperty("--mouse-x", `${mouseX}%`);
    event.currentTarget.style.setProperty("--mouse-y", `${mouseY}%`);
    transformCard(event);
}

function handleTouchStart(event) {
    touchStartX = event.touches[0].clientX;
    touchStartY = event.touches[0].clientY;
}

function handleTouchMove(event) {
    event.preventDefault(); // Prevent scrolling on touch move
    const touch = event.touches[0];
    const target = event.currentTarget;
    target.style = `background-position : ${
        touchStartX / 5 + touchStartY / 5
    }%; filter : opacity(${touchStartX / 200}) brightness(1.2)`;
    transformCard(touch, touchStartX, touchStartY, target);
}

function transformCard(event, touchStartX = 0, touchStartY = 0, target = null) {
    const currentTarget = target || event.currentTarget;
    const rect = currentTarget.getBoundingClientRect();
    const centerX = rect.width / 2;
    const centerY = rect.height / 2;
    const clientX =
        (event.touches ? event.touches[0].clientX : event.clientX) - rect.left;
    const clientY =
        (event.touches ? event.touches[0].clientY : event.clientY) - rect.top;
    const deltaX = clientX - centerX - touchStartX;
    const deltaY = clientY - centerY - touchStartY;
    const rotateY = deltaX / 20; // x축 회전 각도 조절
    const rotateX = -deltaY / 20; // y축 회전 각도 조절
    currentTarget.style.transform = `perspective(1000px) rotateX(${rotateX}deg) rotateY(${rotateY}deg)`;
}

function handleMouseOut(event) {
    if (resetTimeout) clearTimeout(resetTimeout);
    const target = event.currentTarget;
    resetTimeout = setTimeout(() => {
        target.style.transform =
            "perspective(1000px) rotateY(0deg) rotateX(0deg)";
    }, 400);
}
</script>

<style>
.pieceDetailItem-main-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}

.pieceDetailView-flip-card {
    transition: all 0.25s;
    background-color: transparent;
    width: 284px;
    height: 464px;
    perspective: 1000px;
    cursor: pointer;
}

.pieceDetailView-flip-card-inner {
    position: relative;
    width: 100%;
    height: 100%;
    text-align: center;
    transition: transform 0.6s;
    transform-style: preserve-3d;
}

.pieceDetailView-flip-card-inner.is-flipped {
    transform: rotateY(180deg);
}

.pieceDetailView-flip-card-front,
.pieceDetailView-flip-card-back {
    position: absolute;
    width: 100%;
    height: 100%;
    -webkit-backface-visibility: hidden;
    backface-visibility: hidden;
}

.pieceDetailView-flip-card-back {
    transform: rotateY(180deg);
}
</style>
