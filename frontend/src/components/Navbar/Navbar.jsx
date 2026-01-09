import { NavLink } from "react-router-dom";
import "./Navbar.css";

function Navbar() {
  return (
    <nav className="navbar">
      <NavLink
        to="/"
        end
        className={({ isActive }) =>
          `nav-item ${isActive ? "active main" : ""}`
        }
      >
        메인
      </NavLink>

      <NavLink
        to="/notice"
        className={({ isActive }) =>
          `nav-item ${isActive ? "active notice" : ""}`
        }
      >
        공지
      </NavLink>

      <NavLink
        to="/news"
        className={({ isActive }) =>
          `nav-item ${isActive ? "active news" : ""}`
        }
      >
        소식
      </NavLink>

      <NavLink
        to="/members"
        className={({ isActive }) =>
          `nav-item ${isActive ? "active members" : ""}`
        }
      >
        멤버
      </NavLink>
    </nav>
  );
}

export default Navbar;
