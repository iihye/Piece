<template>
    <div>

        <SearchInput />

        <FilterItem />

        <!-- Cake list -->
        <div class="cake-list">
            <CakeItem
            v-for="item in cakeItems"
            :key="item.id"
            :item="item"
            />
        </div>
    </div>
</template>

<script setup>
import CakeItem from '@/components/item/CakeItem.vue'
import SearchInput from '@/components/modal/SearchInput.vue'
import FilterItem from '@/components/item/FilterItem.vue'
import { ref } from 'vue'

const cakeItems = ref([
    { id: 1, title: '범죄도시4', image: 'path/to/image1.jpg', category: '영화', status: '진행 예정' },
    { id: 2, title: 'IM HERO 임영웅 콘서트', image: 'path/to/image2.jpg', category: '콘서트', status: '진행 예정' },
    { id: 3, title: '데미안', image: 'path/to/image3.jpg', category: '뮤지컬', status: '진행 중' },
    { id: 4, title: '아트', image: 'path/to/image4.jpg', category: '전시', status: '진행 예정' }
]);
</script>

<style>
.cake-list {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 20px;
    justify-content: center;
    padding: 20px;
}
</style>