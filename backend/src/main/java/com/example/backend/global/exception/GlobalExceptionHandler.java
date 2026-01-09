package com.example.backend.global.exception;

import com.example.backend.auth.exception.PasswordResetException;
import com.example.backend.auth.exception.SignInException;
import com.example.backend.auth.exception.SignUpException;
import com.example.backend.global.dto.ErrorReason;
import com.example.backend.global.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * ResponseEntityExceptionHandler 내부 예외 처리
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {

        String url = "";
        if (request instanceof ServletWebRequest servletWebRequest) {
            url = servletWebRequest.getRequest().getRequestURL().toString();
        }

        HttpStatus httpStatus = HttpStatus.valueOf(status.value());

        ErrorResponse errorResponse =
                new ErrorResponse(
                        httpStatus.value(),
                        httpStatus.getReasonPhrase(),
                        ex.getMessage(),
                        url
                );

        return new ResponseEntity<>(errorResponse, headers, httpStatus);
    }

    /**
     * @Valid DTO 검증 실패 (Request Body)
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {

        Map<String, String> fieldErrors =
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        FieldError::getField,
                                        FieldError::getDefaultMessage,
                                        (a, b) -> a
                                )
                        );

        String url = "";
        if (request instanceof ServletWebRequest servletWebRequest) {
            url = servletWebRequest.getRequest().getRequestURL().toString();
        }

        ErrorResponse errorResponse =
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        fieldErrors.toString(),
                        url
                );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * @RequestParam / @PathVariable 검증 실패
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request
    ) {

        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach(violation -> {
            String field =
                    violation.getPropertyPath().toString().contains(".")
                            ? violation.getPropertyPath().toString()
                            .substring(violation.getPropertyPath().toString().lastIndexOf('.') + 1)
                            : violation.getPropertyPath().toString();
            errors.put(field, violation.getMessage());
        });

        ErrorReason errorReason =
                ErrorReason.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .code("BAD_REQUEST")
                        .reason(errors.toString())
                        .build();

        ErrorResponse errorResponse =
                new ErrorResponse(errorReason, request.getRequestURL().toString());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    /**
     * 정적 에러 코드 예외
     */
    @ExceptionHandler(GlobalCodeException.class)
    public ResponseEntity<ErrorResponse> handleGlobalCodeException(
            GlobalCodeException ex,
            HttpServletRequest request
    ) {

        BaseErrorCode errorCode = ex.getErrorCode();
        ErrorReason errorReason = errorCode.getErrorReason();

        ErrorResponse errorResponse =
                new ErrorResponse(errorReason, request.getRequestURL().toString());

        return ResponseEntity
                .status(HttpStatus.valueOf(errorReason.getStatus()))
                .body(errorResponse);
    }

    /**
     * 회원가입 관련 예외
     */
    @ExceptionHandler(SignUpException.class)
    public ResponseEntity<ErrorResponse> handleSignUpException(
            SignUpException ex,
            HttpServletRequest request
    ) {
        ErrorReason reason = ex.getErrorReason();
        ErrorResponse errorResponse =
                new ErrorResponse(reason, request.getRequestURL().toString());

        return ResponseEntity.status(HttpStatus.valueOf(reason.getStatus()))
                .body(errorResponse);
    }

    /**
     * 로그인 관련 예외
     */
    @ExceptionHandler(SignInException.class)
    public ResponseEntity<ErrorResponse> handleSignInException(
            SignInException ex,
            HttpServletRequest request
    ) {
        ErrorReason reason = ex.getErrorReason();
        ErrorResponse errorResponse =
                new ErrorResponse(reason, request.getRequestURL().toString());

        return ResponseEntity.status(HttpStatus.valueOf(reason.getStatus()))
                .body(errorResponse);
    }

    /**
     * 비밀번호 재설정 관련 예외
     */
    @ExceptionHandler(PasswordResetException.class)
    public ResponseEntity<ErrorResponse> handlePasswordResetException(
            PasswordResetException ex,
            HttpServletRequest request
    ) {
        ErrorReason reason = ex.getErrorReason();
        ErrorResponse errorResponse =
                new ErrorResponse(reason, request.getRequestURL().toString());

        return ResponseEntity.status(HttpStatus.valueOf(reason.getStatus()))
                .body(errorResponse);
    }

    /**
     * 동적 에러 코드 예외
     */
    @ExceptionHandler(GlobalDynamicException.class)
    public ResponseEntity<ErrorResponse> handleGlobalDynamicException(
            GlobalDynamicException ex,
            HttpServletRequest request
    ) {

        ErrorResponse errorResponse =
                new ErrorResponse(
                        ex.getStatus(),
                        ex.getCode(),
                        ex.getReason(),
                        request.getRequestURL().toString()
                );

        return ResponseEntity
                .status(HttpStatus.valueOf(ex.getStatus()))
                .body(errorResponse);
    }

    /**
     * 처리되지 않은 모든 예외 (500)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(
            Exception ex,
            HttpServletRequest request
    ) {

        log.error("INTERNAL_SERVER_ERROR", ex);

        GlobalErrorCode internalServerError = GlobalErrorCode.INTERNAL_SERVER_ERROR;

        ErrorResponse errorResponse =
                new ErrorResponse(
                        internalServerError.getStatus(),
                        internalServerError.getCode(),
                        internalServerError.getReason(),
                        request.getRequestURL().toString()
                );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
}
