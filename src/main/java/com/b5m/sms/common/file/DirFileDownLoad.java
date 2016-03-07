package com.b5m.sms.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 파일다운로드 클래스(일반파일용)
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
public class DirFileDownLoad extends FileDownLoad {
	
	public DirFileDownLoad() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public InputStream excuteFileDownLoad(FileDownLoadInputVO fileDownLoadInputVO) throws Exception {
		
		InputStream is = null;
		
		try{
			File file = new File(fileDownLoadInputVO.getsFileFullNm());
			is = new FileInputStream(file);
			
		} catch(Exception e){
			e.printStackTrace();
		} 
		
		return is;
	}
}
