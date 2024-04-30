<template>
    <!-- header -->
    <h1>내 조각 리스트 버전</h1>
    <RouterLink :to="{ name: 'pieceCalendar' }">캘린더</RouterLink>

    <!-- filter -->
    <div class="scroll-container">
        <div class="filter-items-container">
            <FilterItem
                v-for="(item, index) in filterItems"
                :key="index"
                :labelType="item.labelType"
                :title="item.title"
                :isSelect="item.isSelect"
                @click="handleItemWearoffClick(index)"
            ></FilterItem>
        </div>
    </div>

    <!-- list -->
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import FilterItem from "@/components/item/FilterItem.vue";

const filterItems = ref([
    {
        labelType: "ALL",
        title: "전체",
        isSelect: true,
    },
    {
        labelType: "MOVIE",
        title: "영화",
        isSelect: false,
    },
    {
        labelType: "THEATER",
        title: "연극",
        isSelect: false,
    },
    {
        labelType: "MUSICAL",
        title: "뮤지컬",
        isSelect: false,
    },
    {
        labelType: "CONCERT",
        title: "콘서트",
        isSelect: false,
    },
    {
        labelType: "ETC",
        title: "기타",
        isSelect: false,
    },
]);

const handleItemWearoffClick = (index) => {
    for (let item of filterItems.value) {
        if (item.isSelect) {
            item.isSelect = false;
        }
    }
    filterItems.value[index].isSelect = !filterItems.value[index].isSelect;
};
</script>

<style>
.scroll-container {
    width: 360px;
    padding-left: 8rem;
    overflow: scroll;
    overflow: auto;
    white-space: nowrap;
}

.scroll-container::-webkit-scrollbar {
    /* display: none; */
}

.filter-items-container {
    display: flex;
    justify-content: center;
}

.filter-items-container > FilterItem {
    margin-right: 10px;
}
</style>
