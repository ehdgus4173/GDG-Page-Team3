import { useState } from "react";
import List from "../../components/List/PostList";
import Pagination from "../../components/Pagination/Pagination";
import MemberFilter from "../../components/Members/MemberFilter";
import Button from "../../components/Button/Button";
import "./NewsPage.css";

function NewsPage() {
  const [page, setPage] = useState(1);

  // 더미 데이터
  const news = Array.from({ length: 10 }).map((_, idx) => ({
    id: idx,
    title: "공지사항 더미텍스트공지사항 더미텍스트",
    date: "2025.12.31",
    image: "https://placeholder.co/120x80",
    generation: idx % 2 === 0 ? "1기" : "2기"
  }));

  // 필터 정의
  const filters = [
    {
      key: "generation",
      options: ["전체", "1기", "2기", "3기", "4기", "5기"]
    }
  ];

  const [selected, setSelected] = useState({
    generation: "전체"
  });

  // ✅ 필터링 로직
  const filteredNews =
    selected.generation === "전체"
      ? news
      : news.filter(
          (item) => item.generation === selected.generation
        );

  return (
    <section className="container news-page">
      {/* Header */}
      <div className="news-header">
        <h1 className="page-title">소식</h1>
        <Button variant="primary">작성하기</Button>
      </div>

      {/* Generation Filter */}
      <MemberFilter
        filters={filters}
        selected={selected}
        onChange={(key, value) =>
          setSelected({ [key]: value })
        }
      />

      <div className="news-divider" />

      {/* List */}
      <div className="news-list">
        {filteredNews.map((item, index) => (
          <List
            key={item.id}
            index={String(index + 1).padStart(2, "0")}
            title={item.title}
            date={item.date}
            image={item.image}
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

export default NewsPage;
