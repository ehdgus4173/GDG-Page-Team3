import { useEffect, useState } from "react";
import "./MemberDetailModal.css";

import MemberCard from "./MemberCard";
import MemberLinksCard from "./MemberLinksCard";

import { fetchMemberDetail } from "../../services/membersApi";
import { mapMemberDetailToCard } from "../../services/memberMapper";

function MemberDetailModal({ open, onClose, member }) {
  const [detail, setDetail] = useState(null);
  const [loading, setLoading] = useState(false);
  const [errorMsg, setErrorMsg] = useState("");

  useEffect(() => {
    if (!open) return;

    const onKeyDown = (e) => {
      if (e.key === "Escape") onClose();
    };
    window.addEventListener("keydown", onKeyDown);
    return () => window.removeEventListener("keydown", onKeyDown);
  }, [open, onClose]);

  useEffect(() => {
    if (!open || !member?.profileId) return;

    let alive = true;
    async function loadDetail() {
      setLoading(true);
      setErrorMsg("");
      setDetail(null);

      try {
        const data = await fetchMemberDetail(member.profileId);
        const mapped = mapMemberDetailToCard(data);
        if (!alive) return;
        setDetail(mapped);
      } catch (e) {
        if (!alive) return;
        setErrorMsg(e.message || "멤버 상세를 불러오지 못했습니다.");
      } finally {
        if (alive) setLoading(false);
      }
    }

    loadDetail();
    return () => { alive = false; };
  }, [open, member?.profileId]);

  if (!open || !member) return null;

  // ✅ detail이 아직 없으면 목록 정보(member)라도 보여주기
  const cardData = detail || member;

  return (
    <div className="member-modal-overlay" onClick={onClose} role="presentation">
      <div
        className="member-modal"
        onClick={(e) => e.stopPropagation()}
        role="dialog"
        aria-modal="true"
      >
        <button className="member-modal-close" onClick={onClose} aria-label="닫기">
          ×
        </button>

        <div className="member-modal-content">
          <div className="member-modal-left">
            <MemberCard member={cardData} clickable={false} />
          </div>

          <div className="member-modal-right">
            {loading ? (
              <div style={{ padding: 12 }}>불러오는 중...</div>
            ) : errorMsg ? (
              <div style={{ padding: 12 }}>{errorMsg}</div>
            ) : (
              <MemberLinksCard links={cardData.links} />
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default MemberDetailModal;
