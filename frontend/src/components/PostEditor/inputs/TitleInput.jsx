function TitleInput({ value, onChange }) {
    return (
      <div className="post-editor-title">
        <input
          className="post-editor-title-input display-1"
          value={value}
          onChange={(e) => onChange(e.target.value)}
          placeholder="제목을 입력하세요"
        />
      </div>
    );
  }
  
  export default TitleInput;
  