package com.transporter.utils;

import java.util.List;

/**
 * @author Devappa.Arali
 *
 */

public class Utils {
	
	public static boolean isNullOrEmpty(String toTest) {
		return (toTest == null) || (toTest.length() == 0);
	}
	
	public static boolean isNullOrEmpty(List list) {
		return (list == null) || (list.isEmpty());
	}
}
