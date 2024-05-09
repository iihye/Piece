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

<script>
export default {
    data() {
        return {
            year: new Date().getFullYear(),
            currentYear: new Date().getFullYear(),
        };
    },
    methods: {
        changeYear(change) {
            this.year += change;
            this.$emit("year-changed", this.year);
        },
    },
};
</script>

<style scoped>
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
