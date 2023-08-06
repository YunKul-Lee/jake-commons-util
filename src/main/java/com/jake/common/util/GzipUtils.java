package com.jake.common.util;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * GZIP 처리를 위한 유틸티 클래스
 *
 */
public class GzipUtils {

	/**
	 * 문자열을 GZIP 으로 압축한다.
	 *
	 */
	public static byte[] compress(String data) {
		byte[] srcBytes = data.getBytes();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (GZIPOutputStream gzip = new GZIPOutputStream(bos)){
			gzip.write(srcBytes);
			gzip.close();

			return bos.toByteArray();
		} catch (IOException ioe) {
			throw new RuntimeException("GZIP compress fail", ioe);
		}
	}

	/**
	 * GZIP 압축된 데이터를 해제한다.
	 *
	 */
	public static String decompress(byte[] compressed) {
		ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
		try (GZIPInputStream gis = new GZIPInputStream(bis)) {
			byte[] decompress = IOUtils.toByteArray(gis);
			return new String(decompress, StandardCharsets.UTF_8);
		} catch (IOException ioe) {
			throw new RuntimeException("GZIP decompress fail", ioe);
		}
	}

	/**
	 * GZIP 적용여부 확인
	 *
	 */
	public static boolean isCompressed(final byte[] compressed) {
		return ((compressed[0] == (byte) (GZIPInputStream.GZIP_MAGIC)) &&
				(compressed[1] == (byte) GZIPInputStream.GZIP_MAGIC >> 8));
	}
}
