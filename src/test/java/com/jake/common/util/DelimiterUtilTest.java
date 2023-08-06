package com.jake.common.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DelimiterUtilTest {

	@Test
	void encodeTest() {
		List<String> data = Arrays.asList("one","two","three");
		String encodedData = DelimiterUtils.encode(data);
		assertEquals("one|two|three", encodedData);
	}

	@Test
	void encodeEmptyTest() {
		List<String> data = new ArrayList<>();
		String encodedData = DelimiterUtils.encode(data);
		assertEquals("", encodedData);
	}
}
