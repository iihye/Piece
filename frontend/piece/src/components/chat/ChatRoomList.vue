<template>
  <div class="chatRoomListHeader-main-container">
    <!-- filter -->
    <div v-if="showFilter == 0" class="chatroomlist-scroll-container">
      <div class="chatroomlist-tab-navigation">
        <div class="chatroomlist-tab-menu" ref="tabMenu">
          <FilterItem
            v-for="(item, index) in filterItems"
            class="chatroomlist-tab-btn"
            :key="index"
            :labelType="item.labelType"
            :title="item.title"
            :isSelect="item.isSelect"
            @click="handleItemSelectClick(index)"
          ></FilterItem>
        </div>
      </div>
    </div>
    <div
      class="chatroomlist-cardItems"
      v-for="chatRoom in filteredMyList"
      :key="chatRoom"
      @click="goToChatConversation(chatRoom)"
    >
      <!-- 오픈채팅 -->
      <div
        v-if="chatRoom.isPersonal === false"
        class="chatroomlist-sub-container"
      >
        <div class="chatroomlist-item-container">
          <img
            class="chatroomlist-item-squreImage"
            :src="chatRoom.culture.imageUrl"
            alt="profileImage"
          />
          <div class="chatroomlist-info-conatiner">
            <div class="chatroomlist-info-nickname">
              {{ chatRoom.culture.title }}
            </div>
            <div class="chatroomlist-info-message">
              {{ chatRoom.participantCount }}명이 대화하고 있어요!
            </div>
            <div class="chatroomlist-info-time">n분 전</div>
          </div>
        </div>
      </div>

      <!-- 일대일 채팅 -->
      <div
        v-if="chatRoom.isPersonal === true"
        class="chatroomlist-sub-container"
      >
        <div
          class="chatroomlist-item-container"
          v-for="participant in chatRoom.participants"
          :key="participant.userId"
        >
          <img
            v-if="
              (participant.profileImage === null ||
                participant.profileImage === '') &&
              participant.userId != loginUserId
            "
            class="chatroomlist-item-circleImage"
            src="@/assets/defaultprofile.png"
          />
          <img
            v-else-if="participant.userId != loginUserId"
            class="chatroomlist-item-circleImage"
            :src="participant.profileImage"
          />

          <div
            class="chatroomlist-info-conatiner"
            v-if="participant.userId != loginUserId"
          >
            <div class="chatroomlist-info-nickname">
              {{ participant.nickname }}
            </div>
            <div class="chatroomlist-info-message">
              {{ chatRoom.lastMessage }}
            </div>
            <!-- <div class="chatroomlist-info-time">n분 전</div> -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch, computed, onBeforeUnmount } from "vue";
import { useRouter } from "vue-router";
import { useChatRoomStore } from "@/stores/chatroom";
import FilterItem from "@/components/item/FilterItem.vue";

const router = useRouter();
const chatRoomStore = useChatRoomStore();
const loginUserId = ref();
const showFilter = ref(0);

// 필터 관련 코드 시작
const filteredMyList = computed(() => computedFilteredMyList());
const selectedOption = ref("ALL");

function computedFilteredMyList() {
  if (selectedOption.value === "ALL") {
    return chatRoomList.value;
  } else if (selectedOption.value === "MOVIE") {
    return chatRoomList.value.filter(
      (item) => item.culture.cultureType === "MOVIE"
    );
  } else if (selectedOption.value === "THEATER") {
    return chatRoomList.value.filter(
      (item) => item.culture.cultureType === "THEATER"
    );
  } else if (selectedOption.value === "MUSICAL") {
    return chatRoomList.value.filter(
      (item) => item.culture.cultureType === "MUSICAL"
    );
  } else if (selectedOption.value === "CONCERT") {
    return chatRoomList.value.filter(
      (item) => item.culture.cultureType === "CONCERT"
    );
  } else if (selectedOption.value === "ETC") {
    return chatRoomList.value.filter(
      (item) => item.culture.cultureType === "ETC"
    );
  } else {
    return [];
  }
}

// 여기에 필터 요소 입력
const filterItems = ref([
  {
    labelType: "ALL",
    title: "전체",
    isSelect: true,
  },
  {
    labelType: "MOVIE",
    title: "영화",
    isSelect: false,
  },
  {
    labelType: "THEATER",
    title: "연극",
    isSelect: false,
  },
  {
    labelType: "MUSICAL",
    title: "뮤지컬",
    isSelect: false,
  },
  {
    labelType: "CONCERT",
    title: "콘서트",
    isSelect: false,
  },
  {
    labelType: "ETC",
    title: "기타",
    isSelect: false,
  },
]);

const handleItemSelectClick = (index) => {
  for (let item of filterItems.value) {
    if (item.isSelect) {
      item.isSelect = false;
    }
  }
  filterItems.value[index].isSelect = !filterItems.value[index].isSelect;
  selectedOption.value = filterItems.value[index].labelType;
};

// filter
const tabMenu = ref(null);
const activeDrag = ref(false);

const handleMouseMove = (event) => {
  if (!activeDrag.value) return;
  tabMenu.value.scrollLeft -= event.movementX;
  tabMenu.value.classList.add("dragging");
};

