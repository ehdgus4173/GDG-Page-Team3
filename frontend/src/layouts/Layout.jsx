import Header from "../components/Header/Header";
import { Outlet, useLocation } from "react-router-dom";
import "../styles/layout.css";

function Layout() {
  const { pathname } = useLocation();

  const isAuthPage =
    pathname.startsWith("/login") ||
    pathname.startsWith("/signup") ||
    pathname.startsWith("/find-password");

  // ✅ 메인 페이지만 container 없이 전체폭
  const isMainPage = pathname === "/";

  return (
    <div className="layout">
      <Header />

      <main className="layout-content">
        {isAuthPage || isMainPage ? (
          <Outlet />
        ) : (
          <div className="container">
            <Outlet />
          </div>
        )}
      </main>
    </div>
  );
}

export default Layout;
