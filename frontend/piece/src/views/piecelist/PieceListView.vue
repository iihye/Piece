<template>
    <div class="piecelistview-main-container">
        <!-- search -->
        <div class="piecelistview-search-container">
            <SearchInput
                :handlePrevClick="handlePrev"
                :handleSearchClick="handleSearch"
                @searchContent="handleSearchContent"
            ></SearchInput>
        </div>

        <!-- filter -->
        <div class="piecelistview-scroll-container">
            <div class="piecelistview-tab-navigation">
                <div class="piecelistview-tab-menu" ref="tabMenu">
                    <FilterItem
                        v-for="(item, index) in filterItems"
                        class="piecelistview-tab-btn"
                        :key="index"
                        :labelType="item.labelType"
                        :title="item.title"
                        :isSelect="item.isSelect"
                        @click="handleItemSelectClick(index)"
                    ></FilterItem>
                </div>
            </div>
        </div>

        <!-- list -->
        <div v-if="filteredList.length === 0" class="piecelistview-list-noitem">
            <NoItem :content="'모아볼 조각이 없어요'"></NoItem>
        </div>
        <div v-else>
            <div class="piecelistview-list-container">
                <div class="piecelistview-list-grid">
                    <div
                        v-for="(item, index) in filteredList"
                        :key="index"
                        class="piecelistview-list-item"
                    >
                        <ListImageItem
                            :pieceId="item.pieceId"
                            :performanceType="item.performanceType"
                            :frontImg="item.frontImg"
                            :title="item.title"
                            @click="handleItemClick(item)"
                        ></ListImageItem>
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

const searchValue = ref("");
const piecelistList = computed(() => store.getPiecelistList);
const filteredList = computed(() => computedFilteredList());
const selectedOption = ref("ALL");

// search
// modal에서 prev 클릭했을 때 실행되는 함수
const handlePrev = () => {
    alert("이전 클릭");
};

// modal에서 search 클릭했을 때 실행되는 함수
const handleSearch = () => {
    alert("검색 클릭");
    console.log(searchValue.value);
};

const handleSearchContent = (value) => {
    console.log("(부모)자동 완성 받아옴:", value);
    searchValue.value = value;
};

// list
function computedFilteredList() {
    if (selectedOption.value === "ALL") {
        return piecelistList.value;
    } else if (selectedOption.value === "MOVIE") {
        return piecelistList.value.filter(
            (item) => item.performanceType === "MOVIE"
        );
    } else if (selectedOption.value === "THEATER") {
        return piecelistList.value.filter(
            (item) => item.performanceType === "THEATER"
        );
    } else if (selectedOption.value === "MUSICAL") {
        return piecelistList.value.filter(
            (item) => item.performanceType === "MUSICAL"
        );
    } else if (selectedOption.value === "CONCERT") {
        return piecelistList.value.filter(
            (item) => item.performanceType === "CONCERT"
        );
    } else if (selectedOption.value === "ETC") {
        return piecelistList.value.filter(
            (item) => item.performanceType === "ETC"
        );
    } else {
        return [];
    }
}

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
    selectedOption.value = filterItems.value[index].labelType;
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
    commonStore.headerTitle = "조각 모아보기";
    commonStore.headerType = "header2";

    await store.findPiecelistList();
    tabMenu.value.addEventListener("mousedown", handleMouseDown);
    document.addEventListener("mouseup", handleMouseUp);
    tabMenu.value.addEventListener("mousemove", handleMouseMove);
});

onBeforeUnmount(() => {
    tabMenu.value.removeEventListener("mousedown", handleMouseDown);
    document.removeEventListener("mouseup", handleMouseUp);
    tabMenu.value.removeEventListener("mousemove", handleMouseMove);
});
</script>

<style>
.piecelistview-main-container {
    display: flex;
    flex-direction: column;
    height: calc(100% - 5rem);
}

.piecelistview-main-container > :first-child {
    flex: 0 0 auto;
}

.piecelistview-main-container > :not(:first-child) {
    flex: 1;
}

.piecelistview-search-container {
    margin-bottom: 1rem;
}

/* filter */
.piecelistview-scroll-container {
    position: relative;
    /* width: 450px; */
    transition: 0.5s ease;
    margin-bottom: 2rem;
}

.piecelistview-tab-navigation {
    position: relative;
    max-width: fit-content;
    margin: 0 auto;
    display: flex;
    justify-content: center;
    align-items: center;
}

.piecelistview-tab-menu {
    list-style: none;
    white-space: nowrap;
    overflow-x: auto;
    user-select: none;
    scroll-behavior: smooth;
}

.piecelistview-tab-menu.dragging {
    scroll-behavior: unset;
    cursor: grab;
}

.piecelistview-tab-menu::-webkit-scrollbar {
    display: none;
}

.piecelistview-tab-btn {
    display: inline-block;
    margin: 0 0.2rem;
    cursor: pointer;
    user-select: none;
    transition: 0.3s ease;
}

.piecelistview-tab-menu.dragging .tab-btn {
    pointer-events: none;
}

/* list */
.piecelistview-list-container {
    overflow-y: scroll;
    height: 60vh;
}

.piecelistview-list-container::-webkit-scrollbar {
    width: 0.2rem;
}

.piecelistview-list-container::-webkit-scrollbar-thumb {
    background-color: var(--gray2-color);
    border-radius: 1rem;
}

.piecelistview-list-container::-webkit-scrollbar-track {
    background-color: var(--gray-color);
    border-radius: 1rem;
}

.piecelistview-list-grid {
    display: grid;
    grid-template-columns: repeat(2, minmax(8rem, 1fr));
    justify-items: center;
    grid-gap: 1rem;
}

.piecelistview-list-item {
    width: auto;
}

.piecelistview-list-noitem {
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
