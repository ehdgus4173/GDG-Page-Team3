import { useNavigate } from "react-router-dom";
import PostEditorForm from "../../components/PostEditor/PostEditorForm";

function NoticeWritePage() {
  const navigate = useNavigate();

  const handleSubmit = (payload) => {
    // TODO: API 연결 (공지 생성)
    console.log("NOTICE payload:", payload);
    navigate("/notice");
  };

  return <PostEditorForm onSubmit={handleSubmit} />;
}

export default NoticeWritePage;
