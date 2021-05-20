package org.acme.rest;

import java.util.StringJoiner;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class CustomError {
	private int errorCode;
	private String errorMessage;

	public CustomError() {
	}

	public CustomError(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", CustomError.class.getSimpleName() + "[", "]")
			.add("errorCode=" + this.errorCode)
			.add("errorMessage='" + this.errorMessage + "'")
			.toString();
	}
}
