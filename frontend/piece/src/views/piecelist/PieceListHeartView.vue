<template>
    <div class="piecelistheartview-main-container">
        <!-- filter -->
        <div class="piecelistheartview-scroll-container">
            <div class="piecelistheartview-tab-navigation">
                <div class="piecelistheartview-tab-menu" ref="tabMenu">
                    <FilterItem
                        v-for="(item, index) in filterItems"
                        class="piecelistheartview-tab-btn"
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

        <div
            v-if="filteredList.length === 0"
            class="piecelistheartview-list-noitem"
        >
            <NoItem :content="'찜한 목록이 없어요'"></NoItem>
        </div>
        <div v-else>
            <div class="piecelistheartview-list-container">
                <div class="piecelistheartview-list-grid">
                    <div
                        v-for="(item, index) in filteredList"
                        :key="index"
                        class="piecelistheartview-list-item"
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
import { usePiecelistStore } from "@/stores/piecelist";
import FilterItem from "@/components/item/FilterItem.vue";
import ListImageItem from "@/components/item/ListImageItem.vue";
import NoItem from "@/components/item/NoItem.vue";

const store = usePiecelistStore();

const piecelistHeartList = computed(() => store.getPiecelistHeartList);
const filteredList = computed(() => computedFilteredList());
const selectedOption = ref("ALL");

function computedFilteredList() {
    if (selectedOption.value === "ALL") {
        return piecelistHeartList.value;
    } else if (selectedOption.value === "MOVIE") {
        return piecelistHeartList.value.filter(
            (item) => item.performanceType === "MOVIE"
        );
    } else if (selectedOption.value === "THEATER") {
        return piecelistHeartList.value.filter(
            (item) => item.performanceType === "THEATER"
        );
    } else if (selectedOption.value === "MUSICAL") {
        return piecelistHeartList.value.filter(
            (item) => item.performanceType === "MUSICAL"
        );
    } else if (selectedOption.value === "CONCERT") {
        return piecelistHeartList.value.filter(
            (item) => item.performanceType === "CONCERT"
        );
    } else if (selectedOption.value === "ETC") {
        return piecelistHeartList.value.filter(
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
    await store.findPiecelistHeartList();
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
.piecelistheartview-main-container {
    display: flex;
    flex-direction: column;
    height: 100%;
}

.piecelistheartview-main-container > :first-child {
    flex: 0 0 auto;
}

.piecelistheartview-main-container > :not(:first-child) {
    flex: 1;
}

/* filter */
.piecelistheartview-scroll-container {
    position: relative;
    /* width: 450px; */
    transition: 0.5s ease;
    margin-bottom: 1rem;
}

.piecelistheartview-tab-navigation {
    position: relative;
    max-width: fit-content;
    margin: 0 auto;
    display: flex;
    justify-content: center;
    align-items: center;
}

.piecelistheartview-tab-menu {
    list-style: none;
    white-space: nowrap;
    overflow-x: auto;
    user-select: none;
    scroll-behavior: smooth;
}

.piecelistheartview-tab-menu.dragging {
    scroll-behavior: unset;
    cursor: grab;
}

.piecelistheartview-tab-menu::-webkit-scrollbar {
    display: none;
}

.piecelistheartview-tab-btn {
    display: inline-block;
    margin: 0 0.2rem;
    cursor: pointer;
    user-select: none;
    transition: 0.3s ease;
}

.piecelistheartview-tab-menu.dragging .tab-btn {
    pointer-events: none;
}

/* list */
.piecelistheartview-list-container {
    overflow-y: scroll;
    height: 60vh;
}

.piecelistheartview-list-container::-webkit-scrollbar {
    width: 0.2rem;
}

.piecelistheartview-list-container::-webkit-scrollbar-thumb {
    background-color: var(--gray2-color);
    border-radius: 1rem;
}

.piecelistheartview-list-container::-webkit-scrollbar-track {
    background-color: var(--gray-color);
    border-radius: 1rem;
}

.piecelistheartview-list-grid {
    display: grid;
    grid-template-columns: repeat(2, minmax(8rem, 1fr));
    justify-items: center;
    grid-gap: 1rem;
}

.piecelistheartview-list-item {
    width: auto;
}

.piecelistheartview-list-noitem {
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
