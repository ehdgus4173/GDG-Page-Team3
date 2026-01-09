import { useState } from "react";
import Button from "../../components/Button/Button";
import "../../styles/auth.css";

const SignupPage = () => {
  const [rankOpen, setRankOpen] = useState(false);
  const [partOpen, setPartOpen] = useState(false);

  const [rank, setRank] = useState("");
  const [part, setPart] = useState("");

  const ranks = ["리드", "코어", "멤버"];
  const parts = ["AI", "Front-end", "Back-end", "App", "Design"];

  return (
    <div className="auth-wrapper">
      <div className="auth-card">
        <h1 className="auth-title">회원가입</h1>

        <div className="auth-form">
          {/* 이름 */}
          <div className="form-group">
            <label className="form-label">이름</label>
            <input className="form-input" placeholder="홍길동" />
          </div>

          {/* 기수 + 직급 */}
          <div className="form-row">
            <div className="form-group">
              <label className="form-label">기수</label>
              <input className="form-input" placeholder="기수" />
            </div>

            <div className="form-group dropdown">
              <label className="form-label">직급</label>
              <div
                className="dropdown-trigger"
                onClick={() => {
                  setRankOpen(!rankOpen);
                  setPartOpen(false);
                }}
              >
                <span>{rank || "직급 선택"}</span>
                <span className="arrow">▼</span>
              </div>

              {rankOpen && (
                <ul className="dropdown-menu">
                  {ranks.map((r) => (
                    <li
                      key={r}
                      onClick={() => {
                        setRank(r);
                        setRankOpen(false);
                      }}
                    >
                      {r}
                    </li>
                  ))}
                </ul>
              )}
            </div>
          </div>

          {/* 파트 */}
          <div className="form-group dropdown">
            <label className="form-label">파트</label>
            <div
              className="dropdown-trigger"
              onClick={() => {
                setPartOpen(!partOpen);
                setRankOpen(false);
              }}
            >
              <span>{part || "파트 선택"}</span>
              <span className="arrow">▼</span>
            </div>

            {partOpen && (
              <ul className="dropdown-menu">
                {parts.map((p) => (
                  <li
                    key={p}
                    onClick={() => {
                      setPart(p);
                      setPartOpen(false);
                    }}
                  >
                    {p}
                  </li>
                ))}
              </ul>
            )}
          </div>

          {/* 학번 */}
          <div className="form-group">
            <label className="form-label">학번</label>
            <input className="form-input" placeholder="21204482" />
          </div>

          {/* 학과 */}
          <div className="form-group">
            <label className="form-label">학과</label>
            <input className="form-input" placeholder="지디지학과" />
          </div>

          {/* 이메일 */}
          <div className="form-group">
            <label className="form-label">이메일</label>

            <div className="email-row">
              <input
                className="form-input"
                placeholder="학교 이메일 아이디"
              />
              <span className="email-domain">@seoultech.ac.kr</span>
              <Button variant="outline-gray" size="sm">
               전송
              </Button>

            </div>

            <div className="email-row">
              <input className="form-input" placeholder="인증번호" />
              <Button variant="outline-gray" size="sm">
                인증
              </Button>

            </div>
          </div>

          {/* 비밀번호 */}
          <div className="form-group">
            <label className="form-label">비밀번호</label>
            <input type="password" className="form-input" />
            <input type="password" className="form-input" />
          </div>

          {/* 회원가입 버튼 */}
          <Button variant="primary">
            회원가입
          </Button>
        </div>
      </div>
    </div>
  );
};

export default SignupPage;
