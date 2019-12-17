/**
 * 
 */
package com.fsd.sba.constant;

/**
 * @author Rajiniganth Jagadeesan
 *
 */
public enum Status {
	
	NEW, IN_PROGRESS, COMPLETED, SUSPENDED;

	public static Status getStatus(String value) {

		Status rs = null;
		for (Status s : Status.values()) {
			if (s.name().equals(value)) {
				rs = s;
				break;
			}
		}
		return rs;
	}

}
