import { api } from "../lib/api";

export async function fetchNotices({ page, size }) {
  return api.get(`/api/notices?page=${page}&size=${size}`);
}
