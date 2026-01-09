import "../../styles/auth.css";

const SignupEmailVerifiedNoticePage = () => {
  return (
    <div className="auth-wrapper">
      <div className="auth-card">
        <h1 className="auth-title">이메일 인증 완료</h1>
        <div className="auth-form">
          <div className="notice success">
            이메일 인증이 완료되었습니다. 가입을 진행하던 창으로 돌아가
            "인증 완료 페이지로 이동" 버튼을 눌러 가입을 마무리해주세요.
          </div>
          <p className="helper-text" style={{ marginTop: "12px" }}>
            현재 페이지에서는 별도 입력이나 가입 처리가 진행되지 않습니다.
            기존 가입 화면으로 돌아가 주세요.
          </p>
        </div>
      </div>
    </div>
  );
};

export default SignupEmailVerifiedNoticePage;
