<template>
    <div class="pieceDetailView-main-container">
        <!-- piece -->
        <div class="pieceDetailView-piece-container">
            <div class="pieceDetailView-top-container">
                <!-- user -->
                <div class="pieceDetailView-user-container">
                    <img
                        class="pieceDetailView-user-img"
                        :src="
                            pieceUser.profileImage ||
                            'https://i.ibb.co/grMvZS9/your-image.jpg'
                        "
                        alt="image"
                    />
                    <div class="pieceDetailView-name-container">
                        <div class="pieceDetailView-user-label">
                            {{ pieceUserLabel }}
                        </div>
                        <div class="pieceDetailView-user-nickname">
                            {{ pieceUser.nickname }}
                        </div>
                    </div>
                </div>
                <!-- icon -->
                <div
                    class="pieceDetailView-icon-container"
                    @click="handleModalSuccess"
                >
                    <font-awesome-icon
                        class="pieceDetailView-top-icon"
                        :icon="['fas', 'ellipsis-vertical']"
                    />
                </div>
            </div>
            <!-- image -->
            <div class="pieceDetailView-image-container">
                <img
                    class="pieceDetailView-image-item"
                    :src="
                        imgFrontBack
                            ? piecelistDetail.frontImg
                            : piecelistDetail.backImg
                    "
                    :alt="piecelistDetail.title"
                    @click="handleImageClick"
                />
                <div v-if="imgFrontBack" class="pieceDetailView-image-message">
                    조각을 클릭하여 뒷면을 확인해보세요
                </div>
                <div v-else class="pieceDetailView-image-message">
                    조각을 클릭하여 앞면을 확인해보세요
                </div>
            </div>

            <!-- icon -->
            <div class="pieceDetailView-heart-container">
                <font-awesome-icon
                    class="pieceDetailView-heart-icon"
                    :icon="
                        pieceDetailHeart ? ['fas', 'heart'] : ['far', 'heart']
                    "
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
                v-if="isMine"
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
            :handleFailClick="handleModalFail"
            :handleDeleteClick="handleDelete"
            :handleReportClick="handleReport"
            :isMine="userId === piecelistDetail.userId ? true : false"
        ></ShareSelectModal>

        <!-- 링크 성공 -->
        <ToastSuccessModal
            v-if="linkSuccessModal"
            class="modal"
            :class="{ 'modal-hidden': isFading }"
            :iconTitle="['fas', 'link']"
            :modalTitle="'링크가 복사되었어요.'"
            :handleSuccessClick="handleLinkSuccess"
        />

        <!-- 신고 -->
        <ReportSelectModal
            v-if="reportModal"
            :handleFailClick="handleReportFail"
            :handleSuccessClick="handleReportSuccess"
        ></ReportSelectModal>

        <!-- 신고 성공 -->
        <ToastSuccessModal
            v-if="reportSuccessModal"
            class="modal"
            :class="{ 'modal-hidden': isFading }"
            :iconTitle="['fas', 'flag']"
            :modalTitle="'신고가 완료되었어요.'"
        />

        <!-- 찜 성공 -->
        <ToastSuccessModal
            v-if="heartSuccessModal"
            class="modal"
            :class="{ 'modal-hidden': isFading }"
            :iconTitle="['fas', 'heart']"
            :modalTitle="'조각을 찜했어요.'"
        />

        <!-- 찜 해제 성공 -->
        <ToastSuccessModal
            v-if="heartFailModal"
            class="modal"
            :class="{
                'modal-hidden': isFading,
            }"
            :iconTitle="['fas', 'heart']"
            :modalTitle="'조각을 찜을 해제했어요.'"
        />

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
    </div>
</template>

<script setup>
import router from "@/router";
import { ref, computed, onMounted } from "vue";
import { useCommonStore } from "@/stores/common";
import { useRoute } from "vue-router";
import { usePiecelistStore } from "@/stores/piecelist";
import RoundButton from "@/components/button/RoundButton.vue";
import ShareSelectModal from "@/components/modal/ShareSelectModal.vue";
import ReportSelectModal from "@/components/modal/ReportSelectModal.vue";
import ToastSuccessModal from "@/components/modal/ToastSuccessModal.vue";

const commonStore = useCommonStore();
const store = usePiecelistStore();

