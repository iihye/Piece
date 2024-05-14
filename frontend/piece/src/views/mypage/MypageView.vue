<template>
    <div class="mypage-main-container">
        <div class="mypage-preview-container">
            <div class="mypage-preview-image-container">
                <img
                    v-if='loginUserInfo.profileImage === null'
                    class="mypage-preview-img"
                    src="@/assets/defaultprofile.png"
                />
                <img
                    v-else
                    class="mypage-preview-img"
                    :src="loginUserInfo.profileImage"
                />
                
            </div>
            <div class="mypage-preview-sub-container">
                <div class="mypage-preview-label">{{ loginUserLabel }}</div>
                <div class="mypage-preview-nickname">
                    {{ loginUserInfo.nickname }}님 반가워요!
                </div>
                <div class="mypage-preview-button-container">
                    <button
                        class="mypage-preview-button"
                        @click="handleMypiece"
                    >
                        내 조각
                    </button>
                    <button
                        class="mypage-preview-button"
                        @click="handleMyheart"
                    >
                        내 찜
                    </button>
                </div>
            </div>
        </div>

        <div class="mypage-router-container">
            <div class="mypage-router-subcontainer">
                <div class="mypage-router-title">내 프로필</div>
                <IconText
                    :itemContent="'닉네임 수정'"
                    :itemIcon="['fas', 'user']"
                    :handleClick="handleNicknameClick"
                ></IconText>
                <IconText
                    :itemContent="'비밀번호 수정'"
                    :itemIcon="['fas', 'lock']"
                    :handleClick="handlePasswordClick"
                ></IconText>
                <IconText
                    :itemContent="'프로필 이미지 수정'"
                    :itemIcon="['fas', 'camera']"
                    :handleClick="handleProfileImgClick"
                ></IconText
                ><IconText
                    :itemContent="'칭호 목록'"
                    :itemIcon="['fas', 'tag']"
                    :handleClick="handleLabelClick"
                ></IconText>
            </div>

            <div class="mypage-router-subcontainer">
                <div class="mypage-router-title">조각 통계</div>
                <IconText
                    :itemContent="'조각 통계'"
                    :itemIcon="['fas', 'chart-pie']"
                    :handleClick="handlePieClick"
                ></IconText>
                <IconText
                    :itemContent="'소비 통계'"
                    :itemIcon="['fas', 'chart-simple']"
                    :handleClick="handleConsumeClick"
                ></IconText>
            </div>

            <div class="mypage-router-subcontainer">
                <div class="mypage-router-item" @click="handleLogoutClick">
                    로그아웃
                </div>
            </div>

            <!-- <div class="mypage-router-subcontainer">
                <div class="mypage-router-title" @click="handleWithdrawalClick">
                    회원탈퇴
                </div>
            </div> -->
        </div>
    </div>
</template>

<script setup>
import router from "@/router";
import { onMounted, computed } from "vue";
import { useCommonStore } from "@/stores/common";
import IconText from "@/components/text/IconText.vue";
import axios from "axios";

const commonStore = useCommonStore();

const loginUserInfo = computed(() => commonStore.getLoginUserInfo);
const loginUserLabel = computed(() => commonStore.getLoginUserLabel);

function handleMypiece() {
    router.push({ name: "pieceListMy" });
}

function handleMyheart() {
    router.push({ name: "pieceListHeart" });
}

function handleNicknameClick() {
    router.push({ name: "nickname" });
}

function handlePasswordClick() {
    router.push({ name: "password" });
}

function handleProfileImgClick() {
    router.push({ name: "profileImg" });
}

function handleLabelClick() {
    router.push({ name: "label" });
}

function handlePieClick() {
    router.push({ name: "piece" });
}

function handleConsumeClick() {
    router.push({ name: "consume" });
}

function handleLogoutClick() {
    alert("로그아웃 클릭");

    // 로그아웃 성공시에 실행
    commonStore.loginUser = "";
    commonStore.isLogin = false;
    localStorage.clear();
    axios.defaults.headers.common["Authorization"] = undefined;
    router.push({ name: "main" });
}

function handleWithdrawalClick() {
    alert("회원탈퇴 클릭");
}

onMounted(async () => {
    commonStore.headerTitle = "내정보";
    commonStore.headerType = "header2";

    await commonStore.findLoginUserInfo();
});
</script>

<style>
.mypage-main-container {
    display: flex;
    flex-direction: column;
    height: 100%;
}

/* preview */
.mypage-preview-container {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 10rem;
    background-color: var(--sub2-color);
    margin-bottom: 1.8rem;
    user-select: none;
}

.mypage-preview-image-container {
    margin-right: 1rem;
}

.mypage-preview-img {
    width: 5rem;
    height: 5rem;
    border: 1px solid var(--gray-color);
    border-radius: 50%;
    object-fit: cover;
    background-color: var(--white-color);
}

.mypage-preview-label {
    font-family: "bold";
    font-size: 1rem;
    color: var(--main-color);
    margin-top: 0.8rem;
}

.mypage-preview-nickname {
    font-family: "Semi";
    font-size: 1rem;
    color: var(--black-color);
    margin-top: 0.2rem;
}

.mypage-preview-button-container {
    display: flex;
    justify-content: space-between;
    margin-top: 0.6rem;
}

.mypage-preview-button-container button {
    width: 100%;
    min-width: 5rem;
    height: 2rem;
    font-size: 0.8rem;
    font-family: "Regular";
    color: var(--white-color);
    background-color: var(--main-color);
    border: none;
    border-radius: 0.4rem;
    margin-right: 0.4rem;
}

.mypage-preview-button-container button:hover {
    cursor: pointer;
}

/* router */
.mypage-router-container {
}

.mypage-router-subcontainer {
    margin: 0 2rem 2rem 1rem;
}

.mypage-router-title {
    font-family: "bold";
    font-size: 1.2rem;
    color: var(--black-color);
    margin-bottom: 1rem;
    user-select: none;
}

.mypage-router-item {
    font-family: "bold";
    font-size: 1.2rem;
    color: var(--black-color);
    margin-bottom: 1rem;
    user-select: none;
}

.mypage-router-item:hover {
    cursor: pointer;
}
</style>
