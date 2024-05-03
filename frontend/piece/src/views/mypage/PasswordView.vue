<template>
  <div class="password-change-view">
    <h2>비밀번호 변경</h2>
    <form @submit.prevent="submitPasswordChange">
      <TextInput
        textInputName="현재 비밀번호"
        textInputPlaceholder="현재 비밀번호를 입력하세요"
        @textInputValue="currentPassword = $event"
        textInputRequired="true"
        type="password"
      />
      <TextInput
        textInputName="새로운 비밀번호"
        textInputPlaceholder="새로운 비밀번호를 입력하세요"
        @textInputValue="newPassword = $event"
        textInputRequired="true"
        type="password"
      />
      <button type="submit" class="change-password-button">변경</button>
    </form>
  </div>
</template>

<script setup>
import TextInput from '@/components/text/TextInput.vue';
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const currentPassword = ref('');
const newPassword = ref('');
const router = useRouter();

const submitPasswordChange = async () => {
  try {
    const userId = getSessionUserId(); // 세션에서 사용자 ID 가져오기
    const response = await axios.put(`${import.meta.env.VITE_REST_PIECE_API}/users/${userId}/password`, {
      currentPassword: currentPassword.value,
      newPassword: newPassword.value
    });
    console.log("비밀번호 변경 성공:", response.data);
    alert('비밀번호가 성공적으로 변경되었습니다!');
    router.push('/'); // 변경 성공 후 홈으로 리다이렉션
  } catch (error) {
    console.error("비밀번호 변경 실패:", error.response ? error.response.data : error);
    alert('비밀번호 변경 실패: ' + (error.response ? error.response.data.message : '서버 에러'));
  }
};

const getSessionUserId = () => {
  // 세션에서 사용자 ID를 가져오는 로직 작성
  // 예시: sessionStorage에서 사용자 ID를 가져오는 경우
  return sessionStorage.getItem('userId');
};
</script>

<style scoped>
.password-change-view {
  max-width: 400px;
  margin: auto;
  padding: 20px;
  text-align: center;
}

.change-password-button {
  margin-top: 20px;
  width: 100%;
  padding: 10px;
  background-color: #FF9494;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.change-password-button:hover {
  background-color: #FF9494;
}
</style>
