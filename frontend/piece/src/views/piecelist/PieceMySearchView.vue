<template>
    <div class="piecemysearch-container">

        <!-- 검색바 -->
        <div class="piecemysearch-searchbar">
            <SearchInput :handlePrevClick="handlePrev" :handleSearchClick="handleSearch"
                @searchContent="searchKeyword" />
        </div>

        <!-- 검색 결과 -->
        <div v-if="!searchResult" class="piecemysearch-list-noitem">
            <NoItem :content="'검색된 조각이 없어요'"></NoItem>
        </div>
        <div v-else>
            <div class="piecemysearch-list-container">
                <div class="piecemysearch-list-grid">
                    <div v-for="(item, index) in searchResult" :key="index" class="piecemysearch-list-item">
                        <ListImageItem :pieceId="item.pieceId" :performanceType="item.performanceType"
                            :frontImg="item.frontImg" :title="item.title" @click="searchDetail(item)">
                        </ListImageItem>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useCommonStore } from "@/stores/common";
import { useSearchStore } from "@/stores/search";
import SearchInput from '@/components/modal/SearchInput.vue';
import NoItem from "@/components/item/NoItem.vue";
import ListImageItem from "@/components/item/ListImageItem.vue";

const router = useRouter();
const route = useRoute();
const commonStore = useCommonStore();
const searchStore = useSearchStore();


// 이전버튼
const handlePrev = () => {
    router.push("/piecelist");
}

// 검색버튼
const handleSearch = () => {
    console.log("키워드 : " + searchValue.value);
    router.push({ name: "piecemysearch", params: { keyword: searchValue.value } });
}

// 검색어
const searchValue = ref("");
const searchKeyword = (value) => {
    console.log(value);
    searchValue.value = value;
};

// 검색내용
const searchResult = computed(() => searchStore.getSearchList);

watch(() => route.params.keyword, (newKeyword) => {
    searchStore.searchPiece(newKeyword);
}, { immediate: true });

// 상세보기
const searchDetail = (item) => {
    router.push({ name: "pieceDetail", params: { pieceId: item.pieceId } });
};

onMounted(() => {
    commonStore.headerType = "header1";
    commonStore.headerTitle = "검색결과";
    searchStore.searchPiece(route.params.keyword);
});

</script>

<style scoped>
.piecemysearch-container {
    display: flex;
    flex-direction: column;
    min-height: calc(100vh - 7.25rem);
}

.piecemysearch-container> :first-child {
    flex: 0 0 auto;
}

.piecemysearch-container> :not(:first-child) {
    flex: 1;
}

.piecemysearch-searchbar {
    margin-bottom: 1rem;
    user-select: none;
}

/* list */
.piecemysearch-list-container {
    overflow-y: scroll;
    height: 66vh;
}

.piecemysearch-list-container::-webkit-scrollbar {
    width: 0.2rem;
}

.piecemysearch-list-container::-webkit-scrollbar-thumb {
    background-color: var(--gray2-color);
    border-radius: 1rem;
}

.piecemysearch-list-container::-webkit-scrollbar-track {
    background-color: var(--gray-color);
    border-radius: 1rem;
}

.piecemysearch-list-grid {
    display: grid;
    grid-template-columns: repeat(2, minmax(8rem, 1fr));
    justify-items: center;
    grid-gap: 0.2rem;
}

.piecemysearch-list-item {
    width: auto;
    user-select: none;
    cursor: pointer;
}

.piecemysearch-list-noitem {
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>