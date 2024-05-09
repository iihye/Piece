<template>
    <h1>piecemake</h1>

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

<style></style>
