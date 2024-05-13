<template>
    <div class="piecemakeview-main-container">
        <div class="piecemakeview-main-title">조각을 만들어볼까요?</div>
        <div class="piecemakeview-sub-container">
            <!-- image -->
            <div class="piecemakeview-image-container">
                <!-- prev button -->
                <!-- <font-awesome-icon
                    class="piecemakeview-image-button"
                    :icon="['fas', 'angle-left']"
                    style="color: var(--gray2-color)"
                    @click="prev"
                /> -->
                <!-- album -->
                <div class="piecemakeview-image-album">
                    <div class="images">
                        <img
                            class="image"
                            v-for="(imageUrl, index) in imageUrls"
                            :key="index"
                            :src="imageUrl"
                        />
                    </div>

                    <div
                        v-if="imageUrls.length > 1"
                        class="image-circle-wrapper"
                    >
                        <div
                            class="image-circle"
                            v-for="(imageUrl, index) in imageUrls"
                            :key="index"
                            :v-model="circleIndex"
                            :class="{ activeImg: index === curPos }"
                        ></div>
                    </div>
                </div>
                <!-- next button -->
                <!-- <font-awesome-icon
                    class="piecemakeview-image-button"
                    :icon="['fas', 'angle-right']"
                    style="color: var(--gray2-color)"
                    @click="next"
                /> -->
            </div>

            <div class="piecemakeview-image-content" v-html="curContent"></div>
        </div>

        <!-- button -->
        <div class="piecemakeview-button-container">
            <RouterLink
                class="piecemakeview-button-button"
                :to="{ name: 'pieceimage' }"
                >시작하기</RouterLink
            >
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useCommonStore } from "@/stores/common";
const commonStore = useCommonStore();

const imgIndex = ref(0);

// dummy data
const imageUrls = ref([
    "https://i.ibb.co/grMvZS9/your-image.jpg",
    "https://i.ibb.co/grMvZS9/your-image.jpg",
    "https://i.ibb.co/grMvZS9/your-image.jpg",
    "https://i.ibb.co/grMvZS9/your-image.jpg",
]);

// slider
const curPos = ref(0);
const position = ref(0);
const startX = ref(0);
const endX = ref(0);
let IMAGE_WIDTH = 0;
let images = null;
const curContent = computed(() => {
    if (curPos.value == 0) {
        return "앞면에 들어갈 이미지를<br>선택해주세요";
    } else if (curPos.value == 1) {
        return "조각을 완성할 기본 정보를<br>입력해주세요";
    } else if (curPos.value == 2) {
        return "별점과 함께 감상평을<br>남겨주세요";
    } else {
        return "레이아웃과 AI로 생성된<br>배경으로 조각을 완성해주세요";
    }
});

const getImageWidth = computed(() => {
    const imgWidth = document.querySelector(".images").offsetWidth;
    return imgWidth;
});

const prev = () => {
    if (curPos.value > 0) {
        position.value += IMAGE_WIDTH;
        images.style.transform = `translateX(${position.value}px)`;
        curPos.value--;
    }
};

const next = () => {
    if (curPos.value < imageUrls.value.length - 1) {
        position.value -= IMAGE_WIDTH;
        images.style.transform = `translateX(${position.value}px)`;
        curPos.value++;
    }
};

const touchStart = (event) => {
    startX.value = event.touches[0].pageX;
};

const touchEnd = (event) => {
    endX.value = event.changedTouches[0].pageX;
    if (startX.value > endX.value) next();
    else prev();
};

onMounted(async () => {
    commonStore.headerTitle = "조각 만들기";
    commonStore.headerType = "header2";

    // slider
    IMAGE_WIDTH = getImageWidth.value;
    images = document.querySelector(".images");
    images.addEventListener("touchstart", touchStart);
    images.addEventListener("touchend", touchEnd);
});
</script>

<style scoped>
.piecemakeview-main-container {
    display: flex;
    flex-direction: column;
    height: calc(100vh - 9.25rem);
    padding-top: 1rem;
    padding-bottom: 1rem;
    margin: 0 1rem 0 1rem;
}

.piecemakeview-main-title {
    font-family: "Bold";
    font-size: 1.6rem;
    color: var(--black-color);
    margin: 0 0 1rem 0;
    user-select: none;
}

.piecemakeview-sub-container {
    width: 100%;
    height: 40rem;
    background-color: var(--sub2-color);
}

.piecemakeview-button-container {
    display: flex;
    flex: none;
    justify-content: center;
    margin: 1rem;
    bottom: 0;
}

.piecemakeview-button-button {
    background-color: var(--main-color);
    border: 0;
    border-radius: 0.625rem;
    height: 2.5rem;
    width: 7.5rem;
    color: var(--white-color);
    font-family: "Semi";
    font-size: 1rem;
    display: flex;
    justify-content: center;
    align-items: center;
    text-decoration: none;
}

/* image */
.piecemakeview-image-container {
    display: flex;
    justify-content: center;
    align-items: center;
    padding-top: 1rem;
}

/* 사진 있을 때 */
.piecemakeview-image-button {
    margin: 1rem;
}

.piecemakeview-image-button:hover {
    cursor: pointer;
}

/* album */
.piecemakeview-image-album {
    width: 18rem;
    height: 25rem;
    min-width: 15rem;
    min-height: 18rem;
    overflow: hidden;
}

.images {
    position: relative;
    display: flex;
    height: auto;
    transition: transform 0.5s;
}

.image {
    width: 18rem;
    height: 25rem;
}

.image-circle-wrapper {
    display: flex;
    position: absolute;
    left: 50%;
    transform: translate(-50%, -18px);
}

.image-circle {
    width: 8px;
    height: 8px;
    border-radius: 50%;
    background-color: var(--white-color);
    border: 1px solid var(--gray-color);
    margin-right: 12px;
}

.image-circle:last-child {
    margin-right: 0;
}

.image-circle.activeImg {
    background-color: var(--gray2-color);
}

/* content */
.piecemakeview-image-content {
    font-family: "Semi";
    font-size: 1.4rem;
    line-height: 1.8rem;
    color: var(--black-color);
    margin: 1rem;
    user-select: none;
    padding: 0 1rem 0 1rem;
}
</style>
