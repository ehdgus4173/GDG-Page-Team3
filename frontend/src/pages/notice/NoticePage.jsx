import { useEffect, useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";
import List from "../../components/List/PostList";
import Pagination from "../../components/Pagination/Pagination";
import Button from "../../components/Button/Button";
import { fetchNotices } from "../../services/noticeApi";
import "./NoticePage.css";

function formatDate(iso) {
  if (!iso) return "";
  const d = new Date(iso);
  const yyyy = d.getFullYear();
  const mm = String(d.getMonth() + 1).padStart(2, "0");
  const dd = String(d.getDate()).padStart(2, "0");
  return `${yyyy}.${mm}.${dd}`;
}

function NoticePage() {
  const [page, setPage] = useState(1); // UI: 1-based
  const size = 10;

  const navigate = useNavigate();

  const [notices, setNotices] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  // ⚠️ totalPages는 지금 API 응답에 없으면 임시값 필요
  // 백이 Page 형태로 주면 totalPages를 실제로 연결 가능
  const totalPages = 5;

  const apiPage = useMemo(() => page - 1, [page]); // ✅ 0-based 변환

  useEffect(() => {
    let alive = true;

    const run = async () => {
      setLoading(true);
      setError("");
      try {
        const data = await fetchNotices({ page: apiPage, size });

        // 스웨거 Example Value는 "배열"로 보임
        const mapped = (data || []).map((n) => ({
          id: n.id,
          title: n.title,
          date: formatDate(n.createdAt),
          image: n.thumbnailUrl || "https://placehold.co/120x80",
        }));

        if (alive) setNotices(mapped);
      } catch (e) {
        if (alive) setError(e.message || "공지 목록을 불러오지 못했습니다.");
      } finally {
        if (alive) setLoading(false);
      }
    };

    run();
    return () => {
      alive = false;
    };
  }, [apiPage]);

  return (
    <section className="container notice-page">
      <div className="notice-page-header">
        <h1 className="page-title">공지</h1>
        <Button variant="primary" onClick={() => navigate("/notice/write")}>
          작성하기
        </Button>
      </div>

      <div className="notice-page-divider" />

      {loading && <div style={{ padding: "16px 0" }}>불러오는 중...</div>}
      {error && (
        <div style={{ padding: "16px 0", color: "crimson" }}>{error}</div>
      )}
      {!loading && !error && notices.length === 0 && (
        <div style={{ padding: "16px 0" }}>공지사항이 없습니다.</div>
      )}

      <div className="notice-page-list">
        {notices.map((notice, index) => (
          <List
            key={notice.id}
            index={String(index + 1 + (page - 1) * size).padStart(2, "0")}
            title={notice.title}
            date={notice.date}
            image={notice.image}
            onClick={() => navigate(`/notice/${notice.id}`)}
          />
        ))}
      </div>

      <Pagination
        currentPage={page}
        totalPages={totalPages}
        onPageChange={setPage}
      />
    </section>
  );
}

export default NoticePage;
