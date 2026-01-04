package com.example.backend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GlobalDynamicException extends RuntimeException {
	private final int status;
	private final String code;
	private final String reason;
}