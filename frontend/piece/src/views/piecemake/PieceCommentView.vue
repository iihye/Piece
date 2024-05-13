<template>
    <h1>마지막 조각을 완성해주세요</h1>
    <div class="piececommentview-container">
        <div class="piececommentview-starinfo">
            <div class="pieveimageview-star-label">
                <label for="pieveimageview-star-rating" class="pieveimageview-required-label">별점</label>
            </div>
            <div class="pieveimageview-star-rating">
                <div
                    class="pieveimageview-star"
                    v-for="index in 5"
                    :key="index"
                    @click="check(index)"
                    @mouseover="hover(index)"
                    @mouseleave="leave"
                >
                    <font-awesome-icon 
                        :icon="index <= hoverScore ? ['fas', 'star'] : ['far', 'star']" 
                        :style="{ color: index <= hoverScore ? 'var(--main-color)' : 'var(--gray2-color)' }"
                        class="pieveimageview-star-icon"
                    />
                </div>
            </div>

        </div>
        <div class="form-group">
            <div class="label-and-counter">
                <label for="comment" class="pieveimageview-required-label">한줄평</label>
                <div>{{ characterCount }}/50</div>
            </div>
            <textarea id="comment" placeholder="한줄평을 남겨주세요"
            :value="commentInput"
            @input="handleInput"
            maxlength="50"></textarea>
        </div>
    </div>
    <div class="piecemake-button-container">
        <RouterLink :to="{ name: 'piecefront' }">
            <RoundButton :roundButtonContent="'다음'" :isRoundDisable="isRoundDisable"></RoundButton>
        </RouterLink>
    </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import {usePieceStore} from '@/stores/piece.js'
import RoundButton from '@/components/button/RoundButton.vue';

const isRoundDisable = computed(() => {
    return !(!pieceValue.score || !pieceValue.comment);
});

const pieceStore = usePieceStore();
const pieceValue = pieceStore.pieceValue;
const commentInput = ref(pieceValue.comment || '');

function updateSelected(optionId) {
    pieceStore.setPieceValue('score', optionId);
}

function handleInput(event) {
  const input = event.target.value;
  if (input.length <= 50) {
    commentInput.value = input; // 입력 값을 업데이트
    pieceStore.setPieceValue('comment', input); // 스토어에 저장
  }
}

const characterCount = computed(() => commentInput.value.length);


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
.label-and-counter {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.piececommentview-container {
    font-family: "Regular";
    font-size: 1.4rem;
    font-size: 1rem;
}

.piececommentview-starinfo {
    display: flex;
}

.pieveimageview-star-rating {
    display: flex;
    justify-content: center;
    gap: 0.25rem; /* Adjust or remove the gap */
    /* width: 100%; */
    flex-grow: 4;
}

.pieveimageview-star-label {
    flex-grow: 1;
}

.pieveimageview-star-icon {
    cursor: pointer;
    margin-right: -0.25rem; /* Slight overlap to prevent gap detection */
}

.form-group {
    margin-top: 20px;
}

textarea {
    width: 100%;
    height: 100px;
    border-radius: 5px;
    margin-top: 0.625rem;
    resize: none;
    border-radius: 0.625rem;
    border: 1px solid var(--gray-color);
    outline-color: var(--main-color);
}

.pieveimageview-required-label::after {
    content: '*';
    color: var(--red-color);
    vertical-align: 0.2rem;
}

.piecemake-button-container {
  position: fixed;
  left: 0;
  right: 0;
  bottom: 4rem;
  text-align: center;
}
</style>
