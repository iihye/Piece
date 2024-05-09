<template>
    <div class="header-container">
        <div v-if="headerType === 'header1'" class="header-all">
            <div class="header-title">{{ headerTitle }}</div>
        </div>
        <div v-else-if="headerType === 'header2'" class="header-all">
            <font-awesome-icon
                :icon="['fas', 'chevron-left']"
                class="header-back"
                @click="headerBack"
            />
            <div class="header-title">{{ headerTitle }}</div>
            <div class="header-blank"></div>
        </div>
        <div v-else-if="headerType === 'header3'" class="header-all">
            <font-awesome-icon
                :icon="['fas', 'chevron-left']"
                class="header-back"
                @click="headerBack"
            />
            <div class="header-title">{{ headerTitle }}</div>
            <font-awesome-icon
                :icon="['fas', 'bars']"
                class="header-list"
                @click="headerList"
            />
        </div>
        <div v-else-if="headerType === 'header4'" class="header-all">
            <font-awesome-icon
                :icon="['fas', 'chevron-left']"
                class="header-back"
                @click="headerBack"
            />
            <div class="header-title">{{ headerTitle }}</div>
            <font-awesome-icon
                :icon="['far', 'calendar']"
                class="header-calendar"
                @click="headerCalendar"
            />
        </div>
        <div v-else-if="headerType === 'header5'" class="header-all">
            <div class="header-left">
                <font-awesome-icon
                    :icon="['fas', 'chevron-left']"
                    class="header-back"
                    @click="headerBack"
                />
                <div class="header-chatinfo">
                    <img :src="userImg" class="header-img" />
                    <div class="header-chatname">{{ userName }}</div>
                </div>
            </div>
            <font-awesome-icon
                :icon="['fas', 'arrow-right-from-bracket']"
                class="header-out"
                @click="headerOut"
            />
        </div>
        <div v-else-if="headerType === 'header6'" class="header-all">
            <div class="header-left">
                <font-awesome-icon
                    :icon="['fas', 'chevron-left']"
                    class="header-back"
                    @click="headerBack"
                />
                <div class="header-chatinfo">
                    <img :src="userImg" class="header-img" />
                    <div class="header-chatroom">
                        <div class="header-chatname">{{ userName }}</div>
                        <div class="header-count">
                            {{ userCount }}명이 대화중이예요
                        </div>
                    </div>
                </div>
            </div>
            <font-awesome-icon
                :icon="['fas', 'arrow-right-from-bracket']"
                class="header-out"
                @click="headerOut"
            />
        </div>
    </div>
</template>

<script setup>
import { computed } from "vue";
import { useRouter } from "vue-router";
import { useCommonStore } from "@/stores/common";

const commonStore = useCommonStore();
const router = useRouter();

const headerType = computed(() => commonStore.getHeaderType);
const headerTitle = computed(() => commonStore.getHeaderTitle);
const userName = computed(() => commonStore.getHeaderChatName);
const userImg = computed(() => commonStore.getHeaderChatImg);
const userCount = computed(() => commonStore.getHeaderChatCount);

const headerBack = () => {
    router.go(-1);
};

const headerList = () => {
    router.push("/piecelist/my/list");
};

const headerCalendar = () => {
    router.push("/piecelist/my/cal");
};

const headerOut = () => {
    router.push("/chatroom");
};
</script>

<style scoped>
.header-container {
    margin: 0;
    background-color: var(--white-color);
    display: flex;
    align-items: center;
    justify-content: center;
}

.header-all {
    display: flex;
    align-items: center;
    width: 100%;
    margin-left: 1rem;
    margin-right: 1rem;
    justify-content: space-between;
}

.header-title {
    margin: 0;
    width: 100%;
    font-family: "Bold";
    font-size: 1rem;
    display: flex;
    justify-content: center;
    user-select: none;
}

.header-back,
.header-list,
.header-calendar,
.header-out {
    color: var(--gray2-color);
}

.header-back:hover,
.header-list:hover,
.header-calendar:hover,
.header-out:hover {
    cursor: pointer;
}

.header-img {
    width: 2rem;
    height: 2rem;
    border-radius: 50%;
    border: 1px solid var(--gray-color);
}

.header-left {
    display: flex;
    align-items: center;
}

.header-chatname {
    display: flex;
    font-family: "Bold";
    font-size: 1rem;
}

.header-img {
    margin-right: 0.5rem;
}

.header-chatinfo {
    display: flex;
    align-items: center;
    margin-left: 1rem;
}

.header-count {
    font-size: 1rem;
    color: var(--main-color);
}
</style>
