<template>
    <div class="nothingpiece-container">
        <div class="nothingpiece-image">
            <img class="nothingpiece-icon"
                src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Food/Shortcake.png" />
        </div>
        <div class="nothingpiece-content">
            케이크의 마지막 조각을 <br />감상평으로 채워주세요!
        </div>
    </div>

</template>

<script setup>

</script>

<style scoped>
.nothingpiece-container {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.nothingpiece-image {
    background-color: var(--sub2-color);
    border-radius: 50%;
    width: 10rem;
    height: 10rem;
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 2rem;
}

.nothingpiece-icon {
    width: 6rem;
    height: 6rem;
}

.nothingpiece-content {
    font-family: "Medium";
    font-size: 1rem;
    text-align: center;
    user-select: none;
}
</style>