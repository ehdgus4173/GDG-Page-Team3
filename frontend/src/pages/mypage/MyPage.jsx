import { useMemo } from "react";
import { useNavigate, Link } from "react-router-dom";
import MemberCard from "../../components/Members/MemberCard";
import MemberLinksCard from "../../components/Members/MemberLinksCard";
import "../../styles/layout.css";
import "./MyPage.css";

function MyPage() {
  const navigate = useNavigate();

  // ✅ 더미 유저 데이터 (나중에 로그인/백엔드 연결되면 여기만 교체)
  const me = useMemo(
    () => ({
      id: 1,
      name: "박진아",
      major: "문화예술학과",
      desc: "한줄소개 소개 인사를 어떻게 해야할까",
      imageUrl: "https://placehold.co/600x600",
      tags: ["코어", "Front-end"],
      skills: ["Figma", "React", "UI"],
      links: [
        { label: "Github", url: "https://github.com" },
        { label: "Github", url: "https://github.com" },
        { label: "Github", url: "https://github.com" },
        { label: "Github", url: "https://github.com" },
      ],
    }),
    []
  );

  return (
    <section className="container">
      <div className="mypage-header">
        <h1 className="page-title">마이페이지</h1>

        <div className="mypage-actions">
          {/* ✅ 비밀번호 수정: 이미 구현된 페이지로 연결 */}
          <Link className="mypage-btn outline" to="/find-password">
            비밀번호 수정
          </Link>

          {/* ✅ 마이페이지 편집 */}
          <button
            className="mypage-btn solid"
            onClick={() => navigate("/mypage/edit")}
          >
            편집하기
          </button>
        </div>
      </div>

      <div className="mypage-content">
        <div className="mypage-left">
          {/* ✅ 모달에서 쓰던 카드 그대로 재사용 / 클릭 비활성 */}
          <MemberCard member={me} clickable={false} />
        </div>

        <div className="mypage-right">
          {/* ✅ 모달에서 쓰던 링크 카드 그대로 재사용 */}
          <MemberLinksCard links={me.links} />
        </div>
      </div>
    </section>
  );
}

export default MyPage;
