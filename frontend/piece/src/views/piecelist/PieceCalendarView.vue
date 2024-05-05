<template>
    <div class="piececalendarview-main-container">
        <!-- header -->
        <div class="piececalendarview-header-container">
            <button @click="changeMonth(-1)">이전</button>
            <h2>{{ state.calendarHeader }}</h2>
            <button @click="changeMonth(1)">다음</button>
        </div>

        <!-- Calendar -->
        <div class="piececalendarview-calendar-container">
            <div class="piececalendarview-weekdays-container">
                <div v-for="day in week" :key="day" class="piececalendarview-weekdays-weekday">{{ day }}</div>
            </div>
            <div class="piececalendarview-weekdays-week" v-for="(week, index) in state.days" :key="index">
                <div class="piececalendarview-weekdays-day" v-for="dayData in week" :key="dayData.day">
                <div>{{ dayData.day }}</div>
                <img v-if="dayData.imageUrl" :src="dayData.imageUrl" alt="Day image">
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue';
import { usePiecelistStore } from "@/stores/piecelist";

const store = usePiecelistStore();
const piecelistMyCalendar = computed(() => store.getPiecelistMyCalendar);

const today = ref(new Date());
const year = today.value.getFullYear();
const month = today.value.getMonth();
const week = ['일', '월', '화', '수', '목', '금', '토'];
const state = reactive({
    calendarHeader: '',
    days: []
});

function calendarImplementation() {
    state.days = [];
    const year = today.value.getFullYear();
    const month = today.value.getMonth();
    // store.findPiecelistMyCalendar(year, month + 1);

    const startDayOfTheMonth = new Date(year, month, 1).getDay();
    const endDayOfTheMonth = new Date(year, month + 1, 0).getDate();
    const emptyStartDays = Array(startDayOfTheMonth).fill({ day: null, imageUrl: null });
    const daysOfMonth = Array.from({ length: endDayOfTheMonth }, (_, i) => ({
        day: i + 1,
        imageUrl: getImageUrlForDay(i + 1, month, year)
    }));
    const fullMonth = [...emptyStartDays, ...daysOfMonth];
    const weeks = [];

    while (fullMonth.length > 0) {
        const weekDays = fullMonth.splice(0, 7);
        while (weekDays.length < 7) {
            weekDays.push({ day: null, imageUrl: null });
        }
        weeks.push(weekDays);
    }

    state.days = weeks;
    state.calendarHeader = `${year}년 ${month + 1}월`;
}

function changeMonth(val) {
    today.value = new Date(today.value.getFullYear(), today.value.getMonth() + val, 1);
    calendarImplementation();
}

function getImageUrlForDay(day, month, year) {
    const fullDate = `${year}-${String(month + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
    const piece = piecelistMyCalendar.value.find(p => p.date === fullDate);
    return piece ? piece.frontImg : null;
}
onMounted(async () => {
    await store.findPiecelistMyCalendar(year, month + 1);
    calendarImplementation();
});

</script>

<style>
.piececalendarview-main-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    }

.piececalendarview-header-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px;
}

.piececalendarview-calendar-container {
    width: 100%;
}

.piececalendarview-weekdays-container, .piececalendarview-weekdays-week {
    display: flex;  
    justify-content: space-around;
}

.piececalendarview-weekdays-weekday, .piececalendarview-weekdays-day {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: calc(100% / 7);  
    border: 1px solid #ccc;
    min-height: 100px;  
}

.piececalendarview-weekdays-day {
    width: 100px;
    height: 100px;
    border: 1px solid #ccc;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}
.piececalendarview-weekdays-day img {
    width: 80%;
    height: auto;
    max-height: 60px;
}
</style>
