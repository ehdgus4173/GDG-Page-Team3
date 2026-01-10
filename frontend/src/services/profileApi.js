// src/services/profileApi.js
import { requestJSON } from "./http";

export async function fetchMyProfile() {
  return requestJSON("/api/profile");
}
