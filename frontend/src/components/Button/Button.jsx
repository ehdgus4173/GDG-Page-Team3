import "./Button.css";

function Button({
  variant = "primary",
  disabled = false,
  onClick,
  type = "button",
  children,
  ...rest
}) {
  return (
    <button
      type={type}
      className={`btn btn-${variant} ${disabled ? "disabled" : ""}`}
      disabled={disabled}
      onClick={onClick}
      {...rest}
    >
      {children}
    </button>
  );
}

export default Button;
