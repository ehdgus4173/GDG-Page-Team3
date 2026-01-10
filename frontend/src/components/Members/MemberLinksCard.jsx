import "./MemberLinksCard.css";

function MemberLinksCard({ links = [] }) {
  return (
    <section className="member-links-card">
      <div className="member-links-header">
        <h3>포트폴리오 / SNS</h3>
      </div>

      <div className="member-links-list">
        {links.length === 0 ? (
          <p className="member-links-empty">등록된 링크가 없습니다.</p>
        ) : (
          links.map((item, idx) => (
            <a
              key={`${item.label}-${idx}`}
              className="member-link-item"
              href={item.url}
              target="_blank"
              rel="noopener noreferrer"
            >
              <div className="member-link-left">
                <div className="member-link-icon" aria-hidden="true">
                  {/* 일단 깃허브 느낌 아이콘 대체(나중에 SVG로 교체 가능) */}
                  <span>⌁</span>
                </div>
                <span className="member-link-label">{item.label}</span>
              </div>
              <span className="member-link-arrow" aria-hidden="true">→</span>
            </a>
          ))
        )}
      </div>
    </section>
  );
}

export default MemberLinksCard;
