<template>
<<<<<<< HEAD
  <div class="success-view">
    <p>{{ username }}님!<br> piece를 완성해봐요!</p>
    <button @click="goToLogin">로그인하기</button>
  </div>
=======
    <div class="success-view">
        <p>
            {{ username }}님!<br />
            piece를 완성해봐요!
        </p>
        <button @click="goToLogin">로그인하기</button>
    </div>
>>>>>>> d8b3a8ee8b5107aefca9ea40bc592bb020e0b57a
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const username = ref(""); // 초기 값은 빈 문자열

// 사용자 이름을 API로부터 받아오는 함수
async function fetchUsername() {
    // 예제를 위한 임시 코드
    // 실제 사용시에는 API 호출 코드로 대체하세요.
    return "김싸피";
}

onMounted(async () => {
    username.value = await fetchUsername();
});

function goToLogin() {
    router.push("/login"); // 로그인 페이지로 이동
}
</script>

<style>
:root {
    --main-color: #ff9494; /* 메인 컬러 설정 */
}

.success-view {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
    text-align: center;
}

.success-view p {
  margin-bottom: 40px;
  font-size: 18px;
  color: var(--main-color); /* 메인 컬러 사용 */
}

button {
  padding: 10px 20px;
  font-size: 16px;
  background-color: var(--main-color); /* 변수를 사용하여 배경 색상 설정 */
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: var(--main-color); /* 호버 상태의 배경 색상도 동일하게 적용 */
}
</style>
