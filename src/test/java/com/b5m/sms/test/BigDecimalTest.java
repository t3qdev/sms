package com.b5m.sms.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

public class BigDecimalTest {

	@Test
	public void test() {
		String floatNm = "12345678901.127";
		BigDecimal bd = new BigDecimal(floatNm);
		System.out.println(bd);
	}

}