const route = useRoute();
const imgFrontBack = ref(true);
const selectModal = ref(false);
const linkSuccessModal = ref(false);
const reportModal = ref(false);
const userId = localStorage.getItem("userId");
const heartSuccessModal = ref(false);
const heartFailModal = ref(false);
const isFading = ref(false);
const reportSuccessModal = ref(false);
const imgFrontModal = ref(false);
const imgBackModal = ref(false);

const pieceUser = computed(() => store.getPieceUser);
const pieceUserLabel = computed(() => store.getPieceUserLabel);

const piecelistDetail = computed(() => store.getPiecelistDetail);
const pieceDetailHeart = computed(() => store.getPieceDetailHeart);

const isMine = computed(() => store.getIsMine);

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

const handleHeartClick = () => {
    if (pieceDetailHeart.value) {
        store.deletePieceDetailHeart(piecelistDetail.value.pieceId);
        store.findPieceDetailHeart(piecelistDetail.value.pieceId);
        heartFailModal.value = true;

        isFading.value = true;
        setTimeout(() => {
            heartFailModal.value = false;
        }, 1500);

        isFading.value = false;
    } else {
        store.addPieceDetailHeart(piecelistDetail.value.pieceId);
        store.findPieceDetailHeart(piecelistDetail.value.pieceId);
        heartSuccessModal.value = true;

        isFading.value = true;
        setTimeout(() => {
            heartSuccessModal.value = false;
        }, 1500);

        isFading.value = false;
    }
};

const handleBackListClick = () => {
    router.go(-1);
};

const handleRecordClick = () => {
    router.push({
        name: "recordDetail",
    });
    store.setPieceDetailViewId(piecelistDetail.value.pieceId);
};

const handleLink = () => {
    const currentUrl = window.location.href;
    navigator.clipboard.writeText(currentUrl);
    linkSuccessModal.value = true;

    isFading.value = true;
    setTimeout(() => {
        linkSuccessModal.value = false;
    }, 1500);

    isFading.value = false;

    handleModalFail();
};

const handleLinkSuccess = () => {
    linkSuccessModal.value = false;
};

const handleKakao = () => {
    alert("서비스 준비중입니다");
};

const handleDelete = () => {
    store.deletePieceDetail(piecelistDetail.value.pieceId);
    router.go(-1);
};

const handleReport = () => {
    reportModal.value = true;
};

const handleReportFail = () => {
    reportModal.value = false;
    handleModalFail();
};

const handleReportSuccess = () => {
    reportModal.value = false;
    handleModalFail();

    reportSuccessModal.value = true;
    isFading.value = true;
    setTimeout(() => {
        reportSuccessModal.value = false;
    }, 1500);

    isFading.value = false;
};

const handleModalSuccess = () => {
    selectModal.value = true;
};

const handleModalFail = () => {
    selectModal.value = false;
};

const handleHeartSuccess = () => {
    heartSuccessModal.value = false;
};

const handleHeartFail = () => {
    heartFailModal.value = false;
};

onMounted(async () => {
    commonStore.headerTitle = "조각 상세보기";
    commonStore.headerType = "header2";

    const pieceId = route.params.pieceId;
    await store.findPiecelistDetail(pieceId);
    await store.findPieceDetailHeart(pieceId);
});
</script>

<style>
.modal {
    transition: opacity 1s ease;
    opacity: 1;
}

.modal-hidden {
    opacity: 0;
    pointer-events: none;
}

.pieceDetailView-main-container {
    display: flex;
    flex-direction: column;
    height: calc(100vh - 8.25rem);
}

.pieceDetailView-user-container {
    display: flex;
    flex-direction: row;
    justify-content: left;
    align-items: center;
    margin-bottom: 0.6rem;
    user-select: none;
}

.pieceDetailView-user-img {
    width: 3rem;
    height: 3rem;
    border: 0.05rem solid var(--gray-color);
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

.pieceDetailView-icon-container:hover {
    cursor: pointer;
}

.pieceDetailView-top-icon {
    margin-left: 1rem;
}

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

.pieceDetailView-heart-icon:hover {
    cursor: pointer;
}

.pieceDetailView-button-container {
    display: flex;
    flex: none;
    justify-content: center;
}

.pieceDetailView-button-button {
    flex: 1;
    margin-left: 0.2rem;
    margin-right: 0.2rem;
}

.pieceDetailView-button-button button {
    width: 100%;
}

.pieceDetailView-button-button button:hover {
    cursor: pointer;
}
</style>
