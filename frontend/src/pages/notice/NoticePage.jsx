import { useState } from "react";
import List from "../../components/List/PostList";
import Pagination from "../../components/Pagination/Pagination";
import Button from "../../components/Button/Button";
import "./NoticePage.css";

function NoticePage() {
  const [page, setPage] = useState(1);

  // 더미 데이터 (UI 확인용)
  const notices = Array.from({ length: 10 }).map((_, idx) => ({
    id: idx,
    title: "공지사항 더미텍스트공지사항 더미텍스트",
    date: "2025.12.31",
    image: "https://placeholder.co/120x80",
  }));

  return (
    <section className="container notice-page">
      {/* Header */}
      <div className="notice-header">
        <h1 className="page-title">공지</h1>

        {/* 👇 기존 Button 컴포넌트 활용 */}
        <Button variant="primary">작성하기</Button>
      </div>

      {/* Divider */}
      <div className="notice-divider" />

      {/* List */}
      <div className="notice-list">
        {notices.map((notice, index) => (
          <List
            key={notice.id}
            index={String(index + 1).padStart(2, "0")}
            title={notice.title}
            date={notice.date}
            image={notice.image}
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
