import axios from 'axios';

function localAxios(url) {
  const instance = axios.create({
    baseURL: url,
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
    },
  });
  instance.interceptors.request.use(config => {
    const accessToken = getAccessToken();
    if (!accessToken) {
      return config;
    }
    addTokenToHeaders(config.headers, accessToken, 'Authorization');
    return config;
  })
  return instance;
}

// 로컬 스토리지에서 토큰 가져오기
function getAccessToken() {
  return localStorage.getItem('accessToken');
}

// 토큰을 헤더에 추가하는 함수
function addTokenToHeaders(headers, token, headerKey) {
  headers[headerKey] = token;
}

export { localAxios };