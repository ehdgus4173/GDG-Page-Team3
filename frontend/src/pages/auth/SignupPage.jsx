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
const SCHOOL_DOMAIN = "@seoultech.ac.kr";

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

  const email = useMemo(
    () => (form.emailId ? `${form.emailId}${SCHOOL_DOMAIN}` : ""),
    [form.emailId]
  );

  useEffect(() => {
    if (resendCooldown <= 0) return;
    const timer = setInterval(() => {
      setResendCooldown((prev) => (prev > 0 ? prev - 1 : 0));
    }, 1000);
    return () => clearInterval(timer);
  }, [resendCooldown]);

  const handleChange = useCallback(
    (key) => (e) => setForm((prev) => ({ ...prev, [key]: e.target.value })),
    []
  );

  const validatePasswords = () => {
    const hasSpecial = /[!@#$%^&*]/.test(form.password);
    if (form.password.length < 8 || !hasSpecial) {
      throw new Error("비밀번호는 8자 이상, 특수문자를 포함해야 합니다.");
    }
    if (form.password !== form.passwordConfirm) {
      throw new Error("비밀번호가 일치하지 않습니다.");
    }
  };

  const handleSignupAndSendEmail = async () => {
    setError("");
    setMessage("");
    try {
      validatePasswords();
      if (!email) throw new Error("학교 이메일을 입력해주세요.");

      setLoading(true);

      const credential = await createUserWithEmailAndPassword(
        auth,
        email,
        form.password
      );

      const verificationUrl = `${window.location.origin}/signup/email-verified`;

      await sendEmailVerification(credential.user, {
        url: verificationUrl,
        handleCodeInApp: false,
      });

      await signOut(auth);

      const saved = JSON.stringify({ ...form, email });
      sessionStorage.setItem("signup_form", saved);
      localStorage.setItem("signup_form", saved);

      setMailSent(true);
      setResendCooldown(60);
      setMessage("인증 메일을 보냈습니다. 메일을 확인해주세요.");
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  };

  const handleResendEmail = async () => {
    try {
      if (resendCooldown > 0) return;
      setLoading(true);

      const credential = await signInWithEmailAndPassword(
        auth,
        email,
        form.password
      );
      await sendEmailVerification(credential.user);
      await signOut(auth);

      setResendCooldown(60);
      setMessage("인증 메일을 재전송했습니다.");
    } catch (e) {
      setError(e.message);
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

          <input className="form-input" placeholder="이름" onChange={handleChange("name")} />
          <input className="form-input" placeholder="기수" onChange={handleChange("grade")} />

          <input className="form-input" placeholder="학번" onChange={handleChange("stdId")} />
          <input className="form-input" placeholder="학과" onChange={handleChange("department")} />

          <div className="email-row">
            <input
              className="form-input"
              placeholder="학교 이메일 아이디"
              onChange={handleChange("emailId")}
            />
            <span className="email-domain">{SCHOOL_DOMAIN}</span>
          </div>

          <input
            type="password"
            className="form-input"
            placeholder="비밀번호"
            onChange={handleChange("password")}
          />
          <input
            type="password"
            className="form-input"
            placeholder="비밀번호 확인"
            onChange={handleChange("passwordConfirm")}
          />

          {!mailSent ? (
            <Button onClick={handleSignupAndSendEmail} disabled={loading}>
              인증 메일 전송
            </Button>
          ) : (
            <>
              <Button onClick={handleResendEmail} disabled={resendCooldown > 0}>
                재전송 {resendCooldown > 0 && `(${resendCooldown}s)`}
              </Button>
              <Button
                variant="primary"
                onClick={() => navigate("/signup/verified")}
              >
                가입 화면으로 돌아가기
              </Button>
            </>
          )}
        </div>
      </div>
    </div>
  );
};

export default SignupPage;
