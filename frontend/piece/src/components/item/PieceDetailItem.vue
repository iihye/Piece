<template>
    <div class="pieceDetailView-image-container">
        <img
            class="pieceDetailView-image-item"
            :src="
                imgFrontBack
                    ? frontImg
                    : backImg
            "
            :alt="title"
            @click="handleImageClick"
        />
        <div v-if="imgFrontBack" class="pieceDetailView-image-message">
            조각을 클릭하여 뒷면을 확인해보세요
        </div>
        <div v-else class="pieceDetailView-image-message">
            조각을 클릭하여 앞면을 확인해보세요
        </div>
    </div>

            <!-- 조각 앞면-->
    <ToastSuccessModal
        v-if="imgFrontModal"
        class="modal"
        :class="{ 'modal-hidden': isFading }"
        :iconTitle="['fas', 'check']"
        :modalTitle="'조각의 앞면이예요.'"
    />

        <!-- 조각 뒷면-->
    <ToastSuccessModal
        v-if="imgBackModal"
        class="modal"
        :class="{ 'modal-hidden': isFading }"
        :iconTitle="['fas', 'check']"
        :modalTitle="'조각의 뒷면이예요.'"
    />
</template>

<script setup>
import { ref, defineProps } from "vue";
import ToastSuccessModal from "@/components/modal/ToastSuccessModal.vue";

const imgFrontModal = ref(false);
const imgBackModal = ref(false);

const props = defineProps({
    frontImg: String,
    backImg: String,
    title: String,
    // inputType: { type: String, default: "text" },
});



const imgFrontBack = ref(true);
const isFading = ref(false);

const handleImageClick = () => {
    imgFrontBack.value = !imgFrontBack.value;
    if (imgFrontBack.value) {
        imgFrontModal.value = true;

        isFading.value = true;
        setTimeout(() => {
            imgFrontModal.value = false;
        }, 1000);

        isFading.value = false;
    } else {
        imgBackModal.value = true;

        isFading.value = true;
        setTimeout(() => {
            imgBackModal.value = false;
        }, 1000);

        isFading.value = false;
    }
};

</script>

<style>
.pieceDetailView-image-container {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}

.pieceDetailView-image-message {
    display: flex;
    justify-content: center;
    align-content: center;
    margin: 0.6rem 2rem 0.6rem 2rem;
    font-family: "Regular";
    font-size: 1rem;
    color: var(--gray2-color);
    width: 284px;
    user-select: none;
}

.pieceDetailView-image-item {
    width: 284px;
    height: 464px;
    display: flex;
    justify-content: center;
    align-content: center;
    user-select: none;
}
</style>