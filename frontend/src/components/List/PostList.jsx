import "./List.css";

function PostList({ index, title, date, image }) {
    return (
      <article className="post-list-item">
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
