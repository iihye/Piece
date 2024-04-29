<template>
    <div id="chatRoomListPage">
      <chatRoomListHeader />
      <chatRoomList />
    </div>
  </template>
  
  <script setup>
  import chatRoomList from '@/components/chat/ChatRoomList.vue'
  import chatRoomListHeader from '@/components/chat/ChatRoomListHeader.vue';
  </script>
  
  <style scopped>
  #chatRoomListPage{
    border:1px solid black;
    width: 400px;
    height: 85vh;
    margin: 0 auto;
  }
  </style>
  
