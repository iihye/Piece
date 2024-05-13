<template>
    <input
      :type="inputType"
      :placeholder="textInputPlaceholder"
      class="textinput-input"
      v-model="textInputValue" 
      @input="onInput"
      @focus="onFocus"
      @blur="onBlur"
    />
</template>

<script setup>
import { ref, defineProps, defineEmits } from 'vue';

const props = defineProps({
    textInputName: String,
    textInputPlaceholder: String,
    inputType: { type: String, default: 'text' }
});

const textInputValue = ref('');
const emits = defineEmits(["update:modelValue", "focus", "blur"]);

const onInput = (event) => {
    emits("update:modelValue", event.target.value);
};

const onFocus = () => {
    emits("focus");
};

const onBlur = () => {
    emits("blur");
};
</script>

<style>
@import "@/components/css/font.css";

.textinput-input {
    font-family: "Regular";
    width: 100%;
    height: 2.25rem;
    border-radius: 0.625rem;
    border: 1px solid var(--gray-color);
    padding-left: 0.625rem;
    box-sizing: border-box; /* Ensures padding doesn't affect width */
}

.textinput-input:focus {
    outline: 1px solid var(--main-color);
    border: none;
}
</style>
