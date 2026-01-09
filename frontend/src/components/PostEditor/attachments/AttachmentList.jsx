import AttachmentItem from "./AttachmentItem";

function AttachmentList({ items, onRemove }) {
  if (!items || items.length === 0) return null;

  return (
    <div className="attach-list">
      {items.map((it) => (
        <AttachmentItem key={it.id} item={it} onRemove={() => onRemove(it.id)} />
      ))}
    </div>
  );
}

export default AttachmentList;
