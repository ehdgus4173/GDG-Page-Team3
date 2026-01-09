import BlockToolbar from "./BlockToolbar";

function TextBlock({ block, onChange, onMoveUp, onMoveDown, onRemove, disableUp, disableDown }) {
  return (
    <div className="block block-text">
      <textarea
        className="block-textarea body-long-1"
        value={block.content}
        onChange={(e) => onChange(e.target.value)}
        placeholder="내용을 입력하세요"
      />
      <BlockToolbar
        onMoveUp={onMoveUp}
        onMoveDown={onMoveDown}
        onRemove={onRemove}
        disableUp={disableUp}
        disableDown={disableDown}
      />
    </div>
  );
}

export default TextBlock;
