import "./Tag.css";

function Tag({ active, children }) {
  return (
    <span className={`tag ${active ? "active" : ""}`}>
      {children}
    </span>
  );
}

export default Tag;
