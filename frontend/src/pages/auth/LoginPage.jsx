import { useMemo, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  signInWithEmailAndPassword,
  reload,
  signOut,
} from "firebase/auth";
import Button from "../../components/Button/Button";
import { auth } from "../../lib/firebase";
import "../../styles/auth.css";

const SCHOOL_DOMAIN = "@g.seoultech.ac.kr";

const LoginPage = () => {
  const navigate = useNavigate();
  const [emailId, setEmailId] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [message, setMessage] = useState("");

  const email = useMemo(
    () => (emailId ? `${emailId}${SCHOOL_DOMAIN}` : ""),
    [emailId]
  );

  const mapError = (msg) => {
    if (!msg) return "로그인 중 오류가 발생했습니다.";
    const lower = msg.toLowerCase();
    if (lower.includes("password")) return "비밀번호가 올바르지 않습니다.";
    if (lower.includes("user-not-found") || lower.includes("email")) return "존재하지 않는 이메일입니다.";
    if (lower.includes("too-many")) return "로그인 시도가 많습니다. 잠시 후 다시 시도해주세요.";
    return msg;
  };

  const handleLogin = async () => {
    setError("");
    setMessage("");
    try {
      if (!emailId) throw new Error("학교 이메일 아이디를 입력해주세요.");
      if (!password) throw new Error("비밀번호를 입력해주세요.");
      setLoading(true);

      // 1) Firebase 로그인
      const credential = await signInWithEmailAndPassword(auth, email, password);
      const user = credential.user;
      await reload(user);

      // 2) 이메일 인증 여부 확인
      if (!user.emailVerified) {
        await signOut(auth);
        throw new Error("이메일 인증이 완료되지 않았습니다. 메일함을 확인해주세요.");
      }

      // 3) ID 토큰 획득 후 백엔드 로그인 검증
      const idToken = await user.getIdToken(true);
      console.log("[login] firebase idToken:", idToken);
      const res = await fetch("/api/auth/signin", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ idToken, email }),
      });

      if (!res.ok) {
        let msg = "로그인에 실패했습니다.";
        try {
          const data = await res.json();
          msg = data?.message || data?.reason || data?.data?.message || msg;
        } catch {
          // ignore parse error
        }
        throw new Error(msg);
      }

      setMessage("로그인에 성공했습니다.");
      navigate("/");
    } catch (err) {
      setError(mapError(err?.message));
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-wrapper">
      <div className="auth-card">
        <h1 className="auth-title">로그인</h1>

        <div className="auth-form">
          {message && <div className="notice success">{message}</div>}
          {error && <div className="notice error">{error}</div>}

          {/* 이메일 */}
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

          {/* 비밀번호 */}
          <div className="form-group">
            <label className="form-label">비밀번호</label>
            <input
              type="password"
              className="form-input"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>

          {/* 비밀번호 찾기 */}
          <Link to="/find-password" className="auth-link align-right">
            비밀번호 찾기
          </Link>

          {/* 로그인 버튼 */}
          <Button variant="primary" onClick={handleLogin} disabled={loading}>
            {loading ? "로그인 중..." : "로그인"}
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
