<template>
  <div class="nickname-view">
    <h2>닉네임을 입력해주세요</h2>
    <form @submit.prevent="submitNickname">
      <TextInput
        textInputName="닉네임"
        textInputPlaceholder="새로운 닉네임을 입력하세요"
        @textInputValue="nickname = $event"
        textInputRequired="true"
      />
      <button type="submit" class="change-nickname-button">확인</button>
    </form>
  </div>
</template>

<script setup>
import TextInput from '@/components/text/TextInput.vue';
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const nickname = ref('');
const router = useRouter();

const submitNickname = async () => {
  try {
    const userId = getSessionUserId();
    const response = await axios.put(`${import.meta.env.VITE_REST_PIECE_API}/users/${userId}/nickname`, {
      newNickname: nickname.value
    });
    console.log("닉네임 변경 성공:", response.data);
    alert('닉네임이 성공적으로 변경되었습니다!');
    router.push('/');
  } catch (error) {
    console.error("닉네임 변경 실패:", error.response ? error.response.data : error);
    alert('닉네임 변경 실패: ' + (error.response ? error.response.data.message : '서버 에러'));
  }
};

function getSessionUserId() {
  return sessionStorage.getItem('userId');
}
</script>

<style scoped>
:root {
    --main-color: #ff9494; /* 정의된 색상 변수 */
}

.nickname-view {
  max-width: 400px;
  margin: auto;
  padding: 20px;
  text-align: center;
}

.change-nickname-button {
  margin-top: 20px;
  width: 100%;
  padding: 10px;
  background-color: var(--main-color); /* 변수 사용 */
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.change-nickname-button:hover {
  background-color: var(--main-color);
}
</style>
