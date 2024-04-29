<template>
    <div
        :class="{ 'labelitem-modal-container': true, 'isWear-color': isWear }"
        @click="handleClick"
    >
        <div class="labelitem-icon-container">
            <div
                class="labelitem-icon-background"
                :class="{
                    'labelitem-icon-background': true,
                    'isMine-background-color': !isMine,
                }"
            ></div>
            <font-awesome-icon
                class="labelitem-icon-icon"
                :icon="['fas', 'film']"
                :style="{
                    color: isMine ? 'var(--main-color)' : 'var(--gray2-color)',
                }"
            />
        </div>
        <div class="labelitem-label-container">
            <div
                :class="{
                    'labelitem-label-title': true,
                    'isMine-color': !isMine,
                }"
            >
                {{ title }}
            </div>
            <div
                :class="{
                    'labelitem-label-content': true,
                    'isMine-color': !isMine,
                }"
            >
                {{ description }}
            </div>
        </div>
        <div v-if="isWear" class="labelitem-wear-container">
            <div class="labelitem-wear-title">착용 중</div>
        </div>
    </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
    isMine: Boolean,
    isWear: Boolean,
    title: String,
    description: String,
    handleItemClick: Function,
});

const handleClick = () => {
    if (props.isMine) {
        props.handleItemClick();
    }
};
</script>

<style>
@import "@/components/css/color.css";
@import "@/components/css/font.css";

.isWear-color {
    background-color: var(--sub2-color);
}

.isMine-color {
    color: var(--gray2-color);
}

.isMine-background-color {
    background-color: var(--gray-color) !important;
}

.labelitem-modal-container {
    display: flex;
    align-items: center;
    padding: 0.8rem 1rem 0.8rem 1rem;
    margin-bottom: 1rem;
}

.labelitem-icon-container {
    position: relative;
    width: 3rem;
    height: 3rem;
    display: flex;
    flex-shrink: 0;
    justify-content: flex-start;
    align-items: center;
}

.labelitem-icon-background {
    position: absolute;
    top: 0;
    left: 0;
    width: 3rem;
    height: 3rem;
    border-radius: 50%;
    background-color: var(--sub-color);
}

.labelitem-icon-container > .labelitem-icon-background + .labelitem-icon-icon {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}

.labelitem-icon-icon {
    width: 2rem;
    height: 2rem;
}

.labelitem-label-container {
    width: 4rem;
    flex-grow: 1; /* 서브 컨테이너가 남은 공간을 모두 차지하도록 설정 */
    margin: 0 1rem 0 1rem;
    display: flex;
    flex-direction: column; /* 세로로 정렬 */
}

.labelitem-label-title {
    font-family: "Semi";
    font-size: 1rem;
    margin-bottom: 0.4rem;
}

.labelitem-label-content {
    font-family: "Regular";
    font-size: 1rem;
}

.labelitem-wear-container {
    width: 4rem;
    display: flex;
    flex-shrink: 0;
}

.labelitem-wear-title {
    font-family: "Semi";
    font-size: 1rem;
    color: var(--main-color);
}
</style>
