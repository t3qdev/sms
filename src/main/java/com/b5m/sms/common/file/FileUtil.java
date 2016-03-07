package com.b5m.sms.common.file;

import java.io.File;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.b5m.sms.common.util.DateUtil;
import com.b5m.sms.common.util.PropertyUtil;
import com.b5m.sms.common.util.RandomUtil;

/**
 * 파일유틸
 * <p> 
 * <수정이력> <br /> 
 * 1. 수정일: 수정자: 수정사유: <br />
 * <p>
 * @since 2013. 8. 18.
 * @version 1.0
 * @author 김병찬
 */
public class FileUtil {
	
	
	/**
	 * B5M CDN에 저장할 파일 이름을 생성하는 함수.
	 * @return
	 */
	public static String getBRSaveFileNameForCurrentTime() {
		StringBuffer savefileName = new StringBuffer("BR_SYS_FILE_");
		savefileName.append(DateUtil.sGetCurrentTime("yyyyMMddHHmmss"));
		savefileName.append("_");
		savefileName.append(RandomUtil.random(10000, 1)[0]);
		return savefileName.toString();
	}
	
	

	/**
	 * 로그
	 */
	protected static Logger logger = Logger.getRootLogger();

	/**
	 * 폴더 존재 여부에 따라 폴더를 생성 한다. 
	 * @since 2010. 5. 31.
	 * @param sPath
	 */
	public static void makeDir(String sPath) {

		File fPath  = new File(sPath);
		if (!fPath.exists()){
			fPath.mkdirs();
		}
	        
	}

	/**
	 * 파일의 확장자 반환. 
	 * @since 2010. 5. 31.
	 * @param sFileNm
	 * @return String
	 */
	public static String getExt(String sFileNm) {
		String sExtType = "";
		int m;
		int iFileLength = sFileNm.length() - 1; 
		for (m=iFileLength;m>0;m--){
			char cNowChar = sFileNm.charAt(m);
			if (cNowChar=='.') break;
			sExtType = cNowChar + sExtType;
		}
		return sExtType;
	}
	
	/**
	 * 타입 반환.
	 * @since 2010. 7. 28. 
	 * @param sFileuploadType 1: 에디터, 2:프로필
	 * @return edit/member/profile
	 */
	public static  String sGetProperty(String sFileuploadType){
		String sReturn = null;
		
		//1. 에디터
		if("1".equals(sFileuploadType)){
			
			sReturn = "podo.upload.edit";
		
		//2.프로필	
		}else if("2".equals(sFileuploadType)){
			sReturn = "podo.upload.profile";
		}
		return sReturn;
	}

	/**
	 * 파일 삭제.
	 * @since 2010. 8. 9. 
	 * @param sFile 
	 */
	public static void vSetDeleteFile(String sFile){
		File fFile = new File(sFile);
		fFile.delete();
	}
	
	/**
	 * 파일명 변경.
	 * @since 2010. 8. 9. 
	 * @param sFile
	 * @param sRenameFile 
	 */
	public static void vSetRenameFile(String sFile,String sRenameFile ){
		File fFile = new File(sFile);
		File fRenameFile = new File(sRenameFile);
		fFile.renameTo(fRenameFile);
	}
	
	/**
	 * 절대경로와 상대경로를 포함해서 디렉토리를 생성한 후 상대 경로를 가져온다.
	 * @since 2010. 9. 25. 
	 * @param sUploadRoot root
	 * @param sUserId 로그인아이디
	 * @param sUploadDir (/image)
	 * @return 
	 */
	public static String makeTimeDir(String sUploadRoot, String sUserId, String sUploadDir) {

		String sFilePath = getDir(sUserId, sUploadDir);
		
		//업로드  디렉토리 작성 (업로드 패스+파일명 )
		makeDir(sUploadRoot + "/" + sFilePath);
		return sFilePath;
	}
	
	/**
	 * 상대경로를  가져온다.
	 * @since 2010. 9. 25. 
	 * @param sUserId 로그인아이디
	 * @param sUploadDir (/image)
	 * @return 
	 */
	public static String getDir(String sUserId, String sUploadDir) {

		//업로드 패스
		String sGetCurrentTime = DateUtil.sGetCurrentTime("yyyyMMdd");
		
		String sFilePath = "/" + sGetCurrentTime + "/" + sUserId + sUploadDir;
		
		return sFilePath;
	}
	
	/**
	 * 시스템시간 + 상대경로
	 * @since 2010. 9. 25. 
	 * @param sUserId 로그인아이디
	 * @return 
	 */
	public static String getTimeDir(String sUploadDir, String sUserId) {

		//시스템 날짜
		String sGetCurrentTime = DateUtil.sGetCurrentTime("yyyyMMdd");
		
//		String sFilePath = File.separator + sGetCurrentTime + sUploadDir;
		String sFilePath =  sUploadDir + "/" + sGetCurrentTime + "/" + sUserId + "/";
		
		return sFilePath;
	}
	
	/**
	 * 파일사이즈 구하기
	 * @since 2010. 9. 25. 
	 * @param sUserId 로그인아이디
	 * @param sUploadDir 상대경로
	 * @return 
	 */
	public static long getFileSize(String sFilePath, String sFileNm) {

		File oFile = new File(sFilePath + sFileNm);

		long lFileSize = 0;
	    if (oFile.exists()) {
	    	lFileSize = oFile.length();
	    }
	    
	    return lFileSize;
	}
	
	/**
	 * 엑셀 템프파일명
	 * @since 2010. 9. 25. 
	 * @param sUserId 로그인아이디
	 * @param sUploadDir 상대경로
	 * @return 
	 */
	public static String getExcelTmp() {
		// 파일업로드 기본 경로
		String sUploadRoot = PropertyUtil.getProperty("tmp.excel.download.path");
		String sFilePath = sUploadRoot + "/" + DateUtil.sGetCurrentDate("yyyyMMdd") + "/" + DateUtil.sGetCurrentTime("HH") + "/" + UUID.randomUUID().toString().replace("-", "") + "/";
	    return sFilePath;
	}
}
