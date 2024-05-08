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
                        type="password"
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
    </div>
</template>

<script setup>
import axios from "axios";
import TextInput from "@/components/text/TextInput.vue";
import { ref, onMounted, watch } from "vue";
import { useRouter } from "vue-router";

const email = ref("");
const password = ref("");
const router = useRouter();
const isFormValid = ref(false);

watch([email, password], ([newEmail, newPassword]) => {
    isFormValid.value = newEmail.trim() !== "" && newPassword.trim() !== "";
});

const submitLogin = async () => {
    console.log("버튼 클릭");
    try {
        const response = await axios.post(
            `${import.meta.env.VITE_REST_PIECE_API}/auth/login`,
            {
                email: email.value,
                password: password.value,
            }
        );
        console.log("로그인 성공:", response.data);
        alert("로그인 성공!");
        router.push("/home"); // 성공 시 홈 페이지로 리다이렉트
    } catch (error) {
        console.error(
            "로그인 실패:",
            error.response ? error.response.data : error
        );
        alert(
            "로그인 실패: " +
                (error.response ? error.response.data.message : "서버 에러")
        );
    }
};
</script>

<style>
.loginview-main-container {
    display: flex;
    flex-direction: column;
    height: 100%;
}

.loginview-main-title {
    font-family: "Bold";
    font-size: 1.6rem;
    color: var(--black-color);
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
    border: 0.1rem solid var(--main-color);
    border-radius: 0.625rem;
    width: 21.25rem;
    height: 4rem;
    color: var(--main-color);
    font-family: "Semi";
    font-size: 1rem;
}
</style>
