package com.exchangerate.app.exception;

public class ExchangeRateException {

	int errorCode;
	String errorMessage;
	
	public ExchangeRateException() {}
	
	public ExchangeRateException(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
}
