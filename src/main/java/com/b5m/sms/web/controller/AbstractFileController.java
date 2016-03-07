package com.b5m.sms.web.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.b5m.sms.common.file.FileResultVO;
import com.b5m.sms.common.file.FileUtil;
import com.b5m.sms.common.file.cdn.ImageUploadUtil;

public class AbstractFileController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFileController.class);
	/**
	 * Size of a byte buffer to read/write file
	 */
	final static int BUFFER_SIZE = 4096;
	//final static String OPT_B5C_DISK = File.separator + "opt" + File.separator + "b5c-disk" + File.separator;
	static String OPT_B5C_DISK;
	
	
	

	public static String getOPT_B5C_DISK() {
		return OPT_B5C_DISK;
	}

	public static void setOPT_B5C_DISK(String oPT_B5C_DISK) {
		OPT_B5C_DISK = oPT_B5C_DISK;
	}

	/**
	 * 생성된 엑셀을 바로 HttpServletResponse에 적는다.
	 * 
	 * @param response
	 * @param fileName
	 * @param workbook
	 * @throws IOException
	 */
	public void writeExcelAttachmentForDownload(HttpServletResponse response, String fileName, Workbook workbook) throws IOException {
		// write it as an excel attachment

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		workbook.write(outByteStream);

		response.setContentType("application/ms-excel");
		byte[] outArray = outByteStream.toByteArray();
		response.setContentLength(outArray.length);
		response.setHeader("Expires:", "0"); // eliminates browser caching
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		OutputStream outStream = response.getOutputStream();

		ByteArrayInputStream inputStream = new ByteArrayInputStream(outArray);

		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;

		// write bytes read from the input stream into the output stream
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}

		if(inputStream!=null)inputStream.close();
		outStream.flush();
		if(outStream!=null)outStream.close();
	}

	/**
	 * 생성된 엑셀을 파일로 생성하고, 생성된 파일을 다운로드 하도록 한 후 파일을 삭제 한다.
	 * 
	 * @param response
	 * @param fileName
	 * @param workbook
	 * @throws IOException
	 */
	public void writeExcelFileAndFileDownload(HttpServletResponse response, String fileName, Workbook workbook) throws IOException {

		// 필요시 파일에 쓰고, 파일에서 읽어 올 수 있음.
		// 출력 파일 위치및 파일명 설정
		FileOutputStream outFile;
		try {
			outFile = new FileOutputStream(fileName);
			workbook.write(outFile);
			outFile.close();

			LOGGER.debug("다운로드를 위한 임의 파일생성 완료");
		} catch (Exception e) {
			e.printStackTrace();
		}

		File downloadFile = new File(fileName);
		FileInputStream inputStream = new FileInputStream(downloadFile);

		response.setContentType("application/ms-excel");
		response.setContentLength((int) downloadFile.length());
		response.setHeader("Expires:", "0"); // eliminates browser caching
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		OutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;

		// write bytes read from the input stream into the output stream
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}

		if(inputStream!=null)inputStream.close();
		outStream.flush();
		if(outStream!=null)outStream.close();

		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
			System.out.println(file.getAbsolutePath());
			LOGGER.debug("다운로드를 위한 임의 파일 삭제 완료");
		}

	}

	/**
	 *  주어진 경로(@param fullPath)에 있는 파일을 웹을 통해 다운로드 한다.
	 * 
	 * @param request
	 * @param response
	 * @param fullPath
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, final String fullPath) throws FileNotFoundException, IOException {
		File downloadFile = new File(fullPath);
		FileInputStream inputStream = new FileInputStream(downloadFile);

		// MIME Type 파일로 부터 얻어오기.
		ServletContext context = request.getSession().getServletContext();
		String mimeType = context.getMimeType(fullPath);
		if (mimeType == null) {
			// set to binary type if MIME mapping not found
			mimeType = "application/octet-stream";
		}
		LOGGER.debug("MIME type: " + mimeType);

		// Content 특성 정의
		response.setContentType(mimeType);
		response.setContentLength((int) downloadFile.length());

		// Header 값 정의
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
		response.setHeader(headerKey, headerValue);

		// get output stream of the response
		OutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;

		// write bytes read from the input stream into the output stream
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}

		if(inputStream!=null)inputStream.close();
		outStream.flush();
		if(outStream!=null)outStream.close();
	}

	/**
	 * 멀티파트 파일을 지정한 이름(@param systemFileName)으로 파일저장소(@param filePath)에 저장.
	 * 
	 * @param multiPartFile
	 * @param systemFileName
	 * @param filePath
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void writeFileToDiskFromMultipartFile(MultipartFile multiPartFile, final String systemFileName, final String filePath) throws IOException, FileNotFoundException {
		byte[] fileBytes = multiPartFile.getBytes();

		final File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}

		final String savedFileFullPath = filePath + systemFileName;

		LOGGER.debug("==== 파일저장 전체 경로 :" + savedFileFullPath);

		// 파일쓰기
		final InputStream is = new ByteArrayInputStream(fileBytes);
		final OutputStream os = new FileOutputStream(savedFileFullPath);
		int numRead;
		while ((numRead = is.read(fileBytes, 0, fileBytes.length)) != -1) {
			os.write(fileBytes, 0, numRead);
		}
		if (is != null) {
			is.close();
		}
		os.flush();
		if(os!=null)os.close();
	}

	/**
	 * Validation 오류 내용 설정 (Cell 에 메모 생성함)
	 * 
	 * @param cell
	 * @param text
	 */
	public void setExcelCellComment(Cell cell, String text) {
		if(cell.getCellComment()!=null){
			cell.removeCellComment();
		}
		Sheet sheet = cell.getSheet();
		Workbook book = sheet.getWorkbook();
		Row row = cell.getRow();

		Drawing drawing = sheet.createDrawingPatriarch();
		CreationHelper factory = book.getCreationHelper();

		ClientAnchor anchor = factory.createClientAnchor();
		anchor.setCol1(cell.getColumnIndex());
		anchor.setCol2(cell.getColumnIndex() + 2);
		anchor.setRow1(row.getRowNum());
		anchor.setRow2(row.getRowNum() + 2);

		Comment comment = drawing.createCellComment(anchor);
		comment.setString(factory.createRichTextString(text));

		cell.setCellComment(comment);
	}

	/**
	 * 멀티파트 파일을 CDN에 업로드 하고, FileResultVO 에 원래파일명, 시스템파일명,CDN파일명과 FileBizType을
	 * 넣어서 리턴 한다.
	 * 
	 * @param MultipartFile
	 *            multiPartFile
	 * @param fileBizType
	 * @return
	 * @throws IOException
	 */
	public FileResultVO uploadMultipartFileToCDN(MultipartFile multiPartFile, String fileBizType) throws IOException {
		FileResultVO fileResultVO = uploadMultipartFileToCDN(multiPartFile);
		fileResultVO.setFileBizType(fileBizType);
		return fileResultVO;
	}

	/**
	 * 멀티파트 파일을 CDN에 업로드 하고, FileResultVO 에 원래파일명, 시스템파일명,CDN파일 명을 넣어서 리턴 한다.
	 * 
	 * @param MultipartFile
	 *            multiPartFile
	 * @return
	 * @throws IOException
	 */
	public FileResultVO uploadMultipartFileToCDN(MultipartFile multiPartFile) throws IOException {

		FileResultVO fileResultVO = new FileResultVO();
		if (!multiPartFile.isEmpty()) {
			String fileName = multiPartFile.getOriginalFilename();
			try {
				byte[] fileBytes = multiPartFile.getBytes();
				final String systemFileName = FileUtil.getBRSaveFileNameForCurrentTime() + "." + FileUtil.getExt(fileName);

				String cdnUrl = ImageUploadUtil.createImageName(fileBytes);
				// 대표이미지 플래그
				// fileResultVO.setFileBizType(itr.next());
				// 원래파일이름 name에서 가지고옴
				fileResultVO.setSavedRealFileNm(fileName);
				// 시스템파일명 systemFileName
				fileResultVO.setSavedFileNm(systemFileName);
				// CDN RepImg URL == cdnUrl
				fileResultVO.setCdnUrl(cdnUrl);
				LOGGER.info(fileResultVO.toString());

			} catch (IOException e) {
				throw e;
			}
		}
		return fileResultVO;
	}

	/**
	 * HTML5 기준 request에서 파일 정보 꺼내서 CDN에 업로드 하고, 결과 리턴.
	 * 
	 * @param req
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public FileResultVO fileUploadToCDN(HttpServletRequest req) throws IOException {
		final String fileName = req.getHeader("file-name");
		FileResultVO fileResultVO = new FileResultVO();
		// 파일쓰기
		InputStream is = null;
		try {
			is = req.getInputStream();

			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			int nRead = -1;
			byte[] data = new byte[BUFFER_SIZE];

			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			buffer.flush();

			final String systemFileName = FileUtil.getBRSaveFileNameForCurrentTime() + "." + FileUtil.getExt(fileName);

			String cdnUrl = ImageUploadUtil.createImageName( buffer.toByteArray());
			// 원래파일이름 name에서 가지고옴
			fileResultVO.setSavedRealFileNm(fileName);
			// 시스템파일명 systemFileName
			fileResultVO.setSavedFileNm(systemFileName);
			// CDN RepImg URL == cdnUrl
			fileResultVO.setCdnUrl(cdnUrl);
			LOGGER.info(fileResultVO.toString());

			fileResultVO.setCdnUrl(cdnUrl);
		} finally {
			if (is != null)
				is.close();
		}

		return fileResultVO;
	}

	/**
	 * None HTML5 기준 request에서 파일 정보 꺼내서 CDN에 업로드 하고, 결과 리턴.
	 * 
	 * @param fileItem
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public FileResultVO uploadFileItemToCDN(FileItem fileItem) throws IOException {
		String fileName = fileItem.getName().substring(fileItem.getName().lastIndexOf(File.separator) + 1);

		FileResultVO fileResultVO = new FileResultVO();
		InputStream is = null;
		try {
			// 파일쓰기
			is = fileItem.getInputStream();

			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			int nRead = -1;
			byte[] data = new byte[BUFFER_SIZE];

			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			buffer.flush();

			final String systemFileName = FileUtil.getBRSaveFileNameForCurrentTime() + "." + FileUtil.getExt(fileName);
			// CDN UPLoad
			String cdnUrl = ImageUploadUtil.createImageName( buffer.toByteArray());
			// 원래파일이름 name에서 가지고옴
			fileResultVO.setSavedRealFileNm(fileName);
			// 시스템파일명 systemFileName
			fileResultVO.setSavedFileNm(systemFileName);
			// CDN RepImg URL == cdnUrl
			fileResultVO.setCdnUrl(cdnUrl);
		} finally {
			if (is != null)
				is.close();
		}

		return fileResultVO;
	}

	/**
	 * 멀티파트 파일을 지정된 파일 위치(OPT_B5C_DISK) 에 저장하고, FileResultVO 에 원래파일명, 시스템파일명을
	 * 넣어서 리턴 한다.
	 * 
	 * @param multiPartFile
	 * @return FileResultVO fileResultVO
	 * @throws IOException
	 */
	public FileResultVO uploadMultipartFileToDisk(MultipartFile multiPartFile) throws IOException {
		FileResultVO fileResultVO = new FileResultVO();
		if (!multiPartFile.isEmpty()) {

			String fileName = multiPartFile.getOriginalFilename();

			final String systemFileName = FileUtil.getBRSaveFileNameForCurrentTime() + "." + FileUtil.getExt(fileName);

			try {
				writeFileToDiskFromMultipartFile(multiPartFile, systemFileName, OPT_B5C_DISK);

				fileResultVO.setSavedRealFileNm(fileName);
				fileResultVO.setSavedFileNm(systemFileName);

			} catch (IOException e) {
				throw e;
			}
		}
		return fileResultVO;
	}

}