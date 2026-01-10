import { useEffect } from "react";
import "./MemberDetailModal.css";

import MemberCard from "./MemberCard";
import MemberLinksCard from "./MemberLinksCard";

function MemberDetailModal({ open, onClose, member }) {
  useEffect(() => {
    if (!open) return;

    const onKeyDown = (e) => {
      if (e.key === "Escape") onClose();
    };
    window.addEventListener("keydown", onKeyDown);
    return () => window.removeEventListener("keydown", onKeyDown);
  }, [open, onClose]);

  if (!open || !member) return null;

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
            {/* 카드 재사용 */}
            <MemberCard member={member} clickable={false} />
          </div>

          <div className="member-modal-right">
            <MemberLinksCard links={member.links} />
          </div>
        </div>
      </div>
    </div>
  );
}

export default MemberDetailModal;
