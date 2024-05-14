<template>
    <div class="signinview-main-container">
        <div class="signinview-main-title">piece에 오신 것을 환영해요!</div>

        <div class="signinview-input-container">
            <form @submit.prevent="submitForm">
                <TextInput
                    textInputName="이메일"
                    textInputPlaceholder="이메일을 입력하세요"
                    @textInputValue="email = $event"
                    textInputRequired="true"
                />
                <TextInput
                    textInputName="비밀번호"
                    textInputPlaceholder="비밀번호를 입력하세요"
                    @textInputValue="password = $event"
                    textInputRequired="true"
                    textInputType="password"
                />
                <TextInput
                    textInputName="닉네임"
                    textInputPlaceholder="닉네임을 입력하세요"
                    @textInputValue="nickname = $event"
                    textInputRequired="true"
                />
                <button
                    type="submit"
                    class="signinview-input-button"
                    :disabled="!isFormValid"
                >
                    확인
                </button>
            </form>
        </div>
    </div>

    <!-- modal -->
    <SuccessModal
        v-if="isSuccessModal"
        :modalTitle="'회원가입이 완료되었습니다'"
        :handleSuccessClick="handleSuccessClick"
    />

    <SuccessModal
        v-if="isFailModal"
        :modalTitle="'회원가입에 실패하였습니다'"
        :handleSuccessClick="handleFailClick"
    />
</template>

<script setup>
import axios from "axios";
import { ref, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import { useCommonStore } from "@/stores/common";
import TextInput from "@/components/text/TextInput.vue";
import SuccessModal from "@/components/modal/SuccessModal.vue";

const commonStore = useCommonStore();

const email = ref("");
const password = ref("");
const nickname = ref("");
const router = useRouter();
const isFormValid = ref(false);
const isSuccessModal = ref(false);
const isFailModal = ref(false);

watch([email, password, nickname], ([newEmail, newPassword, newNickname]) => {
    isFormValid.value =
        newEmail.trim() !== "" &&
        newPassword.trim() !== "" &&
        newNickname.trim() !== "";
});

const submitForm = async () => {
    try {
        const response = await axios.post(
            `${import.meta.env.VITE_REST_USER_API}/users/register`,
            {
                email: email.value,
                password: password.value,
                nickname: nickname.value,
            }
        );
        isSuccessModal.value = true;
    } catch (error) {
        isFailModal.value = true;
    }
};

const handleSuccessClick = () => {
    router.push({ name: "login" });
};

const handleFailClick = () => {
    isFailModal.value = false;
};

onMounted(() => {
    commonStore.headerTitle = "이메일로 시작하기";
    commonStore.headerType = "header2";
});
</script>

<style>
.signinview-main-container {
    display: flex;
    flex-direction: column;
    min-height: calc(100vh - 7.25rem);
}

.signinview-main-title {
    font-family: "Bold";
    font-size: 1.6rem;
    color: var(--black-color);
    margin: 1rem 1rem 2rem 1rem;
    user-select: none;
}

.signinview-input-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-bottom: 1rem;
}

.signinview-input-button {
    background-color: var(--main-color);
    border: 0;
    width: 22.5rem;
    height: 4rem;
    color: var(--white-color);
    font-family: "Semi";
    font-size: 1rem;
    /* background-color: var(--main-color); */
    /* border: 0; */
    /* border-radius: 0.625rem; */
    /* width: 21.25rem; */
    /* height: 4rem; */
    /* color: var(--white-color); */
    /* font-family: "Semi"; */
    /* font-size: 1rem; */
    position: fixed;
    bottom: 4rem;
    left: 50%;
    transform: translateX(-50%);
}

.signinview-input-button:hover {
    cursor: pointer;
}

.signup-form {
    max-width: 400px;
    margin: auto;
    padding: 20px;
    text-align: center;
}

.signup-button {
    margin-top: 20px;
    width: 100%;
    padding: 10px;
    background-color: pink;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.signup-button:hover {
    background-color: pink;
}
</style>
