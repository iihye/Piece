<template>
    <div class="labelview-main-container">
        <div class="labelview-main-title">나를 표현하는 칭호를 골라주세요</div>
        <div class="labelview-main-content">칭호를 클릭하여 착용해보세요</div>

        <!-- preview -->
        <div class="labelview-preview-container">
            <img
                class="labelview-preview-img"
                :src="
                    loginUserInfo.profileImage ||
                    'https://i.ibb.co/grMvZS9/your-image.jpg'
                "
                alt="image"
            />
            <div class="labelview-preview-label">{{ loginUserLabel }}</div>
            <div class="labelview-preview-nickname">
                {{ loginUserInfo.nickname }}
            </div>
        </div>

        <div class="labelview-button-container">
            <SmallButton
                :smallButtonContent="'칭호 해제'"
                :smallButtonFunction="handleItemWearoffClick"
            ></SmallButton>
        </div>

        <!-- select -->
        <div class="labelview-selct-container">
            <select class="labelview-select-dropbox" v-model="selectedOption">
                <option value="select-all">전체</option>
                <option value="select-mine">보유 중인 칭호</option>
                <option value="select-not">보유하지 않은 칭호</option>
            </select>
        </div>

        <!-- list -->
        <div class="labelview-list-container">
            <LabelItem
                v-for="(item, index) in filteredLabelList"
                :key="index"
                :labelType="item.labelType"
                :title="item.title"
                :description="item.description"
                :isMine="item.myLabels"
                :isWear="item.wearLabels"
                @click="
                    item.myLabels &&
                        (item.wearLabels
                            ? handleItemWearoffClick(item.labelId)
                            : handleItemWearClick(item.labelId))
                "
            ></LabelItem>
        </div>

        <!-- 칭호 착용 모달 -->
        <SuccessModal
            v-if="wearModal"
            :modalTitle="'칭호를 착용했어요!'"
            :handleSuccessClick="handleWearSuccess"
        />

        <!-- 칭호 착용 해제 모달 -->
        <SuccessModal
            v-if="wearoffModal"
            :modalTitle="'칭호를 해제했어요!'"
            :handleSuccessClick="handleWearoffSuccess"
        />
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useCommonStore } from "@/stores/common";
import { useUserStore } from "@/stores/user";
import LabelItem from "@/components/item/LabelItem.vue";
import SuccessModal from "@/components/modal/SuccessModal.vue";
import SmallButton from "@/components/button/SmallButton.vue"; // 사용할 컴포넌트 import

const commonStore = useCommonStore();
const store = useUserStore();

const loginUserInfo = computed(() => commonStore.getLoginUserInfo);
const loginUserLabel = computed(() => commonStore.getLoginUserLabel);

const mypageLabelList = computed(() => store.getMypageLabelList);
const filteredLabelList = computed(() => computeFilteredLabelList());
const mypageLabelWearoff = computed(() => store.getMypageLabelWearoff);

const selectedOption = ref("select-all");
const wearModal = ref(false);
const wearoffModal = ref(false);

function computeFilteredLabelList() {
    if (selectedOption.value === "select-all") {
        return mypageLabelList.value;
    } else if (selectedOption.value === "select-mine") {
        return mypageLabelList.value.filter((item) => item.myLabels);
    } else if (selectedOption.value === "select-not") {
        return mypageLabelList.value.filter((item) => !item.myLabels);
    } else {
        return [];
    }
}

const handleItemWearClick = (labelId) => {
    wearModal.value = true;
    store.addMypageLabelWear(labelId);
};

const handleItemWearoffClick = () => {
    wearoffModal.value = true;
    store.deleteMypageLabelWear();
};

const handleWearSuccess = () => {
    wearModal.value = false;
};

const handleWearoffSuccess = () => {
    wearoffModal.value = false;
};

onMounted(async () => {
    commonStore.headerTitle = "칭호 목록";
    commonStore.headerType = "header2";

    await commonStore.findLoginUserInfo();
    await store.checkMypageLabelList();
    // ---------------------
    // TODO: 유저 정보 불러오기 api 연결
    // ---------------------
});
</script>

<style>
.labelview-main-container {
    display: flex;
    flex-direction: column;
    height: calc(100vh - 8.25rem);
    padding-top: 1rem;
    margin: 0 1rem 0 1rem;
}

.labelview-main-title {
    font-family: "Bold";
    font-size: 1.6rem;
    color: var(--black-color);
    margin: 0 0 0.4rem 0;
    user-select: none;
}

.labelview-main-content {
    font-family: "Regular";
    font-size: 1rem;
    color: var(--gray2-color);
    margin: 0;
    margin-bottom: 1rem;
    display: flex;
    justify-content: left;
    align-items: center;
    user-select: none;
}

.labelview-preview-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border: 0.15rem solid var(--main-color);
    border-radius: 0.625rem;
    padding: 1rem;
    margin-bottom: 0.6rem;
    height: 10rem;
}

.labelview-preview-img {
    width: 5rem;
    height: 5rem;
    border: 0.15rem solid var(--gray-color);
    border-radius: 50%;
}

.labelview-preview-label {
    font-family: "bold";
    font-size: 1rem;
    color: var(--main-color);
    margin-top: 0.8rem;
}

.labelview-preview-nickname {
    font-family: "Semi";
    font-size: 1rem;
    color: var(--black-color);
    margin-top: 0.2rem;
}

.labelview-button-container {
    display: flex;
    justify-content: center;
    margin-bottom: 1rem;
}

.labelview-selct-container {
    display: flex;
    justify-content: right;
    margin-bottom: 0.6rem;
}

.labelview-select-dropbox {
    width: 10rem;
    height: 2.4rem;
    font-family: "Medium";
    font-size: 1rem;
    color: var(--gray2-color);
    border: 1px solid var(--gray-color);
    padding-left: 0.2rem;
}

.labelview-list-container {
    overflow-y: scroll;
    flex: 1;
}

.labelview-list-container::-webkit-scrollbar {
    width: 0.2rem;
}

.labelview-list-container::-webkit-scrollbar-thumb {
    background-color: var(--gray2-color);
    border-radius: 1rem;
}

.labelview-list-container::-webkit-scrollbar-track {
    background-color: var(--gray-color);
    border-radius: 1rem;
}
</style>
