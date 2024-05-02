<template>
    <div class="footer">
        <RouterLink :to="{ name: 'main' }">홈 </RouterLink>
        <RouterLink :to="{ name: 'piecelistmain' }">모아보기 </RouterLink>
        <RouterLink :to="{ name: 'piecemake' }">조각 만들기 </RouterLink>
        <RouterLink :to="{ name: 'chat' }">채팅 </RouterLink>
        <RouterLink :to="{ name: 'mypage' }">내정보 </RouterLink>
    </div>
</template>

<script setup>
import { RouterLink } from "vue-router";
</script>

<style>
.footer {
    margin: 2rem;
}
</style>