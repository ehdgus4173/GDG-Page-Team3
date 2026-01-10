import { useMemo, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../../styles/layout.css";
import "./MyPageEdit.css";

function MyPageEdit() {
  const navigate = useNavigate();

  // ✅ 파일 선택용 input을 버튼으로 트리거하기 위해 ref 사용
  const fileInputRef = useRef(null);

  const initial = useMemo(
    () => ({
      name: "황길동",
      generation: "황길동",
      position: "직책",
      part: "파트",
      studentId: "21204482",
      major: "지디지학과",

      // 이미지(초기 더미)
      imageUrl: "https://placehold.co/600x600",

      // 링크 입력(스샷처럼 1줄만 보여주고 싶으면 여기만 1개로)
      platform: "플랫폼",
      link: "황길동",
    }),
    []
  );

  const [form, setForm] = useState(initial);
  const [previewUrl, setPreviewUrl] = useState(initial.imageUrl);

  const setValue = (key, value) =>
    setForm((prev) => ({
      ...prev,
      [key]: value,
    }));

  // ✅ 이미지 버튼 클릭 → 파일 선택창 열기
  const openFilePicker = () => {
    if (fileInputRef.current) fileInputRef.current.click();
  };

  // ✅ 이미지 선택하면 즉시 미리보기
  const handleImageChange = (e) => {
    const file = e.target.files && e.target.files[0];
    if (!file) return;

    // 이미지 파일만
    if (!file.type.startsWith("image/")) {
      alert("이미지 파일만 업로드할 수 있습니다.");
      return;
    }

    const url = URL.createObjectURL(file);
    setPreviewUrl(url);

    // 나중에 백엔드 붙일 때 file도 state로 들고 있으면 됨
    // setForm(prev => ({ ...prev, imageFile: file }))
  };

  const handleSave = () => {
    // ✅ 지금은 UI만: 저장 동작은 나중에 백엔드 연결
    navigate("/mypage");
  };

  return (
    <section className="container">
      {/* 상단: 좌측 타이틀 / 우측 저장 버튼 */}
      <div className="mypage-edit-top">
        <h1 className="mypage-edit-title">마이페이지</h1>
        <button className="mypage-edit-save" onClick={handleSave}>
          저장
        </button>
      </div>

      {/* 중앙 컨텐츠 */}
      <div className="mypage-edit-center">
        {/* 프로필 이미지 */}
        <div className="mypage-edit-avatar">
          <img src={previewUrl} alt="profile" className="mypage-edit-avatar-img" />

          <button type="button" className="mypage-edit-img-btn" onClick={openFilePicker}>
            이미지 수정
          </button>

          {/* 실제 파일 input (숨김) */}
          <input
            ref={fileInputRef}
            type="file"
            accept="image/*"
            className="mypage-edit-file"
            onChange={handleImageChange}
          />
        </div>

        {/* 카드 1: 기본 정보 */}
        <div className="mypage-edit-card">
          <div className="mypage-edit-form">
            <div className="field full">
              <label>이름</label>
              <input value={form.name} onChange={(e) => setValue("name", e.target.value)} />
            </div>

            <div className="field">
              <label>기수</label>
              <input
                value={form.generation}
                onChange={(e) => setValue("generation", e.target.value)}
              />
            </div>

            <div className="field">
              <label>직책</label>
              <select
                value={form.position}
                onChange={(e) => setValue("position", e.target.value)}
              >
                <option value="직책">직책</option>
                <option value="운영진">운영진</option>
                <option value="코어">코어</option>
                <option value="멤버">멤버</option>
              </select>
            </div>

            <div className="field full">
              <label>파트</label>
              <select value={form.part} onChange={(e) => setValue("part", e.target.value)}>
                <option value="파트">파트</option>
                <option value="AI">AI</option>
                <option value="Front-end">Front-end</option>
                <option value="Back-end">Back-end</option>
                <option value="App">App</option>
                <option value="Design">Design</option>
              </select>
            </div>

            <div className="field full">
              <label>학번</label>
              <input
                value={form.studentId}
                onChange={(e) => setValue("studentId", e.target.value)}
              />
            </div>

            <div className="field full">
              <label>학과</label>
              <input value={form.major} onChange={(e) => setValue("major", e.target.value)} />
            </div>
          </div>
        </div>

        {/* 카드 2: 링크 */}
        <div className="mypage-edit-card">
          <div className="mypage-edit-form two-col">
            <div className="field">
              <label>플랫폼</label>
              <select
                value={form.platform}
                onChange={(e) => setValue("platform", e.target.value)}
              >
                <option value="플랫폼">플랫폼</option>
                <option value="Github">Github</option>
                <option value="Notion">Notion</option>
                <option value="Velog">Velog</option>
                <option value="LinkedIn">LinkedIn</option>
                <option value="Portfolio">Portfolio</option>
              </select>
            </div>

            <div className="field">
              <label>링크</label>
              <input
                value={form.link}
                onChange={(e) => setValue("link", e.target.value)}
                placeholder="https://"
              />
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}

export default MyPageEdit;
