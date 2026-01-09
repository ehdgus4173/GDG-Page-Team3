import "./NoticeImage.css";

function NoticeImage({ src, alt }) {
  return (
    <div className="notice-image-wrap">
      <img className="notice-image" src={src} alt={alt} />
    </div>
  );
}

export default NoticeImage;
