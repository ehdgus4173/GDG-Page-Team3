import { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import Navbar from "../Navbar/Navbar";
import GDGLogo from "../Logo/GDGLogo";
import "../../styles/layout.css";
import "./Header.css";

function Header() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const location = useLocation();

  useEffect(() => {
    const loggedIn = localStorage.getItem("isLoggedIn") === "true";
    setIsLoggedIn(loggedIn);
  }, [location.pathname]);

  return (
    <header className="header">
      <div className="container header-inner">
        {/* 좌 */}
        <div className="header-left">
          <GDGLogo size={28} />
          <span className="logo-text">
            Google Developer Groups
            <br />
            On Campus · SeoulTech University
          </span>
        </div>

        {/* 중 */}
        <div className="header-center">
          <Navbar />
        </div>

        {/* 우 */}
        <div className="header-right">
          {isLoggedIn ? (
            <Link to="/mypage">마이페이지</Link>
          ) : (
            <>
              <Link to="/login">로그인</Link>
              <Link to="/signup">회원가입</Link>
            </>
          )}
        </div>
      </div>
    </header>
  );
}

export default Header;
