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
// RoundButtonFunction ( 버튼을 눌렀을 때 실행되는 함수 )
const handleBackListClick = () => {
    router.push({ name: "pieceListMy" });
};

const handleRecordClick = () => {
    alert("서비스 준비중입니다!");
};

onMounted(async () => {
    const pieceId = route.params.pieceId;
    console.log(route.params.pieceId);
    // const pieceId = route.parmas.pieceId;
    await store.findPiecelistDetail(pieceId);
});
</script>

<style scoped>
.pieceDetailView-main-container {
    /* display: flex;
    flex-direction: column;
    align-items: center; */
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
    margin: 0 2px; /* 버튼 사이의 간격 조정 */
    flex: 1; /* 각 버튼이 컨테이너의 공간을 동등하게 차지하도록 설정 */
}

.pieceDetailView-button-button button {
    width: 100%;
}
</style>
