<template>
    <div class="pieceDetailView-main-container">
        <div class="pieceDetailView-piece-container">
            <img
                class="pieceDetailView-piece-img"
                :src="piecelistDetail.frontImg"
                :alt="title"
            />
        </div>

        <div class="pieceDetailView-button-container">
            <RoundButton
                class="pieceDetailView-button-button"
                :roundButtonContent="'목록으로 돌아가기'"
                :roundButtonFunction="handleBackListClick"
                :isRoundDisable="true"
            ></RoundButton>
            <RoundButton
                class="pieceDetailView-button-button"
                :roundButtonContent="'자세히 기록하기'"
                :roundButtonFunction="handleRecordClick"
                :isRoundDisable="true"
            ></RoundButton>
        </div>
    </div>
</template>

<script setup>
import router from "@/router";
import { ref, computed, onMounted } from "vue";
import { usePiecelistStore } from "@/stores/piecelist";
import RoundButton from "@/components/button/RoundButton.vue";
import { useRoute } from "vue-router";

const store = usePiecelistStore();
const route = useRoute();

const piecelistDetail = computed(() => store.getPiecelistDetail);

const handleBackListClick = () => {
    router.go(-1);
};

const handleRecordClick = () => {
    alert("서비스 준비중입니다!");
};

onMounted(async () => {
    const pieceId = route.params.pieceId;
    await store.findPiecelistDetail(pieceId);
});
</script>

<style>
.pieceDetailView-main-container {
    display: flex;
    flex-direction: column;
    height: 100%;
}

.pieceDetailView-main-container > :first-child {
    flex: 0 0 auto;
}

.pieceDetailView-main-container > :not(:first-child) {
    flex: 1;
}

.pieceDetailView-piece-container {
    display: flex;
    justify-content: center;
    margin: 20px 0;
}

.pieceDetailView-piece-img {
    width: 284px;
    height: 464px;
}

.pieceDetailView-button-container {
    display: flex;
    justify-content: center; /* 가운데 정렬 */
    bottom: 0;
}

.pieceDetailView-button-button {
    margin: 0 2px;
    flex: 1;
}

.pieceDetailView-button-button button {
    width: 100%;
}
</style>
