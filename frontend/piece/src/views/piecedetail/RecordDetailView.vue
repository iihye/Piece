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
import { useCommonStore } from "@/stores/common";
import { usePiecelistStore } from "@/stores/piecelist";

const commonStore = useCommonStore();
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
    commonStore.headerTitle = "자세히 기록하기";
    commonStore.headerType = "header2";

    await store.findPieceDetailRecord(2);
});
</script>

<style></style>
