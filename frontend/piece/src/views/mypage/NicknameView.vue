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
import TextInput from '@/components/text/TextInput.vue'; // TextInput 컴포넌트 임포트
import { ref } from 'vue';

const nickname = ref(''); // 새 닉네임을 저장할 변수

// 닉네임 변경 함수
const submitNickname = () => {
  // 여기에 닉네임 변경 로직 추가
  console.log("새 닉네임:", nickname.value);
  // 닉네임 변경 API 호출 및 결과 처리
};
</script>

<style scoped>
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
  background-color: #FF9494; /* 핑크 색상 */
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.change-nickname-button:hover {
  background-color: #FF9494; /* 핑크 색상 */
}
</style>
