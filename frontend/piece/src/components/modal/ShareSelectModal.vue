<template>
    <div class="shareselectmodal-background-container">
        <div class="shareselectmodal-main-container">
            <!-- <div class="shareselectmodal-modal-title">
                공유 방법을 선택해주세요
            </div> -->
            <div
                class="shareselectmodal-modal-container"
                @click="handleLinkClick"
            >
                <div class="shareselectmodal-icon-container">
                    <div class="shareselectmodal-icon-background"></div>
                    <font-awesome-icon
                        class="shareselectmodal-icon-icon"
                        :icon="['fas', 'link']"
                        style="color: var(--main-color)"
                    />
                </div>
                <div class="shareselectmodal-modal-content">
                    링크로 공유하기
                </div>
            </div>
            <div
                class="shareselectmodal-modal-container"
                @click="handleKakaoClick"
            >
                <div class="shareselectmodal-icon-container">
                    <div class="shareselectmodal-icon-background"></div>
                    <font-awesome-icon
                        class="shareselectmodal-icon-icon"
                        :icon="['fas', 'comment']"
                        style="color: var(--main-color)"
                    />
                </div>
                <div class="shareselectmodal-modal-content">
                    카카오톡으로 공유하기
                </div>
            </div>
            <div
                v-if="isMine"
                class="shareselectmodal-modal-container"
                @click="handleDeleteClick"
            >
                <div class="shareselectmodal-icon-container">
                    <div class="shareselectmodal-icon-background"></div>
                    <font-awesome-icon
                        class="shareselectmodal-icon-icon"
                        :icon="['fas', 'trash']"
                        style="color: var(--main-color)"
                    />
                </div>
                <div class="shareselectmodal-modal-content">조각 삭제하기</div>
            </div>
            <div
                v-if="!isMine"
                class="shareselectmodal-modal-container"
                @click="handleReportClick"
            >
                <div class="shareselectmodal-icon-container">
                    <div class="shareselectmodal-icon-background"></div>
                    <font-awesome-icon
                        class="shareselectmodal-icon-icon"
                        :icon="['fas', 'flag']"
                        style="color: var(--main-color)"
                    />
                </div>
                <div class="shareselectmodal-modal-content">조각 신고하기</div>
            </div>

            <div class="shareselectmodal-button-container">
                <button
                    class="shareselectmodal-button-fail"
                    @click="handleFailClick"
                >
                    취소
                </button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { defineProps } from "vue";

defineProps({
    handleLinkClick: Function,
    handleKakaoClick: Function,
    handleFailClick: Function,
    handleDeleteClick: Function,
    handleReportClick: Function,
    isMine: Boolean,
});
</script>

<style>
@import "@/components/css/color.css";
@import "@/components/css/font.css";

.shareselectmodal-background-container {
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

.shareselectmodal-main-container {
    width: 100%;
    max-width: 16rem;
    height: auto;
    border-radius: 1rem;
    background-color: var(--white-color);
    padding: 2rem 1.6rem 2rem 1.6rem;
}

.shareselectmodal-modal-title {
    font-family: "Semi";
    font-size: 1.4rem;
    color: var(--black-color);
    margin-bottom: 2rem;
}

.shareselectmodal-modal-container {
    display: flex;
    justify-content: left;
    align-items: center;
    margin-bottom: 1rem;
}

.shareselectmodal-modal-content {
    font-family: "Regular";
    font-size: 1rem;
    color: var(--black-color);
    margin-left: 0.4rem;
    display: flex;
    justify-content: left;
    align-items: center;
}

.shareselectmodal-icon-container {
    position: relative;
    width: 2rem;
    height: 2rem;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-right: 0.4rem;
}

.shareselectmodal-icon-background {
    position: absolute;
    top: 0;
    left: 0;
    width: 2rem;
    height: 2rem;
    border-radius: 50%;
    background-color: var(--sub-color);
}

.shareselectmodal-icon-container
    > .shareselectmodal-icon-background
    + .shareselectmodal-icon-icon {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}

.shareselectmodal-button-container {
    width: 16rem;
    display: flex;
    justify-content: right;
    align-items: center;
}

.shareselectmodal-button-fail {
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
