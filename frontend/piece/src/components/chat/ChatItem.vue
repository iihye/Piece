<template>
    <div class="chatitem-sender-container">
        <img
            class="chatitem-sender-img"
            :src="props.senderImg || 'https://i.ibb.co/grMvZS9/your-image.jpg'"
            alt="image"
        />

        <div class="chatitem-sender-content">
            <div class="chatitem-sender-info">
                <div class="chatitem-sender-label">{{ props.senderLabel }}</div>
                <div class="chatitem-sender-nickname">
                    {{ props.senderNickname }}
                </div>
            </div>
            <div class="chatitem-sender-message">
                {{ props.content }}
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
    padding: 0.2rem;
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

.chatitem-sender-content {
    display: flex;
    flex-direction: column;
    margin-left: 0.6rem;
}

.chatitem-sender-info {
    display: flex;
    justify-self: row;
    margin-bottom: 0.4rem;
}

.chatitem-sender-label {
    font-family: "bold";
    font-size: 0.8rem;
    color: var(--main-color);
    margin-right: 0.4rem;
}

.chatitem-sender-nickname {
    font-family: "Semi";
    font-size: 0.8rem;
    color: var(--gray2-color);
}

.chatitem-sender-message {
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
</style>
