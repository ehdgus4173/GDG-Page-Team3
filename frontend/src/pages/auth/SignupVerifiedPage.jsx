import { useEffect, useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";
import { reload, signInWithEmailAndPassword, signOut } from "firebase/auth";
import Button from "../../components/Button/Button";
import { auth } from "../../lib/firebase";
import "../../styles/auth.css";

const SignupVerifiedPage = () => {
  const navigate = useNavigate();
  const [savedForm, setSavedForm] = useState(null);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    try {
      const raw = sessionStorage.getItem("signup_form");
      if (!raw) return;
      const parsed = JSON.parse(raw);
      setSavedForm(parsed);
      if (parsed?.email) {
        setEmail(parsed.email);
      }
    } catch (e) {
      setError("저장된 가입 정보를 불러오지 못했습니다. 처음부터 다시 시도해주세요.");
    }
  }, []);

  const friendlyMessage = useMemo(
    () => (msg) => {
      if (!msg) return "요청 처리 중 오류가 발생했습니다.";
      const normalized = msg.toLowerCase();
      if (normalized.includes("email already exists") || normalized.includes("이미 존재하는 이메일")) {
        return "이미 가입된 이메일입니다.";
      }
      if (normalized.includes("password")) {
        return "비밀번호가 올바르지 않거나 권한이 없습니다.";
      }
      if (normalized.includes("not verified") || normalized.includes("인증")) {
        return "이메일 인증을 먼저 완료해주세요.";
      }
      return msg;
    },
    []
  );

  const handleConfirm = async () => {
    setError("");
    setMessage("");
    try {
      if (!email) throw new Error("학교 이메일을 입력해주세요.");
      if (!password) throw new Error("비밀번호를 입력해주세요.");
      if (!savedForm) throw new Error("가입 정보를 찾을 수 없습니다. 처음부터 다시 진행해주세요.");

      setLoading(true);

      const credential = await signInWithEmailAndPassword(auth, email, password);
      const user = credential.user;
      await reload(user);
      if (!user.emailVerified) {
        throw new Error("이메일 인증이 아직 완료되지 않았습니다.");
      }

      const idToken = await user.getIdToken(true);

      const payload = {
        idToken,
        email,
        name: savedForm.name,
        grade: Number(savedForm.grade),
        position: savedForm.position,
        part: savedForm.part,
        stdId: savedForm.stdId,
        department: savedForm.department,
      };

      const res = await fetch("/api/auth/signup/confirm", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      });

      if (!res.ok) {
        let msg = "가입 확정 중 오류가 발생했습니다.";
        try {
          const data = await res.json();
          msg = data?.message || data?.reason || data?.data?.message || msg;
        } catch (e) {
          // ignore json parse error
        }
        throw new Error(msg);
      }

      setMessage("가입이 완료되었습니다. 이제 로그인할 수 있습니다.");
      sessionStorage.removeItem("signup_form");
      await signOut(auth);
      navigate("/");
    } catch (err) {
      setError(friendlyMessage(err?.message));
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-wrapper">
      <div className="auth-card">
        <h1 className="auth-title">이메일 인증 완료</h1>

        <div className="auth-form">
          {message && <div className="notice success">{message}</div>}
          {error && <div className="notice error">{error}</div>}

          <p className="helper-text" style={{ marginBottom: "12px" }}>
            이메일에서 인증을 완료했다면, 아래 정보를 확인하고 비밀번호를 입력해 가입을 마무리해주세요.
          </p>

          <div className="form-group">
            <label className="form-label">이메일</label>
            <input
              className="form-input"
              placeholder="example@seoultech.ac.kr"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>

          <div className="form-group">
            <label className="form-label">비밀번호</label>
            <input
              type="password"
              className="form-input"
              placeholder="비밀번호"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>

          {savedForm && (
            <div className="form-group">
              <label className="form-label">가입 정보 확인</label>
              <div className="helper-text" style={{ lineHeight: 1.6 }}>
                {savedForm.name} / {savedForm.grade}기 / {savedForm.position} / {savedForm.part}
                <br />
                학번: {savedForm.stdId} / 학과: {savedForm.department}
              </div>
            </div>
          )}

          {!savedForm && (
            <div className="notice error">
              저장된 가입 정보를 찾을 수 없습니다. 처음부터 다시 진행해주세요.
            </div>
          )}

          <div className="form-row" style={{ gap: "12px", marginTop: "12px" }}>
            <Button variant="outline-gray" disabled={loading} onClick={() => navigate("/signup")}>
              처음으로 돌아가기
            </Button>
            <Button variant="primary" disabled={loading || !savedForm} onClick={handleConfirm}>
              {loading ? "가입 처리 중..." : "회원가입 완료"}
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SignupVerifiedPage;