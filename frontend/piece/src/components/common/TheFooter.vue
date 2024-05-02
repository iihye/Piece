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
                        <div class="footer-name">조각만들기</div>
                    </div>
                </RouterLink>
                <RouterLink :to="{ name: 'chat' }" class="footer-menu">
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
    list: ['piecelistmain', 'cakelist', 'cakeDetail', 'pieceList', 'pieceCalendar', 'pieceListMy', 'pieceDetail', 'recordDetail'],
    make: ['piecemake', 'pieceimage', 'pieceimagecrop', 'pieceinfo', 'piececomment', 'piecefront', 'pieceback', 'piecebackground', 'piecesave',],
    chat: ['chatRoom', 'chat'],
    myinfo: ['mypage', 'nickname', 'password', 'settings', 'profileImg', 'label', 'piece', 'consume']
};



</script>

<style>
.footer-container {
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 3.75rem;
    z-index: 85;
    display: grid;
    text-align: center;
}

.footer-all {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
}

.footer-menu {
    display: flex;
    flex-direction: column;
}

.footer-icon {
    width: 2rem;
    height: 2rem;
    color: var(--gray2-color);
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
