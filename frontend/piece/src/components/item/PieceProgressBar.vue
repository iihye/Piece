<template>
    <div class="piecemakeview-progressbar">
        <div 
            class="piecemakeview-progressbar-main" 
            :style="{ width: 'calc((100% / 6) * ' + progress + ')' }">
        </div>
    </div>
</template>

<script>
import { computed } from 'vue';
import { useCommonStore } from "@/stores/common";

export default {
    setup() {
        const commonStore = useCommonStore(); 
        const progress = computed(() => commonStore.getProgress);
        return { progress };
    }
}

</script>


<style>
.piecemakeview-progressbar {
    display: flex;
    width: 100%;
    height: 2px;
    overflow: hidden; 
    background-color: var(--gray-color);
}

.piecemakeview-progressbar-main {
    background-color: var(--main-color);
}
</style>
