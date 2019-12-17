package com.fsd.sba.exception;

import org.springframework.http.HttpStatus;

public class SbaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;
	private SbaResponseCode code;
	private HttpStatus status;

	public SbaException(SbaResponseCode code) {
		this.code = code;
		this.status = code.getStatus();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	/**
	 * @return the code
	 */
	public SbaResponseCode getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(SbaResponseCode code) {
		this.code = code;
	}
	
	

}
