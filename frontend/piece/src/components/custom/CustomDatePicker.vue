<template>
    <VueDatePicker 
        id="date-picker"
        locale="ko" 
        :enable-time-picker="false" 
        :format="format"
        v-model="date"
        @input="onDateChange"
        >

        <template v-slot:header="{ year, month }">
            <div class="datepicker-header">{{ year }} 월 {{ month }}</div>
        </template>
    </VueDatePicker>
</template>

<script setup>
import { ref } from 'vue';
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'

const date = ref(null);
const emits = defineEmits(["date"])

function onDateChange(newDate) {
    date.value = newDate;
    emits('date', date.value);  // Emit an update event
}

const format = (date) => {
  const day = date.getDate();
  const month = date.getMonth() + 1;
  const year = date.getFullYear();

  return `${year}/${month}/${day}`;
}
</script>

<style >
.dp__theme_light {
    --dp-primary-color: var(--red-color);
}

.dp__month_year_wrap {
    width: 100px;
}

</style>