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
                <div v-for="day in week" :key="day" class="piececalendarview-weekdays-weekday">
                    {{ day }}
                </div>
            </div>
            <div class="piececalendarview-weekdays-week" v-for="(week, index) in state.days" :key="index">
                <div class="piececalendarview-weekdays-day" v-for="dayData in week" :key="dayData.day">
                    <div>{{ dayData.day }}</div>
                    <img v-if="dayData.imageUrl" :src="dayData.imageUrl" alt="Day image" />
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { computed, onMounted } from "vue";
import { usePiecelistStore } from "@/stores/piecelist";

const store = usePiecelistStore();

const today = computed(() => store.getToday);
const year = computed(() => store.getYear);
const month = computed(() => store.getMonth);
const week = ["일", "월", "화", "수", "목", "금", "토"];
const state = computed(() => store.getState);

function changeMonth(val) {
    const newToday = new Date(today.value);
    newToday.setMonth(newToday.getMonth() + val);
    store.setToday(newToday);

    store.findPiecelistMyCalendar(today.value.getFullYear(), today.value.getMonth() + 1);
}

onMounted(async () => {
    await store.findPiecelistMyCalendar(year.value, month.value + 1);
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

.piececalendarview-weekdays-container,
.piececalendarview-weekdays-week {
    display: flex;
    justify-content: space-around;
}

.piececalendarview-weekdays-weekday,
.piececalendarview-weekdays-day {
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
