<template>
    <VueDatePicker v-model="time" time-picker>
        <template #input-icon>
            <font-awesome-icon class="dp__input_icons" :icon="['far', 'clock']" />
        </template>
    </VueDatePicker>
</template>

<script setup>
import { ref } from 'vue';
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'

const time = ref({
  hours: new Date().getHours(),
  minutes: new Date().getMinutes()
});
</script>

<style>
.input-slot-image {
    height: 13.33px;
    width: auto;
    margin-left: 0.5rem;
}
</style>