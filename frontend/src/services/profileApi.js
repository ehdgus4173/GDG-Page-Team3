// src/services/profileApi.js
import { auth } from "../lib/firebase";

export async function fetchMyProfile() {
  const user = auth.currentUser;
  if (!user) throw new Error("로그인이 필요합니다.");

  const idToken = await user.getIdToken(); // Firebase ID Token

  const res = await fetch("/api/profile", {
    method: "GET",
    headers: {
      Authorization: `Bearer ${idToken}`,
    },
  });

  if (!res.ok) {
    let msg = "프로필 조회 실패";
    try {
      const data = await res.json();
      msg = data?.message || data?.reason || msg;
    } catch {}
    throw new Error(msg);
  }
  return res.json();
}
