import { useEffect, useState } from "react";
import MemberCard from "./MemberCard";
import MemberDetailModal from "./MemberDetailModal";
import "./MemberGrid.css";

import { fetchMembers } from "../../services/membersApi";
import { mapMemberListItemToCard } from "../../services/memberMapper";

function MemberGrid({ page, selected, onTotalPages }) {
  const [open, setOpen] = useState(false);
  const [selectedMember, setSelectedMember] = useState(null);

  const [members, setMembers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [errorMsg, setErrorMsg] = useState("");

  useEffect(() => {
    let alive = true;

    async function load() {
      setLoading(true);
      setErrorMsg("");

      try {
        // ✅ size는 카드 16개 기준 (원하면 바꿔)
        const data = await fetchMembers({
          generation: selected?.generation,
          part: selected?.part,
          page,
          size: 16,
        });

        // 백엔드가 배열만 주는 경우 / 페이지 객체로 주는 경우 둘 다 대응
        const list = Array.isArray(data) ? data : (data?.content || data?.items || []);
        const totalPages = data?.totalPages;

        const mapped = list.map(mapMemberListItemToCard);

        if (!alive) return;
        setMembers(mapped);

        if (typeof totalPages === "number" && onTotalPages) onTotalPages(totalPages);
      } catch (e) {
        if (!alive) return;
        setErrorMsg(e.message || "멤버 목록을 불러오지 못했습니다.");
        setMembers([]);
      } finally {
        if (alive) setLoading(false);
      }
    }

    load();
    return () => { alive = false; };
  }, [page, selected?.generation, selected?.part, onTotalPages]);

  const handleCardClick = (member) => {
    setSelectedMember(member); // 여기엔 profileId가 있어야 함
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setSelectedMember(null);
  };

  if (loading) return <div style={{ padding: 20 }}>불러오는 중...</div>;
  if (errorMsg) return <div style={{ padding: 20 }}>{errorMsg}</div>;

  return (
    <>
      <div className="member-grid">
        {members.map((member) => (
          <MemberCard
            key={member.profileId}
            member={member}
            onClick={handleCardClick}
          />
        ))}
      </div>

      <MemberDetailModal
        open={open}
        onClose={handleClose}
        member={selectedMember} // ✅ 여기서 profileId로 상세 재조회할거야
      />
    </>
  );
}

export default MemberGrid;
