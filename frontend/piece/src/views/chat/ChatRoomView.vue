<template>
    <div class="chatroomview-main-container">
        <chatRoomListHeader />
        <chatRoomList class="chatroomview-list-container"/>
    </div>
    <!-- <div id="chatroomview-chatRoomListPage">
        
        <chatRoomList />
    </div> -->
</template>

<script setup>
import { onMounted } from "vue";
import { useCommonStore } from "@/stores/common";
import { useWebSocketStore } from "@/stores/websocket";
import chatRoomList from "@/components/chat/ChatRoomList.vue";
import chatRoomListHeader from "@/components/chat/ChatRoomListHeader.vue";

const webSocketStore = useWebSocketStore();
const commonStore = useCommonStore();

// modal test와 관련 없는 코드
onMounted(() => {
    commonStore.headerTitle = "채팅 목록";
    commonStore.headerType = "header2";

    if (webSocketStore.getStompClient()) {
        webSocketStore.unsubscribe();
    } else {
        webSocketStore.stompConnect();
    }
});
</script>

<style>
.chatroomview-main-container {
    display: flex;
    flex-direction: column;
    height: 100%;
}

.chatroomview-list-container{
    height: 100%;
}

/*  */
#chatroomview-chatRoomListPage {
    /* border: 0.063rem solid var(--black-color);
  width: 25rem;
  height: 42.5rem;
  margin: 0 auto; */
}
</style>
