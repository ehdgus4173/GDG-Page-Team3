import { useParams, useNavigate } from "react-router-dom";

import NoticeHeader from "../../components/Notice/NoticeHeader";
import NoticeContent from "../../components/Notice/NoticeContent";
import NoticeImage from "../../components/Notice/NoticeImage";
import NoticeAttachments from "../../components/Notice/NoticeAttachments";
import NoticeNavigation from "../../components/Notice/NoticeNavigation";
import NoticeBackButton from "../../components/Notice/NoticeBackButton";

import "./NoticeDetailPage.css";

function NoticeDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();

  const notice = {
    id,
    title: "공지사항 더미텍스트공지사항 더미텍스트",
    createdAt: "2025.12.31 04:02",
    viewCount: 32,
    imageUrl: "https://placehold.co/900x520", // ✅ 대표 이미지
    content: `
안녕하세요, 네이버웍스입니다.

네이버웍스 코어 드라이브 서비스 및
네이버웍스 드라이브의 공동편집 기능 개선 작업이
12/18(목) 예정되어 있습니다.
    `,
    attachments: [
      {
        name: "공지사항 더미텍스트공지사항 더미텍스트.pdf",
        url: "https://example.com/file1.pdf",
      },
      {
        name: "공지사항 더미텍스트공지사항 더미텍스트.pdf",
        url: "https://example.com/file2.pdf",
      },
    ],
    prev: { id: 1, title: "이전 공지사항 더미텍스트" },
    next: { id: 3, title: "다음 공지사항 더미텍스트" },
  };

  return (
    <section className="container notice-detail-page">
      <NoticeHeader
        title={notice.title}
        createdAt={notice.createdAt}
        viewCount={notice.viewCount}
      />

      <div className="notice-detail-divider" />

      {/* ✅ 대표 이미지 */}
      {notice.imageUrl && <NoticeImage src={notice.imageUrl} alt={notice.title} />}

      <NoticeContent content={notice.content} />


      {/* ✅ 첨부파일 */}
      {notice.attachments?.length > 0 && (
        <NoticeAttachments files={notice.attachments} />
      )}
      <div className="notice-detail-divider" />

      <NoticeNavigation
        prev={notice.prev}
        next={notice.next}
        onPrev={() => navigate(`/notice/${notice.prev.id}`)}
        onNext={() => navigate(`/notice/${notice.next.id}`)}
      />

      <NoticeBackButton onClick={() => navigate("/notice")} />
    </section>
  );
}

export default NoticeDetailPage;
