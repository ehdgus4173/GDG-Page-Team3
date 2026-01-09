import { useState } from "react";
import { Link } from "react-router-dom";
import Button from "../../components/Button/Button";
import "../../styles/auth.css";

const LoginPage = () => {
  const [isError, setIsError] = useState(false);

  const handleLogin = () => {
    setIsError(true);
  };

  return (
    <div className="auth-wrapper">
      <div className="auth-card">
        <h1 className="auth-title">로그인</h1>

        <div className="auth-form">
          {/* 이메일 */}
          <div className="form-group">
            <label className="form-label">이메일</label>
            <div className="email-row">
              <input
                className="form-input"
                placeholder="학교 이메일 아이디"
                onChange={() => setIsError(false)}
              />
              <span className="email-domain">@g.seoultech.ac.kr</span>
            </div>
          </div>

          {/* 비밀번호 */}
          <div className="form-group">
            <label className="form-label">비밀번호</label>
            <input
              type="password"
              className={`form-input ${isError ? "error" : ""}`}
              onChange={() => setIsError(false)}
            />

            {isError && (
              <span className="form-error">
                아이디 또는 비밀번호가 틀렸습니다
              </span>
            )}
          </div>

          {/* 비밀번호 찾기 */}
          <Link to="/find-password" className="auth-link align-right">
            비밀번호 찾기
          </Link>

          {/* 로그인 버튼 */}
          <Button variant="primary" onClick={handleLogin}>
            로그인
          </Button>

          {/* 회원가입 */}
          <Link to="/signup" className="auth-link">
            회원가입
          </Link>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
