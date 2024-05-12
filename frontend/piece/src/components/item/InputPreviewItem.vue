<template>
  <div @click="handleClick(item)" @mouseover="hover = true" @mouseleave="hover = false" :class="{ 'hover': hover }">
    <img :src="item.poster_path" alt="Poster" class="movie-poster" />
    <span class="movie-title" v-html="highlightSearchQuery(item.title)"></span>
  </div>
</template>

<script setup>
import { ref, defineProps, defineEmits } from 'vue';

const props = defineProps({
  item: Object,
  searchQuery: String
});

const emits = defineEmits(['select']);
const hover = ref(false);

function handleClick(item) {
  emits('select', item);
}

function highlightSearchQuery(title) {
  if (!props.searchQuery) return title;

  // searchQuery와 일치하는 부분을 다른 색상으로 표시
  const regex = new RegExp(props.searchQuery, 'gi');
  return title.replace(regex, match => `<span style="color: var(--main-color);">${match}</span>`);
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
