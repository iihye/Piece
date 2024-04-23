<template>
    <div>
        <RouterView />
        <ConfirmButton />
    </div>
</template>

<script setup>
import { RouterView } from "vue-router";
import ConfirmButton from "@/components/button/ConfirmButton.vue";
</script>

<style></style>
