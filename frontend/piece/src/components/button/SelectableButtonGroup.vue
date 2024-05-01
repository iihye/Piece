<template>
    <div class="button-group" :id="groupId">
        <SelectableButton
            v-for="option in options"
            :key="option.id"
            :id="option.id"
            :name="option.name"
            :isSelected="selected === option.id"
            @selected="updateSelected"
        />
    </div>
</template>
  
<script setup>
import { ref, defineProps, defineEmits } from 'vue';
import SelectableButton from '@/components/button/SelectableButton.vue';

const props = defineProps({
    groupName: String,
    groupId: String,
    options: Array
});

const emits = defineEmits(['selected']);
const selected = ref('');

function updateSelected(id) {
    selected.value = id;
    emits('selected', id);
}
</script>
  
<style scoped>
@import "@/components/css/color.css";

.group-label {
    font-size: 1rem;
    margin-bottom: 0.5rem;
    display: block;
}

.button-group {
    display: flex;
    flex-wrap: wrap;
}
</style>
  