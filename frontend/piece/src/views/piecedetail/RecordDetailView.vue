<template>
    <!-- header -->
    <button @click="handleSuccess">완료</button>

    <!-- image -->
    <div>
        <div
            v-if="
                pieceDetailRecord.imgList &&
                pieceDetailRecord.imgList.length > 0
            "
        >
            <img
                v-for="(imgSrc, index) in pieceDetailRecord.imgList"
                :key="index"
                :src="imgSrc"
                :alt="pieceDetailRecord.title"
                @click="handleImageClick"
            />
        </div>

        <div v-else>
            <div>이미지 없음</div>
        </div>
    </div>

    <!-- content -->
    <div>{{ pieceDetailRecord.record }}</div>
</template>

<script setup>
import router from "@/router";
import { ref, computed, onMounted } from "vue";
import { usePiecelistStore } from "@/stores/piecelist";

const store = usePiecelistStore();

const pieceDetailRecord = computed(() => store.getPieceDetailRecord);
console.log(pieceDetailRecord.value);

const handleSuccess = () => {
    alert("완료 클릭");
};

const handleImageClick = () => {
    alert("이미지 클릭");
};

onMounted(async () => {
    await store.findPieceDetailRecord(2);
});
</script>

<style></style>
