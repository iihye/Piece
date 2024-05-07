<template>
    <button
      :class="{'active': isSelected}"
      @click="select"
    >
      {{ name }}
    </button>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
    id: String,
    name: String,
    isSelected: Boolean
});

const emits = defineEmits(['selected']);

function select() {
emits('selected', props.id);
}
</script>

<style scoped>
@import "@/components/css/color.css";
@import "@/components/css/font.css";

button {
  font-family: "Regular";
  padding: 0.625rem 1.25rem;
  border: 0.0625rem solid var(--gray-color);
  background-color: var(--white-color);
  height: 2.25rem;
  cursor: pointer;
  outline: none;
  border-radius: 0.625rem;
  /* margin-right: 0.625rem; */
  /* width: 6rem;
  height: 2rem; */
  transition: background-color 0.2s, color 0.2s;
}
  
button.active {
  background-color: var(--main-color);
  color: var(--white-color);
  border-color: var(--main-color);
}
</style>