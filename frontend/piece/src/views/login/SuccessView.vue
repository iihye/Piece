<template>
    <div class="success-view">
        <p>
            {{ username }}님!<br />
            piece를 완성해봐요!
        </p>
        <button @click="goToLogin">로그인하기</button>
    </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const username = ref(""); // 초기 값은 빈 문자열

// 가정: 사용자 이름을 API로부터 받아오는 함수
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
.success-view {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
    text-align: center;
}

.success-view h1 {
    margin-bottom: 20px;
    color: #ff9494;
}

.success-view p {
    margin-bottom: 40px;
    font-size: 18px;
    color: #333;
}
</style>
