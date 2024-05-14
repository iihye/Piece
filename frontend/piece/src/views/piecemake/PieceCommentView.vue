<template>
    <div class="piececommentview-container">
        <!-- title -->
        <div class="piececommentview-main-title">
            마지막 조각을 완성해주세요
        </div>
        <div class="piececommentview-main-content">
            별점과 한줄평을 남겨보세요
        </div>

        <!-- star -->
        <div class="piececommentview-starinfo">
            <div class="piececommentview-star-label">
                <label
                    for="piececommentview-star-rating"
                    class="piececommentview-required-label"
                    >별점</label
                >
            </div>
            <div class="piececommentview-star-rating">
                <div
                    class="piececommentview-star"
                    v-for="index in 5"
                    :key="index"
                    @click="check(index)"
                    @mouseover="hover(index)"
                    @mouseleave="leave"
                >
                    <font-awesome-icon
                        :icon="
                            index <= hoverScore
                                ? ['fas', 'star']
                                : ['far', 'star']
                        "
                        :style="{
                            color:
                                index <= hoverScore
                                    ? 'var(--main-color)'
                                    : 'var(--gray2-color)',
                        }"
                        class="piececommentview-star-icon"
                    />
                </div>
            </div>
        </div>

        <!-- comment -->
        <div class="piececommentview-comment-group">
            <div class="piececommentview-label-and-counter">
                <label for="comment" class="piececommentview-required-label"
                    >한줄평</label
                >
                <div class="piececommentview-counter">
                    {{ characterCount }}/50
                </div>
            </div>
            <textarea
                class="piececommentview-textarea"
                id="comment"
                placeholder="한줄평을 남겨주세요"
                :value="commentInput"
                @input="handleInput"
                maxlength="50"
            ></textarea>
        </div>

        <!-- private -->
        <div class="piececommentview-private-container">
            <div>
                <label>조각 비공개</label>
            </div>
            <div>
                <input
                    type="checkbox"
                    class="piececommentview-private-checkbox"
                    name="scales"
                    checked
                    v-model="pieceValue.isPrivate"
                />
            </div>
        </div>
    </div>
    <div class="piecemake-button-container">
        <RouterLink :to="{ name: 'piecefront' }">
            <RoundButton
                :roundButtonContent="'다음'"
                :isRoundDisable="isRoundDisable"
            ></RoundButton>
        </RouterLink>
    </div>
</template>

<script setup>
import { ref, computed, watch } from "vue";
import { usePieceStore } from "@/stores/piece.js";
import RoundButton from "@/components/button/RoundButton.vue";

const isRoundDisable = computed(() => {
    return !(!pieceValue.score || !pieceValue.comment);
});

const pieceStore = usePieceStore();
const pieceValue = pieceStore.pieceValue;
const commentInput = ref(pieceValue.comment || "");

function updateSelected(optionId) {
    pieceStore.setPieceValue("score", optionId);
}

function handleInput(event) {
    const input = event.target.value;
    if (input.length <= 50) {
        commentInput.value = input; // 입력 값을 업데이트
        pieceStore.setPieceValue("comment", input); // 스토어에 저장
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

<style scoped>
.piececommentview-container {
    display: flex;
    flex-direction: column;
    height: calc(100vh - 9.25rem);
    padding-top: 1rem;
    padding-bottom: 1rem;
    margin: 0 1rem 0 1rem;
}

/* title */
.piececommentview-main-title {
    font-family: "Bold";
    font-size: 1.6rem;
    color: var(--black-color);
    margin: 0 0 0.4rem 0;
    user-select: none;
}

.piececommentview-main-content {
    font-family: "Regular";
    font-size: 1rem;
    color: var(--gray2-color);
    margin-bottom: 1rem;
    display: flex;
    justify-content: left;
    align-items: center;
    user-select: none;
}

/* star */
.piececommentview-starinfo {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 1rem;
}

.piececommentview-star-label {
    flex-grow: 1;
}

.piececommentview-required-label::after {
    content: "*";
    color: var(--red-color);
    vertical-align: 0.2rem;
}

.piececommentview-star-rating {
    display: flex;
    justify-content: center;
    gap: 0.25rem; /* Adjust or remove the gap */
    /* width: 100%; */
    flex-grow: 4;
}

label {
    font-family: "Semi";
    font-size: 1rem;
    text-align: left;
}

.piececommentview-star-icon {
    cursor: pointer;
    margin-right: -0.25rem; /* Slight overlap to prevent gap detection */
}

/* comment */
.piececommentview-comment-group {
    margin-bottom: 1rem;
}

.piececommentview-label-and-counter {
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
}

.piececommentview-counter {
    font-family: "Regular";
    font-size: 1rem;
    color: var(--gray2-color);
}

.piececommentview-textarea {
    width: calc(100% - 2rem);
    height: 5rem;
    border-radius: 5px;
    margin-top: 0.625rem;
    padding: 1rem;
    resize: none;
    border-radius: 0.625rem;
    border: 1px solid var(--gray-color);
    outline-color: var(--main-color);
    font-family: "Regular";
    font-size: 1rem;
    line-height: 1.4rem;
}

/* private */
.piececommentview-private-container {
    display: flex;
    margin-top: 0.625rem;
}

.piececommentview-private-checkbox {
    accent-color: var(--main-color);
    margin-left: 1rem;
}

/* button */
.piecemake-button-container {
    position: fixed;
    left: 0;
    right: 0;
    bottom: 4rem;
    text-align: center;
}
</style>
