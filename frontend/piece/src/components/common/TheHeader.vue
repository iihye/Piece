<template>
    <div class="header-container">
        <div class="header-all">
            <div class="header-title">piece</div>
        </div>
    </div>
</template>

<script setup></script>

<style>
.header-container {
    z-index: 85;
    height: 2.75rem;
    margin: 0;
    background-color: var(--white-color);
    display: flex;
    align-items: center;
}

.header-all {
    display: flex;
}

.header-title {
    margin: 0;
    font-family: "Medium";
    font-size: 1.6rem;
    display: flex;
    justify-content: center;
}
</style>
