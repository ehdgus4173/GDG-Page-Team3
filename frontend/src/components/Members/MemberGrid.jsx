import MemberCard from "./MemberCard";
import "./MemberGrid.css";

const dummyMembers = Array.from({ length: 16 });

function MemberGrid() {
  return (
    <div className="member-grid">
      {dummyMembers.map((_, i) => (
        <MemberCard key={i} />
      ))}
    </div>
  );
}

export default MemberGrid;
