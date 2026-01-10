// src/services/membersApi.js
import { requestJSON } from "./http";

function normalizeGeneration(generation) {
  // "전체" -> undefined, "1기" -> 1
  if (!generation || generation === "전체") return undefined;
  const n = parseInt(String(generation).replace("기", ""), 10);
  return Number.isFinite(n) ? n : undefined;
}

function normalizePart(part) {
  if (!part || part === "전체") return undefined;
  return part;
}

export async function fetchMembers({ generation, part, page, size } = {}) {
  const g = normalizeGeneration(generation);
  const p = normalizePart(part);

  const qs = new URLSearchParams();
  if (g !== undefined) qs.set("generation", String(g));
  if (p) qs.set("part", p);

  // ✅ 백엔드가 페이지를 지원한다면 쓰고, 아니면 무시될 수 있음
  if (page) qs.set("page", String(page));
  if (size) qs.set("size", String(size));

  const query = qs.toString();
  return requestJSON(`/api/member${query ? `?${query}` : ""}`);
}

export async function fetchMemberDetail(memberId) {
  return requestJSON(`/api/member/${memberId}`);
}
