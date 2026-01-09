import { useEffect, useMemo, useState } from "react";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import { sendPasswordResetEmail } from "firebase/auth";
import Button from "../../components/Button/Button";
import { auth } from "../../lib/firebase";
import "../../styles/auth.css";

const SCHOOL_DOMAIN = "@g.seoultech.ac.kr";

const FindPasswordPage = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  const [emailId, setEmailId] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const fullEmail = useMemo(
    () => (emailId ? `${emailId}${SCHOOL_DOMAIN}` : ""),
    [emailId]
  );

  // 만약 reset 링크(oobCode)로 들어오면 액션 페이지로 바로 이동
  useEffect(() => {
    const hasOob = searchParams.get("oobCode");
    if (hasOob) {
      navigate(`/auth/action?${searchParams.toString()}`, { replace: true });
    }
  }, [navigate, searchParams]);

  const handleSendMail = async () => {
    setError("");
    setMessage("");
    if (!fullEmail) {
      setError("학교 이메일을 입력해주세요.");
      return;
    }
    try {
      setLoading(true);
      await sendPasswordResetEmail(auth, fullEmail, {
        url: `${window.location.origin}/auth/action`,
        handleCodeInApp: true,
      });
      // 보안상 존재 여부는 노출하지 않고 항상 성공 메시지 표시
      setMessage("재설정 메일을 보냈습니다. 메일함과 스팸함을 확인해주세요.");
    } catch (err) {
      // Firebase 오류라도 동일한 메시지로 응답해 사용자 존재 여부 노출 방지
      setMessage("재설정 메일을 보냈습니다. 메일함과 스팸함을 확인해주세요.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-wrapper">
      <div className="auth-card">
        <h1 className="auth-title">비밀번호 찾기</h1>

        <div className="auth-form">
          {message && <div className="notice success">{message}</div>}
          {error && <div className="notice error">{error}</div>}

          <div className="form-group">
            <label className="form-label">이메일</label>
            <div className="email-row">
              <input
                className="form-input"
                placeholder="학교 이메일 아이디"
                value={emailId}
                onChange={(e) => setEmailId(e.target.value)}
              />
              <span className="email-domain">{SCHOOL_DOMAIN}</span>
            </div>
          </div>

          <div className="form-row" style={{ gap: "12px", marginTop: "8px" }}>
            <Button variant="primary" disabled={loading} onClick={handleSendMail}>
              {loading ? "전송 중..." : "재설정 메일 전송"}
            </Button>
            <Link to="/login" className="auth-link">
              로그인으로 돌아가기
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FindPasswordPage;
