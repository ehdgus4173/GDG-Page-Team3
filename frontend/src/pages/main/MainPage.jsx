import "./MainPage.css";

const MainPage = () => {
  return (
    <div className="main-page">
      {/* ================= HERO ================= */}
      <section className="hero">
        {/* 알약 이미지 */}
        <div className="hero-visual">
          <img
            src="/hero-pills.png"
            alt="GDG visual"
            className="image-2-icon"
          />
        </div>

        {/* 제목 */}
        <h1 className="hero-title google-developer-groups">
          Google Developer Groups <br />
          on Campus SeoulTech
        </h1>

        {/* 설명 */}
        <p className="hero-desc">
          우리는 자유롭고 편안한 분위기 속에서 각자의 역량과 창의성을 발휘하고,<br />
          팀 전체가 함께 성장할 수 있는 기회를 만들어가고자 합니다.
        </p>
      </section>

      {/* ================= WHAT WE DO ================= */}
{/* ================= WHAT WE DO ================= */}
<section className="what">
  <h2>What We Do</h2>

  <div className="what-list">
    {/* 1 */}
    <div className="what-cell cell-1">
      <div className="pill red" />
      <p className="text-box left">
        커리큘럼 및 세션 참여를 통해<br />
        학습과 공유의 장을 만듭니다.
      </p>
    </div>

    {/* 2 */}
    <div className="what-cell cell-2">
      <div className="pill blue" />
      <p className="text-box right">
        프로젝트 운영 및 참여를 통해<br />
        실질적인 성과와 경험을 합니다.
      </p>
    </div>

    {/* 3 */}
    <div className="what-cell cell-3">
      <div className="pill yellow" />
      <p className="text-box left">
        커뮤니티 활성화를 통해<br />
        서로의 성장을 돕는 문화를 형성하는 것에 집중합니다.
      </p>
    </div>

    {/* 4 */}
    <div className="what-cell cell-4">
      <div className="pill green" />
      <p className="text-box right">
        배움과 도전, 그리고 협력으로<br />
        성장하는 공동체를 지향합니다.
      </p>
    </div>
  </div>
</section>


      {/* ================= REVIEW ================= */}
      <section className="review">
        <h2>GDGoC SeoulTech 어떤가요?</h2>

        <div className="review-grid">
          <div className="review-card">
            <b>혼자였으면 절대 안 했을 프로젝트를 여기서 처음 해봤어요</b>
            <p>
              각자 잘하는 게 달라서 모르는 부분을 자연스럽게 채워주고,<br />
              결과까지 같이 가져가는 경험이었어요.
            </p>
            <span>AI 파트 박진아</span>
          </div>

          <div className="review-card">
            <b>파트는 나뉘어 있지만, 일은 같이 해요.</b>
            <p>
              각자 잘하는 게 달라서 모르는 부분을 자연스럽게 채워주고,<br />
              결과까지 같이 가져가는 경험이었어요.
            </p>
            <span>AI 파트 박진아</span>
          </div>

          <div className="review-card">
            <b>실력보다 태도를 더 중요하게 보는 분위기라 부담이 없어요.</b>
            <p>
              처음엔 많이 부족했는데 질문하고 시도하는 과정 자체를<br />
              응원해주는 분위기가 좋았어요.
            </p>
            <span>AI 파트 박진아</span>
          </div>

          <div className="review-card">
            <b>이론보다 ‘일단 만들어보자’는 말이 인상 깊었어요.</b>
            <p>
              완성도가 낮아도 괜찮다고 해서 도전할 수 있었고,<br />
              결국 결과물로 남았어요.
            </p>
            <span>AI 파트 박진아</span>
          </div>
        </div>
      </section>

      {/* ================= ACTIVITY ================= */}
      <section className="activity">
        <p className="activity-title">
          같은 관심사를 가진 사람들과<br />
          함께 배우고, 나누고, 만드는 활동을 합니다.
        </p>

        <div className="activity-grid">
          <div className="activity-card">
            <b>세션</b>
            <p>
              관심 분야를 매주 함께 공부하고,
              배운 내용에 대해 이야기하는 시간입니다.
            </p>
          </div>

          <div className="activity-card">
            <b>소규모 프로젝트</b>
            <p>
              다양한 파트 팀원들과 함께 간단한 서비스를 만들고,
              결과를 공유합니다.
            </p>
          </div>

          <div className="activity-card">
            <b>커뮤니티 활동</b>
            <p>밋업, 관심주제 공유</p>
          </div>
        </div>
      </section>

      {/* ================= MORE ================= */}
<section className="more">
  <h2 className="more-title">GDGoC SeoulTech가 더 궁금하다면?</h2>

  <div className="more-cards">
    {/* Instagram */}
      <a
        className="more-circle instagram"
        href="https://www.instagram.com/gdgoc_seoultech/"
        target="_blank"
        rel="noopener noreferrer"
      >
        <span>Instagram</span>
        <span className="arrow">→</span>
      </a>

    {/* Recruit */}
      <a
        className="more-tilt recruit"
        href="https://docs.google.com/forms/d/e/1FAIpQLSe2xckWBugjHQWg3ykeipKexfg8y6o2jgoqqqFfvyMqwIVMyQ/viewform?usp=header"
        target="_blank"
        rel="noopener noreferrer"
      >
        <span>Recruit</span>
        <span className="arrow">→</span>
      </a>

        </div>
      </section>
    </div>
  );
};

export default MainPage;
