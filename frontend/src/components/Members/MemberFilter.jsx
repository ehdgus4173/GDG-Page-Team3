import "./MemberFilter.css";

function MemberFilter({ filters, selected, onChange }) {
  return (
    <div className="member-filter">
      {filters.map((group) => (
        <div className="filter-group" key={group.key}>
          {group.options.map((option) => (
            <button
              key={option}
              className={`filter-btn ${
                selected[group.key] === option ? "active" : ""
              }`}
              onClick={() =>
                onChange(group.key, option)
              }
            >
              {option}
            </button>
          ))}
        </div>
      ))}
    </div>
  );
}

export default MemberFilter;
