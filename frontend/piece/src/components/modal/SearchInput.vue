<template>
    <div class="searchinput-main-container">
        <!-- <div class="searchinput-left-container" @click="handlePrevClick">
            <font-awesome-icon
                class="searchinput-icon-icon"
                :icon="['fas', 'arrow-left']"
                style="color: var(--gray2-color)"
            />
        </div> -->
        <div class="searchinput-input-container">
            <input class="searchinput-input-content" placeholder="검색어를 입력하세요" v-model="searchContent"
                @compositionend="handleSearchAuto" />
        </div>
        <div class="searchinput-right-container" @click="handleSearchClick">
            <font-awesome-icon class="searchinput-icon-icon" :icon="['fas', 'magnifying-glass']"
                style="color: var(--gray2-color)" />
        </div>
    </div>
</template>

<script setup>
import { defineProps, ref, defineEmits } from "vue";

defineProps({
    handlePrevClick: Function,
    handleSearchClick: Function,
});

const emits = defineEmits(["searchContent"]);
const searchContent = ref("");

const handleSearchAuto = () => {
    if (searchContent.value.length >= 1) {
        // console.log("(자식)자동 완성 감지: ", searchContent.value);
        emits("searchContent", searchContent.value);
    }
};
</script>

<style>
@import "@/components/css/color.css";
@import "@/components/css/font.css";

.searchinput-main-container {
    position: relative;
    display: flex;
    align-items: center;
}

.searchinput-left-container {
    width: 1rem;
    height: 1rem;
    margin: 0.4rem;
    position: absolute;
    top: 0;
    left: 0;
}

.searchinput-right-container {
    width: 1rem;
    height: 1rem;
    margin: 0.4rem;
    position: absolute;
    top: 0;
    right: 0;
}

.searchinput-icon-icon {
    display: flex;
    justify-content: center;
    align-items: center;
}

.searchinput-input-container {
    width: 100%;
}

.searchinput-input-content {
    width: calc(100% - 3rem);
    font-family: "Regular";
    font-size: 1rem;
    color: var(--black-color);
    margin: 0.4rem 0 0 0.4rem;
    padding: 0 0.4rem 0.4rem 0.4rem;
    border: none;
    border-bottom: 1.4px solid var(--gray-color);
    transition: border-bottom-color 0.3s;
    display: flex;
    justify-content: center;
    align-items: center;
}

.searchinput-input-content:focus {
    outline: none;
    border-bottom-color: var(--main-color);
}
</style>
