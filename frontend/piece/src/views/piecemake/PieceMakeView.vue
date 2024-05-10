<template>
    <h1 class="piecemakeview-message">준비중인 페이지입니다</h1>

    <!-- TODO: 주석 해제 -->
    <!-- <RouterLink :to="{ name: 'pieceimage' }">시작하기</RouterLink> -->
</template>

<script setup>
import { onMounted } from "vue";
import { useCommonStore } from "@/stores/common";
const commonStore = useCommonStore();

onMounted(async () => {
    commonStore.headerTitle = "조각 만들기";
    commonStore.headerType = "header2";
});
</script>

<style>
.piecemakeview-message{
    font-family: "Semi";
    font-size: 20px;
    font-weight: bold;
    color: #333;
    margin-bottom: 20px;
    text-align: center;
}
</style>
