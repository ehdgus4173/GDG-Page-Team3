import { useEffect, useState } from "react";
import { onAuthStateChanged } from "firebase/auth";
import { auth } from "../../lib/firebase";

import Navbar from "../Navbar/Navbar";
import "../../styles/layout.css";
import "./Header.css";
import GDGLogo from "../Logo/GDGLogo";

function Header() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const unsub = onAuthStateChanged(auth, (user) => {
      // 이메일 인증까지 끝난 사용자만 로그인으로 치고 싶으면 user?.emailVerified도 체크
      setIsLoggedIn(!!user && user.emailVerified);
      // 인증 상관없이 Firebase 로그인만 되면 true로 하려면:
      // setIsLoggedIn(!!user);
    });

    return () => unsub();
  }, []);

  return (
    <header className="header">
      <div className="container header-inner">
        <div className="header-left">
          <GDGLogo size={28} />
          <span className="logo-text">
            Google Developer Groups
            <br />
            On Campus · SeoulTech University
          </span>
        </div>

        <div className="header-center">
          <Navbar />
        </div>

        <div className="header-right">
          {isLoggedIn ? (
            <a href="/mypage">마이페이지</a>
          ) : (
            <>
              <a href="/login">로그인</a>
              <a href="/signup">회원가입</a>
            </>
          )}
        </div>
      </div>
    </header>
  );
}

export default Header;
