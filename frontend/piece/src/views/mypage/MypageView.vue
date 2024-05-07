<template>
    <h1>mypage</h1>
    <RouterLink class="mypageview-link" :to="{ name: 'nickname' }"
        >닉네임 변경</RouterLink
    >
    <RouterLink class="mypageview-link" :to="{ name: 'password' }"
        >비밀변호 변경</RouterLink
    >
    <RouterLink class="mypageview-link" :to="{ name: 'profileImg' }"
        >프로필이미지 변경</RouterLink
    >
    <RouterLink class="mypageview-link" :to="{ name: 'label' }"
        >칭호</RouterLink
    >
    <RouterLink class="mypageview-link" :to="{ name: 'piece' }"
        >조각통계</RouterLink
    >
    <RouterLink class="mypageview-link" :to="{ name: 'consume' }"
        >소비통계</RouterLink
    >
</template>

<script setup></script>

<style scoped>
.mypageview-link {
    display: flex;
}
</style>
