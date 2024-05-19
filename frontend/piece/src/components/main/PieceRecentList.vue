<template>
    <div class="recentlist-container">
        <div class="recentlist-title">
            <img class="recentlist-title-icon"
                src="https://github.com/microsoft/fluentui-emoji/blob/main/assets/Birthday%20cake/3D/birthday_cake_3d.png?raw=true" />
            <div class="recentlist-title-text">지금, 마지막 조각이 완성되고 있어요</div>
        </div>
        <div class="recentlist-content">
            <div class="recentlist-imageslider">
                <div class="recentlist-track">
                    <div v-for="(image, index) in recentList" :key="index" class="recentlist-slider">
                        <img :src="image.frontImg" class="recentlist-image" />
                    </div>
                </div>

            </div>
            <SmallButton :smallButtonContent="'보러가기'" :smallButtonFunction="startPiece" class="recentlist-button">
            </SmallButton>
        </div>

    </div>
</template>

<script setup>
import { onMounted, computed } from 'vue';
import { usePiecelistStore } from "@/stores/piecelist";
import SmallButton from '@/components/button/SmallButton.vue';
import { useRouter } from 'vue-router';

const listStore = usePiecelistStore();
const router = useRouter();

const recentList = computed(() => listStore.getRecentPieceList);

const startPiece = () => {
    router.push("/piecelist");
}

onMounted(() => {
    listStore.findRecentPieceList();
})

</script>

<style scoped>
.recentlist-title {
    display: flex;
    align-items: center;
    margin: 1rem;
}

.recentlist-title-icon {
    width: 1.6rem;
    height: 1.6rem;
}

.recentlist-title-text {
    font-size: 1rem;
    font-family: Bold;
    margin-left: 1rem;
}

.recentlist-content {
    background-color: var(--sub2-color);
    height: 16.25rem;
    width: 360px;
    display: flex;
    flex-direction: column;
    align-items: center;
}


/* 이미지 슬라이더 */
.recentlist-imageslider {
    height: 10rem;
    margin: 1.2rem;
    overflow: hidden;
    position: relative;
    /* width: 22.5rem; */
    width: 360px;
}

.recentlist-imageslider::before,
.recentlist-imageslider::after {
    content: "";
    height: 18.75rem;
    position: absolute;
    width: 22.5rem;
    z-index: 2;
}

.recentlist-imageslider::after {
    right: 0;
    top: 0;
    transform: rotateZ(180deg);
}

.recentlist-imageslider::before {
    left: 0;
    top: 0;
}

.recentlist-track {
    animation: scroll 40s linear infinite;
    display: flex;
    width: calc(7rem * 10);
}

.recentlist-slider {
    width: 7rem;
    height: 10rem;
    padding: 0.75rem;
}

.recentlist-image {
    width: 7rem;
    height: 10rem;
}

@keyframes scroll {
    0% {
        transform: translateX(0);
    }

    100% {
        transform: translateX(calc(-7rem * 7));
    }
}
</style>