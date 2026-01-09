import { useNavigate } from "react-router-dom";
import PostEditorForm from "../../components/PostEditor/PostEditorForm";

function NewsWritePage() {
  const navigate = useNavigate();

  const handleSubmit = (payload) => {
    // TODO: API 연결 (소식 생성)
    console.log("NEWS payload:", payload);
    navigate("/news");
  };

  return <PostEditorForm onSubmit={handleSubmit} />;
}

export default NewsWritePage;
