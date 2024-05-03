import { ref, computed } from 'vue';
import { defineStore } from 'pinia';
import router from '@/router';
import axios from 'axios';

export const useUserStore = defineStore('user', () => {
    // =========== STATE ===============
    const mypageLabelList = ref({});
    const mypageLabelWear = ref({});
    const mypageLabelWearoff = ref(false);
    const user = ref(null);
    const userId = ref(null); // 추가: 사용자 ID 저장

    // =========== GETTER ===============
    const getMypageLabelList = computed(() => mypageLabelList.value);
    const getMypageLabelWear = computed(() => mypageLabelWear.value);
    const getMypageLabelWearoff = computed(() => mypageLabelWearoff.value);
    const getUserId = computed(() => userId.value); // 추가: 사용자 ID 반환

    // =========== ACTION ===============
    async function findMypageLabelList() {
        try {
            const response = await axios.get(`${import.meta.env.VITE_REST_PIECE_API}/mylabels`);
            mypageLabelList.value = response.data.data;
            checkMypageLabelWear();
        } catch (error) {
            console.error('Error fetching labels:', error);
        }
    }

    async function login(email, password) {
        try {
            const response = await axios.post(`${import.meta.env.VITE_REST_URL}/auth/login`, { email, password });
            user.value = response.data;
            userId.value = response.data.userId; // 추가: 사용자 ID 저장
            console.log("로그인 성공:", response.data);
            router.push('/home');
        } catch (error) {
            console.error("로그인 실패:", error.response ? error.response.data : error);
        }
    }

    async function register(email, password, nickname) {
        try {
            const response = await axios.post(`${import.meta.env.VITE_REST_URL}/users/register`, { email, password, nickname });
            user.value = response.data;
            userId.value = response.data.userId; // 추가: 사용자 ID 저장
            console.log("회원가입 성공:", response.data);
            router.push('/home');
        } catch (error) {
            console.error("회원가입 실패:", error.response ? error.response.data : error);
        }
    }

    async function addMypageLabelWear(labelId) {
        try {
            await axios.put(`${import.meta.env.VITE_REST_PIECE_API}/mylabels/${labelId}`);
            await findMypageLabelList();
        } catch (error) {
            console.error('Error wearing label:', error);
        }
    }

    async function deleteMypageLabelWear() {
        try {
            await axios.put(`${import.meta.env.VITE_REST_PIECE_API}/mylabels/wearoff`);
            await findMypageLabelList();
        } catch (error) {
            console.error('Error removing label wear:', error);
        }
    }

    function checkMypageLabelWear() {
        mypageLabelWearoff.value = Object.values(mypageLabelList.value).every((item) => !item.wearLabels);
    }

    return {
        mypageLabelList,
        mypageLabelWear,
        mypageLabelWearoff,
        user,
        userId,
        getMypageLabelList,
        getMypageLabelWear,
        getMypageLabelWearoff,
        getUserId,
        findMypageLabelList,
        login,
        register,
        addMypageLabelWear,
        deleteMypageLabelWear,
        checkMypageLabelWear
    };
}, { persist: true });
