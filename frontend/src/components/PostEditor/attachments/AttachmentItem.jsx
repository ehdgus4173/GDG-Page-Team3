function AttachmentItem({ item, onRemove }) {
    return (
      <div className="attach-item">
        <div className="attach-item-left">
          <span className="attach-file-icon" aria-hidden="true">📄</span>
          <span className="body-1 attach-file-name">{item.name}</span>
        </div>
  
        <button type="button" className="attach-remove" onClick={onRemove} aria-label="첨부 삭제">
          ✕
        </button>
      </div>
    );
  }
  
  export default AttachmentItem;
  