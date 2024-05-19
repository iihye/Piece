<template>
    <div class="chatitem-sender-container">
        <img
            class="chatitem-sender-img"
            :src="props.senderImg || 'https://i.ibb.co/grMvZS9/your-image.jpg'"
            alt="image"
        />

        <!-- 메시지 관련 부분 시작-->
        <div class="chatitem-senderview-container">
            <div class="chatitem-userHeader">
                <div class="chatitem-userTitle">
                    {{ props.senderLabel }}
                </div>
                <div class="chatitem-userName">
                    {{ props.senderNickname }}
                </div>
            </div>
            <div class="chatitem-sender-container">
                <div class="chatitem-sender-content">{{ props.content }}</div>
            </div> 
        </div>
    </div>
</template>

<script setup>
import { computed, defineProps, onMounted } from "vue";
import { useCommonStore } from "@/stores/common";
import { useCakeStore } from "@/stores/cake";

const commonStore = useCommonStore();
const store = useCakeStore();

const cakeChatUser = computed(() => store.getCakeChatUser);
const cakeChatUserLabel = computed(() => store.getCakeChatUserLabel);

const props = defineProps({
    chatRoomId: Number,
    senderLabel: String,
    senderNickname: String,
    senderImg: String,
    content: String,
    createdAt: String,
});

// onMounted(() => {
//     store.findCakeChatUser(props.senderId);
// });
</script>

<style>
.chatitem-sender-container {
    display: flex;
    justify-self: center;
    align-items: top;
    margin-bottom: 0.6rem;
    user-select: none;
}

.chatitem-sender-img {
    width: 2.6rem;
    height: 2.6rem;
    border: 1px solid var(--gray-color);
    border-radius: 50%;
    object-fit: cover;
}

.chatitem-sender-background{
    width: 100%;
    background-color: var(--sub-color);
    border-radius: 0.4rem;
}

.chatitem-sender-message {
    display: inline;
    overflow-wrap: break-word;
    padding: 0.8rem 0.8rem 0.8rem 0.8rem;
    font-family: "Regular";
    font-size: 1rem;
    color: var(--black-color);
    line-height: 1.2rem;
}

.chatitem-sender-msg{
    display: flex;
    background-color: red;
}

.chatitem-senderview-container{
    display: flex;
    flex-direction: column;
    margin-left: 0.4rem;
}

/* 칭호+이름 */
.chatitem-userHeader {
    display: flex;
    justify-self: row;
    margin-bottom: 0.4rem;
}

/* 칭호 */
.chatitem-userTitle {
    font-family: "bold";
    font-size: 0.8rem;
    color: var(--main-color);
    margin-right: 0.4rem;
}

/* 이름 */
.chatitem-userName {
    font-family: "Semi";
    font-size: 0.8rem;
    color: var(--gray2-color);
}

/* 메시지+시간 */
.chatitem-sender-container{
    display: flex;
    justify-content: left;
    align-items: flex-start;
    width: 100%;
}

/* 메시지 */
.chatitem-sender-content{
    max-width: 14rem;
    overflow-wrap: break-word;
    padding: 0.4rem;
    background-color: var(--sub-color);
    border-radius: 0.4rem;
    padding: 0.8rem 0.8rem 0.8rem 0.8rem;
    font-family: "Regular";
    font-size: 1rem;
    color: var(--black-color);
    line-height: 1.2rem;
}

/* 시간 */
.chatitem-sender-time{
    font-family: "Regular";
    font-size: 0.8rem;
    color: var(--gray2-color);
    margin-left: 0.2rem;
    display: flex;
    align-self: flex-end;
}
</style>
