<template>
    <div>
        <RouterView />
        <SmallButton />
    </div>
</template>

<script setup>
import { RouterView } from "vue-router";
import SmallButton from "@/components/button/SmallButton.vue";
</script>

<style></style>
