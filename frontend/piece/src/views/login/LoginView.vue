<template>
  <div class="login-form">
    <h2>piece에 다시 오셨군요!</h2>
    <img
        src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Food/Birthday%20Cake.png"
        alt="Birthday Cake"
        width="160"
        height="160"
    />
    <form @submit.prevent="submitLogin">
      <TextInput
        textInputName="이메일"
        textInputPlaceholder="이메일을 입력하세요"
        @textInputValue="email = $event"
        textInputRequired="true"
      />
      <TextInput
        class="password-input"
        textInputName="비밀번호"
        textInputPlaceholder="비밀번호를 입력하세요"
        @textInputValue="password = $event"
        textInputRequired="true"
      />
      <button type="submit" class="login-button">로그인</button>
    </form>
    <p class="registration-text">아직 회원이 아니신가요?</p>
    <!-- <RouterLink to="/signin" class="registration-button">이메일로 시작하기</RouterLink> -->
  </div>
</template>

<script setup>
import axios from 'axios';
import TextInput from '@/components/text/TextInput.vue';
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const email = ref('');
const password = ref('');
const router = useRouter();

const submitLogin = async () => {
  try {
    const response = await axios.post(`${import.meta.env.VITE_REST_PIECE_API}/auth/login`, {
      email: email.value,
      password: password.value
    });
    console.log("로그인 성공:", response.data);
    alert('로그인 성공!');
    router.push('/'); // 성공 시 홈 페이지로 리다이렉트
  } catch (error) {
    console.error("로그인 실패:", error.response ? error.response.data : error);
    alert('로그인 실패: ' + (error.response ? error.response.data.message : '서버 에러'));
  }
};
</script>

<style>
:root {
    --main-color: #ff9494; /* 핑크 색상 정의 */
}

.login-form {
  max-width: 300px;
  margin: auto;
  padding: 20px;
  text-align: center;
}

.welcome-image {
  max-width: 100%;
  margin: 20px 0;
}

.login-button {
  margin-top: 20px;
  width: 100%;
  padding: 10px;
  background-color: var(--main-color); /* 변수를 사용하여 배경 색상 설정 */
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.login-button:hover {
  background-color: var(--main-color); /* 호버 상태의 배경 색상도 동일하게 적용 */
}

.registration-text {
  text-align: center;
  margin-top: 10px;
}

.registration-button {
  display: block;
  width: 100%;
  padding: 10px;
  text-align: center;
  color: var(--main-color); /* 변수를 사용하여 텍스트 색상 설정 */
  text-decoration: none;
  border: 2px solid var(--main-color); /* 변수를 사용하여 테두리 색상 설정 */
  border-radius: 4px;
  margin-top: 10px;
}
</style>
