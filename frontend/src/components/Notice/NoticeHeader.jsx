import "./NoticeHeader.css";

function NoticeHeader({ title, createdAt, viewCount }) {
  return (
    <header className="notice-header">
      <h1 className="notice-title">{title}</h1>

      <div className="notice-meta caption">
        <span>{createdAt}</span>
        <span>조회 {viewCount}</span>
      </div>
    </header>
  );
}

export default NoticeHeader;
