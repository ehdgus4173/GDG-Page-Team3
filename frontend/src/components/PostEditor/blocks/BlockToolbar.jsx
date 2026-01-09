function BlockToolbar({ onMoveUp, onMoveDown, onRemove, disableUp, disableDown }) {
    return (
      <div className="block-toolbar">
        <button type="button" onClick={onMoveUp} disabled={disableUp} aria-label="위로">
          ▲
        </button>
        <button type="button" onClick={onMoveDown} disabled={disableDown} aria-label="아래로">
          ▼
        </button>
        <button type="button" onClick={onRemove} aria-label="삭제">
          ✕
        </button>
      </div>
    );
  }
  
  export default BlockToolbar;
  