import Header from "../components/Header/Header";
import { Outlet } from "react-router-dom";
import "../styles/layout.css";

function Layout() {
  return (
    <div className="layout">
      <Header />

      <main className="layout-content">
        <div className="container">
          <Outlet />
        </div>
      </main>
    </div>
  );
}

export default Layout;
