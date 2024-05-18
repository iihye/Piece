<template>
<!-- filter -->
<div class="cakelistview-scroll-container">
    <div class="cakelistview-tab-navigation">
        <div class="cakelistview-tab-menu" ref="tabMenu">
            <FilterItem
                v-for="(item, index) in filterItems"
                class="cakelistview-tab-btn"
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
<div class="cakelistview-list-container" ref="listContainer">
    <div class="cakelistview-list-grid">
        <div
            v-for="(item, index) in filteredCakeList"
            :key="index"
            class="cakelistview-list-item"
        >
            <ListCakeItem
                :cultureId="item.cultureId"
                :cultureType="item.cultureType"
                :code="item.code"
                :title="item.title"
                :imageUrl="item.imageUrl"
                :frontImg="item.frontImg"
                @click="handleItemClick(item)"
            >
            </ListCakeItem>
        </div>
    </div>
</div>
</template>



<script setup>
import router from "@/router";
import { ref, computed, onMounted, onBeforeUnmount } from "vue";
import { useCommonStore } from "@/stores/common";
import { useCakeStore } from "@/stores/cake";
import FilterItem from "@/components/item/FilterItem.vue";
import ListCakeItem from "@/components/item/ListCakeItem.vue";

const commonStore = useCommonStore();
const store = useCakeStore();

const filteredCakeList = computed(() => store.getCakeListFiltered);
const selectedOptionCakeList = computed(() => store.getSelectOptionCakeList);

const handleItemClick = (item) => {
    router.push({
        name: "CakeDetail",
        params: {
            id: item.cultureId,
        },
    });
};

const filterItems = ref([
    { labelType: "ALL", title: "전체", isSelect: true },
    { labelType: "MOVIE", title: "영화", isSelect: false },
    { labelType: "THEATER", title: "연극", isSelect: false },
    { labelType: "MUSICAL", title: "뮤지컬", isSelect: false },
    { labelType: "CONCERT", title: "콘서트", isSelect: false },
    { labelType: "ETC", title: "기타", isSelect: false }
]);

const handleItemSelectClick = async (index) => {
    filterItems.value.forEach((item) => (item.isSelect = false));
    filterItems.value[index].isSelect = true;
    store.setSelectOptionCakeList(filterItems.value[index].labelType);
    await store.findCakeList(selectedOptionCakeList.value, 10);
    listContainer.value.scrollTop = 0;
};

// filter
const tabMenu = ref(null);
const listContainer = ref(null);
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

const handleScroll = async () => {
    const container = listContainer.value;
    if (container.scrollTop + container.clientHeight >= container.scrollHeight - 10) {
        await store.loadMoreCakes();
    }
};

onMounted(async () => {
    commonStore.headerTitle = "케이크 모아보기";
    commonStore.headerType = "header2";

    await store.findCakeList(selectedOptionCakeList.value, 10);

    const index = filterItems.value.findIndex(
        (item) => item.labelType === selectedOptionCakeList.value
    );

    if (index !== -1) {
        filterItems.value[0].isSelect = false;
        filterItems.value[index].isSelect = true;
    }

    if (index === 5) {
        tabMenu.value.scrollLeft = 1000;
    }

    tabMenu.value.addEventListener("mousedown", handleMouseDown);
    document.addEventListener("mouseup", handleMouseUp);
    tabMenu.value.addEventListener("mousemove", handleMouseMove);
    listContainer.value.addEventListener('scroll', handleScroll);
});

onBeforeUnmount(() => {
    tabMenu.value.removeEventListener("mousedown", handleMouseDown);
    document.removeEventListener("mouseup", handleMouseUp);
    tabMenu.value.removeEventListener("mousemove", handleMouseMove);
    listContainer.value.removeEventListener('scroll', handleScroll);
});
</script>



<style>
.cakelistview-scroll-container {
    position: relative;
    transition: 0.5s ease;
    margin-bottom: 2rem;
}

.cakelistview-tab-navigation {
    position: relative;
    max-width: fit-content;
    margin: 0 auto;
    display: flex;
    justify-content: center;
    align-items: center;
}

.cakelistview-tab-menu {
    list-style: none;
    white-space: nowrap;
    overflow-x: auto;
    user-select: none;
    scroll-behavior: smooth;
}

.cakelistview-tab-menu.dragging {
    scroll-behavior: unset;
    cursor: grab;
}

.cakelistview-tab-menu::-webkit-scrollbar {
    display: none;
}

.cakelistview-tab-btn {
    display: inline-block;
    margin: 0 0.2rem;
    cursor: pointer;
    user-select: none;
    transition: 0.3s ease;
}

.cakelistview-tab-menu.dragging .tab-btn {
    pointer-events: none;
}

.cakelistview-list-container {
    height: 80vh;
    overflow-y: auto;
    -ms-overflow-style: none;
    scrollbar-width: none;
}

.cakelistview-list-container::-webkit-scrollbar {
    display: none;
}

.cakelistview-list-grid {
    display: flex;
    flex-wrap: wrap;
}

.cakelistview-list-item {
    width: auto;
    cursor: pointer;
}
</style>