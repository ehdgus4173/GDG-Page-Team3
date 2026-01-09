import { Link } from "react-router-dom";
import Button from "../../components/Button/Button";
import "../../styles/auth.css";

const FindPasswordPage = () => {
  return (
    <div className="auth-wrapper">
      <div className="auth-card">
        <h1 className="auth-title">비밀번호 찾기</h1>

        <div className="auth-form">
          {/* 이메일 */}
          <div className="form-group">
            <label className="form-label">이메일</label>

            <div className="email-row">
              <input
                className="form-input"
                placeholder="학교 이메일 아이디"
              />
              <span className="email-domain">@seoultech.ac.kr</span>
              <Button variant="outline">전송</Button>
            </div>

            <div className="email-row">
              <input className="form-input" placeholder="인증번호" />
              <Button variant="outline">인증</Button>
            </div>
          </div>

          {/* 비밀번호 */}
          <div className="form-group">
            <label className="form-label">비밀번호</label>
            <input type="password" className="form-input" />
            <input type="password" className="form-input" />
          </div>

          {/* 비밀번호 변경 */}
          <Button variant="primary">
            비밀번호 변경
          </Button>

          {/* 로그인으로 */}
          <Link to="/login" className="auth-link">
            로그인으로 돌아가기
          </Link>
        </div>
      </div>
    </div>
  );
};

export default FindPasswordPage;
