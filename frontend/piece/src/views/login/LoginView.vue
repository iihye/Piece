<template>
    <div class="loginview-main-container">
        <div class="loginview-main-title">piece에 다시 오셨군요!</div>
        <div class="loginview-sub-container">
            <div class="loginview-image-container">
                <img
                    class="loginview-main-image"
                    src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Food/Birthday%20Cake.png"
                    alt="Birthday Cake"
                    width="100"
                    height="100"
                />
            </div>

            <div class="loginview-input-container">
                <form @submit.prevent="submitLogin">
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

                    <button
                        type="submit"
                        class="loginview-input-button"
                        :disabled="!isFormValid"
                    >
                        로그인
                    </button>
                </form>
            </div>

            <div class="loginview-regist-container">
                <div class="loginview-regist-content">
                    아직 회원이 아니신가요?
                </div>
                <RouterLink to="/signin" class="loginview-regist-button"
                    >이메일로 시작하기</RouterLink
                >
            </div>
        </div>

        <!-- 로그인 성공 모달 -->
        <ImageSuccessModal
            v-if="loginSuccessModal"
            :modalTitle="'piece에서 조각을 완성해보세요!'"
            :handleSuccessClick="handleLoginSuccess"
        />

        <!-- 로그인 실패 모달 -->
        <SuccessModal
            v-if="loginFailModal"
            :modalTitle="'아이디 또는 비밀번호가 <br>일치하지 않습니다'"
            :handleSuccessClick="handleLoginFail"
        />
    </div>
</template>

<script setup>
import axios from "axios";
import { ref, computed, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import { useCommonStore } from "@/stores/common";
import { useUserStore } from "@/stores/user";
import TextInput from "@/components/text/TextInput.vue";
import ImageSuccessModal from "@/components/modal/ImageSuccessModal.vue";
import SuccessModal from "@/components/modal/SuccessModal.vue";

const commonStore = useCommonStore();
const userStore = useUserStore();

const email = ref("");
const password = ref("");
const router = useRouter();
const isFormValid = ref(false);
const loginSuccessModal = ref(false);
const loginFailModal = ref(false);
const loginUserInfo = computed(() => commonStore.getLoginUserInfo);
const nickname = ref("");

watch([email, password], ([newEmail, newPassword]) => {
    isFormValid.value = newEmail.trim() !== "" && newPassword.trim() !== "";
});

const submitLogin = async () => {
    try {
        const response = await axios.post(
            `${import.meta.env.VITE_REST_USER_API}/auth/login`,
            {
                email: email.value,
                password: password.value,
            }
        );

        const res = response.data.data;
        if (response.data.code == "LOGIN_SUCCESS") {
            // token 저장
            commonStore.loginUser = res.userId;
            commonStore.isLogin = true;
            const accessToken = res.token;
            axios.defaults.headers.common[
                "Authorization"
            ] = `Bearer ${accessToken}`;
            localStorage.setItem("accessToken", `Bearer ${accessToken}`);
            localStorage.setItem("userId", res.userId);

            loginSuccessModal.value = true;

            userStore.readTutorial();
        } else {
            // 실패 모달
            loginFailModal.value = true;
        }
    } catch (error) {
        console.log(error);
        loginFailModal.value = true;
    }
};

const handleLoginSuccess = () => {
    loginSuccessModal.value = false;
    router.push({ name: "main" });
};

const handleLoginFail = () => {
    loginFailModal.value = false;
};

onMounted(() => {
    commonStore.headerTitle = "로그인";
    commonStore.headerType = "header2";
});
</script>

<style>
.loginview-main-container {
    display: flex;
    flex-direction: column;
    min-height: calc(100vh - 7.25rem);
}

.loginview-main-title {
    font-family: "Bold";
    font-size: 1.6rem;
    color: var(--black-color);
    margin: 1rem 1rem 2rem 1rem;
    margin-bottom: 0.6rem;
    user-select: none;
}

.loginview-sub-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-top: 4rem;
}

.loginview-image-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 4rem;
}

.loginview-input-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-bottom: 1rem;
}

.loginview-input-button {
    background-color: var(--main-color);
    border: 0;
    border-radius: 0.625rem;
    margin: 0.8rem 0 0.8rem 0;
    width: 21.25rem;
    height: 4rem;
    color: var(--white-color);
    font-family: "Semi";
    font-size: 1rem;
}

.loginview-input-button:hover {
    cursor: pointer;
}

.loginview-regist-content {
    text-align: center;
}

.loginview-regist-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-top: 1rem;
}

.loginview-regist-content {
    font-family: "Regular";
    font-size: 1rem;
    color: var(--gray2-color);
    margin-bottom: 0.4rem;
}

.loginview-regist-button {
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: var(--white-color);
    border: 1px solid var(--main-color);
    border-radius: 0.625rem;
    width: 21.25rem;
    height: 4rem;
    color: var(--main-color);
    font-family: "Semi";
    font-size: 1rem;
}
</style>
