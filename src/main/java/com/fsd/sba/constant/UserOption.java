/**
 * 
 */
package com.fsd.sba.constant;

/**
 * @author Rajiniganth Jagadeesan
 *
 */
public enum UserOption {

	ALL, PROJECT, TASK;

	public static UserOption getOption(String value) {

		UserOption rs = ALL;
		for (UserOption s : UserOption.values()) {
			if (s.name().equals(value)) {
				rs = s;
				break;
			}
		}
		return rs;
	}

}
