import "./NoticeContent.css";

function NoticeContent({ content }) {
  return (
    <article className="notice-content body-long-1">
      {content}
    </article>
  );
}

export default NoticeContent;
