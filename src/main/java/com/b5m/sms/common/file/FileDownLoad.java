package com.b5m.sms.common.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 파일다운로드 클래스(스트리밍 방식으로 화면단에서 다운로드 창이 뜬다)
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
public abstract class FileDownLoad {

	/**
	 * 파일다운로드
	 * @since 2010. 10. 1. 
	 * @param request
	 * @param response
	 * @param fis 경로+파일명 스트림
	 * @param sRealFileNm 실제 파일명
	 * @param iFileLength 파일사이즈
	 */
	public void excutefileDownLoad(HttpServletRequest request, HttpServletResponse response, FileDownLoadInputVO fileDownLoadInputVO) {
		OutputStream out = null;
		InputStream is = null;
		try {
			String sRealFileNm = fileDownLoadInputVO.getsRealFileNm();
			if(request.getHeader("User-Agent").contains("MSIE") || request.getHeader("User-Agent").contains("rv:11.0")) { 
				sRealFileNm = URLEncoder.encode(sRealFileNm, "UTF-8");
				sRealFileNm = sRealFileNm.replaceAll("\\+", " "); 
	
				response.setHeader("Content-Disposition", "attachment; filename=\"" + sRealFileNm + "\""); 
				response.setHeader("Content-Transfer-Encoding", "binary"); 
				//response.setContentLength((int) lFileLength); 
				response.setHeader("Content-Length", Long.toString(fileDownLoadInputVO.getlFileSize())); 
				response.setContentType("application/octet-stream"); 
				response.setHeader("Connection", "close"); 
			} else { 
				response.setContentType("application/octet-stream");
				//response.setContentLength((int) lFileLength); 
				response.setHeader("Content-Disposition", "attachment; fileName=\"" + new String(sRealFileNm.getBytes("UTF-8"),"ISO-8859-1") + "\";");
				response.setHeader("Content-Transfer-Encoding", "binary");
				response.setHeader("Content-Length", Long.toString(fileDownLoadInputVO.getlFileSize())); 
				response.setHeader("Connection", "close"); 
			} 
			
			is = excuteFileDownLoad(fileDownLoadInputVO);
			
			byte[] b = new byte[1024]; // 파일 크기
			int read = 0;
		
			out = response.getOutputStream();
//			System.out.println("fileDownLoadInputVO.getlFileSize()===>"+fileDownLoadInputVO.getlFileSize());
			while((read=is.read(b)) != -1){
				out.write(b, 0, read);
			}
			
			out.flush();
			
		} catch (Exception e) {

			String sENm = e.getClass().getName();
			if(sENm.equals("org.apache.catalina.connector.ClientAbortException")){
				//System.out.println("사용자 다운로드 취소");
			}else{
				e.printStackTrace();
			}

		} finally{
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				e.printStackTrace();
				}
			}
		}
	}
	
	public abstract InputStream excuteFileDownLoad(FileDownLoadInputVO fileDownLoadInputVO) throws Exception;
}
