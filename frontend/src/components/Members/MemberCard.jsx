import "./MemberCard.css";

function MemberCard({ member, onClick, clickable = true }) {
  // member 없을 때도 안전하게
  const data = member || {
    name: "박진아",
    major: "문화예술학과",
    desc: "한줄소개 소개 인사를 어떻게 해야할까",
    imageUrl: "https://placehold.co/300x300",
    tags: ["코어", "Front-end"],
    skills: ["Figma", "Figma", "Figma"],
  };

  const handleClick = () => {
    if (!clickable) return;
    if (onClick) onClick(data);
  };

  return (
    <article
      className={`member-card ${clickable ? "clickable" : ""}`}
      onClick={handleClick}
      role={clickable ? "button" : undefined}
      tabIndex={clickable ? 0 : -1}
      onKeyDown={(e) => {
        if (!clickable) return;
        if (e.key === "Enter" || e.key === " ") handleClick();
      }}
    >
      <div className="member-image">
        <img src={data.imageUrl} alt="member" />
      </div>

      <div className="member-tags">
        <span className="tag outline">{data.tags?.[0] || "코어"}</span>
        <span className="tag solid">{data.tags?.[1] || "Front-end"}</span>
      </div>

      <h3 className="member-name">{data.name}</h3>
      <p className="member-major">{data.major}</p>
      <p className="member-desc">{data.desc}</p>

      <div className="member-skills">
        {(data.skills || []).slice(0, 3).map((s, i) => (
          <span key={`${s}-${i}`} className="skill">{s}</span>
        ))}
      </div>
    </article>
  );
}

export default MemberCard;
