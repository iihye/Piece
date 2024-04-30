import { defineStore } from 'pinia';
import SockJS from 'sockjs-client/dist/sockjs.min.js';
import Stomp from 'webstomp-client';

export const useWebSocketStore = defineStore(
  'websocket',
  () => {
    // =========== STATE ===============
    const state = {
      socket: null, // WebSocket 연결
      stompClient: null, // Stomp 클라이언트
      subscription: null, // 구독 상태
    };

    // =========== GETTER ===============
    const getStompClient = () => {
      return state.stompClient;
    };

    // =========== ACTION ===============
    const stompConnect = () => {
      // WebSocket 연결 생성
      state.socket = new SockJS('http://localhost:8080/ws');

      // Stomp 클라이언트 생성
      state.stompClient = Stomp.over(state.socket);

      // Stomp 연결
      state.stompClient.connect(
        {},
        frame => {
          console.log('Stomp 연결에 성공했습니다.');
          // Stomp 연결 성공 시 추가 작업 수행
        },
        error => {
          console.error('Stomp 연결에 실패했습니다.', error);
          // Stomp 연결 실패 시 추가 작업 수행
        }
      );
    };

    const stompDisconnect = () => {
      if (state.stompClient) {
        state.stompClient.disconnect();
        console.log('Stomp 연결이 종료되었습니다.');
      }
    };

    const unsubscribe = () => {
      if (state.stompClient && state.subscription) {
        state.subscription.unsubscribe();
        console.log('구독이 해지되었습니다.');
      }
    };

    return {
      state,
      stompConnect,
      stompDisconnect,
      unsubscribe,
      getStompClient,
    };
  }
);