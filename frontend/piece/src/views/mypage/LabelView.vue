<template>
    <div>
        <div class="labelview-main-title">나를 표현하는 칭호를 골라주세요</div>
        <div class="labelview-main-content">칭호를 클릭하여 착용해보세요</div>
        <div class="labelview-selct-container">
            <select class="labelview-select-dropbox" v-model="selectedOption">
                <option value="select-all">전체</option>
                <option value="select-mine">보유 중인 칭호</option>
                <option value="select-not">보유하지 않은 칭호</option>
            </select>
        </div>
        <LabelItem
            v-for="(item, index) in filteredLabelList"
            :key="index"
            :isMine="item.myLabels"
            :isWear="item.wearLabels"
            :title="item.title"
            :description="item.description"
            :handleItemClick="handleItemClick"
        ></LabelItem>

        <button @click="findMyPageLabelList"></button>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useUserStore } from "@/stores/user";
import LabelItem from "@/components/item/LabelItem.vue";

const store = useUserStore();
// const isMine = ref(true);
// const isWear = ref(true);
// const title = ref("영화 애호가");
// const description = ref("영화 많이 본 사람");
const mypageLabelList = computed(() => store.getMypageLabelList);
const selectedOption = ref("select-all");

const filteredLabelList = computed(() => {
    if (selectedOption.value === "select-all") {
        return mypageLabelList.value;
    } else if (selectedOption.value === "select-mine") {
        return mypageLabelList.value.filter((item) => item.myLabels);
    } else if (selectedOption.value === "select-not") {
        return mypageLabelList.value.filter((item) => !item.myLabels);
    } else {
        return [];
    }
});

const handleItemClick = () => {
    console.log(mypageLabelList);
    alert("착용하시겠습니까?");
};

const findMyPageLabelList = () => {
    console.log("버튼 클릭");
    mypageLabelList.value = store.getMypageLabelList;
};

// const findMyPageLabelList = function () {
//     axios({
//         url: `${import.meta.env.VITE_REST_PIECE_API}/mylabels`,
//         method: "GET",
//     })
//         .then((res) => {
//             console.log(res.data.data);
//             mypageLabelList.value = res.data.data;
//             // console.log(myLabelList.value);
//         })
//         .catch((err) => {});
// };

onMounted(async () => {
    await store.findMyPageLabelList();
});
</script>

<style>
.labelview-main-title {
    font-family: "Semi";
    font-size: 1.4rem;
    color: var(--black-color);
    margin-bottom: 0.6rem;
}

.labelview-main-content {
    font-family: "Regular";
    font-size: 1rem;
    color: var(--gray2-color);
    margin: 0;
    margin-bottom: 1rem;
    display: flex;
    justify-content: left;
    align-items: center;
}

.labelview-selct-container {
    display: flex;
    justify-content: right;
    margin-bottom: 0.6rem;
}

.labelview-select-dropbox {
    width: 10rem;
    height: 2rem;
    font-family: "Medium";
    font-size: 1rem;
    color: var(--gray2-color);
    border: 1px solid var(--gray-color);
    padding-left: 0.2rem;
}
</style>
