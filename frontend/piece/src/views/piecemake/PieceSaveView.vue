<template>
    <h1>마지막 조각이 완성되었어요!</h1>
    <button @click="savePiece">저장</button>
    <RouterLink :to="{ name: 'piecelistmain' }">내 조각 모아보기</RouterLink>
    <PieceDetailView
      :frontImg="pieceStore.imageFront"
      :backImg="pieceStore.imageBack"
      :title="pieceStore.title"
    />
</template>

<script setup>
import {usePieceStore} from '@/stores/piece.js'
import PieceDetailView from '@/components/item/PieceDetailItem.vue'

const pieceStore = usePieceStore();
const savePiece = () => {
  pieceStore.savePiece().then((response) => {
    console.log('Piece saved:', response);
  })
}
</script>

<style></style>
