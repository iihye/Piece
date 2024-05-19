<template>
    <div class="piececalendarview-main-container">
        <div class="piececalendarview-header-container">
            <font-awesome-icon
                class="piececalendarview-header-icon"
                :icon="['fas', 'angle-left']"
                style="color: var(--gray2-color)"
                @click="changeMonth(-1)"
            />
            <div class="piececalendarview-header-content">
                {{ state.calendarHeader }}
            </div>
            <font-awesome-icon
                class="piececalendarview-header-icon"
                :icon="['fas', 'angle-right']"
                style="color: var(--gray2-color)"
                @click="changeMonth(1)"
            />
        </div>

        <!-- Calendar -->
        <div class="piececalendarview-calendar-container">
            <div class="piececalendarview-weekdays-container">
                <div
                    v-for="day in week"
                    :key="day"
                    class="piececalendarview-weekdays-weekday"
                >
                    {{ day }}
                </div>
            </div>
            <div
                class="piececalendarview-weekdays-week"
                v-for="(week, index) in state.days"
                :key="index"
            >
                <div
                    class="piececalendarview-weekdays-day"
                    v-for="dayData in week"
                    :key="dayData.day"
                >
                    <img
                        v-if="dayData.imageUrl"
                        :src="dayData.imageUrl"
                        alt="image"
                        @click="handleDayClick(dayData.pieceId)"
                    />
                    <div v-else>
                        {{ dayData.day }}
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import router from "@/router";
import { computed, onMounted } from "vue";
import { useCommonStore } from "@/stores/common";
import { usePiecelistStore } from "@/stores/piecelist";

const commonStore = useCommonStore();
const store = usePiecelistStore();

const today = computed(() => store.getToday);
const year = computed(() => store.getYear);
const month = computed(() => store.getMonth);
const week = ["일", "월", "화", "수", "목", "금", "토"];
const state = computed(() => store.getState);

function changeMonth(val) {
    const newToday = new Date(today.value);
    newToday.setMonth(newToday.getMonth() + val);

    // 미래 클릭 안댐
    const currentMonth = new Date().getMonth();

    if (newToday.getFullYear() > new Date().getFullYear() || 
        (newToday.getFullYear() === new Date().getFullYear() && newToday.getMonth() > currentMonth)) {
        return;
    }

    store.setToday(newToday);

    store.findPiecelistMyCalendar(
        newToday.getFullYear(),
        newToday.getMonth() + 1
    );
}

function handleDayClick(pieceId) {
    router.push({ name: "pieceDetail", params: { pieceId: pieceId } });
}

onMounted(async () => {
    commonStore.headerTitle = "내 조각 모아보기";
    commonStore.headerType = "header3";
});

onMounted(async () => {
    await store.findPiecelistMyCalendar(year.value, month.value + 1);
});
</script>

<style>
.piececalendarview-main-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 100%;
    user-select: none;
}

.piececalendarview-main-container > :first-child {
    flex: 0 0 auto;
}

.piececalendarview-main-container > :not(:first-child) {
    flex: 1;
}

.piececalendarview-header-icon:hover {
    cursor: pointer;
}

.piececalendarview-header-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 0.6rem;
}

.piececalendarview-header-content {
    display: flex;
    align-items: center;
    justify-content: center;
    text-align: center;
    font-family: "Semi";
    font-size: 1.4rem;
    color: var(--main-color);
    margin: 0 1rem 0 1rem;
    width: 6rem;
}

.piececalendarview-calendar-container {
    width: 100%;
    height: 100%;
}

.piececalendarview-weekdays-container {
    display: flex;
    justify-content: space-around;
}

.piececalendarview-weekdays-weekday {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: calc(100% / 7);
    height: auto;
    font-family: "Medium";
    font-size: 0.8rem;
    color: var(--gray2-color);
    margin: 1rem 0 1rem 0;
}

.piececalendarview-weekdays-week {
    display: flex;
    justify-content: space-around;
}

.piececalendarview-weekdays-day {
    width: calc(100% / 7);
    min-height: 100px;
    font-family: "Regular";
    font-size: 0.8rem;
    height: 100px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}

.piececalendarview-weekdays-day img {
    width: 100%;
    height: 100%;
}

.piececalendarview-weekdays-day img:hover {
    cursor: pointer;
}
</style>
