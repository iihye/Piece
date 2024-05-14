<template>
    <div
        class="inputpreviewitem-container"
        @click="handleClick(item)"
        @mouseover="hover = true"
        @mouseleave="hover = false"
        :class="{ 'inputpreviewitem-hover': hover }"
    >
        <img
            :src="item.poster_path"
            alt="Poster"
            class="inputpreviewitem-movie-poster"
        />
        <span
            class="inputpreviewitem-movie-title"
            v-html="highlightSearchQuery(item.title)"
        ></span>
    </div>
</template>

<script setup>
import { ref, defineProps, defineEmits } from "vue";

const props = defineProps({
    item: Object,
    searchQuery: String,
});

const emits = defineEmits(["select"]);
const hover = ref(false);

function handleClick(item) {
    emits("select", item);
}

function highlightSearchQuery(title) {
    if (!props.searchQuery) return title;

    // searchQuery와 일치하는 부분을 다른 색상으로 표시
    const regex = new RegExp(props.searchQuery, "gi");
    return title.replace(
        regex,
        (match) => `<span style="color: var(--main-color);">${match}</span>`
    );
}
</script>

<style scoped>
.inputpreviewitem-container {
    cursor: pointer;
    transition: background-color 0.3s;
    margin-bottom: 0.2rem;
    display: flex;
    align-items: center;
}

.inputpreviewitem-hover {
    background-color: var(--gray-color);
}

.inputpreviewitem-movie-poster {
    width: 50px;
    height: 75px;
    object-fit: cover;
    margin-right: 10px;
}

.inputpreviewitem-movie-title {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: 500px;
}
</style>
