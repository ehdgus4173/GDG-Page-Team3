// src/utils/memberMapper.js

export function snsLinksToArray(snsLinks) {
  if (!snsLinks) return [];

  // 백엔드가 리스트 [{type, url}] 형태를 주는 경우 (현재 구조)
  if (Array.isArray(snsLinks)) {
    return snsLinks
      .filter((item) => item && typeof item === "object" && item.url)
      .map((item) => ({
        label: typeLabel(item.type),
        url: item.url,
      }));
  }

  // 혹시 과거 버전: { github: "...", blog: "..." } 형태 대응
  if (typeof snsLinks === "object") {
    return Object.entries(snsLinks)
      .filter(([_, url]) => url && url !== "string")
      .map(([key, url]) => ({
        label: keyLabel(key),
        url,
      }));
  }

  return [];
}

function typeLabel(type) {
  const key = `${type ?? ""}`.toLowerCase();
  const map = {
    github: "Github",
    blog: "Blog",
    linkedin: "LinkedIn",
    instagram: "Instagram",
    behance: "Behance",
    etc: "Etc",
  };
  return map[key] || type || "Link";
}

function keyLabel(key) {
  const map = {
    github: "Github",
    blog: "Blog",
    linkedin: "LinkedIn",
    instagram: "Instagram",
    behance: "Behance",
    etc: "Etc",
  };
  return map[key] || key;
}
  
  // 멤버 "목록" 아이템용 (목록 응답이 뭐가 오든 일단 안전하게)
  export function mapMemberListItemToCard(m) {
    const profileId = m.profileId ?? m.id ?? m.memberId;
  
    const generation = m.generation;
    const part = m.part;
  
    return {
      profileId,
      name: m.name || "",
      major: `${generation ? `${generation}기` : ""}${generation && part ? " · " : ""}${part || ""}`.trim(),
      desc: m.introduction || m.bio || "",
      imageUrl: m.imageUrl || m.imageURL || "https://placehold.co/300x300",
      tags: [generation ? `${generation}기` : "멤버", part || ""].filter(Boolean),
      skills: Array.isArray(m.techStacks)
        ? m.techStacks.map((x) => (typeof x === "string" ? x : x?.name)).filter(Boolean)
        : [],
      links: snsLinksToArray(m.snsLinks),
    };
  }
  
  // 멤버 "상세" 응답용
  export function mapMemberDetailToCard(detail) {
    return mapMemberListItemToCard({
      ...detail,
      introduction: detail.introduction,
      techStacks: detail.techStacks,
      snsLinks: detail.snsLinks,
    });
  }
  
  // 내 프로필 응답용 (/api/profile)
  export function mapMyProfileToCard(p) {
    return {
      profileId: p.profileId,
      name: p.name || "",
      major: `${p.generation ? `${p.generation}기` : ""}${p.generation && p.part ? " · " : ""}${p.part || ""}`.trim(),
      desc: p.bio || "",
      imageUrl: p.imageUrl || "https://placehold.co/600x600",
      tags: [p.generation ? `${p.generation}기` : "멤버", p.part || ""].filter(Boolean),
      skills: Array.isArray(p.techStacks)
        ? p.techStacks.map((x) => (typeof x === "string" ? x : x?.name)).filter(Boolean)
        : [],
      links: snsLinksToArray(p.snsLinks),
    };
  }
  