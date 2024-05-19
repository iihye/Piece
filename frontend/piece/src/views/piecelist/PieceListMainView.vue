<template>
    <div class="piecelistmain-main-container">
        <div class="piecelistmain-container">
            <div class="piecelistmain-top">
                <RouterLink
                    :to="{ name: 'cakelist' }"
                    class="piecelistmain-square"
                >
                    <div class="piecelistmain-title">케이크 모아보기</div>
                    <div class="piecelistmain-imagebox">
                        <img
                            src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Food/Birthday%20Cake.png"
                            alt="Birthday Cake"
                            class="piecelistmain-image"
                        />
                    </div>
                </RouterLink>

                <RouterLink
                    :to="{ name: 'pieceCalendar' }"
                    class="piecelistmain-square"
                >
                    <div class="piecelistmain-title">내 조각 모아보기</div>
                    <div class="piecelistmain-imagebox">
                        <img
                            src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Spiral%20Calendar.png"
                            alt="Spiral Calendar"
                            class="piecelistmain-image"
                        />
                    </div>
                </RouterLink>
            </div>
            <div class="piecelistmain-bottom">
                <RouterLink
                    :to="{ name: 'pieceList' }"
                    class="piecelistmain-square"
                >
                    <div class="piecelistmain-title">조각 모아보기</div>
                    <div class="piecelistmain-imagebox">
                        <img
                            src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Food/Shortcake.png"
                            alt="Shortcake"
                            class="piecelistmain-image"
                        />
                    </div>
                </RouterLink>
                <RouterLink
                    :to="{ name: 'pieceListHeart' }"
                    class="piecelistmain-square"
                >
                    <div class="piecelistmain-title">찜한 조각 모아보기</div>
                    <div class="piecelistmain-imagebox">
                        <img
                            src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/Red%20Heart.png"
                            alt="Red Heart"
                            class="piecelistmain-image"
                        />
                    </div>
                </RouterLink>
            </div>
        </div>
    </div>
</template>

<script setup>
import { computed, onMounted } from "vue";
import { useCommonStore } from "@/stores/common";
import { usePiecelistStore } from "@/stores/piecelist";

const commonStore = useCommonStore();
const store = usePiecelistStore();

const year = computed(() => store.getYear);
const month = computed(() => store.getMonth);

onMounted(async () => {
    commonStore.headerTitle = "모아보기";
    commonStore.headerType = "header2";

    store.setSelectOption("ALL");
    store.setSelectOptionMyList("ALL");
    store.setSelectOptionHeartList("ALL");

    await store.findPiecelistMyCalendar(year.value, month.value + 1);
});
</script>

<style scoped>
.piecelistmain-main-container {
    display: flex;
    justify-content: center;
    align-items: center;
    position: fixed;
    top: 40%;
    left: 50%;
    transform: translate(-50%, -50%);
}
.piecelistmain-container {
    display: flex;
    flex-direction: row;
}

.piecelistmain-square {
    width: 10.25rem;
    height: 12.5rem;
    background-color: var(--sub2-color);
    border-radius: 0.625rem;
    padding: 1.3rem;
    flex-direction: column;
    display: flex;
    justify-content: space-between;
    margin: 0.25rem;
    margin-bottom: 0.5rem;
    box-sizing: border-box;
}

.piecelistmain-title {
    font-family: "bold";
    font-size: 1rem;
    color: var(--black-color);
}

.piecelistmain-imagebox {
    display: flex;
    justify-content: flex-end;
}

.piecelistmain-image {
    width: 5.5rem;
    height: 5.5rem;
}

a {
    text-decoration: none;
    font-weight: bold;
}
</style>
