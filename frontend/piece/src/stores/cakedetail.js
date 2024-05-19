import { ref, computed } from "vue";
import { defineStore } from "pinia";
import axios from "axios";

export const useCakeDetailStore = defineStore("cakedetail", () => {
    const cakeDetail = ref({});
    const cakeChatList = ref([]);
    const cakeHeartCount = ref(0);
    const cakeHeartState = ref(false);
    const cakeCultureType = ref("");
    const chatRoomId = ref(0);

    const setCakeCultureType = (cultureType) => {
        cakeCultureType.value = cultureType;
    };

    const getCakeCultureType = computed(() => cakeCultureType.value);
    const getCakeDetail = computed(() => cakeDetail.value);
    const getCakeChatList = computed(() => cakeChatList.value);
    const getCakeHeartCount = computed(() => cakeHeartCount.value);
    const getCakeHeartState = computed(() => cakeHeartState.value);
    const getChatRoomId = computed(() => chatRoomId.value);

    const fetchConcertCakeDetail = async (concertId) => {
        try {
            const response = await axios.get(
                `${import.meta.env.VITE_REST_PIECE_API}/cultures/kopis/${concertId}`
            );
            if (response.data.code === "FIND_KOPIS_CULTURE_SUCCESS") {
                cakeDetail.value = response.data.data;
                cakeHeartState.value = response.data.data.isHearted;
            }
        } catch (error) {
            console.error("공연 상세정보 가져오기 실패", error);
        }
    };

    const fetchTmdbDetail = async (code) => {
        try {
            const response = await axios.get(
                `${import.meta.env.VITE_REST_PIECE_API}/cultures/tmdb/${code}`
            );
            if (response.data.code === "FIND_TMDB_CULTURE_SUCCESS") {
                cakeDetail.value = response.data.data;
                cakeHeartState.value = response.data.data.isHearted;
            } else {
                console.error("Failed to fetch TMDB details:", response.data);
                selectedMovie.value = null;
            }
        } catch (error) {
            console.error("TMDB 상세정보 가져오기 실패", error);
            selectedMovie.value = null;
            throw error;
        }
    };

    const fetchHeartCount = async (cultureId) => {
        try {
            const response = await axios.get(
                `${import.meta.env.VITE_REST_PIECE_API}/cultures/heart/count/${cultureId}`
            );
            if (response.data.code === "COUNT_CULTURE_HEART_SUCCESS") {
                cakeHeartCount.value = response.data.data;
            }
        } catch (error) {
            console.error("하트수 가져오기 실패", error);
        }
    };

    const toggleHeart = async (cultureId) => {
        try {
            const response = await axios.post(
                `${import.meta.env.VITE_REST_PIECE_API}/cultures/heart/${cultureId}`
            );
            if (response.data.code === "TOGGLE_HEART_SUCCESS") {
                await fetchHeartCount(cultureId);
                cakeHeartState.value = true;
            }
            return response.data;
        } catch (error) {
            console.error("찜하기 실패", error);
            throw error;
        }
    };

    const removeHeart = async (cultureId) => {
        try {
            const response = await axios.delete(
                `${import.meta.env.VITE_REST_PIECE_API}/cultures/heart/${cultureId}`
            );
            if (response.data.code === "REMOVE_HEART_SUCCESS") {
                await fetchHeartCount(cultureId);
                cakeHeartState.value = false;
            }
            return response.data;
        } catch (error) {
            console.error("찜하기 취소 실패", error);
            throw error;
        }
    };

    const findCakeChatList = async (concertId) => {
        try {
            const response = await axios.get(
                `${import.meta.env.VITE_REST_PIECE_API}/chats/${concertId}`
            );
            if (response.data.code === "FIND_CHAT_LIST_SUCCESS") {
                cakeChatList.value = response.data.data;
            }
        } catch (error) {
            console.error("채팅목록 가져오기 실패", error);
        }
    };

    // 채팅방 번호로 조회하기
    const findCultureId = async (cultureId) => {
        console.log("채팅방 번호 조회하기");
        try {
            const response = await axios.get(
                `${import.meta.env.VITE_REST_CHAT_API}/chatrooms/${cultureId}`
            );
            chatRoomId.value = response.data.data;
            if (chatRoomId.value > 0) {
                console.log("찾은 채팅방 번호는 ", chatRoomId.value);
                findChatingList();
            }
        } catch (error) {
            if (error.response.status === 404) {
                console.log("채팅방을 새로 만들기");
                makeOpenchat(cultureId);
            }
            throw error;
        }
    };

    const makeOpenchat = function (cultureId) {
        axios({
            url: `${import.meta.env.VITE_REST_CHAT_API}/chatrooms/createopen`,
            method: "POST",
            data: {
                cultureId: cultureId,
                chatRoomName: "오픈채팅방",
            },
        })
            .then((res) => {
                chatRoomId.value = res.data.data;
                console.log("만든 채팅방 번호는 ", chatRoomId.value);
            })
            .catch((err) => {});
    };

    const findChatingList = function () {
        console.log("기존 채팅방이 존재하니까, 채팅방 리스트 가져오기");
        axios({
            url: `${import.meta.env.VITE_REST_CHAT_API}/chats/list`,
            method: "POST",
            data: {
                chatRoomId: chatRoomId.value,
                count: 5,
            },
        })
            .then((res) => {
                cakeChatList.value = res.data.data;
            })
            .catch((err) => {});
    };

    // 채팅 참여하기
    const joinChatRoom = function () {
        console.log("채팅방 참여하기");
        axios({
            url: `${import.meta.env.VITE_REST_CHAT_API}/participate`,
            method: "POST",
            data: {
                chatRoomId: chatRoomId.value,
                userId: Number(localStorage.getItem("userId")),
            },
        })
            .then((res) => {
                console.log(res);
                console.log("채팅방 참여 성공", res.data.data);
            })
            .catch((err) => {
                console.log("에러 발생");
                if (err.status === 400) {
                    console.log("이미 참여한 상태입니다");
                }
            });
        console.log("채팅방 참여하기 끝");

    };

    // 채팅 참여 여부 조회
    const isParticipateChatRoom = function () {
        axios({
            url: `${import.meta.env.VITE_REST_CHAT_API}/isparticipate`,
            method: "POST",
            data: {
                isPersonal: false,
            },
        }).then((res) => {
            console.log(res.data.data);
        });
    };

    return {
        cakeDetail,
        cakeChatList,
        cakeHeartCount,
        cakeHeartState,
        cakeCultureType,
        chatRoomId,

        getCakeDetail,
        getCakeChatList,
        getCakeHeartCount,
        getCakeHeartState,
        getCakeCultureType,
        getChatRoomId,

        setCakeCultureType,
        fetchConcertCakeDetail,
        fetchTmdbDetail,
        fetchHeartCount,
        findCakeChatList,
        toggleHeart,
        removeHeart,

        // chat
        findCultureId,
        findChatingList,
        joinChatRoom,
    };
});
