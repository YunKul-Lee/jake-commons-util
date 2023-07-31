package com.jake.common.util;

import com.google.common.base.CaseFormat;

public abstract class StringUtils {

	public static String convertSnakeCaseToCamelCase(String upperSnakeCase) {
		return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, upperSnakeCase);
	}

	public static boolean isEmpty(Object str) {
		return (str == null || "".equals(str));
	}

	public static boolean isNotEmpty(Object str) {
		return (str != null && !"".equals(str));
	}

	public static boolean containsText(CharSequence str) {
		int strLen = str.length();
		for(int i = 0; i < strLen; i++) {
			if(Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}
}
