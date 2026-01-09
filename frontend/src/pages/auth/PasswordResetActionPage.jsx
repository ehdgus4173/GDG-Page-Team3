import { useEffect, useState } from "react";
import { useNavigate, useSearchParams, Link } from "react-router-dom";
import { verifyPasswordResetCode, confirmPasswordReset, applyActionCode } from "firebase/auth";
import Button from "../../components/Button/Button";
import { auth } from "../../lib/firebase";
import "../../styles/auth.css";

const PasswordResetActionPage = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const oobCode = searchParams.get("oobCode");
  const modeParam = searchParams.get("mode"); // verifyEmail | resetPassword | others

  const [mode, setMode] = useState("");
  const [verifiedEmail, setVerifiedEmail] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    // mode 파라미터가 없거나 알 수 없으면 기본적으로 resetPassword로 처리
    const normalized = (modeParam || "").trim();
    if (normalized === "verifyEmail" || normalized === "resetPassword") {
      setMode(normalized);
    } else {
      setMode("resetPassword");
    }
  }, [modeParam]);

  useEffect(() => {
    const handleAction = async () => {
      if (!oobCode) {
        setError("인증 코드가 없습니다. 이메일에서 링크를 다시 확인해주세요.");
        return;
      }
      try {
        setLoading(true);
        if (mode === "resetPassword") {
          const email = await verifyPasswordResetCode(auth, oobCode);
          setVerifiedEmail(email);
          setMessage("이메일 인증이 확인되었습니다. 새 비밀번호를 입력해주세요.");
        } else if (mode === "verifyEmail") {
          await applyActionCode(auth, oobCode);
          setMessage("이메일 인증이 완료되었습니다. 가입을 진행하던 창으로 돌아가주세요.");
          setTimeout(() => navigate("/signup/email-verified"), 1200);
        } else {
          // 알 수 없는 모드는 기본적으로 resetPassword로 취급
          const email = await verifyPasswordResetCode(auth, oobCode);
          setVerifiedEmail(email);
          setMessage("이메일 인증이 확인되었습니다. 새 비밀번호를 입력해주세요.");
          setMode("resetPassword");
        }
      } catch (err) {
        setError("인증 코드가 유효하지 않거나 만료되었습니다. 다시 요청해주세요.");
      } finally {
        setLoading(false);
      }
    };
    handleAction();
  }, [mode, oobCode, navigate]);

  const validatePasswords = () => {
    const hasSpecial = /[!@#$%^&*()\-_=+\[\]{};:'",.<>/?]/.test(newPassword || "");
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
      if (!oobCode) throw new Error("인증 코드가 없습니다.");
      if (!verifiedEmail) throw new Error("인증된 이메일을 확인할 수 없습니다.");
      validatePasswords();
      setLoading(true);

      // Firebase에 새 비밀번호 적용 (oobCode 1회성)
      await confirmPasswordReset(auth, oobCode, newPassword);

      // 서버(DB) 비밀번호도 동기화
      const res = await fetch("/api/auth/password/reset", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email: verifiedEmail, newPassword }),
      });
      if (!res.ok) {
        const data = await res.json().catch(() => ({}));
        throw new Error(data?.message || data?.reason || "비밀번호 변경 요청에 실패했습니다.");
      }

      setMessage("비밀번호가 변경되었습니다. 새 비밀번호로 로그인해주세요.");
      setTimeout(() => navigate("/login"), 1200);
    } catch (err) {
      setError(err?.message || "비밀번호 변경 중 오류가 발생했습니다.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-wrapper">
      <div className="auth-card">
        <h1 className="auth-title">
          {mode === "verifyEmail" ? "이메일 인증" : "비밀번호 재설정"}
        </h1>

        <div className="auth-form">
          {message && <div className="notice success">{message}</div>}
          {error && <div className="notice error">{error}</div>}

          {mode === "verifyEmail" ? (
            <>
              <p className="helper-text" style={{ marginBottom: "12px" }}>
                이메일 인증이 완료되었습니다. 가입을 진행하던 창으로 돌아가 주세요.
              </p>
            </>
          ) : (
            <>
              <div className="form-group">
                <label className="form-label">인증된 이메일</label>
                <input
                  className="form-input"
                  value={verifiedEmail || ""}
                  readOnly
                  placeholder="인증된 이메일을 불러오는 중..."
                />
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
                  placeholder="새 비밀번호 확인"
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
    </div>
  );
};

export default PasswordResetActionPage;
