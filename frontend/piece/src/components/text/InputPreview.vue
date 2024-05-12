<template>
<div class="inputpreview-container" v-if="isFocused" @mousedown.stop>
    <div v-if="searchQuery && !searchResults.length">
    검색 결과가 없습니다.
    </div>
    <div v-else-if="searchResults.length" class="inputpreview-item">
    <div v-for="item in searchResults" :key="item.poster_path">
      <InputPreviewItem :item="item" @select="handleSelect"></InputPreviewItem>
    </div>
    </div>
    <div v-else>
    영화명을 입력해주세요.
    </div>
</div>
</template>

<script setup>
import { ref } from 'vue';
import InputPreviewItem from '@/components/item/InputPreviewItem.vue';


defineProps({
    isFocused: Boolean,
    searchQuery: String,
    searchResults: Array
});

const emit = defineEmits(['select']);
const hover = ref(false);

function handleSelect(item) {
  emit('select', item);
}

function handleItemMouseDown(event) {
  event.stopPropagation(); // 이벤트 전파 중지
}
</script>

<style>
.hover {
  background-color: #f0f0f0; /* Light grey background on hover */
}

.inputpreview-item {
    width: 100%;
    padding: 1rem;
    position: absolute;
    z-index: 2;
    background-color: var(--white-color);
    box-shadow: 0 0.875rem 1.75rem var(--gray-color), 0 0.625rem 0.625rem var(--gray-color);
}
</style>