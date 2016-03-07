package com.b5m.sms.common.filter;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.b5m.sms.common.file.FileResultVO;
import com.b5m.sms.common.file.FileUtil;
import com.b5m.sms.common.util.GlobalConstant;
import com.b5m.sms.common.util.ParseUtil;
import com.b5m.sms.common.util.PropertyUtil;
import com.b5m.sms.common.util.StringUtil;
import com.b5m.sms.common.util.ThumnailUtil;
import com.b5m.sms.common.vomap.DataBox;

/**
 * 파일업로드필터 Filter Class.
 * <p> 
 * <수정이력> <br /> 
 * 1. 수정일: 수정자: 수정사유: <br />
 * <p>
 * @since 2010. 5. 13.
 * @version 1.0
 * @author 권오준
 */
public class FileUploadFilter implements Filter {
	
	protected static Logger logger = Logger.getRootLogger();
	
	private FilterConfig config;
	
	/**
	 * Default constructor.
	 */
	public FileUploadFilter() {
	}
	
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}
	
	/* 
	 * 파일업로드 필터.
	 * @since 2010. 5. 28.
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 * @param request ServletRequest
	 * @param response ServletResponse
	 * @param chain FilterChain
	 * @throws java.io.IOException
	 * @throws javax.servlet.ServletException
	 */
	public void doFilter(ServletRequest req,ServletResponse res, FilterChain chain) throws java.io.IOException, javax.servlet.ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		
		JSONObject jsonObj = null;
		PrintWriter pw = null;
		
		try{
			
			// 기타 파라미터 정보를 담는다.
			DataBox box = new DataBox(request);
			
			if (ServletFileUpload.isMultipartContent(request)) {
	            
				String sTask = StringUtil.sGetNullStr(request.getParameter("task"), "file");

				// 업로드시 임시 저장 공간 생성
	    		DiskFileItemFactory factory = new DiskFileItemFactory();
	    		// 업로드시 임시 저장 메모리 사이즈
	    		factory.setSizeThreshold(1024*50);

	    		// 업로드 핸들러 생성
	    		ServletFileUpload upload = new ServletFileUpload(factory);
	    		// 리퀘스트당 업로드 최대 사이즈(파일전체 사이즈)
	    		upload.setSizeMax(ParseUtil.iGetParseInt(PropertyUtil.getProperty(sTask + ".upload.allfilelimit.default"), 0));
	    		upload.setHeaderEncoding("UTF-8");
	    		
	    		List<FileItem> items = null;
	    		
	    		try {
	    			// 업로드 객체에서 파일객체 받아오기
	    			items = upload.parseRequest(request);
	    		} catch (SizeLimitExceededException e) {
	    			
	    			jsonObj = new JSONObject();
        			pw = response.getWriter();
        			jsonObj.put("file_all_size_flag", "false");
        			pw.write(jsonObj.toString());
        			pw.flush();
        			pw.close();
        			
	    			throw new Exception("전체사이즈 용량초과");
	    		}
	    		
	    		//String sUserId = CookieControlUtil.vGetCookie(request, "user_id");
	    		String sUserId = "open";
	    		// 회원가입등일때
	    		
	    		if(sUserId == null || sUserId.equals("")) sUserId = "noname";
	    		
				//String sUserId = "user01";
				// 허용된 파일타입을 가져온다.
				
				String sExtensionNmAllow = PropertyUtil.getProperty(sTask + ".upload.filetype.default");
				
				// 허용된 파일타입을 배열에 넣는다.
				String[] sExtensionNmArray = StringUtil.sGetSplit(sExtensionNmAllow, "|");
				//실제파일명
				String sOriginalFilename = null;
				// 확장자명
				String sOrigExtensionNm = null;
				// 개별 파일업로드 용량
				long lFileLimit = ParseUtil.iGetParseInt(PropertyUtil.getProperty(sTask + ".upload.filelimit.default"), 0);
				// 파일업로드 기본 경로
				String sUploadRoot = PropertyUtil.getProperty(sTask + ".upload.path.root");
				// 파일업로드 하위경로
				String sUploadDir = FileUtil.getTimeDir(PropertyUtil.getProperty(sTask + ".upload.path.default"), sUserId);
				// 파일전체경로
				String sFullFilePath = sUploadRoot + sUploadDir;
				// 저장된 파일명
				String savedFileName = null;
				// 섬네일여부
				String sThumnailFlag = StringUtil.sGetNullStr(PropertyUtil.getProperty(sTask + ".thumnail.flag"), "");
				
	            Iterator iterator = items.iterator();
	            
	            // DB에 저장할 업로드 파일 정보 리스트 
				List<FileResultVO> fileInfoList = new ArrayList<FileResultVO>();
				
				while (iterator.hasNext()) {

	                FileItem item = (FileItem) iterator.next();
	                // 파일일경우
	                if (!item.isFormField()) {
	                    if (item.getSize() > 0) {
	                    	
	                    	//개별파일 용량체크
	    					if (lFileLimit < item.getSize()) {
	    						
	    						jsonObj = new JSONObject();
	                			pw = response.getWriter();
	                			jsonObj.put("file_size_flag", "false");
	                			pw.write(jsonObj.toString());
	                			pw.flush();
	                			pw.close();
	                			
	    						throw new Exception("개별 파일 용량이 초과했습니다.");
	    					}
	    					
	    					//파일 이름을 가져온다      
	    					sOriginalFilename = item.getName();
	       
	                        sOrigExtensionNm = FileUtil.getExt(sOriginalFilename);
	    					
	                        try {
		    					//허가된 확장자 체크
		    					getExtensionNmCheck(sOrigExtensionNm, sExtensionNmArray);
	                        } catch (Exception e) {
	                        	
	                        	jsonObj = new JSONObject();
	                			pw = response.getWriter();
	                			jsonObj.put("ext_flag", "false");
	                			pw.write(jsonObj.toString());
	                			pw.flush();
	                			pw.close();
	                			
	        	    			throw new Exception("허가된 파일타입이 아닙니다.");
	        	    		}
	                        
	                        File dir = new File(sFullFilePath);
	                		if (!dir.isDirectory()) {
	                			dir.mkdirs();
	                		}
	                        
	                        //sFileNm = DateUtil.sGetCurrentTime("yyyyMMddHHmmssSSS") + "." + sOrigExtensionNm;
	                        
	                        
	                        savedFileName = FileUtil.getBRSaveFileNameForCurrentTime() + "." + sOrigExtensionNm;
	                        
	                        
	                		// 파일저장
	                		File file = new File(sFullFilePath + savedFileName);
	                		item.write(file);
	                		
	                		FileResultVO fileResultVO = new FileResultVO();

	                		// 저장된 파일명
	                		fileResultVO.setSavedFileNm(savedFileName);
	                		// 파일경로(절대경로)
	                		fileResultVO.setSavedRootPath(sUploadRoot);
	                		// 파일경로(기본경로 제외)
	                		fileResultVO.setSavedFilePath(sUploadDir);
	                		// 파일풀경로
	                		fileResultVO.setSavedFileFullPath(sFullFilePath);
	                		// 실제 파일명
	                		fileResultVO.setSavedRealFileNm(sOriginalFilename);
	                		// 파일사이즈(크기)
	                		fileResultVO.setSavedfileSize(item.getSize());
	                		// 확장자명
	    					fileResultVO.setSavedExtensionNm(sOrigExtensionNm);
	    					fileInfoList.add(fileResultVO);
	    					
	    					// 섬네일여부가 Y일경우
	    					if(sThumnailFlag.equals("Y")){
	    						ThumnailUtil.makeThumnail(sFullFilePath, savedFileName, sTask, sOrigExtensionNm);
	    					}
	                    } 
	                // 일반 파라미터일 경우
	                }else{
	                	
	                	box.put(item.getFieldName(), item.getString("UTF-8"));
	                }
	            }

	            request.setAttribute(GlobalConstant.UPLOADED_FILE_LIST,fileInfoList);
			}
			
			// 파라미터를 box에 다시 담는다.
			request.setAttribute("box",box);
			chain.doFilter(req, res);
		}catch (Exception e){
			logger.error(e);
			
			jsonObj = new JSONObject();
			pw = response.getWriter();
			try {
				jsonObj.put("success_flag", "false");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pw.write(jsonObj.toString());
			pw.flush();
			pw.close();
			
			try {
				throw new Exception("파일업로드 에러");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}finally{
			
		}
		
	 }

	private void getExtensionNmCheck(String sExtensionNm, String[] sExtensionNmArray) throws Exception {

		boolean bAllow = false;

		// 허용된 파일타입 배열안의 갯수
		int sExtensionNmArrayLng = sExtensionNmArray.length;
				
		for (int i = 0; i < sExtensionNmArrayLng; i++) {
			if (sExtensionNmArray[i].equals(sExtensionNm)) {
				bAllow = true;
				break;
			}
		}

		// 값을 넣지 않을 경우 전체 파일허용
		if (sExtensionNmArrayLng == 0) {
			bAllow = true;
		}
		
		// 값을 넣지 않을 경우 전체 파일허용
		if (!bAllow) {
			throw new Exception("허가된 파일타입이 아닙니다.");
		}

	}
	
	/* 
	 * 필터 destroy.
	 * @since 2010. 6. 3.
	 * @see javax.servlet.Filter#destroy() 
	 */
	public void destroy() {
		this.config = null;
	}
	
}

