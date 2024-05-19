<template>
    <!-- search -->
    <div class="cakelistview-search-area">
        <!-- <TextInput
            class="cakelistview-search-input"
            placeholder="검색어를 입력하세요"
            v-model="searchQuery"
            @focus="handleFocus"
            @update:modelValue="(value) => (searchQuery = value)"
        />
        <InputPreview
            class="cakelistview-search-preview"
            :searchQuery="searchQuery"
            :searchResults="searchResults"
            :isFocused="isFocused"
            @select="handleSelect"
        /> -->
    </div>

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
                    v-for="(item, index) in displayedCakeList"
                    :key="index"
                    class="cakelistview-list-item"
                >
                    <ListCakeItem
                        :cultureId="item.cultureId"
                        :cultureType="item.cultureType"
                        :code="item.code"
                        :title="item.title"
                        :imageUrl="item.imageUrl"
                        :frontImg="convertToHttps(item.imageUrl)"
                        @click="handleItemClick(item)"
                    >
                    </ListCakeItem>
                </div>
            </div>
        </div>

    <!-- </div> -->
</template>

<script setup>
import router from "@/router";
import { ref, watch, computed, onMounted, onBeforeUnmount } from "vue";
import { useCommonStore } from "@/stores/common";
import { useCakeStore } from "@/stores/cake";
import { useCakeDetailStore } from "@/stores/cakedetail";
import FilterItem from "@/components/item/FilterItem.vue";
import ListCakeItem from "@/components/item/ListCakeItem.vue";
import TextInput from "@/components/text/OnlyInput.vue";
import InputPreview from "@/components/text/InputPreview.vue";

const commonStore = useCommonStore();
const store = useCakeStore(); 
const detailStore = useCakeDetailStore();

const filteredCakeList = computed(() => store.getCakeListFiltered);
const selectedOptionCakeList = computed(() => store.getSelectOptionCakeList);

const searchResults = ref([]);
const searchQuery = ref("");
const isFocused = ref(false);

const displayedCakeList = computed(() => {
    if (searchQuery.value) {
        return filteredCakeList.value.filter(item => 
            item.title.toLowerCase().includes(searchQuery.value.toLowerCase())
        );
    } else {
        return filteredCakeList.value;
    }
});

watch(searchQuery, (newValue) => {
    if (newValue.length >= 2) {
        searchMovieDebouncing(newValue);
    } else {
        searchResults.value = [];
    }
});

const searchMovieDebouncing = debounce(async (query) => {
    const data = await store.getSearchMovieList(query);
    if (data) {
        searchResults.value = data;
    }
}, 250);

const emit = defineEmits(["select"]);

function handleSelect(item) {
    handleBlur();
    emit("select", item);
}

const handleItemClick = async (item) => {
    try {
        if (item.cultureType === "MOVIE") {
            if (item && item.code && item.cultureId) {
                router.push({
                    name: "CakeDetail",
                    params: {
                        concertId: item.code,
                        cultureId: item.cultureId,
                    }
                });
                detailStore.setCakeCultureType(item.cultureType);
            } else {
                console.error('Invalid movie data:', movieData);
            }
        } else {
            if (item && item.code && item.cultureId) {
                router.push({
                    name: "CakeDetail",
                    params: {
                        concertId: item.code,
                        cultureId: item.cultureId,
                    }
                });
                detailStore.setCakeCultureType(item.cultureType);
            } else {
                console.error('Invalid concert data:', item);
            }
        }
    } catch (error) {
        console.error('Error handling item click:', error);
    }
};

// https
function convertToHttps(url) {
    if (url.startsWith('http://')) {
        return url.replace('http://', 'https://');
    }
    return url;
}

const handleFocus = () => {
    isFocused.value = true;
};

const handleBlur = () => {
    isFocused.value = false;
};

// debounce
function debounce(func, delay) {
    let timeoutId;

    return function (...args) {
        clearTimeout(timeoutId);
        timeoutId = setTimeout(() => {
            func.apply(this, args);
        }, delay);
    };
}

const handleDocumentClick = (event) => {
    if (!event.target.closest(".cakelistview-container")) {
        handleBlur();
    }
};

document.addEventListener("click", handleDocumentClick);

const filterItems = ref([
    { labelType: "ALL", title: "전체", isSelect: true },
    { labelType: "MOVIE", title: "영화", isSelect: false },
    { labelType: "THEATER", title: "연극", isSelect: false },
    { labelType: "MUSICAL", title: "뮤지컬", isSelect: false },
    { labelType: "CONCERT", title: "콘서트", isSelect: false },
    { labelType: "ETC", title: "기타", isSelect: false },
]);

const handleItemSelectClick = async (index) => {
    filterItems.value.forEach((item) => (item.isSelect = false));
    filterItems.value[index].isSelect = true;
    store.setSelectOptionCakeList(filterItems.value[index].labelType);
    await store.findCakeList(selectedOptionCakeList.value, 10, true);
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

    if (tabMenu.value) {
        tabMenu.value.addEventListener("mousedown", handleMouseDown);
        tabMenu.value.addEventListener("mousemove", handleMouseMove);
    }
    document.addEventListener("mouseup", handleMouseUp);
    listContainer.value.addEventListener("scroll", handleScroll);
});


onBeforeUnmount(() => {
    if (tabMenu.value) {
        tabMenu.value.removeEventListener("mousedown", handleMouseDown);
        tabMenu.value.removeEventListener("mousemove", handleMouseMove);
    }
    document.removeEventListener("mouseup", handleMouseUp);
    listContainer.value.removeEventListener("scroll", handleScroll);
});

</script>

<style>
/* filter */
.cakelistview-scroll-container {
    position: relative;
    /* width: 450px; */
    transition: 0.5s ease;
    margin-bottom: 1rem;
}

.cakelistview-tab-navigation {
    position: relative;
    max-width: fit-content;
    margin: 0 auto;
    display: flex;
    justify-content: center;
    align-items: center;
}

/* search */
.cakelistview-search-area {
    width: 15rem;
    margin-bottom: 1rem;
    align-self: end;
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

/* list */
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
    justify-items: left;
}

.cakelistview-list-item {
    width: auto;
    cursor: pointer;
}

.cakelistview-list-noitem {
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>
