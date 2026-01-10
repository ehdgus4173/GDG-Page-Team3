import { useCallback, useEffect, useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  createUserWithEmailAndPassword,
  sendEmailVerification,
  signInWithEmailAndPassword,
  signOut,
} from "firebase/auth";
import Button from "../../components/Button/Button";
import { auth } from "../../lib/firebase";
import "../../styles/auth.css";

const ranks = ["리드", "코어", "멤버"];
const parts = ["AI", "Front-end", "Back-end", "App", "Design"];
const SCHOOL_DOMAIN = "@g.seoultech.ac.kr";

const SignupPage = () => {
  const navigate = useNavigate();
  const [rankOpen, setRankOpen] = useState(false);
  const [partOpen, setPartOpen] = useState(false);

  const [form, setForm] = useState({
    name: "",
    grade: "",
    position: "",
    part: "",
    stdId: "",
    department: "",
    emailId: "",
    password: "",
    passwordConfirm: "",
  });

  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [resendCooldown, setResendCooldown] = useState(0);
  const [mailSent, setMailSent] = useState(false);
  useEffect(() => {
    if (resendCooldown <= 0) return;
    const timer = setInterval(() => {
      setResendCooldown((prev) => (prev > 0 ? prev - 1 : 0));
    }, 1000);
    return () => clearInterval(timer);
  }, [resendCooldown]);

  const mapFriendlyMessage = (msg) => {
    if (!msg) return "요청 처리 중 오류가 발생했습니다.";
    const normalized = msg.toLowerCase();
    if (normalized.includes("email already exists") || normalized.includes("이미 존재하는 이메일")) {
      return "이미 가입된 이메일입니다.";
    }
    if (normalized.includes("invalid email") || normalized.includes("이메일 형식")) {
      return "이메일 형식이 올바르지 않습니다.";
    }
    if (normalized.includes("domain")) {
      return "학교 이메일(@seoultech.ac.kr)만 가입할 수 있습니다.";
    }
    if (normalized.includes("password")) {
      return "비밀번호는 8자 이상, 특수문자를 포함해야 합니다.";
    }
    if (normalized.includes("not verified") || normalized.includes("인증이 아직")) {
      return "이메일 인증을 완료한 후 다시 시도해주세요.";
    }
    if (normalized.includes("firebase token")) {
      return "로그인을 다시 시도한 뒤 진행해주세요.";
    }
    return msg;
  };

  const email = useMemo(
    () => (form.emailId ? `${form.emailId}${SCHOOL_DOMAIN}` : ""),
    [form.emailId]
  );

  const handleChange = useCallback((key) => (e) => {
    setForm((prev) => ({ ...prev, [key]: e.target.value }));
  }, []);

  const handleRankSelect = (value) => {
    setForm((prev) => ({ ...prev, position: value }));
    setRankOpen(false);
  };

  const handlePartSelect = (value) => {
    setForm((prev) => ({ ...prev, part: value }));
    setPartOpen(false);
  };

  const validatePasswords = () => {
    const hasSpecial = /[!@#$%^&*()\-_=+\[\]{};:'",.<>/?]/.test(form.password || "");
    if (!form.password || form.password.length < 8 || !hasSpecial) {
      throw new Error("비밀번호는 8자 이상, 특수문자를 포함해야 합니다.");
    }
    if (form.password !== form.passwordConfirm) {
      throw new Error("비밀번호가 일치하지 않습니다.");
    }
  };

  const handleSignupAndSendEmail = async () => {
    setError("");
    setMessage("");
    setMailSent(false);
    try {
      if (!email) throw new Error("학교 이메일을 입력해주세요.");
      validatePasswords();
      if (!form.name || !form.grade || !form.position || !form.part || !form.stdId || !form.department) {
        throw new Error("필수 정보를 모두 입력해주세요.");
      }
      setLoading(true);
      // 1) Firebase 사용자 생성
      const userCredential = await createUserWithEmailAndPassword(
        auth,
        email,
        form.password
      );
      // 2) 인증 메일 발송 (인증 완료 후 /signup/verified로 이동)
      const verificationUrl = `${window.location.origin}/signup/email-verified`;
      await sendEmailVerification(userCredential.user, {
        url: verificationUrl,
        handleCodeInApp: false,
      });
      await signOut(auth);
      // 새로고침/탭 이동을 대비해 가입 정보를 저장
      const saved = JSON.stringify({
        ...form,
        email,
        savedAt: Date.now(),
      });
      sessionStorage.setItem("signup_form", saved);
      localStorage.setItem("signup_form", saved);
      setMessage("인증 메일을 보냈습니다. 메일을 확인하고 인증을 완료해주세요.");
      setResendCooldown(60);
      setMailSent(true);
    } catch (err) {
      setError(mapFriendlyMessage(err.message) || "회원가입 중 오류가 발생했습니다.");
    } finally {
      setLoading(false);
    }
  };


  const handleResendEmail = async () => {
    setError("");
    setMessage("");
    try {
      if (!email) throw new Error("학교 이메일을 입력해주세요.");
      validatePasswords();
      if (resendCooldown > 0) {
        throw new Error(`재전송은 ${resendCooldown}초 후 가능합니다.`);
      }
      setLoading(true);
      const credential = await signInWithEmailAndPassword(auth, email, form.password);
      await sendEmailVerification(credential.user);
      await signOut(auth);
      setMessage("인증 메일을 재전송했습니다. 메일함과 스팸함을 확인해주세요.");
      setResendCooldown(60);
      setMailSent(true);
    } catch (err) {
      setError(mapFriendlyMessage(err.message) || "재전송 중 오류가 발생했습니다.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-wrapper">
      <div className="auth-card">
        <h1 className="auth-title">회원가입</h1>

        <div className="auth-form">
          {message && <div className="notice success">{message}</div>}
          {error && <div className="notice error">{error}</div>}

          {/* 이름 */}
          <div className="form-group">
            <label className="form-label">이름</label>
            <input
              className="form-input"
              placeholder="홍길동"
              value={form.name}
              onChange={handleChange("name")}
            />
          </div>

          {/* 기수 + 직급 */}
          <div className="form-row">
            <div className="form-group">
              <label className="form-label">기수</label>
              <input
                className="form-input"
                placeholder="기수"
                value={form.grade}
                onChange={handleChange("grade")}
              />
            </div>

            <div className={`form-group dropdown ${rankOpen ? "open" : ""}`}>
              <label className="form-label">직급</label>
              <div
                className="dropdown-trigger"
                onClick={() => {
                  setRankOpen(!rankOpen);
                  setPartOpen(false);
                }}
              >
                <span>{form.position || "직급 선택"}</span>
                <span className="arrow">▼</span>
              </div>

              {rankOpen && (
                <ul className="dropdown-menu">
                  {ranks.map((r) => (
                    <li
                      key={r}
                      onClick={() => handleRankSelect(r)}
                    >
                      {r}
                    </li>
                  ))}
                </ul>
              )}
            </div>
          </div>

          {/* 파트 */}
          <div className={`form-group dropdown ${partOpen ? "open" : ""}`}>
            <label className="form-label">파트</label>
            <div
              className="dropdown-trigger"
              onClick={() => {
                setPartOpen(!partOpen);
                setRankOpen(false);
              }}
            >
              <span>{form.part || "파트 선택"}</span>
              <span className="arrow">▼</span>
            </div>

            {partOpen && (
              <ul className="dropdown-menu">
                {parts.map((p) => (
                  <li
                    key={p}
                    onClick={() => handlePartSelect(p)}
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
            <input
              className="form-input"
              placeholder="21204482"
              value={form.stdId}
              onChange={handleChange("stdId")}
            />
          </div>

          {/* 학과 */}
          <div className="form-group">
            <label className="form-label">학과</label>
            <input
              className="form-input"
              placeholder="컴퓨터공학과"
              value={form.department}
              onChange={handleChange("department")}
            />
          </div>

          {/* 이메일 */}
          <div className="form-group">
            <label className="form-label">이메일</label>

            <div className="email-row">
              <input
                className="form-input"
                placeholder="학교 이메일 아이디"
                value={form.emailId}
                onChange={handleChange("emailId")}
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
              placeholder="비밀번호"
              value={form.password}
              onChange={handleChange("password")}
            />
            <input
              type="password"
              className="form-input"
              placeholder="비밀번호 확인"
              value={form.passwordConfirm}
              onChange={handleChange("passwordConfirm")}
            />
            <div className="helper-text">
              비밀번호는 8자 이상이며 특수문자를 포함해야 합니다.
            </div>
          </div>

<div className="form-row" style={{ gap: "12px", marginTop: "8px" }}>
  {!mailSent && (
    <Button
      variant="outline-gray"
      disabled={loading}
      onClick={handleSignupAndSendEmail}
    >
      {loading ? "처리 중..." : "인증 메일 전송"}
    </Button>
  )}

  {mailSent && (
    <>
      <Button
        variant="outline-gray"
        disabled={loading || resendCooldown > 0}
        onClick={handleResendEmail}
      >
        {resendCooldown > 0
          ? `재전송 ${resendCooldown}s`
          : "인증 메일 재전송"}
      </Button>

      <Button
        variant="primary"
        disabled={loading}
        onClick={() => navigate("/signup/verified")}
      >
        인증 완료 페이지로 이동
      </Button>
    </>
  )}
</div>

        </div>
      </div>
    </div>
  );
};

export default SignupPage;