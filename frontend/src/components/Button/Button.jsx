import "./Button.css";

function Button({
  variant = "primary",
  size = "md",
  disabled = false,
  onClick,
  type = "button",
  children,
  ...rest
}) {
  return (
    <button
      type={type}
      className={`btn btn-${variant} btn-${size} ${
        disabled ? "disabled" : ""
      }`}
      disabled={disabled}
      onClick={onClick}
      {...rest}
    >
      {children}
    </button>
  );
}


export default Button;
