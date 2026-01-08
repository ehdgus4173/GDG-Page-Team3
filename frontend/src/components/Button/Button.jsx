import "./Button.css";

function Button({ variant = "primary", disabled, children }) {
  return (
    <button
      className={`btn btn-${variant} ${disabled ? "disabled" : ""}`}
      disabled={disabled}
    >
      {children}
    </button>
  );
}

export default Button;
