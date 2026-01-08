import TextBlock from "./TextBlock";
import ImageBlock from "./ImageBlock";

function BlockRenderer({ blocks, onChangeText, onMoveUp, onMoveDown, onRemove }) {
  return (
    <div className="post-editor-blocks">
      {blocks.map((b, idx) => {
        const isFirst = idx === 0;
        const isLast = idx === blocks.length - 1;

        if (b.type === "text") {
          return (
            <TextBlock
              key={b.id}
              block={b}
              onChange={(v) => onChangeText(b.id, v)}
              onMoveUp={() => onMoveUp(b.id)}
              onMoveDown={() => onMoveDown(b.id)}
              onRemove={() => onRemove(b.id)}
              disableUp={isFirst}
              disableDown={isLast}
            />
          );
        }

        if (b.type === "image") {
          return (
            <ImageBlock
              key={b.id}
              block={b}
              onMoveUp={() => onMoveUp(b.id)}
              onMoveDown={() => onMoveDown(b.id)}
              onRemove={() => onRemove(b.id)}
              disableUp={isFirst}
              disableDown={isLast}
            />
          );
        }

        return null;
      })}
    </div>
  );
}

export default BlockRenderer;
