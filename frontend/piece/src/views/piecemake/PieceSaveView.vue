<template>
    <h1>저장하기</h1>
    <button @click="savePiece">저장</button>
    <RouterLink :to="{ name: 'piecelistmain' }">내 조각 모아보기</RouterLink>
</template>

<script setup>
import {usePieceStore} from '@/stores/piece.js'
const pieceStore = usePieceStore();
const savePiece = () => {
  pieceStore.savePiece().then((response) => {
    console.log('Piece saved:', response);
  })
}
</script>

<style></style>
