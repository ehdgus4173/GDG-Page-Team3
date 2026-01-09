import { useRef, useState } from "react";

function AttachmentDropzone({ onFiles }) {
  const inputRef = useRef(null);
  const [dragOver, setDragOver] = useState(false);

  const open = () => inputRef.current && inputRef.current.click();

  const handleFiles = (files) => {
    if (!files || files.length === 0) return;
    onFiles(files);
  };

  const onChange = (e) => {
    handleFiles(e.target.files);
    e.target.value = "";
  };

  const onDrop = (e) => {
    e.preventDefault();
    setDragOver(false);
    handleFiles(e.dataTransfer.files);
  };

  return (
    <div
      className={`attach-dropzone ${dragOver ? "is-over" : ""}`}
      onDragOver={(e) => {
        e.preventDefault();
        setDragOver(true);
      }}
      onDragLeave={() => setDragOver(false)}
      onDrop={onDrop}
      onClick={open}
      role="button"
      tabIndex={0}
    >
      <div className="attach-dropzone-inner">
        <div className="attach-plus">+</div>
        <div className="caption">파일을 드래그하거나 클릭하세요</div>
      </div>

      <input
        ref={inputRef}
        type="file"
        multiple
        style={{ display: "none" }}
        onChange={onChange}
      />
    </div>
  );
}

export default AttachmentDropzone;
