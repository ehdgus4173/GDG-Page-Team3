import { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import MemberCard from "../../components/Members/MemberCard";
import MemberLinksCard from "../../components/Members/MemberLinksCard";
import "../../styles/layout.css";
import "./MyPage.css";

import { fetchMyProfile } from "../../services/profileApi";
import { mapMyProfileToCard } from "../../services/memberMapper";

function MyPage() {
  const navigate = useNavigate();

  const [me, setMe] = useState(null);
  const [loading, setLoading] = useState(false);
  const [errorMsg, setErrorMsg] = useState("");

  useEffect(() => {
    let alive = true;

    async function load() {
      setLoading(true);
      setErrorMsg("");
      try {
        const data = await fetchMyProfile();
        const mapped = mapMyProfileToCard(data);
        if (!alive) return;
        setMe(mapped);
      } catch (e) {
        if (!alive) return;
        setErrorMsg(e.message || "마이페이지 정보를 불러오지 못했습니다.");
      } finally {
        if (alive) setLoading(false);
      }
    }

    load();
    return () => { alive = false; };
  }, []);

  return (
    <section className="container">
      <div className="mypage-header">
        <h1 className="page-title">마이페이지</h1>

        <div className="mypage-actions">
          <Link className="mypage-btn outline" to="/find-password">
            비밀번호 수정
          </Link>

          <button
            className="mypage-btn solid"
            onClick={() => navigate("/mypage/edit")}
          >
            편집하기
          </button>
        </div>
      </div>

      {loading && <div style={{ padding: 20 }}>불러오는 중...</div>}
      {errorMsg && <div style={{ padding: 20 }}>{errorMsg}</div>}

      {me && (
        <div className="mypage-content">
          <div className="mypage-left">
            <MemberCard member={me} clickable={false} />
          </div>

          <div className="mypage-right">
            <MemberLinksCard links={me.links} />
          </div>
        </div>
      )}
    </section>
  );
}

export default MyPage;
