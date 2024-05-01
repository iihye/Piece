import axios from 'axios';

function localAxios() {
  const instance = axios.create({
    baseURL: `${import.meta.env.VITE_REST_PIECE_API}/api`,
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
    },
  });
  return instance;
}

export { localAxios };