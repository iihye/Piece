import axios from 'axios';

function localAxios(url) {
  const instance = axios.create({
    baseURL: url,
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
    },
  });
  return instance;
}

export { localAxios };