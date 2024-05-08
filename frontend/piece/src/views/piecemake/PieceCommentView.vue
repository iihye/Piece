<template>
    <h1>마지막 조각을 완성해주세요</h1>
    <div class="star-rating">
        <div
            class="star"
            v-for="index in 5"
            :key="index"
            @click="check(index)"
            @mouseover="hover(index)"
            @mouseleave="leave"
        >
            <font-awesome-icon 
                :icon="index <= hoverScore ? ['fas', 'star'] : ['far', 'star']" 
                :style="{ color: index <= hoverScore ? 'var(--main-color)' : 'var(--gray2-color)' }"
                class="star-icon"
            />
        </div>
    </div>
    <div class="form-group">
        <label for="comment">한줄평*</label>
        <textarea id="comment" placeholder="한줄평을 남겨주세요" v-model="pieceValue.comment" maxlength="50"></textarea>
    </div>
    <RouterLink :to="{ name: 'piecefront' }">앞면</RouterLink>
</template>

<script setup>
import { ref } from 'vue';
import {usePieceStore} from '@/stores/piece.js'

const pieceStore = usePieceStore();
const pieceValue = pieceStore.pieceValue;
function updateSelected(optionId) {
    pieceStore.setPieceValue('score', optionId);
}

const score = ref(0);
const hoverScore = ref(0);

function check(index) {
    score.value = index;
    hoverScore.value = index;
    updateSelected(index);
}

function hover(index) {
    hoverScore.value = index;
}

function leave() {
    hoverScore.value = score.value;
}
</script>

<style>
.star-rating {
    display: flex;
    justify-content: center;
    gap: 0.25rem; /* Adjust or remove the gap */
}

.star-icon {
    cursor: pointer;
    margin-right: -0.25rem; /* Slight overlap to prevent gap detection */
}

.form-group {
    margin-top: 20px;
}

textarea {
    width: 100%;
    height: 100px;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
}
</style>
