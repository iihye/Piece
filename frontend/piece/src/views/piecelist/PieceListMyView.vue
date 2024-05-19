<template>
    <div class="piecelistmyview-main-container">

        <div class="piecelistmyview-search-container">
            <SearchInput :handlePrevClick="handlePrev" :handleSearchClick="handleSearch"
                @searchContent="handleSearchContent"></SearchInput>
        </div>

        <!-- filter -->
        <div class="piecelistmyview-scroll-container">
            <div class="piecelistmyview-tab-navigation">
                <div class="piecelistmyview-tab-menu" ref="tabMenu">
                    <FilterItem v-for="(item, index) in filterItems" class="piecelistmyview-tab-btn" :key="index"
                        :labelType="item.labelType" :title="item.title" :isSelect="item.isSelect"
                        @click="handleItemSelectClick(index)"></FilterItem>
                </div>
            </div>
        </div>

        <!-- list -->
        <div v-if="!piecelistMyList || filteredMyList.length === 0" class="piecelistmyview-list-noitem">
            <NoItem :content="'내 조각이 없어요'"></NoItem>
        </div>
        <div v-else>
            <div class="piecelistmyview-list-container">
                <div class="piecelistmyview-list-grid">
                    <div v-for="(item, index) in filteredMyList" :key="index" class="piecelistmyview-list-item">
                        <ListImageItem :pieceId="item.pieceId" :performanceType="item.performanceType"
                            :frontImg="item.frontImg" :title="item.title" @click="handleItemClick(item)">
                        </ListImageItem>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import router from "@/router";
import { ref, computed, onMounted, onBeforeUnmount } from "vue";
import { useCommonStore } from "@/stores/common";
import { usePiecelistStore } from "@/stores/piecelist";
import SearchInput from "@/components/modal/SearchInput.vue";
import FilterItem from "@/components/item/FilterItem.vue";
import ListImageItem from "@/components/item/ListImageItem.vue";
import NoItem from "@/components/item/NoItem.vue";

const commonStore = useCommonStore();
const store = usePiecelistStore();

const year = computed(() => store.getYear);
const month = computed(() => store.getMonth);
const piecelistMyList = computed(() => store.getPiecelistMyList);
const filteredMyList = computed(() => store.getPiecelistMyListFiltered);
const selectedOption = computed(() => store.getSelectOptionMyList);

// search

const searchValue = ref("");

// modal에서 prev 클릭했을 때 실행되는 함수
const handlePrev = () => {
    router.go(-1);
};

// modal에서 search 클릭했을 때 실행되는 함수
const handleSearch = () => {
    router.push({ name: "piecemysearch", params: { keyword: searchValue.value } });
};

const handleSearchContent = (value) => {
    searchValue.value = value;
};

const handleItemClick = (item) => {
    router.push({ name: "pieceDetail", params: { pieceId: item.pieceId } });
};

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

const handleItemSelectClick = (index) => {
    for (let item of filterItems.value) {
        if (item.isSelect) {
            item.isSelect = false;
        }
    }
    filterItems.value[index].isSelect = !filterItems.value[index].isSelect;
    // selectedOption.value = filterItems.value[index].labelType;
    store.setSelectOptionMyList(filterItems.value[index].labelType);
    store.findPiecelistMyList();
};

// filter
const tabMenu = ref(null);
const activeDrag = ref(false);

const handleMouseMove = (event) => {
    if (!activeDrag.value) return;
    tabMenu.value.scrollLeft -= event.movementX;
    tabMenu.value.classList.add("dragging");
};

const handleMouseUp = () => {
    activeDrag.value = false;
    tabMenu.value.classList.remove("dragging");
};

const handleMouseDown = () => {
    activeDrag.value = true;
};

onMounted(async () => {
    commonStore.headerTitle = "내 조각 모아보기";
    commonStore.headerType = "header4";

    const index = filterItems.value.findIndex(
        (item) => item.labelType === selectedOption.value
    );

    if (index !== -1) {
        filterItems.value[0].isSelect = false;
        filterItems.value[index].isSelect = true;
    }

    if (index == 5) {
        tabMenu.value.scrollLeft = 1000;
    }

    await store.findPiecelistMyList();
    tabMenu.value.addEventListener("mousedown", handleMouseDown);
    document.addEventListener("mouseup", handleMouseUp);
    tabMenu.value.addEventListener("mousemove", handleMouseMove);

    await store.findPiecelistMyCalendar(year.value, month.value + 1);
});

onBeforeUnmount(() => {
    tabMenu.value.removeEventListener("mousedown", handleMouseDown);
    document.removeEventListener("mouseup", handleMouseUp);
    tabMenu.value.removeEventListener("mousemove", handleMouseMove);
});
</script>

<style>
.piecelistmyview-main-container {
    display: flex;
    flex-direction: column;
    min-height: calc(100vh - 7.25rem);
}

.piecelistmyview-main-container> :first-child {
    flex: 0 0 auto;
}

.piecelistmyview-main-container> :not(:first-child) {
    flex: 1;
}

.piecelistmyview-search-container {
    margin-bottom: 1rem;
    user-select: none;
}

/* filter */
.piecelistmyview-scroll-container {
    position: relative;
    /* width: 450px; */
    transition: 0.5s ease;
    margin-bottom: 1rem;
}

.piecelistmyview-tab-navigation {
    position: relative;
    max-width: fit-content;
    margin: 0 auto;
    display: flex;
    justify-content: center;
    align-items: center;
}

.piecelistmyview-tab-menu {
    list-style: none;
    white-space: nowrap;
    overflow-x: auto;
    user-select: none;
    scroll-behavior: smooth;
}

.piecelistmyview-tab-menu.dragging {
    scroll-behavior: unset;
    cursor: grab;
}

.piecelistmyview-tab-menu::-webkit-scrollbar {
    display: none;
}

.piecelistmyview-tab-btn {
    display: inline-block;
    margin: 0 0.2rem;
    cursor: pointer;
    user-select: none;
    transition: 0.3s ease;
}

.piecelistmyview-tab-menu.dragging .tab-btn {
    pointer-events: none;
}

/* list */
.piecelistmyview-list-container {
    overflow-y: scroll;
    height: 66vh;
}

.piecelistmyview-list-container::-webkit-scrollbar {
    width: 0.2rem;
}

.piecelistmyview-list-container::-webkit-scrollbar-thumb {
    background-color: var(--gray2-color);
    border-radius: 1rem;
}

.piecelistmyview-list-container::-webkit-scrollbar-track {
    background-color: var(--gray-color);
    border-radius: 1rem;
}

.piecelistmyview-list-grid {
    display: grid;
    grid-template-columns: repeat(2, minmax(8rem, 1fr));
    justify-items: center;
    grid-gap: 0.2rem;
}

.piecelistmyview-list-item {
    width: auto;
    user-select: none;
    cursor: pointer;
}

.piecelistmyview-list-noitem {
    display: flex;
    justify-content: center;
    align-items: center;
}

/* @media (min-width: 1024px) {
    .piecelistmyview-list-grid {
        grid-template-columns: repeat(4, minmax(16rem, 5fr));
    }
} */
</style>
