<template>
    <footer>
        <div class="footer-container">
            <div class="footer-all">
                <RouterLink :to="{ name: 'main' }" class="footer-menu">
                    <div :class="{ 'footer-set': true, 'active': pageName === 'main' }">
                        <font-awesome-icon :icon="['fas', 'house']" class="footer-icon" />
                        <div class="footer-name">홈</div>
                    </div>
                </RouterLink>
                <RouterLink :to="{ name: 'piecelistmain' }" class="footer-menu">
                    <div :class="{ 'footer-set': true, 'active': arrayRoutes.list.includes(pageName) }">
                        <font-awesome-icon :icon="['fas', 'box-open']" class="footer-icon" />
                        <div class="footer-name">모아보기</div>
                    </div>
                </RouterLink>
                <RouterLink :to="{ name: 'piecemake' }" class="footer-menu">
                    <div :class="{ 'footer-set': true, 'active': arrayRoutes.make.includes(pageName) }">
                        <font-awesome-icon :icon="['fas', 'cheese']" class="footer-icon" />
                        <div class="footer-name">조각 만들기</div>
                    </div>
                </RouterLink>
                <RouterLink :to="{ name: 'chatRoom' }" class="footer-menu">
                    <div :class="{ 'footer-set': true, 'active': arrayRoutes.chat.includes(pageName) }">
                        <font-awesome-icon :icon="['fas', 'paper-plane']" class="footer-icon" />
                        <div class="footer-name">채팅</div>
                    </div>
                </RouterLink>
                <RouterLink :to="{ name: 'mypage' }" class="footer-menu">
                    <div :class="{ 'footer-set': true, 'active': arrayRoutes.myinfo.includes(pageName) }">
                        <font-awesome-icon :icon="['fas', 'user']" class="footer-icon" />
                        <div class="footer-name">내정보</div>
                    </div>
                </RouterLink>
            </div>
        </div>
    </footer>
</template>

<script setup>
import { computed } from 'vue';
import { RouterLink, useRoute } from "vue-router";

const route = useRoute();

const pageName = computed(() => { return route.name });

const arrayRoutes = {
    home: ['main'],
    list: ['piecelistmain', 'cakelist', 'cakeDetail', 'pieceList', 'pieceCalendar', 'pieceListMy', 'pieceListHeart', 'pieceDetail', 'recordDetail'],
    make: ['piecemake', 'pieceimage', 'pieceimagecrop', 'pieceinfo', 'piececomment', 'piecefront', 'pieceback', 'piecebackground', 'piecesave',],
    chat: ['chatRoom', 'chat'],
    myinfo: ['mypage', 'nickname', 'password', 'settings', 'profileImg', 'label', 'piece', 'consume']
};

</script>

<style scoped>
@import "@/components/css/color.css";
@import "@/components/css/font.css";

.footer-container {
    position: fixed;
    bottom: 0rem;
    left: 0;
    width: 100%;
    z-index: 85;
    display: grid;
    text-align: center;
    font-family: "Medium";
    padding: 1rem 0rem;
}

.footer-all {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    align-items: flex-end;
}

.footer-menu {
    display: flex;
    flex-direction: column;
    text-decoration: none;
}

.footer-icon {
    width: 1.6rem;
    height: 1.6rem;
    color: var(--gray-color);
}

.footer-name {
    font-size: 0.75rem;
    color: var(--gray2-color);
}

.footer-set.active .footer-name,
.footer-set.active .footer-icon {
    color: var(--main-color);
}
</style>