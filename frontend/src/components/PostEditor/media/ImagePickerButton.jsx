import { useRef } from "react";

function ImagePickerButton({ onPick }) {
  const ref = useRef(null);

  const open = () => {
    if (ref.current) ref.current.click();
  };

  const onChange = (e) => {
    const file = e.target.files && e.target.files[0];
    if (file) onPick(file);
    e.target.value = ""; // 같은 파일 재선택 가능
  };

  return (
    <div className="post-editor-imagepicker">
      <button type="button" className="imagepicker-btn" onClick={open}>
        <span className="imagepicker-icon" aria-hidden="true">🖼️</span>
        <span className="caption">사진</span>
      </button>
      <input
        ref={ref}
        type="file"
        accept="image/*"
        style={{ display: "none" }}
        onChange={onChange}
      />
    </div>
  );
}

export default ImagePickerButton;
