<template>
    <div class="textinput-container">
        <label for="textInputContent" class="textinput-label">{{ textInputName }}</label>
        <input type="text" name="textInputContent" id="textInputContent" :placeholder="textInputPlaceholder"
            class="textinput-input" v-model="textInputValue" @input="textInputFuntion" />
    </div>
</template>

<script setup>

import { ref, defineProps, defineEmits } from 'vue';

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
@import "@/components/css/color.css";

.textinput-label {
    font-size: 1rem;
}

.textinput-label::after {
    content: '*';
    color: var(--red-color);
    vertical-align: 0.2rem;
}

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

.textinput-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 20px;
}
</style>