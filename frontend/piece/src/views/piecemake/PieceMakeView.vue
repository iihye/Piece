<template>
    <RouterLink :to="{ name: 'pieceimage' }">시작하기</RouterLink>
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
