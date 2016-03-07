package com.b5m.sms.common.rowhandler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;

import com.b5m.sms.common.excel.BigExcelWriter;
import com.b5m.sms.common.excel.SpreadSheetWriter;
import com.b5m.sms.common.vomap.DataBox;
import com.ibatis.sqlmap.client.event.RowHandler;

public class ExcelRowHandlerCallback implements RowHandler {

	//private DataBox box = null;
	//private HttpServletRequest request = null;
	//private HttpServletResponse response = null;
	
	private String sStyleType[];		// 셀 스타일
	private int iRowNum = 1;			// 엑셀 등록 로우번호
	private int iTitleField;			// 타이틀 갯수
	private String[] sTitleField;		// 타이틀이 담긴 배열
	private SpreadSheetWriter sw;
	
	private BigExcelWriter bigExcelWriter;
	
	Writer fw = null;
	FileOutputStream out = null;
	
	public ExcelRowHandlerCallback(DataBox box, BigExcelWriter bigExcelWriter, SpreadSheetWriter sw) throws IOException{
		
		this.bigExcelWriter = bigExcelWriter;
		this.sw = sw;
		
		sTitleField = box.getValues("sTitleField");
		iTitleField = sTitleField.length;
		sStyleType = box.getValues("sStyleType");
	}
	
	@Override
	public void handleRow(Object rowObject) {
		HashMap hm= (HashMap)rowObject;
		
		try {
			bigExcelWriter.generateContent(iTitleField, sTitleField, iRowNum, hm, sStyleType, sw);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		iRowNum++;

		
		// TODO Auto-generated method stub
		
	}
}
