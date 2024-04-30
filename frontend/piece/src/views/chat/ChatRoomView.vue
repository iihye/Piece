<template>
  <div id="chatRoomListPage">
    <chatRoomListHeader />
    <chatRoomList />
  </div>
</template>

<script setup>
import {onMounted} from 'vue';
import chatRoomList from '@/components/chat/ChatRoomList.vue'
import chatRoomListHeader from '@/components/chat/ChatRoomListHeader.vue';
import {useWebSocketStore} from '@/stores/websocket';

const webSocketStore=useWebSocketStore();

onMounted(() => {
  if(webSocketStore.getStompClient()){
    // webSocketStore.unsubscribe();
  }else{
    webSocketStore.stompConnect(); 
  }
})
</script>

<style scopped>
#chatRoomListPage{
  border:1px solid black;
  width: 400px;
  height: 85vh;
  margin: 0 auto;
}
</style>

