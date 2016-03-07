package com.b5m.sms.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class OptionMaker {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BsTopMngController.class);
	
	
	/*public static void main(String[] args) {
		
		GoodsCommaListOptionVO commaListTypeA = new GoodsCommaListOptionVO("옵션한글 A,옵션한글 B,옵션한글 C,옵션한글 D,옵션한글 E",
																			"옵션중국 A,옵션중국 B,옵션중국 C,옵션중국 D,옵션중국 E",
																			"A-KVAL1:A-KVAL2,B-KVAL1:B-KVAL2:B-KVAL3,C-KVAL1:C-KVAL2,D-KVAL1,E-KVAL1:E-KVAL2",
																			"A-CVAL1:A-CVAL2,B-CVAL1:B-CVAL2:B-CVAL3,C-CVAL1:C-CVAL2,D-CVAL1,E-CVAL1:E-KVAL2");
		
		
		GoodsCommaListOptionVO commaListTypeB = new GoodsCommaListOptionVO("옵션한글 A,옵션한글 B,옵션한글 C",
																			"옵션중국 A,옵션중국 B,옵션중국 C",
																			"A-KVAL1:A-KVAL2,B-KVAL1:B-KVAL2:B-KVAL3,C-KVAL1:C-KVAL2",
																			"A-CVAL1:A-CVAL2,B-CVAL1:B-CVAL2:B-CVAL3,C-CVAL1:C-CVAL2");


		
		GoodsCommaListOptionVO commaList = new GoodsCommaListOptionVO("옵션한글 A,옵션한글 B,옵션한글 C,옵션한글 D,옵션한글 E",
				"옵션중국 A,옵션중국 B,옵션중국 C,옵션중국 D,옵션중국 E",
				"A-KVAL1:A-KVAL2,B-KVAL1:B-KVAL2:B-KVAL3,C-KVAL1:C-KVAL2,D-KVAL1,E-KVAL1:E-KVAL2",
				"A-CVAL1:A-CVAL2,B-CVAL1:B-CVAL2:B-CVAL3,C-CVAL1:C-CVAL2,D-CVAL1,E-CVAL1:E-KVAL2");
		
		GoodsCommaListOptionVO commaListC = new GoodsCommaListOptionVO("옵션한글 A,옵션한글",
				"옵션중국 A,옵션중국 B",
				"A-KVAL1:A-KVAL2,B-KVAL1:B-KVAL2:B-KVAL3",
				"A-CVAL1:A-CVAL2,B-CVAL1:B-CVAL2:B-CVAL3");
																
		commaListC.make5OptionsList();
		List<List<String>> list = commaListC.getCartesianProductFor5Options();

		for (List<String> strings : list) {
			System.out.println(strings);
			
		}

	}

}*/
	
	

}