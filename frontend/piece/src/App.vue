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
    /* left: 0; */
    /* width: 22.5rem; */
    width: 360px;
    height: 3.25rem;
    z-index: 85;
}

.app-content {
    padding-top: 3.25rem;
    margin-bottom: 4rem;
    min-height: calc(100vh - 7.25rem);
}

.TheFooter {
    position: fixed;
    bottom: 0;
    /* left: 0; */
    /* width: 22.5rem; */
    width: 360px;;
    height: 4rem;
    z-index: 85;
    background-color: var(--white-color);
}
</style>
