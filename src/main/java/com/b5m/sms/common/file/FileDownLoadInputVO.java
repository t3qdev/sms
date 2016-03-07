package com.b5m.sms.common.file;

/**
 * 파일다운로드에서 필요한 VO
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
public class FileDownLoadInputVO {
	
	// 실제 파일명
	private String sRealFileNm;
	// 저장된 파일명
	private String sFileNm;
	// 저장된 파일경로
	private String sFilePath;
	//파일경로 + 파일명
	private String sFileFullNm;
	// 파일사이즈
	private long lFileSize;

	public String getsRealFileNm() {
		return sRealFileNm;
	}

	public void setsRealFileNm(String sRealFileNm) {
		this.sRealFileNm = sRealFileNm;
	}

	public String getsFileNm() {
		return sFileNm;
	}

	public void setsFileNm(String sFileNm) {
		this.sFileNm = sFileNm;
	}

	public String getsFilePath() {
		return sFilePath;
	}

	public void setsFilePath(String sFilePath) {
		this.sFilePath = sFilePath;
	}

	public String getsFileFullNm() {
		return sFileFullNm;
	}

	public void setsFileFullNm(String sFileFullNm) {
		this.sFileFullNm = sFileFullNm;
	}

	public long getlFileSize() {
		return lFileSize;
	}

	public void setlFileSize(long lFileSize) {
		this.lFileSize = lFileSize;
	}
}
