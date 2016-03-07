package com.b5m.sms.common.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 바이트 관련 유틸
 * <p> 
 * <수정이력> <br /> 
 * 1. 수정일: 수정자: 수정사유: <br />
 * <p>
 * @since 2013. 8. 18.
 * @version 1.0
 * @author 김병찬
 */
public class ByteUtil {

	/**
	 * InputStream to byte
	 * @since 2010. 9. 23. 
	 * @param pInputStream
	 * @return 
	 */
	public static byte[] inputStreamToByte(InputStream pInputStream) {
        if (pInputStream == null) {
            return null;
        }

        int lBufferSize = 1024;
        byte[] lByteBuffer = new byte[lBufferSize];

        int lBytesRead = 0;
        int lTotbytesRead = 0;
        int lCount = 0;

        ByteArrayOutputStream lByteArrayOutputStream = new ByteArrayOutputStream(lBufferSize * 2);

        try {
            while ((lBytesRead = pInputStream.read(lByteBuffer)) != -1) {
                lTotbytesRead += lBytesRead;
                lCount++;

                lByteArrayOutputStream.write(lByteBuffer, 0, lBytesRead);
            }
        } catch (Throwable e) {
            e.printStackTrace(System.out);
        }

        byte[] lDataBytes = lByteArrayOutputStream.toByteArray();

        return lDataBytes;
    }
	
	/**
	 * ���ϸ��� ���� ����Ʈ ���� �Ѿ���� üũ�Ѵ�.
	 * @since 2010. 9. 29. 
	 * @param sFileNm ���ϸ�
	 * @param iByte ���� ����Ʈ ��
	 * @return 
	 */
	public static boolean byteSizeCheck(String sFileNm, int iByte) {
		
		int iFileNm = sFileNm.getBytes().length;
		
		if(iFileNm >= iByte){
			return true;
		}else{
			return false;
		}
	}
}
