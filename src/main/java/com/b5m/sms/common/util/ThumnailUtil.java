package com.b5m.sms.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.b5m.sms.common.file.FileUtil;

public class ThumnailUtil {

	/**
	 * 로그
	 */
	protected static Logger logger = Logger.getRootLogger();

	/** 섬네일을 생성한다.
	 * @param sFullFilePath 파일경로
	 * @param sFileNm 파일명
	 * @param sTask 미디어타입(1:사진)
	 * @param sExtensionNm 확장자명
	 */
	public static void makeThumnail(String sFullFilePath, String sFileNm, String sTask, String sExtensionNm) {

		//섬네일 스트림
        InputStream isThumnail = null;
		OutputStream outStream = null;
		FileInputStream f = null;
		//섬네일 파일 시작
        //섬네일 수
		try {
	        int iThumnailCnt = ParseUtil.iGetParseInt(PropertyUtil.getProperty(sTask + ".thumnail.count"), 0);
	        
	        int iWidth = 0;
	        int iHeight = 0;
	        String sThumnailFileNm = null;
	        
	        
	        //섬네일로 만들 수를 프로퍼티에서 읽어와서 그 갯수 및 타입만큼 섬네일을 한다.
	        for(int i=0; i < iThumnailCnt; i++){
	        	
	        	f = new FileInputStream(sFullFilePath + sFileNm);
	        	//섬네일 가로사이즈
	        	iWidth = ParseUtil.iGetParseInt(PropertyUtil.getProperty(sTask + ".thumnail.width_" + i), 0);
	        	//섬네일 세로사이즈
	        	iHeight = ParseUtil.iGetParseInt(PropertyUtil.getProperty(sTask + ".thumnail.height_" + i), 0);
	        	//섬네일 파일명
	        	sThumnailFileNm = sFullFilePath + sFileNm.replaceAll("."+FileUtil.getExt(sFileNm), "") + "_" + iWidth + "X" + iHeight + "." + sExtensionNm;
	        	//섬네일 스트림
	        	isThumnail = ImageUtil.resize(f, iWidth, iHeight, sExtensionNm);
	        	
	        	// 출력할 파일명과 읽어들일 파일명을지정한다.
	            File file = new File(sThumnailFileNm);
	            
	            outStream = new FileOutputStream(file);
	            // 읽어들일 버퍼크기를 메모리에 생성
	            byte[] buf = new byte[1024];
	            int len = 0;
	            // 끝까지 읽어들이면서 File 객체에 내용들을 쓴다
	            while ((len = isThumnail.read(buf)) > 0){
	            	
	            	outStream.write(buf, 0, len);
	            }
	            // Stream 객체를 모두 닫는다.
	            outStream.close();
		        isThumnail.close();
		        f.close();
	        }
	        
    
        //섬네일 파일 끝
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// Stream 객체를 모두 닫는다.
            if(outStream != null)
				try {
					outStream.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            if(isThumnail != null)
				try {
					isThumnail.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            
            if(f != null){
            	try {
					f.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
			e.printStackTrace();
		}
	}
	
	/** 섬네일을 삭제한다.
	 * @param sFullFilePath 파일경로
	 * @param sFileNm 파일명
	 * @param sTask 미디어타입(1:사진)
	 * @param sExtensionNm 확장자명
	 */
	public static void deleteThumnail(String sFullFilePath, String sFileNm, String sTask, String sExtensionNm) {

		//섬네일 파일 시작
        //섬네일 수
		try {
	        int iThumnailCnt = ParseUtil.iGetParseInt(PropertyUtil.getProperty(sTask + ".thumnail.count"), 0);
	        
	        int iWidth = 0;
	        int iHeight = 0;
	        String sThumnailFileNm = null;
	        
	        //섬네일로 만들 수를 프로퍼티에서 읽어와서 그 갯수 및 타입만큼 섬네일을 한다.
	        for(int i=0; i < iThumnailCnt; i++){
	        	
	        	
	        	//섬네일 가로사이즈
	        	iWidth = ParseUtil.iGetParseInt(PropertyUtil.getProperty(sTask + ".thumnail.width_" + i), 0);
	        	//섬네일 세로사이즈
	        	iHeight = ParseUtil.iGetParseInt(PropertyUtil.getProperty(sTask + ".thumnail.height_" + i), 0);
	        	//섬네일 파일명
	        	sThumnailFileNm = sFullFilePath + sFileNm + "_" + iWidth + "X" + iHeight + "." + sExtensionNm;
	        	//섬네일 스트림
	        	//isThumnail = ImageUtil.resize(f, iWidth, iHeight, sExtensionNm);
	        	//System.out.println("sThumnailFileNm======" + sThumnailFileNm);
	        	// 출력할 파일명과 읽어들일 파일명을지정한다.
	            File file = new File(sThumnailFileNm);
	            file.delete();
	        }
    
        //섬네일 파일 끝
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// Stream 객체를 모두 닫는다.
			e.printStackTrace();
		}
	}
}
