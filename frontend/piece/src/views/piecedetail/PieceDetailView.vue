<template>
    <div class="pieceDetailView-main-container">
        <!-- piece -->
        <div class="pieceDetailView-piece-container">
            <div class="pieceDetailView-top-container">
                <!-- user -->
                <div class="pieceDetailView-user-container">
                    <img class="pieceDetailView-user-img" :src="userDetail.profileImg" alt="image" />
                    <div class="pieceDetailView-name-container">
                        <div class="pieceDetailView-user-label">
                            {{ userDetail.label }}
                        </div>
                        <div class="pieceDetailView-user-nickname">
                            {{ userDetail.nickname }}
                        </div>
                    </div>
                </div>
                <!-- icon -->
                <div class="pieceDetailView-icon-container">
                    <font-awesome-icon
                        class="pieceDetailView-top-icon"
                        :icon="['fas', 'ellipsis-vertical']"
                        @click="handleModal"
                    />
                </div>
            </div>
            <!-- image -->
            <div class="pieceDetailView-image-container">
                <img
                    class="pieceDetailView-image-item"
                    :src="imgFrontBack ? piecelistDetail.frontImg : piecelistDetail.backImg"
                    :alt="piecelistDetail.title"
                    @click="handleImageClick"
                />
            </div>
            <!-- icon -->
            <div class="pieceDetailView-heart-container">
                <font-awesome-icon
                    class="pieceDetailView-heart-icon"
                    :icon="pieceDetailHeart ? ['fas', 'heart'] : ['far', 'heart']"
                    style="color: var(--main-color)"
                    @click="handleHeartClick"
                />
            </div>
        </div>

        <!-- button -->
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

        <!-- modal -->
        <ShareSelectModal
            v-if="selectModal"
            :handleLinkClick="handleLink"
            :handleKakaoClick="handleKakao"
            :handleFailClick="handleFail"
            :handleDeleteClick="handleDelete"
            :handleReportClick="handleReport"
            :isMine="userId === piecelistDetail.userId ? true : false"
        ></ShareSelectModal>

        <ImageSuccessModal
            v-if="linkSuccessModal"
            :modalTitle="'링크가 복사되었어요!'"
            :handleSuccessClick="handleLinkSuccess"
        />
    </div>
</template>

<script setup>
import router from "@/router";
import { ref, computed, onMounted } from "vue";
import { useRoute } from "vue-router";
import { usePiecelistStore } from "@/stores/piecelist";
import RoundButton from "@/components/button/RoundButton.vue";
import ShareSelectModal from "@/components/modal/ShareSelectModal.vue";
import ImageSuccessModal from "@/components/modal/ImageSuccessModal.vue";

const store = usePiecelistStore();
const route = useRoute();
const imgFrontBack = ref(true);
const selectModal = ref(false);
const linkSuccessModal = ref(false);
// user dummy data 추후 수정
const userId = ref(2);
const isMine = computed(() => userId.value === piecelistDetail.value.userId);

// userDetail dummy data 추후 수정
const userDetail = ref({
    label: "새로운",
    nickname: "김싸피",
    profileImg: "https://i.ibb.co/grMvZS9/your-image.jpg",
});
const piecelistDetail = computed(() => store.getPiecelistDetail);
const pieceDetailHeart = computed(() => store.getPieceDetailHeart);

const handleImageClick = () => {
    imgFrontBack.value = !imgFrontBack.value;
};

const handleHeartClick = () => {
    if (pieceDetailHeart.value) {
        store.deletePieceDetailHeart(piecelistDetail.value.pieceId);
        store.findPieceDetailHeart(piecelistDetail.value.pieceId);
    } else {
        store.addPieceDetailHeart(piecelistDetail.value.pieceId);
        store.findPieceDetailHeart(piecelistDetail.value.pieceId);
    }
};

const handleBackListClick = () => {
    router.go(-1);
};

const handleRecordClick = () => {
    router.push({
        name: "recordDetail",
        params: { pieceId: piecelistDetail.value.pieceId },
    });
};

const handleLink = () => {
    const currentUrl = window.location.href;
    navigator.clipboard.writeText(currentUrl);
    linkSuccessModal.value = true;
    handleFail();
};

const handleLinkSuccess = () => {
    linkSuccessModal.value = false;
};

const handleKakao = () => {
    alert("서비스 준비중입니다");
};

const handleDelete = () => {
    alert("삭제");
};

const handleReport = () => {
    alert("신고");
};

const handleModal = () => {
    selectModal.value = true;
};

const handleFail = () => {
    selectModal.value = false;
};

onMounted(async () => {
    const pieceId = route.params.pieceId;
    await store.findPiecelistDetail(pieceId);
    await store.findPieceDetailHeart(pieceId);
});
</script>

<style>
.pieceDetailView-main-container {
    display: flex;
    flex-direction: column;
    height: 100%;
}

.pieceDetailView-user-container {
    display: flex;
    flex-direction: row;
    justify-content: left;
    align-items: center;
    margin-bottom: 0.6rem;
}

.pieceDetailView-user-img {
    width: 3rem;
    height: 3rem;
    border: 0.15rem solid var(--gray-color);
    border-radius: 50%;
    margin-right: 0.6rem;
}

.pieceDetailView-user-label {
    font-family: "bold";
    font-size: 1rem;
    color: var(--main-color);
    margin-bottom: 0.2rem;
}

.pieceDetailView-user-nickname {
    font-family: "Semi";
    font-size: 1rem;
    color: var(--black-color);
}

.pieceDetailView-piece-container {
    display: flex;
    flex: 1;
    flex-direction: column;
    justify-content: center;
    align-content: center;
    margin: 20px 0;
}

.pieceDetailView-top-container {
    display: flex;
    justify-content: space-between;
    padding: 0 2rem 0.6rem 2rem;
}

.pieceDetailView-icon-container {
    display: flex;
    justify-content: flex-end;
    align-items: center;
}

.pieceDetailView-top-icon {
    margin-left: 1rem;
}

.pieceDetailView-image-container {
    display: flex;
    justify-content: center;
    align-content: center;
}

.pieceDetailView-image-item {
    width: 284px;
    height: 464px;
}

.pieceDetailView-heart-container {
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 0.6rem 2rem 0 2rem;
}

.pieceDetailView-heart-icon {
    width: 1.2rem;
    height: 1.2rem;
}

.pieceDetailView-button-container {
    display: flex;
    flex: none;
    justify-content: center;
    margin: 1rem;
}

.pieceDetailView-button-button {
    flex: 1;
    margin-left: 0.2rem;
    margin-right: 0.2rem;
}

.pieceDetailView-button-button button {
    width: 100%;
}
</style>
