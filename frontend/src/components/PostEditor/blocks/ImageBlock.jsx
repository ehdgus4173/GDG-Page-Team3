import BlockToolbar from "./BlockToolbar";

function ImageBlock({ block, onMoveUp, onMoveDown, onRemove, disableUp, disableDown }) {
  return (
    <div className="block block-image">
      <img className="block-image-preview" src={block.previewUrl} alt="첨부 이미지" />
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

export default ImageBlock;
