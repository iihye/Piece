<template>
    <div class="year-selector">
        <font-awesome-icon
            class="yearselector-icon"
            :icon="['fas', 'angle-left']"
            style="color: var(--gray2-color)"
            @click="changeYear(-1)"
        />
        <span class="yearselector-year">{{ year }}</span>
        <font-awesome-icon
            class="yearselector-icon"
            :icon="['fas', 'angle-right']"
            style="color: var(--gray2-color)"
            @click="changeYear(1)"
        />
    </div>
</template>

<script setup>
import { ref } from 'vue';

const currentYear = new Date().getFullYear();
const year = ref(currentYear);
const emit = defineEmits(['year-changed']);

// const changeYear = (change) => {
//     if ((year.value === currentYear && change > 0) || (year.value > currentYear)) {
//         return;
//     }
//     year.value += change;
//     emit('year-changed', year.value);
// };

const changeYear = (change) => {
    const newYear = year.value + change;
    if (newYear <= currentYear) {
        year.value = newYear;
        emit('year-changed', newYear);
    }
};
</script>

<style>
.year-selector {
    display: flex;
    justify-content: center;
    align-items: center;
}

.yearselector-year {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 20%;
    font-family: "Semi";
    font-size: 1.4rem;
    color: var(--main-color);
    margin: 0 1rem 0 1rem;
}

.year-selector button {
    margin: 0 5px;
}
</style>
