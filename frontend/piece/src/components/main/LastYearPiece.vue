<template>
    <div class="lastyearpiece-container">
        <div class="lastyearpiece-title">
            <img class="lastyearpiece-icon"
                src="https://github.com/microsoft/fluentui-emoji/blob/main/assets/Birthday%20cake/3D/birthday_cake_3d.png?raw=true" />
            <div class="lastyearpiece-text"> 1년 전 내가 완성한 조각이예요!</div>
        </div>
        <div class="lastyearpiece-content">

            <img :src="oneYear.frontImg" class="lastyearpiece-image">

            <SmallButton :smallButtonContent="'보러가기'" :smallButtonFunction="startPiece" class="recentlist-button">
            </SmallButton>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useUserStore } from "@/stores/user";
import SmallButton from '@/components/button/SmallButton.vue';

const userStore = useUserStore();

const oneYear = computed(() => userStore.getOneYearPiece);

const startPiece = () => {
}

onMounted(() => {
    userStore.readOneYearPiece();
})

</script>

<style scoped>
.lastyearpiece-container {
    height: 18.75rem;
    width: 360px;
}

.lastyearpiece-title {
    display: flex;
    align-items: center;
    margin: 1rem;
}

.lastyearpiece-icon {
    width: 1.6rem;
    height: 1.6rem;
}

.lastyearpiece-text {
    font-size: 1rem;
    font-family: Bold;
    margin-left: 1rem;
}

.lastyearpiece-content {
    background-color: var(--sub2-color);
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 16.25rem;
}

.lastyearpiece-image {
    width: 7rem;
    height: 10rem;
    margin: 1.2rem;
}
</style>