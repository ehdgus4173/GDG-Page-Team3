package com.example.backend.global.annotation;

import com.example.backend.global.interfaces.SwaggerExampleExceptions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorExceptionsExample {
	Class<? extends SwaggerExampleExceptions> value();
}