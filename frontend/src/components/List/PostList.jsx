import "./List.css";

function PostList({ index, title, date, image, onClick }) {
  return (
    <article
      className="post-list-item"
      onClick={onClick}
      role="button"
      tabIndex={0}
      onKeyDown={(e) => {
        if (e.key === "Enter" || e.key === " ") onClick?.();
      }}
    >
      <div className="post-list-left">
        <span className="post-list-index">{index}</span>
        <h3 className="post-list-title">{title}</h3>
        <span className="post-list-date">{date}</span>
      </div>

      {image && (
        <div className="post-list-image">
          <img src={image} alt="공지 이미지" />
        </div>
      )}
    </article>
  );
}

export default PostList;
