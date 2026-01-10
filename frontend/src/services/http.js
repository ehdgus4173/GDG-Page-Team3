// src/services/http.js
const BASE_URL = import.meta.env.VITE_API_BASE_URL || ""; 
// 예) .env에 VITE_API_BASE_URL=http://localhost:8080

function buildHeaders(extra = {}) {
  const headers = { "Content-Type": "application/json", ...extra };

  // ✅ 프로젝트에서 토큰 저장 위치가 다르면 여기만 바꾸면 됨
  const token = localStorage.getItem("accessToken"); 
  if (token) headers.Authorization = `Bearer ${token}`;

  return headers;
}

export async function requestJSON(path, { method = "GET", body, headers } = {}) {
  const res = await fetch(`${BASE_URL}${path}`, {
    method,
    headers: buildHeaders(headers),
    body: body ? JSON.stringify(body) : undefined,
    credentials: "include", // 쿠키 기반이면 유지, 아니면 있어도 큰 문제 없음
  });

  // 서버가 공통 에러 바디({success:false, code, reason...}) 주는 형태라면 여기서 처리 가능
  const text = await res.text();
  const data = text ? JSON.parse(text) : null;

  if (!res.ok) {
    const message = data?.reason || data?.message || `HTTP ${res.status}`;
    const err = new Error(message);
    err.status = res.status;
    err.data = data;
    throw err;
  }

  return data;
}
