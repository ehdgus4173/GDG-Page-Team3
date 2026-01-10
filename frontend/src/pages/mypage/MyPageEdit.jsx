import { useEffect, useMemo, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../../styles/layout.css";
import "./MyPageEdit.css";
import { fetchMyProfile, updateMyProfile } from "../../services/profileApi";

function MyPageEdit() {
  const navigate = useNavigate();

  // ✅ 파일 선택용 input을 버튼으로 트리거하기 위해 ref 사용
  const fileInputRef = useRef(null);

  const initial = useMemo(
    () => ({
      name: "",
      generation: "",
      position: "",
      part: "",
      studentId: "",
      major: "",
      bio: "",
      imageUrl: "https://placehold.co/600x600",
      platform: "",
      link: "",
    }),
    []
  );

  const [form, setForm] = useState(initial);
  const [previewUrl, setPreviewUrl] = useState(initial.imageUrl);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [saving, setSaving] = useState(false);

  const setValue = (key, value) =>
    setForm((prev) => ({
      ...prev,
      [key]: value,
    }));

  useEffect(() => {
    let alive = true;
    const load = async () => {
      setLoading(true);
      setError("");
      try {
        const data = await fetchMyProfile();
        if (!alive) return;
        const firstLink = data.snsLinks && data.snsLinks.length > 0 ? data.snsLinks[0] : null;
        setForm({
          name: data.name || "",
          generation: data.generation || "",
          position: data.role || "",
          part: data.part || "",
          studentId: data.studentId || "",
          major: data.department || "",
          bio: data.bio || "",
          imageUrl: data.imageUrl || initial.imageUrl,
          // 백엔드 enum 타입 그대로 사용 (예: GITHUB, BLOG, ETC)
          platform: firstLink?.type || "",
          link: firstLink?.url || "",
        });
        setPreviewUrl(data.imageUrl || initial.imageUrl);
      } catch (e) {
        if (!alive) return;
        setError(e.message || "프로필 정보를 불러오지 못했습니다.");
      } finally {
        if (alive) setLoading(false);
      }
    };
    load();
    return () => {
      alive = false;
    };
  }, [initial.imageUrl]);

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
    const payload = {
      name: form.name || null,
      generation: Number(form.generation) || null,
      role: form.position || null,
      part: form.part || null,
      studentId: form.studentId || null,
      imageUrl: form.imageUrl || previewUrl,
      bio: form.bio || null,
      department: form.major || null,
      techStacks: [], // 현재 UI에 스택 입력 필드가 없어서 빈 배열 전송
      snsLinks:
        form.platform && form.link
          ? [
              {
                type: form.platform,
                url: form.link,
              },
            ]
          : [],
    };

    setSaving(true);
    setError("");
    updateMyProfile(payload)
      .then(() => navigate("/mypage"))
      .catch((e) => setError(e.message || "프로필 저장에 실패했습니다."))
      .finally(() => setSaving(false));
  };

  return (
    <section className="container">
      {/* 상단: 좌측 타이틀 / 우측 저장 버튼 */}
      <div className="mypage-edit-top">
        <h1 className="mypage-edit-title">마이페이지</h1>
        <button className="mypage-edit-save" onClick={handleSave} disabled={saving || loading}>
          {saving ? "저장 중..." : "저장"}
        </button>
      </div>

      {loading && <div style={{ padding: 20 }}>불러오는 중...</div>}
      {error && <div style={{ padding: 20, color: "red" }}>{error}</div>}

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
                <option value="">직책</option>
                <option value="LEAD">리드</option>
                <option value="CORE">코어</option>
                <option value="MEMBER">멤버</option>
              </select>
            </div>

            <div className="field full">
              <label>파트</label>
              <select value={form.part} onChange={(e) => setValue("part", e.target.value)}>
                <option value="">파트</option>
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

          <div className="field full">
            <label>한 줄 소개</label>
            <input value={form.bio} onChange={(e) => setValue("bio", e.target.value)} />
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
                <option value="">플랫폼</option>
                <option value="GITHUB">Github</option>
                <option value="BLOG">Velog / Blog</option>
                <option value="LINKEDIN">LinkedIn</option>
                <option value="INSTAGRAM">Instagram</option>
                <option value="BEHANCE">Behance</option>
                <option value="ETC">Notion / Portfolio / 기타</option>
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
