<template>
    <div class="year-selector">
        <button @click="changeYear(-1)">&lt;</button>
        <span>{{ year }}</span>
        <button @click="changeYear(1)" :disabled="year >= currentYear"> &gt; </button>
    </div>
</template>

<script>
export default {
    data() {
        return {
            year: new Date().getFullYear(),
            currentYear: new Date().getFullYear()
        };
    },
    methods: {
        changeYear(change) {
            this.year += change;
            this.$emit('year-changed', this.year);
        }
    }
};
</script>

<style scoped>
.year-selector {
    display: flex;
    justify-content: center;
    align-items: center;
}

.year-selector button {
    margin: 0 5px;
}
</style>

