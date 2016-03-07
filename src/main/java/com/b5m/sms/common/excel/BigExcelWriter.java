/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package com.b5m.sms.common.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.b5m.sms.common.file.FileUtil;

/**
 * 대용량 엑셀업로드
 * @author 김병찬
 * @since 2012. 8. 8.
 * @see
 * <pre>
 * == 개정이력(Modification Information) ==
 *
 * 수정일              수정자                 수정내용
 * ------------        ---------------        ---------------------------
 * 2012. 8. 8.          김병찬               최초 생성
 * </pre>
 */
public class BigExcelWriter {
	public String sheetRef = null;
	public Map<String, XSSFCellStyle> selectStyles = null;
    
    public BigExcelWriter(String sSheetNm, String sFilePath, String sFileTempNm) throws IOException{
    	
    	FileOutputStream os = null;

    	try{
    		// 엑셀 생성
	    	XSSFWorkbook wb = new XSSFWorkbook();

	        XSSFSheet sheet = wb.createSheet(sSheetNm);

	        // 컬럼 스타일 속성 가져오기
	        selectStyles = this.createStyles(wb);
	        //name of the zip entry holding sheet data, e.g. /xl/worksheets/sheet1.xml
	        sheetRef = sheet.getPackagePart().getPartName().getName();
	
	        // 디렉토리 없으면 생성.
	    	FileUtil.makeDir(sFilePath);
	    	// 템플릿 저장
	        os = new FileOutputStream(sFileTempNm);
	        wb.write(os);
	        os.close();
    	}catch(Exception e){
    		if(os != null){
    			os.close();
    		}
    	}
    	
    	
    }
    
    /**
     * 셀 스타일 정의
     * @param wb
     * @return
     */
    public Map<String, XSSFCellStyle> createStyles(XSSFWorkbook wb){
        Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();
        XSSFDataFormat fmt = wb.createDataFormat();

        XSSFCellStyle style0 = wb.createCellStyle();
        style0.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        styles.put("normal", style0);
        
        XSSFCellStyle style1 = wb.createCellStyle();
        style1.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        style1.setDataFormat(fmt.getFormat("0.0%"));
        styles.put("percent", style1);

        XSSFCellStyle style2 = wb.createCellStyle();
        style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        style2.setDataFormat(fmt.getFormat("0.0X"));
        styles.put("coeff", style2);

        XSSFCellStyle style3 = wb.createCellStyle();
        style3.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        style3.setDataFormat(fmt.getFormat("$#,##0.00"));
        styles.put("currency", style3);

        XSSFCellStyle style4 = wb.createCellStyle();
        style4.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        style4.setDataFormat(fmt.getFormat("mmm dd"));
        styles.put("date", style4);

        XSSFCellStyle style5 = wb.createCellStyle();
        XSSFFont headerFont = wb.createFont();
        headerFont.setBold(true);
        style5.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style5.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style5.setFont(headerFont);
        styles.put("header", style5);
        
        XSSFCellStyle style6 = wb.createCellStyle();
        style6.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        style6.setDataFormat(fmt.getFormat("#,##0.00"));
        styles.put("dotNumber2", style6);
        
        XSSFCellStyle style7 = wb.createCellStyle();
        style7.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        style7.setDataFormat(fmt.getFormat("#,##0"));
        styles.put("number", style7);
        
        XSSFCellStyle style8 = wb.createCellStyle();
        style8.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        style8.setDataFormat(fmt.getFormat("#,##0.000"));
        styles.put("dotNumber3", style8);

        return styles;
    }

    /**
     * 타이틀 생성
     * @param sTitle
     * @param sStyleType
     * @param sw
     * @throws Exception
     */
    public void generateHeader(String [] sTitle, String sStyleType, SpreadSheetWriter sw) throws Exception {

        //첫번째 row에 타이틀 작성
        sw.insertRow(0);
        int styleIndex = selectStyles.get(sStyleType).getIndex();
        
        //컬럼 갯수
      	int iTitleSize = sTitle.length;
      	
      	//컬럼갯수 만큼 타이틀 생성
        for (int i = 0; i < iTitleSize; i++) {
        	sw.createCell(i, sTitle[i], styleIndex);
        }
        
        sw.endRow();
    
    }
    
    /**
     * 내용 작성
     * @param iTitleField
     * @param sTitleField
     * @param iRowNum
     * @param rs
     * @param sStyleType
     * @param sw
     * @throws Exception
     */
    public void generateContent(int iTitleField, String [] sTitleField, int iRowNum, HashMap hm, String[] sStyleType, SpreadSheetWriter sw) throws Exception {
    	
    	//write data rows
        sw.insertRow(iRowNum);
       
        for (int j = 0; j < iTitleField; j++) {
        	if(selectStyles.get(sStyleType[j]) == null){
        		sStyleType[j] = "normal";
        	}
        	
        	if("number".equals(sStyleType[j]) || "dotNumber2".equals(sStyleType[j]) || "dotNumber3".equals(sStyleType[j])){
        		double dValue=0;
        		if(hm.get(sTitleField[j])!=null){
        			dValue=Double.parseDouble((String) hm.get(sTitleField[j]));
        		}
        		sw.createCell(j, dValue, selectStyles.get(sStyleType[j]).getIndex());
        	}
        	else if("normal".equals(sStyleType[j])){
        		
        		sw.createCell(j, (String) hm.get(sTitleField[j]), selectStyles.get(sStyleType[j]).getIndex());
        	}
        	
        }
        sw.endRow();
    }

    /**
     * 템플릿 파일에 데이터 복사해서 만듬
     * @param zipfile 템플릿 파일
     * @param tmpfile 시트데이터를 가진 xml 파일
     * @param entry the name of the sheet entry to substitute, e.g. xl/worksheets/sheet1.xml
     * @param out 결과값에 대한 스트림
     */
    public void substitute(File zipfile, File tmpfile, OutputStream out) throws IOException {
    	
    	ZipFile zip = null;
    	InputStream is = null;
    	ZipOutputStream zos = null;
    	
    	try{
	        zip = new ZipFile(zipfile);
	
	        zos = new ZipOutputStream(out);
	        String entry = sheetRef.substring(1);
	        @SuppressWarnings("unchecked")
	        Enumeration<ZipEntry> en = (Enumeration<ZipEntry>) zip.entries();
	        while (en.hasMoreElements()) {
	            ZipEntry ze = en.nextElement();
	            if(!ze.getName().equals(entry)){
	                zos.putNextEntry(new ZipEntry(ze.getName()));
	                InputStream isze = zip.getInputStream(ze);
	                copyStream(isze, zos);
	                isze.close();
	            }
	        }
	        zos.putNextEntry(new ZipEntry(entry));
	        is = new FileInputStream(tmpfile);
	        copyStream(is, zos);
	        is.close();
	
	        zip.close();
	        zos.close();
    	}catch(Exception e){
    		if(zip != null){
    			zip.close();
    		}
    		
    		if(is != null){
    			is.close();
    		}
    		
    		if(zos != null){
    			zos.close();
    		}
    	}
    }

    /**
     * 파일복사
     * @param in
     * @param out
     * @throws IOException
     */
    private void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] chunk = new byte[1024];
        int count;
        while ((count = in.read(chunk)) >=0 ) {
        	out.write(chunk,0,count);
        }
    }
}