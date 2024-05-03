<template>
    <div class="reportselectmodal-background-container">
        <div class="reportselectmodal-main-container">
            <div class="reportselectmodal-modal-title">
                어떤 이유로 신고하시나요
            </div>

            <div
                class="reportselectmodal-modal-container"
                v-for="(item, index) in reportContents"
                :key="index"
                @click="handleContainerClick(index)"
            >
                <div class="reportselectmodal-modal-content">
                    {{ item.content }}
                </div>
                <div class="reportselectmodal-icon-container">
                    <font-awesome-icon
                        class="reportselectmodal-icon-icon"
                        :icon="['fas', 'check']"
                        :style="{ color: selectedIconColor(index) }"
                    />
                </div>
            </div>

            <div class="reportselectmodal-button-container">
                <button
                    class="reportselectmodal-button-fail"
                    @click="handleFailClick"
                >
                    취소
                </button>
                <button
                    class="reportselectmodal-button-success"
                    @click="
                        isButtonSuccessDisabled ? handleSuccessClick() : null
                    "
                    :style="{ color: successClickColor }"
                >
                    신고하기
                </button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { defineProps, ref } from "vue";

defineProps({
    handleFailClick: Function,
    handleSuccessClick: Function,
});

const reportContents = [
    { index: 1, content: "스팸, 광고" },
    { index: 2, content: "음란, 선정성" },
    { index: 3, content: "폭언, 비속어, 혐오 발언" },
    { index: 4, content: "불법 정보(도박, 사행성)" },
    { index: 5, content: "기타" },
];

const selectedIndex = ref(null);
const successClickColor = ref(null);
const isButtonSuccessDisabled = ref(false);

const handleContainerClick = (index) => {
    selectedIndex.value = index;
    successClickColor.value = "var(--main-color)";
    isButtonSuccessDisabled.value = true;
};

const selectedIconColor = (index) => {
    if (index === selectedIndex.value) {
        return "var(--main-color)";
    } else {
        return "var(--gray-color)";
    }
};
</script>

<style scoped>
@import "@/components/css/color.css";
@import "@/components/css/font.css";

.reportselectmodal-background-container {
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
}

.reportselectmodal-main-container {
    width: 100%;
    max-width: 16rem;
    height: auto;
    border-radius: 1rem;
    background-color: var(--white-color);
    padding: 2rem 1.6rem 2rem 1.6rem;
}

.reportselectmodal-modal-container {
    justify-content: left;
    align-items: center;
    margin-bottom: 2rem;
}

.reportselectmodal-modal-title {
    font-family: "Semi";
    font-size: 1.4rem;
    color: var(--black-color);
    margin-bottom: 2rem;
}

.reportselectmodal-modal-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.reportselectmodal-modal-content {
    font-family: "Regular";
    font-size: 1rem;
    color: var(--black-color);
    margin-left: 0.4rem;
    margin-bottom: 0.8rem;
    display: flex;
    justify-content: left;
    align-items: center;
}

.reportselectmodal-icon-container {
    position: relative;
    width: 2rem;
    height: 2rem;
    display: flex;
    justify-content: center;
    align-items: center;
}

.reportselectmodal-button-container {
    width: 16rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.reportselectmodal-button-fail {
    width: flex;
    height: flex;
    font-family: "Bold";
    font-size: 1rem;
    background-color: var(--white-color);
    border: 0;
    border-radius: 0.625rem;
    color: var(--gray2-color);
}

.reportselectmodal-button-success {
    width: flex;
    height: flex;
    font-family: "Bold";
    font-size: 1rem;
    background-color: var(--white-color);
    border: 0;
    border-radius: 0.625rem;
    color: var(--gray2-color);
}
</style>
