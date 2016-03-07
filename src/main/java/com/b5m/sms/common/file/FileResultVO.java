package com.b5m.sms.common.file;

/**
 * 파일결과를 담는 VO
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
public class FileResultVO {

	// 저장된 파일명
	private String savedFileNm = null;
	// 절대파일경로
	private String savedRootPath = null;
	// 파일경로(기본경로 제외)
	private String savedFilePath = null;
	//파일전체경로
	private String savedFileFullPath;
	// 실제 파일명
	private String savedRealFileNm = null;
	// 확장자명
	private String savedExtensionNm = null;
	// 파일사이즈(크기)
	private long savedfileSize;
	
	// CDN 파일 URL
	private String cdnUrl;
	
	// 파일 종류( 대표/목록, ...)
	private String fileBizType;

	
	
	public String getSavedFileNm() {
		return savedFileNm;
	}

	public void setSavedFileNm(String savedFileNm) {
		this.savedFileNm = savedFileNm;
	}

	public String getSavedRootPath() {
		return savedRootPath;
	}

	public void setSavedRootPath(String savedRootPath) {
		this.savedRootPath = savedRootPath;
	}

	public String getSavedFilePath() {
		return savedFilePath;
	}

	public void setSavedFilePath(String savedFilePath) {
		this.savedFilePath = savedFilePath;
	}

	public String getSavedFileFullPath() {
		return savedFileFullPath;
	}

	public void setSavedFileFullPath(String savedFileFullPath) {
		this.savedFileFullPath = savedFileFullPath;
	}

	public String getSavedRealFileNm() {
		return savedRealFileNm;
	}

	public void setSavedRealFileNm(String savedRealFileNm) {
		this.savedRealFileNm = savedRealFileNm;
	}

	public String getSavedExtensionNm() {
		return savedExtensionNm;
	}

	public void setSavedExtensionNm(String savedExtensionNm) {
		this.savedExtensionNm = savedExtensionNm;
	}

	public long getSavedfileSize() {
		return savedfileSize;
	}

	public void setSavedfileSize(long savedfileSize) {
		this.savedfileSize = savedfileSize;
	}

	public String getCdnUrl() {
		return cdnUrl;
	}

	public void setCdnUrl(String cdnUrl) {
		this.cdnUrl = cdnUrl;
	}

	public String getFileBizType() {
		return fileBizType;
	}

	public void setFileBizType(String fileBizType) {
		this.fileBizType = fileBizType;
	}

	@Override
	public String toString() {
		return "FileResultVO [savedFileNm=" + savedFileNm + ", savedRootPath="
				+ savedRootPath + ", savedFilePath=" + savedFilePath
				+ ", savedFileFullPath=" + savedFileFullPath
				+ ", savedRealFileNm=" + savedRealFileNm
				+ ", savedExtensionNm=" + savedExtensionNm + ", savedfileSize="
				+ savedfileSize + ", cdnUrl=" + cdnUrl + ", fileBizType="
				+ fileBizType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cdnUrl == null) ? 0 : cdnUrl.hashCode());
		result = prime * result
				+ ((fileBizType == null) ? 0 : fileBizType.hashCode());
		result = prime
				* result
				+ ((savedExtensionNm == null) ? 0 : savedExtensionNm.hashCode());
		result = prime
				* result
				+ ((savedFileFullPath == null) ? 0 : savedFileFullPath
						.hashCode());
		result = prime * result
				+ ((savedFileNm == null) ? 0 : savedFileNm.hashCode());
		result = prime * result
				+ ((savedFilePath == null) ? 0 : savedFilePath.hashCode());
		result = prime * result
				+ ((savedRealFileNm == null) ? 0 : savedRealFileNm.hashCode());
		result = prime * result
				+ ((savedRootPath == null) ? 0 : savedRootPath.hashCode());
		result = prime * result
				+ (int) (savedfileSize ^ (savedfileSize >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileResultVO other = (FileResultVO) obj;
		if (cdnUrl == null) {
			if (other.cdnUrl != null)
				return false;
		} else if (!cdnUrl.equals(other.cdnUrl))
			return false;
		if (fileBizType == null) {
			if (other.fileBizType != null)
				return false;
		} else if (!fileBizType.equals(other.fileBizType))
			return false;
		if (savedExtensionNm == null) {
			if (other.savedExtensionNm != null)
				return false;
		} else if (!savedExtensionNm.equals(other.savedExtensionNm))
			return false;
		if (savedFileFullPath == null) {
			if (other.savedFileFullPath != null)
				return false;
		} else if (!savedFileFullPath.equals(other.savedFileFullPath))
			return false;
		if (savedFileNm == null) {
			if (other.savedFileNm != null)
				return false;
		} else if (!savedFileNm.equals(other.savedFileNm))
			return false;
		if (savedFilePath == null) {
			if (other.savedFilePath != null)
				return false;
		} else if (!savedFilePath.equals(other.savedFilePath))
			return false;
		if (savedRealFileNm == null) {
			if (other.savedRealFileNm != null)
				return false;
		} else if (!savedRealFileNm.equals(other.savedRealFileNm))
			return false;
		if (savedRootPath == null) {
			if (other.savedRootPath != null)
				return false;
		} else if (!savedRootPath.equals(other.savedRootPath))
			return false;
		if (savedfileSize != other.savedfileSize)
			return false;
		return true;
	}

	

}
