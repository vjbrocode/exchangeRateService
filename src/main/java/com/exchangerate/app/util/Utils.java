package com.exchangerate.app.util;

public class Utils {
	
	/**
	 * Round to 4 decimal digits
	 * @param val
	 * @return
	 */
	public static Double round(Double val) {
		return (double)Math.round(val * 10000d) / 10000d;
	}
}
