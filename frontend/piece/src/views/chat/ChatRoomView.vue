<template>
  <div id="chatroomview-chatRoomListPage">
    <chatRoomListHeader />
    <chatRoomList />
  </div>
</template>

<script setup>
import { onMounted } from "vue";
import chatRoomList from "@/components/chat/ChatRoomList.vue";
import chatRoomListHeader from "@/components/chat/ChatRoomListHeader.vue";
import { useWebSocketStore } from "@/stores/websocket";

const webSocketStore = useWebSocketStore();

// modal test와 관련 없는 코드
onMounted(() => {
  if (webSocketStore.getStompClient()) {
    // webSocketStore.unsubscribe();
  } else {
    webSocketStore.stompConnect();
  }
});
</script>

<style>
@import "@/components/css/color.css";

#chatroomview-chatRoomListPage {
  /* border: 0.063rem solid var(--black-color);
  width: 25rem;
  height: 42.5rem;
  margin: 0 auto; */
}
</style>
