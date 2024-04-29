<template>
    <h1>칭호</h1>

    <div>
        <h1>여기가 가려지는 뒷배경입니다</h1>
        <YesNoModal
            :modalTitle="'칭호를 착용할까요?'"
            :modalContent="'나의 프로필에서 착용 중인<br>칭호를 보여줄 수 있어요!'"
            :buttonFail="'취소'"
            :buttonSuccess="'적용하기'"
            :handleFailClick="handleFail"
            :handleSuccessClick="handleSuccess"
        />
    </div>
</template>

<script setup>
import YesNoModal from "@/components/modal/YesNoModal.vue";

// modal에서 Fail 클릭했을 때 실행되는 함수
const handleFail = () => {
    alert("실패 클릭");
};

// modal에서 Success 클릭했을 때 실행되는 함수
const handleSuccess = () => {
    alert("성공 클릭");
};
</script>

<style></style>
