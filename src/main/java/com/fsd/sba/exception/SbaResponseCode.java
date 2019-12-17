/**
 * 
 */
package com.fsd.sba.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Rajiniganth Jagadeesan
 *
 */
public enum SbaResponseCode {
	
	SUCCESS(HttpStatus.OK, "SBA01"),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SBA01"),
	INVALID_REQUEST(HttpStatus.BAD_REQUEST, "SBA02"),
	DATA_NOT_AVAILABLE_FOR_DELETE(HttpStatus.NOT_FOUND, "SBA03"),
	USER_NOT_AVAILALE(HttpStatus.BAD_REQUEST, "SBA04"),
	USER_OCCUPIED(HttpStatus.BAD_REQUEST, "SBA05"),
	PROJECT_NOT_FOUND(HttpStatus.BAD_REQUEST, "SBA06"),
	TASK_NOT_FOUND(HttpStatus.BAD_REQUEST, "SBA07"),
	PARENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "SBA08"); 
	
	private HttpStatus status;
	private String code;
	
	SbaResponseCode(HttpStatus status, String code) {
		this.code = code;
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * @param code
	 * @return SbaResponseCode
	 */
	public static SbaResponseCode getResponseCode(String code) {
		
		SbaResponseCode rs = SUCCESS;
		for (SbaResponseCode s: SbaResponseCode.values()) {
			if (s.getCode().equals(code)) {
				rs = s;
				break;
			}
		}
		return rs;
	}

}
