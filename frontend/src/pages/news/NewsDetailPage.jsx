import { useParams, useNavigate } from "react-router-dom";

import NoticeHeader from "../../components/Notice/NoticeHeader";
import NoticeContent from "../../components/Notice/NoticeContent";
import NoticeImage from "../../components/Notice/NoticeImage";
import NoticeAttachments from "../../components/Notice/NoticeAttachments";
import NoticeNavigation from "../../components/Notice/NoticeNavigation";
import NoticeBackButton from "../../components/Notice/NoticeBackButton";

import "./NewsDetailPage.css";

function NewsDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();

  // 임시 더미 데이터 (나중에 API 연동)
  const news = {
    id,
    title: "소식 더미텍스트 소식 더미텍스트",
    createdAt: "2025.12.31 04:02",
    viewCount: 32,
    imageUrl: "https://placehold.co/900x520",
    content: `
안녕하세요, GDGoC입니다.

소식 게시글 더미 내용입니다.
줄바꿈은 그대로 유지됩니다.
    `,
    attachments: [
      { name: "소식 첨부파일 더미.pdf", url: "https://example.com/news1.pdf" },
      { name: "소식 첨부파일 더미.pdf", url: "https://example.com/news2.pdf" },
    ],
    prev: { id: 1, title: "이전 소식 더미텍스트" },
    next: { id: 3, title: "다음 소식 더미텍스트" },
  };

  return (
    <section className="container news-detail-page">
      <NoticeHeader
        title={news.title}
        createdAt={news.createdAt}
        viewCount={news.viewCount}
      />

      {/* ✅ 선은 한 군데만 쓰는 걸 추천 (이중선 방지) */}
      <div className="news-detail-divider" />

      {news.imageUrl && <NoticeImage src={news.imageUrl} alt={news.title} />}

      <NoticeContent content={news.content} />

      {news.attachments?.length > 0 && <NoticeAttachments files={news.attachments} />}

      {/* 아래 영역(이전/다음) 위 구분선이 필요하면 여기서만 추가 */}
      <div className="news-detail-divider" />

      <NoticeNavigation
        prev={news.prev}
        next={news.next}
        onPrev={() => navigate(`/news/${news.prev.id}`)}
        onNext={() => navigate(`/news/${news.next.id}`)}
      />

      <NoticeBackButton onClick={() => navigate("/news")} />
    </section>
  );
}

export default NewsDetailPage;
