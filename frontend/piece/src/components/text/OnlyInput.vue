<template>
    <input
      type="text"
      :placeholder="textInputPlaceholder"
      class="textinput-input"
      v-model="textInputValue" 
      @input="textInputFuntion"
    />
</template>

<script setup>
import { ref } from 'vue';

defineProps({
    textInputName: String,
    textInputPlaceholder: String,
});

const textInputValue = ref('');

const emits = defineEmits(["textInputValue"]);

const textInputFuntion = () => {
    emits("textInputValue", textInputValue.value);
}
</script>

<style>
.textinput-input {
    width: 14rem;
    height: 2.25rem;
    border-radius: 0.625rem;
    border: 1px solid var(--gray-color);
    padding-left: 0.625rem
}

.textinput-input:focus {
    width: 14rem;
    height: 2.25rem;
    border-radius: 0.625rem;
    border: 0;
    outline: 1px solid var(--main-color);
}
</style>
