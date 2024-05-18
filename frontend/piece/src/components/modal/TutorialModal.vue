<template>
    <div class="tutorial-container">
        <div class="tutorial-modal">
            <div class="tutorial-header">
                <div class="tutorial-header-title">piece를 소개합니다</div>
                <div v-if="imageUrls.length > 1" class="tutorial-header-circles">
                    <div class="tutorial-circle" v-for="(imageUrl, index) in imageUrls" :key="index"
                        :class="{ activeImg: index === curPos }"></div>
                </div>
            </div>

            <div class="tutorial-header-content">아이콘을 넘기며 다음 내용을 확인해주세요</div>

            <div class="tutorial-content">
                <div class="tutorial-image-album">
                    <div class="tutorialimages">
                        <img class="tutorialimage" v-for="(imageUrl, index) in imageUrls" :key="index"
                            :src="imageUrl" />
                    </div>
                </div>
                <div class="tutorial-content-explain" v-html="curContent"></div>
            </div>

            <div class="tutorial-check-container">
                    <button v-if="curPos === imageUrls.length - 1" class="tutorial-check" @click="tutorialCheck">
                        <font-awesome-icon 
                            :icon="['fas', 'check']" 
                            :style="{
                                color: notTutorial
                                    ? 'var(--main-color)'
                                    : 'var(--gray2-color)',
                            }"/>
                        <div class="tutorial-check-content" :class="{ 'checked': notTutorial }">다시 보지 않기</div>
                    </button>
                </div>

            <div class="tutorial-button">
                    <RoundButton 
                        class="tutorial-button-button" 
                        :roundButtonContent="'piece와 케이크 완성하기'" 
                        :roundButtonFunction="tutorialConfirm"
                        :isRoundDisable="isRoundDisable" />
                </div>


        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import RoundButton from '@/components/button/RoundButton.vue';
import { isTutorialModal } from '@/stores/util';
import { useUserStore } from "@/stores/user";

const userStore = useUserStore();

// 이미지
const imageUrls = ref([
    "https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Food/Birthday%20Cake.png",
    "https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Food/Shortcake.png",
    "https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Food/Birthday%20Cake.png",
]);

// 슬라이더
const curPos = ref(0);
const position = ref(0);
const startX = ref(0);
const endX = ref(0);
let IMAGE_WIDTH = 0;
let tutorialimages = null;
const curContent = computed(() => {
    if (curPos.value == 0) {
        isRoundDisable.value = false;
        return "내가 즐긴 문화생활은<br>하나의 케이크예요";
    } else if (curPos.value == 1) {
        isRoundDisable.value = false;
        return "케이크의 마지막 조각을<br>감상평으로 채워주세요";
    } else {
        isRoundDisable.value = true;
        return "완성된 케이크를 친구와 나누며<br>함께 즐겨보세요!";
    }
});

const getImageWidth = computed(() => {
    const imgWidth = document.querySelector(".tutorialimages").offsetWidth;
    return imgWidth;
});

const prev = () => {
    if (curPos.value > 0) {
        position.value += IMAGE_WIDTH;
        tutorialimages.style.transform = `translateX(${position.value}px)`;
        curPos.value--;
    }
};

const next = () => {
    if (curPos.value < imageUrls.value.length - 1) {
        position.value -= IMAGE_WIDTH;
        tutorialimages.style.transform = `translateX(${position.value}px)`;
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
    // 슬라이더
    IMAGE_WIDTH = getImageWidth.value;
    tutorialimages = document.querySelector(".tutorialimages");
    tutorialimages.addEventListener("touchstart", touchStart);
    tutorialimages.addEventListener("touchend", touchEnd);
});

// 버튼
const isRoundDisable = ref(false);
const notTutorial = ref(false);

function tutorialCheck() {
    notTutorial.value = !notTutorial.value;
}

const tutorialConfirm = () => {

    if (notTutorial.value) {
        userStore.checkTutorial();
    }

    isTutorialModal.value = false;
}

</script>

<style scoped>
.tutorial-container {
    position: fixed;
    width: 100vw;
    height: 100vh;
    background-color: var(--modal-color);
    top: 0;
    left: 0;
    margin: 0;
    z-index: 99 !important;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}

.tutorial-modal {
    width: 22rem;
    height: flex;
    border-radius: 1rem;
    background-color: var(--white-color);
    box-sizing: border-box;
    padding: 2rem 1.6rem 2rem 1.6rem;
}

/* 모달헤더 */
.tutorial-header {
    display: flex;
    flex-direction: row;
    justify-content: space-between;

}

.tutorial-header-title {
    font-family: "Bold";
    font-size: 1.4rem;
    color: var(--black-color);
}

.tutorial-header-circles {
    display: flex;
    justify-content: center;
    align-items: center;
    /* transform: translate(-100%); */
}

.tutorial-circle {
    width: 8px;
    height: 8px;
    border-radius: 50%;
    background-color: var(--gray-color);
    margin-right: 12px;
}

.tutorial-circle:last-child {
    margin-right: 0;
}

.tutorial-circle.activeImg {
    background-color: var(--main-color);
}

.tutorial-header-content{
    font-family: "Regular";
    font-size: 1rem;
    color: var(--gray2-color);
    margin-top: 0.6rem;
}

/* 모달 내용 */
.tutorial-content {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding-top: 2rem;
    padding-bottom: 2rem;
}

.tutorial-image-album {
    width: 8rem;
    height: 8rem;
    overflow: hidden;
}

.tutorialimages {
    position: relative;
    display: flex;
    height: auto;
    transition: transform 0.5s;
}

.tutorialimage {
    width: 8rem;
    height: 8rem;
}

.tutorial-content-explain {
    font-family: "Medium";
    font-size: 1.1rem;
    line-height: 1.4rem;
    color: var(--black-color);
    margin: 1rem;
    user-select: none;
    padding: 0 1rem 0 1rem;
    text-align: center;
}

.tutorial-check-container{
    display: flex;
    justify-content: flex-start;
    align-items: flex-start;
}

.tutorial-check {
    display: flex;
    align-items: center;
    width: 10.25rem;
    cursor: pointer;
    background-color: var(--white-color);
    border: none;
}

.tutorial-check-content{
    font-family: "Semi";
    font-size: 1rem;
    color: var(--gray2-color);
    margin-left: 0.6rem;
}

.tutorial-check-content.checked {
    color: var(--main-color);
}

.tutorial-button{
    display: flex;
    justify-content: center;
    align-items: center;
}

.tutorial-button-button {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 20rem;
    margin-top: 0.6rem;
}
</style>
