// src/services/profileApi.js
import { auth } from "../lib/firebase";
import { onAuthStateChanged } from "firebase/auth";

async function ensureCurrentUser() {
  if (auth.currentUser) return auth.currentUser;

  return new Promise((resolve, reject) => {
    const unsubscribe = onAuthStateChanged(auth, (user) => {
      unsubscribe();
      if (user) resolve(user);
      else reject(new Error("로그인이 필요합니다."));
    });
    setTimeout(() => {
      unsubscribe();
      reject(new Error("로그인이 필요합니다."));
    }, 2000);
  });
}

export async function fetchMyProfile() {
  const user = await ensureCurrentUser();
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

export async function updateMyProfile(payload) {
  const user = await ensureCurrentUser();
  const idToken = await user.getIdToken();

  const res = await fetch("/api/profile", {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${idToken}`,
    },
    body: JSON.stringify(payload),
  });

  if (!res.ok) {
    let msg = "프로필 저장 실패";
    try {
      const data = await res.json();
      msg = data?.message || data?.reason || msg;
    } catch {}
    throw new Error(msg);
  }
  return res.json();
}
