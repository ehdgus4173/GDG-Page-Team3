import "./NoticeNavigation.css";
function NoticeNavigation({ prev, next, onPrev, onNext }) {
    return (
      <div className="notice-navigation">
        {prev && (
          <div className="nav-item prev" onClick={onPrev}>
            <span>이전글</span>
            <p>{prev.title}</p>
          </div>
        )}
  
        {next && (
          <div className="nav-item next" onClick={onNext}>
            <span>다음글</span>
            <p>{next.title}</p>
          </div>
        )}
      </div>
    );
  }
  
  export default NoticeNavigation;
  