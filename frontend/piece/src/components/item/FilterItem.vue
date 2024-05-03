<template>
    <div class="filteritem-main-container">
        <div class="filteritem-icon-container">
            <div
                class="filteritem-icon-background"
                :class="{
                    'filteritem-icon-background': true,
                    'isSelect-background-color': isSelect,
                }"
            ></div>
            <font-awesome-icon
                class="filteritem-icon-icon"
                :icon="getIconName(labelType)"
                :style="{
                    color: isSelect
                        ? 'var(--main-color)'
                        : 'var(--gray2-color)',
                }"
            />
        </div>
        <div class="filteritem-label-container">
            <div
                class="filteritem-label-title"
                :class="{
                    'filteritem-label-title': true,
                    'isSelect-color': isSelect,
                }"
            >
                {{ title }}
            </div>
        </div>
    </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
    pieceId: Number,
    labelType: String,
    title: String,
    isSelect: Boolean,
    handleItemClick: Function,
});

function getIconName(labelType) {
    switch (labelType) {
        case "ALL":
            return ["fas", "list"];
        case "MOVIE":
            return ["fas", "film"];
        case "THEATER":
            return ["fas", "masks-theater"];
        case "MUSICAL":
            return ["fas", "music"];
        case "CONCERT":
            return ["fas", "guitar"];
        case "ETC":
            return ["fas", "cake-candles"];
        case "HOT":
            return ["fas", "fire"];
        default:
            return ["fas", "ban"];
    }
}
</script>

<style>
@import "@/components/css/color.css";
@import "@/components/css/font.css";

/* select color */
.isSelect-color {
    color: var(--main-color) !important;
}

.isSelect-background-color {
    background-color: var(--sub-color) !important;
    border: 0.2rem solid var(--main-color);
    box-sizing: border-box;
}

.filteritem-main-container {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 0.4rem 0.2rem 0.4rem 0.2rem;
    flex-direction: column;
}

.filteritem-icon-container {
    position: relative;
    width: 4rem;
    height: 4rem;
    display: flex;
    justify-content: center;
    align-items: center;
}

.filteritem-icon-background {
    position: absolute;
    top: 0;
    left: 0;
    width: 4rem;
    height: 4rem;
    border-radius: 50%;
    background-color: var(--gray-color);
}

.filteritem-icon-container
    > .filteritem-icon-background
    + .filteritem-icon-icon {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}

.filteritem-icon-icon {
    width: 1.8rem;
    height: 1.8rem;
}

.filteritem-label-container {
    margin: 0.4rem 0 0 0;
    display: flex;
    justify-content: center;
    align-items: center;
}

.filteritem-label-title {
    font-family: "Semi";
    font-size: 1rem;
    color: var(--gray2-color);
    user-select: none;
}
</style>
