import { useState } from "react";
import MemberCard from "./MemberCard";
import MemberDetailModal from "./MemberDetailModal";
import "./MemberGrid.css";

/* 🔹 더미 멤버 데이터 (백엔드 연결 전용) */
const dummyMembers = Array.from({ length: 16 }).map((_, i) => ({
  id: i,
  name: "박진아",
  major: "문화예술학과",
  desc: "한줄소개 소개 인사를 어떻게 해야할까",
  imageUrl: "https://placehold.co/300x300",
  tags: ["코어", "Front-end"],
  skills: ["Figma", "React", "UI"],
  links: [
    { label: "Github", url: "https://github.com" },
    { label: "Portfolio", url: "https://example.com" },
    { label: "Instagram", url: "https://instagram.com" },
    { label: "LinkedIn", url: "https://linkedin.com" },
  ],
}));

function MemberGrid() {
  const [open, setOpen] = useState(false);
  const [selectedMember, setSelectedMember] = useState(null);

  const handleCardClick = (member) => {
    setSelectedMember(member);
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setSelectedMember(null);
  };

  return (
    <>
      <div className="member-grid">
        {dummyMembers.map((member) => (
          <MemberCard
            key={member.id}
            member={member}
            onClick={handleCardClick}
          />
        ))}
      </div>

      {/* 🔹 멤버 상세 모달 */}
      <MemberDetailModal
        open={open}
        onClose={handleClose}
        member={selectedMember}
      />
    </>
  );
}

export default MemberGrid;
