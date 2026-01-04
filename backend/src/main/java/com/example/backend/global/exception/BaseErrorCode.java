package com.example.backend.global.exception;

import com.example.backend.global.dto.ErrorReason;

public interface BaseErrorCode {
	public ErrorReason getErrorReason();

	String getExplainError() throws NoSuchFieldException;
}
