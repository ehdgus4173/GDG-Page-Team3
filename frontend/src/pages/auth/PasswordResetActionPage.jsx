import { useEffect, useState } from "react";
import { useNavigate, useSearchParams, Link } from "react-router-dom";
import Button from "../../components/Button/Button";
import { auth } from "../../lib/firebase";
import "../../styles/auth.css";

const PasswordResetActionPage = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const [verifiedEmail, setVerifiedEmail] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    const handleAction = async () => {
      if (!oobCode) {
        if (mode === "resetPassword") {
          const email = await verifyPasswordResetCode(auth, oobCode);
          setVerifiedEmail(email);
          setMessage("이메일 인증이 확인되었습니다. 새 비밀번호를 입력해주세요.");
      } finally {
        setLoading(false);
      }
    };
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
    } finally {
      setLoading(false);
    }
  };

      </div>
    </div>
  );
};

export default PasswordResetActionPage;
