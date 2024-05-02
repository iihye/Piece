<template>
    <div class="app">
        <TheHeader class="TheHeader" />
        <div class="app-content">
            <RouterView />
        </div>
        <TheFooter class="TheFooter" />
    </div>
</template>

<script setup>
import { RouterView } from "vue-router";
import TheHeader from "./components/common/TheHeader.vue";
import TheFooter from "./components/common/TheFooter.vue";
</script>

<style>
.app {
    max-width: 360px;
    height: 100vh;
    margin: 0 auto;
    display: flex;
    flex-direction: column;
}
/* 
.TheHeader {
    position: fixed;
    top: 0;
    width: 100%;
} */

.TheFooter {
    position: fixed;
    bottom: 0;
    width: 100%;
}

.app-content {
    z-index: 1;
}
</style>
