<template>
    <div class="passwordview-main-container">
        <div class="passwordview-main-title">비밀번호를 입력해주세요</div>

        <div class="passwordview-input-label">비밀번호</div>

        <div class="passwordview-input-container">
            <input
                class="passwordview-input-input"
                type="password"
                minlength="10"
                required
                @input="checkPassword"
            />
            <!-- visible -->
            <font-awesome-icon
                v-if="isVisible"
                @click="handleVisibleClick"
                class="passwordview-input-icon"
                :icon="['fas', 'eye']"
                style="color: var(--gray2-color)"
                width="0.8rem"
                height="0.8rem"
            />
            <font-awesome-icon
                v-if="!isVisible"
                @click="handleVisibleClick"
                class="passwordview-input-icon"
                :icon="['fas', 'eye-slash']"
                style="color: var(--gray2-color)"
                width="0.9rem"
                height="0.9rem"
            />
        </div>
        <div class="passwordview-input-message">
            {{ passwordMessage }}
        </div>
    </div>

    <SquareButton
        class="passwordview-main-button"
        :squareButtonContent="'확인'"
        :squareButtonFunction="handlePasswordClick"
        :isSquareDisable="isPassword"
    ></SquareButton>

    <!-- modal -->
    <SuccessModal
        v-if="isModal"
        :modalTitle="'비밀번호가 변경되었어요!'"
        :handleSuccessClick="handleChangeSuccess"
    />
</template>

<script setup>
import { ref } from "vue";
import router from "@/router";
import SquareButton from "@/components/button/SquareButton.vue";
import SuccessModal from "@/components/modal/SuccessModal.vue";

const passwordMessage = ref("8자 이상의 영문, 숫자만 가능해요");
const isPassword = ref(false);
const isModal = ref(false);
const isVisible = ref(true);

const checkPassword = (e) => {
    if (e.target.value < 8) {
        isPassword.value = false;
        passwordMessage.value = "8자 이상의 영문, 숫자만 가능해요";
    } else {
        isPassword.value = true;
        passwordMessage.value = "사용 가능한 비밀번호예요";
    }
};

const handleVisibleClick = () => {
    const passwordInput = document.querySelector(".passwordview-input-input");
    if (passwordInput.type === "password") {
        passwordInput.type = "text";
    } else {
        passwordInput.type = "password";
    }
    isVisible.value = !isVisible.value;
};

const handlePasswordClick = () => {
    isModal.value = true;
    // ---------------------
    // TODO: 비밀번호 변경 api 연결
    // ---------------------
};

const handleChangeSuccess = () => {
    router.push({ name: "mypage" });
};
</script>

<style>
.passwordview-main-container {
    display: flex;
    flex-direction: column;
    height: 100%;
    padding-top: 1rem;
    margin: 0 1rem 0 1rem;
}

.passwordview-main-title {
    font-family: "Bold";
    font-size: 1.6rem;
    color: var(--black-color);
    margin: 0 0 2rem 0;
    user-select: none;
}

.passwordview-input-label {
    font-family: "Medium";
    font-size: 0.8rem;
    color: var(--gray2-color);
    user-select: none;
}

.passwordview-input-container {
    position: relative;
}

.passwordview-input-input {
    font-family: "Regular";
    font-size: 1.2rem;
    color: var(--gray2-color);
    width: 100%;
    height: 2.25rem;
    border: none;
    border-bottom: 0.1rem solid var(--gray-color);
    transition: border-bottom-color 0.1s;
}

.passwordview-input-input:focus {
    outline: none;
    color: var(--main-color);
    border-bottom-color: var(--main-color);
}

.passwordview-input-icon {
    position: absolute;
    top: 50%;
    right: 0.5rem;
    transform: translateY(-50%);
}

.passwordview-input-message {
    font-family: "Regular";
    font-size: 0.8rem;
    color: var(--gray2-color);
    margin-top: 0.5rem;
    user-select: none;
}

.passwordview-main-button {
    position: fixed;
    bottom: 2.75rem;
}
</style>
