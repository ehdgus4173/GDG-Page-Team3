import "./PostEditorForm.css";

function PostEditorLayout({ children, onSave, saveDisabled }) {
  return (
    <section className="post-editor container">
      <div className="post-editor-topbar">
        <button
          className="post-editor-save"
          type="button"
          onClick={onSave}
          disabled={saveDisabled}
        >
          저장하기
        </button>
      </div>

      {children}
    </section>
  );
}

export default PostEditorLayout;
