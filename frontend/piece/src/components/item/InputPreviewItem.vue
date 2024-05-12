<template>
  <div @click="handleClick(item)" @mouseover="hover = true" @mouseleave="hover = false" :class="{ 'hover': hover }">
    <img :src="item.poster_path" alt="Poster" class="movie-poster" />
    <span class="movie-title">{{ item.title }}</span>
  </div>
</template>

<script setup>
import { ref, defineProps, defineEmits } from 'vue';

const props = defineProps({
  item: Object
});

const emits = defineEmits(['select']);
const hover = ref(false);

function handleClick(item) {
  emits('select', item);
}
</script>

<style scoped>
div {
  cursor: pointer;
  transition: background-color 0.3s;
}

.hover {
  background-color: #f0f0f0; /* Light grey background on hover */
}

.movie-poster {
  width: 50px;
  height: 75px;
  object-fit: cover;
  margin-right: 10px;
}

.movie-title {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 500px;
}
</style>
