import "./MemberCard.css";

function MemberCard() {
  return (
    <article className="member-card">
      <div className="member-image">
        <img src="https://placehold.co/300x300" alt="member" />
      </div>

      <div className="member-tags">
        <span className="tag outline">코어</span>
        <span className="tag solid">Front-end</span>
      </div>

      <h3 className="member-name">박진아</h3>
      <p className="member-major">문화예술학과</p>
      <p className="member-desc">
        한줄소개 소개 인사를 어떻게 해야할까
      </p>

      <div className="member-skills">
        <span className="skill">Figma</span>
        <span className="skill">Figma</span>
        <span className="skill">Figma</span>
      </div>
    </article>
  );
}

export default MemberCard;
