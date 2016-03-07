package com.b5m.sms.common.vomap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.BitSet;
 
/**
 * 문자열을 인코딩하는 Class.
 * <p> 
 * <수정이력> <br /> 
 * 1. 수정일: 수정자: 수정사유: <br />
 * <p>
 * @since 2013. 8. 18.
 * @version 1.0
 * @author 김병찬
 */
public class Encoder {
	/**
	 * bit 인코딩 
	 */
	static BitSet dontNeedEncoding;

	/**
	 * caseDiff
	 */
	static final int caseDiff = 32;

	/**
	 * Encoder 생성자.
	 */
	public Encoder() {  
	}

	static {
		dontNeedEncoding = new BitSet(256);
		for (int i = 97; i <= 122; i++)
			dontNeedEncoding.set(i);

		for (int j = 65; j <= 90; j++)
			dontNeedEncoding.set(j);

		for (int k = 48; k <= 57; k++)
			dontNeedEncoding.set(k);

		dontNeedEncoding.set(32);
		dontNeedEncoding.set(45);
		dontNeedEncoding.set(95);
		dontNeedEncoding.set(46);
		dontNeedEncoding.set(42);
	}

	/**
	 * 문자열을 인코딩.
	 * @since 2010. 5. 28.
	 * @param s 인코딩할 문자열
	 * @return 인코딩된 문자
	 */
	public static String encode(String s) {
		byte byte0 = 10;
		if (s == null) {
			return s;
		}
		StringBuffer stringbuffer = new StringBuffer(s.length());
		ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(
				byte0);
		OutputStreamWriter outputstreamwriter = new OutputStreamWriter(
				bytearrayoutputstream);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (dontNeedEncoding.get(c)) {
				stringbuffer.append(Integer.toHexString(c));
				continue;
			}

			try {
				outputstreamwriter.write(c);
				outputstreamwriter.flush();
			} catch (IOException ioexception) {
				bytearrayoutputstream.reset();
				continue;
			}

			byte abyte0[] = bytearrayoutputstream.toByteArray();
			for (int j = 0; j < abyte0.length; j++) {
				char c1 = Character.forDigit(abyte0[j] >> 4 & 0xf, 16);
				if (Character.isLetter(c1))
					c1 -= ' ';
				stringbuffer.append(c1);
				c1 = Character.forDigit(abyte0[j] & 0xf, 16);
				if (Character.isLetter(c1))
					c1 -= ' ';
				stringbuffer.append(c1);
			}

			bytearrayoutputstream.reset();
		}

		return stringbuffer.toString();
	}
}
