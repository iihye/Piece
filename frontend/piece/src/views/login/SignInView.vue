<template>
  <div class="signup-form">
    <h2>piece에 오신 것을 환영합니다!</h2>
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
        type="password"
      />
      <TextInput
        textInputName="닉네임"
        textInputPlaceholder="닉네임을 입력하세요"
        @textInputValue="nickname = $event"
        textInputRequired="true"
      />
      <button type="submit" class="signup-button">회원가입</button>
    </form>
  </div>
</template>

<script setup>
import axios from 'axios';
import TextInput from '@/components/text/TextInput.vue';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const email = ref('');
const password = ref('');
const nickname = ref('');
const router = useRouter();

const submitForm = async () => {
  try {
    const response = await axios.post('http://localhost:8080/api/users/register', {
      email: email.value,
      password: password.value,
      nickname: nickname.value
    });
    console.log("회원가입 성공:", response.data);
    alert('회원가입이 완료되었습니다!');
    router.push('/home'); // 성공 시 홈 페이지로 리다이렉트
  } catch (error) {
    console.error("회원가입 실패:", error.response ? error.response.data : error);
    alert('회원가입 실패: ' + (error.response ? error.response.data.message : '서버 에러,,,'));
  }
};
</script>

<style scoped>
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
