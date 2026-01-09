import "./NoticeBackButton.css";
function NoticeBackButton({ onClick }) {
    return (
      <div className="notice-back">
        <button onClick={onClick}>목록으로</button>
      </div>
    );
  }
  
  export default NoticeBackButton;
  