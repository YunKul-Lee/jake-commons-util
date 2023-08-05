package com.jake.common.util;

import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

/**
 * 문자열 분할/병합 처리 유틸
 */
public class DelimiterUtil {

	public static final String DELIMITER_SHARP = "#";
	public static final String DELIMITER_PIPE = "|";
	public static final String DELIMITER_UNDERLINE = "_";
	public static final char DELIMITER_CHAR_SHARP = '#';
	public static final char DELIMITER_CHAR_PIPE = '|';
	public static final char DELIMITER_CHAR_UNDERLINE = '_';

	public static <E> String encode(Collection<E> data) {
		return encode(data, DELIMITER_PIPE);
	}

	public static <E> String encode(Collection<E> data, String delimiter) {
		if(data == null || data.isEmpty()) return "";
		StringJoiner joiner = new StringJoiner(delimiter);
		for(E e : data) {
			joiner.add(e.toString());
		}

		return joiner.toString();
	}

//	public static <E> List<E> decodeToList(String source, String delimiter) {
//
//	}
}
