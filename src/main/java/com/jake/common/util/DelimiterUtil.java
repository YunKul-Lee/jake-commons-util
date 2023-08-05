package com.jake.common.util;

import java.util.*;

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

	/**
	 * Collection Data 를 DELIMITER_PIPE("|") 로 구분하여 문자열을 생성한다.
	 *
	 */
	public static <E> String encode(Collection<E> data) {
		return encode(data, DELIMITER_PIPE);
	}

	/**
	 * Collection Data 를 구분자로 나누어 문자열을 생성한다.
	 *
	 */
	public static <E> String encode(Collection<E> data, String delimiter) {
		if(data == null || data.isEmpty()) return "";
		StringJoiner joiner = new StringJoiner(delimiter);
		for(E e : data) {
			joiner.add(e.toString());
		}

		return joiner.toString();
	}

	/**
	 * 구분자로 이루어진 문자열을 List 형태로 변환한다.
	 *
	 */
	public static <E> List<E> decodeToList(String source) {
		return decodeToList(source, DELIMITER_PIPE);
	}

	/**
	 * 구분자로 이루어진 문자열을 List 형태로 변환한다.
	 *
	 */
	public static <E> List<E> decodeToList(String source, String delimiter) {
		String[] strArray = source.split('[' + delimiter + ']');
		List<E> data = new ArrayList<>();
		Arrays.stream(strArray).forEach(item -> data.add((E)item));
		return data;
	}

	/**
	 * 구분자로 이루어진 문자열을 Set 형태로 변환한다.
	 *
	 */
	public static <E> Set<E> decodeToSet(String source) {
		return decodeToSet(source, DELIMITER_PIPE);
	}

	/**
	 * 구분자로 이루어진 문자열을 Set 형태로 변환한다.
	 * (순서 보장)
	 *
	 */
	public static <E> Set<E> decodeToSet(String source, String delimiter) {
		String[] strArray = source.split('[' + delimiter + ']');
		Set<E> data = new LinkedHashSet<>();
		Arrays.stream(strArray).forEach(item -> data.add((E)item));
		return data;
	}
}
