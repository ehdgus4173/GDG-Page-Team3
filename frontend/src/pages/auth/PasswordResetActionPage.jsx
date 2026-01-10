import { useEffect, useState } from "react";
import { useNavigate, useSearchParams, Link } from "react-router-dom";
import {
  verifyPasswordResetCode,
  confirmPasswordReset,
  applyActionCode,
} from "firebase/auth";
import Button from "../../components/Button/Button";
import { auth } from "../../lib/firebase";
import "../../styles/auth.css";

const PasswordResetActionPage = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  // 🔑 URL 파라미터를 그대로 신뢰 (state ❌)
  const mode = searchParams.get("mode"); // verifyEmail | resetPassword
  const oobCode = searchParams.get("oobCode");

  const [verifiedEmail, setVerifiedEmail] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const handleAction = async () => {
      if (!oobCode) {
        setError("인증 코드가 없습니다.");
        setLoading(false);
        return;
      }

      try {
        // ✅ 이메일 인증 링크
        if (mode === "verifyEmail") {
          await applyActionCode(auth, oobCode);
          navigate("/signup/email-verified", { replace: true });
          return;
        }

        // ✅ 비밀번호 재설정 링크
        if (mode === "resetPassword") {
          const email = await verifyPasswordResetCode(auth, oobCode);
          setVerifiedEmail(email);
          setMessage("이메일 인증이 확인되었습니다. 새 비밀번호를 입력해주세요.");
          setLoading(false);
          return;
        }

        // ❌ 알 수 없는 mode
        setError("잘못된 접근입니다.");
      } catch (e) {
        setError("인증 링크가 만료되었거나 이미 처리되었습니다.");
      } finally {
        setLoading(false);
      }
    };

    handleAction();
  }, [mode, oobCode, navigate]);

  // 🔒 초기 로딩 중 깜빡임 방지
  if (loading) return null;

  const validatePasswords = () => {
    const hasSpecial = /[!@#$%^&*()\-_=+\[\]{};:'",.<>/?]/.test(newPassword);
    if (!newPassword || newPassword.length < 8 || !hasSpecial) {
      throw new Error("비밀번호는 8자 이상이며 특수문자를 포함해야 합니다.");
    }
    if (newPassword !== confirmPassword) {
      throw new Error("비밀번호가 일치하지 않습니다.");
    }
  };

  const handleResetPassword = async () => {
    setError("");
    setMessage("");

    try {
      validatePasswords();
      setLoading(true);

      await confirmPasswordReset(auth, oobCode, newPassword);

      setMessage("비밀번호가 변경되었습니다. 새 비밀번호로 로그인해주세요.");
      setTimeout(() => navigate("/login"), 1200);
    } catch (err) {
      setError(err.message || "비밀번호 변경 중 오류가 발생했습니다.");
    } finally {
      setLoading(false);
    }
  };

  // 🔥 verifyEmail 은 여기서 UI를 보여주지 않음 (즉시 redirect)
  if (mode === "verifyEmail") return null;

  return (
    <div className="auth-wrapper">
      <div className="auth-card">
        <h1 className="auth-title">비밀번호 재설정</h1>

        {message && <div className="notice success">{message}</div>}
        {error && <div className="notice error">{error}</div>}

        {!message && (
          <>
            <div className="form-group">
              <label className="form-label">인증된 이메일</label>
              <input className="form-input" value={verifiedEmail} readOnly />
            </div>

            <div className="form-group">
              <label className="form-label">새 비밀번호</label>
              <input
                type="password"
                className="form-input"
                placeholder="새 비밀번호"
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
              />
              <input
                type="password"
                className="form-input"
                placeholder="비밀번호 확인"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
              />
              <div className="helper-text">
                비밀번호는 8자 이상이며 특수문자를 포함해야 합니다.
              </div>
            </div>

            <div className="form-row" style={{ gap: "12px", marginTop: "12px" }}>
              <Button variant="primary" disabled={loading} onClick={handleResetPassword}>
                {loading ? "변경 중..." : "비밀번호 변경"}
              </Button>
              <Link to="/login" className="auth-link">
                로그인으로 돌아가기
              </Link>
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default PasswordResetActionPage;
