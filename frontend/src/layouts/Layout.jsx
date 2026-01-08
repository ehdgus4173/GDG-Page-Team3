import Header from "../components/Header/Header";
import { Outlet, useLocation } from "react-router-dom";
import "../styles/layout.css";

function Layout() {
  const { pathname } = useLocation();

  const isWritePage =
    pathname === "/notice/write" || pathname === "/news/write";

  return (
    <div className="layout">
      <Header />

      <main className={`layout-content ${isWritePage ? "layout-content--write" : ""}`}>
        <div className="container">
          <Outlet />
        </div>
      </main>
    </div>
  );
}

export default Layout;
