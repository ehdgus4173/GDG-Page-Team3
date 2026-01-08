import { useState } from "react";
import { useNavigate } from "react-router-dom";
import List from "../../components/List/PostList";
import Pagination from "../../components/Pagination/Pagination";
import Button from "../../components/Button/Button";
import "./NoticePage.css";

function NoticePage() {
  const [page, setPage] = useState(1);
  const navigate = useNavigate();

  const notices = Array.from({ length: 10 }).map((_, idx) => ({
    id: idx + 1,
    title: "공지사항 더미텍스트공지사항 더미텍스트",
    date: "2025.12.31",
    image: "https://placeholder.co/120x80",
  }));

  return (
    <section className="container notice-page">
      <div className="notice-page-header">
        <h1 className="page-title">공지</h1>
        <Button variant="primary">작성하기</Button>
      </div>

      <div className="notice-page-divider" />

      <div className="notice-page-list">
        {notices.map((notice, index) => (
          <List
            key={notice.id}
            index={String(index + 1).padStart(2, "0")}
            title={notice.title}
            date={notice.date}
            image={notice.image}
            onClick={() => navigate(`/notice/${notice.id}`)}
          />
        ))}
      </div>

      {/* Pagination */}
      <Pagination
        currentPage={page}
        totalPages={5}
        onPageChange={setPage}
      />
    </section>
  );
}

export default NoticePage;
