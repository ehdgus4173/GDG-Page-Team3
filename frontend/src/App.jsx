import { BrowserRouter, Routes, Route } from "react-router-dom";
import Layout from "./layouts/Layout";

// 페이지들 (UI 확인용 더미 페이지)
import MainPage from "./pages/main/MainPage";
import NoticePage from "./pages/notice/NoticePage";
import NewsPage from "./pages/news/NewsPage";
import MembersPage from "./pages/members/MembersPage";
import MyPage from "./pages/mypage/Mypage";
import NoticeDetailPage from "./pages/notice/NoticeDetailPage";
import NewsDetailPage from "./pages/news/NewsDetailPage";
import NoticeWritePage from "./pages/notice/NoticeWritePage";
import NewsWritePage from "./pages/news/NewsWritePage";
import LoginPage from "./pages/auth/LoginPage";
import FindPasswordPage from "./pages/auth/FindPasswordPage";
import SignupPage from "./pages/auth/SignupPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* 모든 페이지가 공통 Layout(Header 포함)을 사용 */}
        <Route element={<Layout />}>
          <Route path="/" element={<MainPage />} />
          <Route path="/notice" element={<NoticePage />} />
          <Route path="/notice/write" element={<NoticeWritePage />} />
          <Route path="/notice/:id" element={<NoticeDetailPage />} />
          <Route path="/news" element={<NewsPage />} />
          <Route path="/news/write" element={<NewsWritePage />} />
          <Route path="/news/:id" element={<NewsDetailPage />} />
          <Route path="/members" element={<MembersPage />} />
          <Route path="/mypage" element={<MyPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/find-password" element={<FindPasswordPage />} />
          <Route path="/signup" element={<SignupPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
