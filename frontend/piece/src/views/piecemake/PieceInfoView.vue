<template>
    <h1>어떤 케이크였나요?</h1>
    <div class="container">
      <div class="form-group">
        <label for="textInput">이름</label>
        <TextInput id="textInput" placeholder="이름을 입력하세요" v-model="name" />
      </div>
      <div class="form-group">
        <label for="movie-types">공연 분류</label>
        <ButtonGroup
          id="movie-types"
          groupName="공연 분류"
          :options="options"
          @selected="selected = $event"
        />
      </div>
      <RouterLink :to="{ name: 'piececomment' }">감상평 작성</RouterLink>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import TextInput from '@/components/text/OnlyInput.vue';
import ButtonGroup from '@/components/button/SelectableButtonGroup.vue';

const name = ref('');
const options = ref([
    { id: 'movie', name: '영화' },
    { id: 'musical', name: '뮤지컬' },
    { id: 'concert', name: '콘서트' },
    { id: 'play', name: '연극' },
    { id: 'other', name: '기타' }
]);
const selected = ref(null);
</script>

<style>
.container {
    display: grid;
    grid-template-columns: auto 1fr;
    gap: 20px;
    /* align-items: center; */
    max-width: 600px;
    margin: auto;
}

.form-group {
    display: contents;
}

label {
    font-size: 1rem;
    font-weight: bold;
    padding: 10px;
    text-align: left;
}

input, .button-group {
    grid-column: 2 / 3; 
}

.button-group {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
}
</style>
