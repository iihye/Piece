<template>
    <h1>어떤 케이크였나요?</h1>
    <div class="container">
        <div class="form-group">
            <label for="movie-types" class="required-label">공연 분류</label>
            <ButtonGroup
                id="movie-types"
                groupName="공연 분류"
                :options="options"
                
                @selected="updateSelected"
            />
        </div>
        <div class="form-group">
            <label for="content-name" class="required-label">공연명</label>
            <TextInput id="content-name" placeholder="공연명을 입력하세요" v-model="pieceValue.name" />
        </div>
        <div class="form-group">
            <label for="date-picker" class="required-label">날짜 선택</label>
            <DatePicker id="date-picker" v-model="pieceValue.date"></DatePicker>
        </div>
        <div class="form-group">
            <label for="time-picker">시간 선택</label>
            <TimePicker id="time-picker" v-model="pieceValue.time"></TimePicker>
        </div>
        <div class="form-group">
            <label for="cast-name">출연</label>
            <TextInput id="cast-name" placeholder="출연진을 입력하세요" v-model="pieceValue.cast" />
        </div>
        <div class="form-group">
            <label for="location-name">장소</label>
            <TextInput id="location-name" placeholder="장소를 입력하세요" v-model="pieceValue.location" />
        </div>
        <div class="form-group">
            <label for="director-name">감독</label>
            <TextInput id="director-name" placeholder="감독을 입력하세요" v-model="pieceValue.director" />
        </div>
        <div class="form-group">
            <label for="seat-info">좌석</label>
            <TextInput id="seat-info" placeholder="좌석을 입력하세요" v-model="pieceValue.seat" />
        </div>
        <div class="form-group">
            <label for="content-name">가격</label>
            <TextInput id="content-name" placeholder="가격을 입력하세요" v-model="pieceValue.price" />
        </div>
        <RouterLink :to="{ name: 'piececomment' }">감상평 작성</RouterLink>
    </div>

</template>

<script setup>
import { ref } from 'vue';
import TextInput from '@/components/text/OnlyInput.vue';
import ButtonGroup from '@/components/button/SelectableButtonGroup.vue';
import DatePicker from '@/components/custom/CustomDatePicker.vue';
import TimePicker from '@/components/custom/CustomTimePicker.vue';
import {usePieceStore} from '@/stores/piece.js'

const pieceStore = usePieceStore();
const pieceValue = pieceStore.pieceValue;
function updateSelected(optionId) {
    pieceStore.setPieceValue('type', optionId);
}

const options = ref([
    { id: 'movie', name: '영화' },
    { id: 'musical', name: '뮤지컬' },
    { id: 'concert', name: '콘서트' },
    { id: 'play', name: '연극' },
    { id: 'other', name: '기타' }
]);
const selected = ref(null);
const date = ref(null);

</script>

<style>
.container {
    display: grid;
    grid-template-columns: auto 1fr;
    gap: 20px;
    /* align-items: center; */
    max-width: 600px;
    margin: auto;
}

.form-group {
    display: contents;
}

label {
    font-size: 1rem;
    font-weight: bold;
    padding: 10px;
    text-align: left;
}

input, .button-group {
    grid-column: 2 / 3; 
}

.button-group {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
}

.required-label::after {
    content: '*';
    color: var(--red-color);
    vertical-align: 0.2rem;
}
</style>
