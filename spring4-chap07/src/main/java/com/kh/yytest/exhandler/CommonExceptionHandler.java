package com.kh.yytest.exhandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("com.kh.yytest")
public class CommonExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public String handleException() {
		return "error/commonException";
	}
}
