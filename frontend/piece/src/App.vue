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
    min-height: 100vh;
    margin: 0 auto;
    display: flex;
    flex-direction: column;
}

.TheHeader {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 2.75rem;
}

.app-content {
    padding-top: 2.75rem;
    padding-bottom: 2.75rem;
    height: calc(100vh - 5.5rem);
}

.TheFooter {
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 2.75rem;
    z-index: 85;
}
</style>
