<template>
    <input
        :type="inputType"
        :placeholder="textInputPlaceholder"
        class="textinput-input"
        :value="textInputValue"
        @input="onInput"
        @focus="onFocus"
        @blur="onBlur"
    />
</template>

<script setup>
import { ref, watch, defineProps, defineEmits } from "vue";

const props = defineProps({
    modelValue: String,
    textInputPlaceholder: String,
    inputType: { type: String, default: "text" },
});

const textInputValue = ref(props.modelValue);  // 초기값을 props.modelValue로 설정
const emits = defineEmits(["update:modelValue", "focus", "blur"]);

// Props의 modelValue 변경 감지
watch(() => props.modelValue, (newValue) => {
    textInputValue.value = newValue;
});

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
.textinput-input {
    font-family: "Regular";
    width: 100%;
    height: 2.25rem;
    border-radius: 0.4rem;
    border: 1px solid var(--gray-color);
    padding-left: 0.6rem;
    box-sizing: border-box;
}

.textinput-input:focus {
    outline: 1px solid var(--main-color);
    border: none;
}
</style>