const handleMouseUp = () => {
  activeDrag.value = false;
  tabMenu.value.classList.remove("dragging");
};

const handleMouseDown = () => {
  activeDrag.value = true;
};

// 필터 관련 코드 끝
const chatRoomList = ref([
  // {
  //     "chatRoomId": 1,
  //     "cultureId": 1,
  //     "updatedAt": "업데이트시간",
  //     "imageUrl": "src/assets/logo.png", // 전역으로 받아올 예정
  //     "chatRoomName": "Vue.js란 과연 무엇인가?",
  //     "isPersonal": false,
  //     "isOpened": true,
  // }
]); // chatroomList를 reactive하게 만들기 위해 ref 사용

async function fetchLoginUserId() {
  loginUserId.value = await localStorage.getItem("userId");
  console.log("로그인되어 있는 userId:", loginUserId.value);
}

// 채팅방 목록 받기
async function fetchRooms(isPersonal) {
  try {
    const chatRooms = await chatRoomStore.getChatRoomList(isPersonal);
    console.log("store에서 채팅방 목록을 받았습니다.");
    if (chatRooms.length > 0) {
      chatRooms.forEach((m) => {
        chatRoomList.value.push(m);
      });
    }
  } catch (error) {
    console.error("Error fetching chat logs:", error);
  }
}

watch(
  () => chatRoomStore.isPersonal,
  (newValue, oldValue) => {
    console.log("isPersonal 값이 변경되었습니다:" + newValue);
    // 변경된 값에 대한 추가적인 처리를 수행할 수 있습니다.
    // 채팅방 정보 갱신
    chatRoomList.value = [];

    console.log(
      "받아올 채팅방 목록 변경. 현재 login user id:" + loginUserId.value
    );

    showFilter.value = newValue;

    fetchRooms(newValue);
  }
);

const goToChatConversation = (chatRoom) => {
  // 선택된 채팅방 정보 변경
  chatRoomStore.setChatRoomId(chatRoom.chatRoomId);
  chatRoomStore.setIsPersonal(chatRoom.isPersonal);

  router.push("/chat");
  // 채팅 메시지 페이지로 이동
};

// 페이지가 마운트될 때 chatroomList를 가져오도록 설정
onMounted(async () => {
  tabMenu.value.addEventListener("mousedown", handleMouseDown);
  document.addEventListener("mouseup", handleMouseUp);
  tabMenu.value.addEventListener("mousemove", handleMouseMove);

  await fetchLoginUserId();

  await fetchRooms(0); // default: 오픈 채팅방
});
</script>

<style>
/* filter */
.chatroomlist-scroll-container {
  position: relative;
  /* width: 450px; */
  transition: 0.5s ease;
  margin-bottom: 2rem;
}

.chatroomlist-tab-navigation {
  position: relative;
  max-width: fit-content;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  align-items: center;
}

.chatroomlist-tab-menu {
  list-style: none;
  white-space: nowrap;
  overflow-x: auto;
  user-select: none;
  scroll-behavior: smooth;
}

.chatroomlist-tab-menu.dragging {
  scroll-behavior: unset;
  cursor: grab;
}

.chatroomlist-tab-menu::-webkit-scrollbar {
  display: none;
}

.chatroomlist-tab-btn {
  display: inline-block;
  margin: 0 0.2rem;
  cursor: pointer;
  user-select: none;
  transition: 0.3s ease;
}

.chatroomlist-tab-menu.dragging .tab-btn {
  pointer-events: none;
}

.chatRoomListHeader-main-container {
  display: flex;
  flex-direction: column;
  overflow-y: auto; /* Enable vertical scroll */
  max-height: calc(
    100vh - 7.25rem - 4rem
  ); /* Set a maximum height for the container */
}

.chatroomlist-cardItems {
  display: flex;
  justify-content: flex-start;
  padding: 0.4rem;
}

.chatroomlist-cardItems:hover {
  background-color: var(--sub-color);
}

.chatroomlist-cardItems:active {
  background-color: var(--main-color);
}

/* item */
.chatroomlist-sub-container {
  width: 100%;
}

.chatroomlist-item-container {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-right: 1rem;
}

.chatroomlist-image-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 4.4rem;
  height: 4.4rem;
  margin-right: 0.6rem;
}

.chatroomlist-item-squreImage {
  width: 4.4rem;
  height: 4.4rem;
  border: 1px solid var(--gray-color);
  border-radius: 10%;
  object-fit: fill;
}

.chatroomlist-item-circleImage {
  width: 4.4rem;
  height: 4.4rem;
  border: 1px solid var(--gray-color);
  border-radius: 50%;
  margin-right: 0.6rem;
}

/* info */
.chatroomlist-info-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  height: auto;
}

.chatroomlist-info-nickname {
  font-family: "Bold";
  font-size: 1rem;
  color: var(--black-color);
  margin-bottom: 0.1rem;

  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  white-space: normal;
  max-height: 3em;
}

.chatroomlist-info-message {
  font-family: "Regular";
  font-size: 1rem;
  color: var(--gray2-color);
  margin-bottom: 0.1rem;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

.chatroomlist-info-time {
  font-family: "Regular";
  font-size: 1rem;
  color: var(--main-color);
  margin-bottom: 0.1rem;
}
</style>
