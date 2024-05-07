


<!-- 수정해야됨. 아예 무시해주세요 -->
<template>
    <!-- header -->
    <h1>케이크 모아보기</h1>

    <!-- filter -->
    <div class="cakelist-scroll-container">
        <div class="cakelist-tab-navigation">
            <div class="cakelist-tab-menu" ref="tabMenu">
                <FilterItem
                    v-for="(item, index) in filterItems"
                    class="cakelist-tab-btn"
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
    <div class="cake-list-container">
        <div class="cake-list-grid">
            <div v-for="(item, index) in filteredMyList" :key="index" class="cake-list-item">
            <ListImageItem
                :pieceId="item.pieceId"
                :performanceType="item.performanceType"
                :frontImg="item.frontImg"
                :title="item.title"
                @click="handleItemClick"
            ></ListImageItem>
        </div>
        </div>
        
    </div>
        <!-- Cake list -->
        <!-- <div class="cake-list">
            <CakeItem
            v-for="item in cakeItems"
            :key="item.id"
            :item="item"
            />
        </div> -->
</template>

<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from "vue";
import { useRouter } from "vue-router";
import CakeItem from '@/components/item/CakeItem.vue'
import SearchInput from '@/components/modal/SearchInput.vue'
import FilterItem from '@/components/item/FilterItem.vue'
import ListImageItem from "@/components/item/ListImageItem.vue";

const store = usePiecelistStore();

const piecelistMyList = computed(() => store.getPiecelistMyList);
const filteredMyList = computed(() => computedFilteredMyList());
const selectedOption = ref("ALL");

const cakeItems = ref([
    { id: 1, title: '범죄도시4', image: '@/assets/logo.png', category: '영화', status: '진행 예정' },
    { id: 2, title: 'IM HERO 임영웅 콘서트', image: 'path/to/image2.jpg', category: '콘서트', status: '진행 예정' },
    { id: 3, title: '데미안', image: 'path/to/image3.jpg', category: '뮤지컬', status: '진행 중' },
    { id: 4, title: '아트', image: 'path/to/image4.jpg', category: '전시', status: '진행 예정' }
]);

function computedFilteredMyList() {
    if (selectedOption.value === "ALL") {
        return piecelistMyList.value;
    } else if (selectedOption.value === "MOVIE") {
        return piecelistMyList.value.filter((item) => item.performanceType === "MOVIE");
    } else if (selectedOption.value === "THEATER") {
        return piecelistMyList.value.filter((item) => item.performanceType === "THEATER");
    } else if (selectedOption.value === "MUSICAL") {
        return piecelistMyList.value.filter((item) => item.performanceType === "MUSICAL");
    } else if (selectedOption.value === "CONCERT") {
        return piecelistMyList.value.filter((item) => item.performanceType === "CONCERT");
    } else if (selectedOption.value === "ETC") {
        return piecelistMyList.value.filter((item) => item.performanceType === "ETC");
    } else {
        return [];
    }
}

const handleItemClick = () => {
    alert("선택");
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
    await store.findPiecelistMyList();
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

/* filter */
.cakelist-scroll-container {
    position: relative;
    /* width: 450px; */
    transition: 0.5s ease;
    margin-bottom: 2rem;
}

.cakelist-tab-navigation {
    position: relative;
    max-width: fit-content;
    margin: 0 auto;
    display: flex;
    justify-content: center;
    align-items: center;
}

.cakelist-tab-menu {
    list-style: none;
    white-space: nowrap;
    overflow-x: auto;
    user-select: none;
    scroll-behavior: smooth;
}

.cakelist-tab-menu.dragging {
    scroll-behavior: unset;
    cursor: grab;
}

.cakelist-tab-menu::-webkit-scrollbar {
    display: none;
}

.cakelist-tab-btn {
    display: inline-block;
    margin: 0 0.2rem;
    cursor: pointer;
    user-select: none;
    transition: 0.3s ease;
}

.cakelist-tab-menu.dragging .tab-btn {
    pointer-events: none;
}

/* list */
.cake-list-grid {
    display: grid;
    grid-template-columns: repeat(2, minmax(8rem, 1fr));
    justify-items: center; 
    grid-gap: 1rem;
}

.cake-list-item {
    width: auto; 
}

@media (min-width: 1024px) {
    .cake-list-grid {
        grid-template-columns: repeat(4, minmax(16rem, 5fr));
    }
}
</style>