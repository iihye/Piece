<template>
    <div class="app">
        <TheHeader class="TheHeader" />
        <div>
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
    align-items: center;
}
.TheHeader {
}
.TheFooter {
    position: fixed;
    bottom: 0;
}
</style>
