<template>
    <h1>어떤 케이크였나요?</h1>
    <div class="pieceinfoview-container">
        <div class="pieceinfoview-form-group">
            <label for="movie-types" class="pieceinfoview-required-label">공연 분류</label>
            <ButtonGroup
                class="pieceinfoview-button-group"
                id="movie-types"
                groupName="공연 분류"
                :options="options"
                @selected="updateSelected"
            />
        </div>
        <div class="pieceinfoview-form-group">
            <label for="content-name" class="pieceinfoview-required-label">공연명</label>
            <TextInput id="content-name" placeholder="공연명을 입력하세요" v-model="pieceValue.title" />
        </div>
        <div class="pieceinfoview-form-group">
            <label for="date-picker" class="pieceinfoview-required-label">날짜 선택</label>
            <DatePicker id="date-picker" v-model="dateValue"></DatePicker>
        </div>
        <div class="pieceinfoview-form-group">
            <label for="time-picker">시간 선택</label>
            <TimePicker id="time-picker" v-model="timeValue"></TimePicker>
        </div>
        <div class="pieceinfoview-form-group">
            <label for="cast-name">출연</label>
            <TextInput id="cast-name" placeholder="출연진을 입력하세요" v-model="pieceValue.cast" />
        </div>
        <div class="pieceinfoview-form-group">
            <label for="location-name">장소</label>
            <TextInput id="location-name" placeholder="장소를 입력하세요" v-model="pieceValue.address" />
        </div>
        <div class="pieceinfoview-form-group">
            <label for="director-name">감독</label>
            <TextInput id="director-name" placeholder="감독을 입력하세요" v-model="pieceValue.supervision" />
        </div>
        <div class="pieceinfoview-form-group">
            <label for="seat-info">좌석</label>
            <TextInput id="seat-info" placeholder="좌석을 입력하세요" v-model="pieceValue.seat" />
        </div>
        <div class="pieceinfoview-form-group">
            <label for="content-name">가격</label>
            <TextInput id="content-name" placeholder="가격을 입력하세요" v-model="pieceValue.price" inputType="number" />
        </div>
    </div>
    <div class="piecemake-button-container">
        <RouterLink :to="{ name: 'piececomment' }">
            <RoundButton :roundButtonContent="'다음'" :isRoundDisable="isRoundDisable"></RoundButton>
        </RouterLink>
    </div>

</template>

<script setup>
import { ref, watch, computed } from 'vue';
import TextInput from '@/components/text/OnlyInput.vue';
import ButtonGroup from '@/components/button/SelectableButtonGroup.vue';
import DatePicker from '@/components/custom/CustomDatePicker.vue';
import TimePicker from '@/components/custom/CustomTimePicker.vue';
import {usePieceStore} from '@/stores/piece.js'
import RoundButton from '@/components/button/RoundButton.vue';

const pieceStore = usePieceStore();
const pieceValue = pieceStore.pieceValue;
function updateSelected(optionId) {
    pieceStore.setPieceValue('performanceType', optionId);
}

const options = ref([
    { id: 'MOVIE', name: '영화' },
    { id: 'MUSICAL', name: '뮤지컬' },
    { id: 'CONCERT', name: '콘서트' },
    { id: 'THEATER', name: '연극' },
    { id: 'ETC', name: '기타' }
]);
const selected = ref(null);
const date = ref(null);

const isRoundDisable = computed(() => {
    return !(!pieceValue.performanceType || !pieceValue.title || !dateValue.value);
});

// 컴포넌트 내부의 날짜, 시간 변수 생성
const dateValue = ref(null);
const timeValue = ref(null);

// Date 형식을 변환하는 함수
function formatDate(date) {
  return date ? new Date(date).toISOString().split('T')[0] : null;
}

// Time 형식을 변환하는 함수
function formatTime(time) {
    if (time) {
        const { hours, minutes } = time;
        const formattedHours = hours < 10 ? `0${hours}` : hours;
        const formattedMinutes = minutes < 10 ? `0${minutes}` : minutes;
        return `${formattedHours}:${formattedMinutes}`;
    } else {
        return null;
    }
}

// 시간, 날짜 형식 지정
function updateDate(newDate) {
    pieceStore.setPieceValue('date', formatDate(newDate));
}

function updateTime(time) {
  pieceStore.setPieceValue('time', formatTime(time));
}

watch(dateValue, (newValue) => {
    updateDate(newValue);
});

watch(timeValue, (newValue) => {
    updateTime(newValue);
});
</script>

<style scoped>
@import "@/components/css/font.css";

.pieceinfoview-container {
    display: grid;
    grid-template-columns: auto 1fr;
    gap: 1.25rem;
    /* align-items: center; */
    max-width: 37.5rem;
    margin: auto;
    margin-bottom: 2rem;

}

.pieceinfoview-form-group {
    display: contents;
}

label {
    font-family: "Regular";
    font-size: 1.4rem;
    font-size: 1rem;
    padding: 0.625rem;
    text-align: left;
}

.pieceinfoview-button-group {
    display: flex;
    flex-wrap: wrap;
    gap: 0.625rem;
}

.pieceinfoview-required-label::after {
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
