<template>
    <h1>piecelist</h1>

    <RouterLink :to="{ name: 'cakelist' }">케이크 모아보기</RouterLink>
    <RouterLink :to="{ name: 'pieceList' }">조각 모아보기</RouterLink>
    <RouterLink :to="{ name: 'pieceListMy' }">내 조각 모아보기</RouterLink>
    <RouterLink :to="{ name: 'pieceList' }">찜한 조각 모아보기</RouterLink>
</template>

<script setup></script>

<style></style>
