package com.b5m.sms.common.util;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.csvreader.CsvWriter;

public class CsvUtil {
    /**
     * list 객체의 field 값을 CSV 파일로 생성하여 그 파일의 내용을 byte[] 형태로 리턴한다.(실제로 서버단에서 파일을 저장하지는 않음)<br />
     * @param list : vo객체 리스트
     * @param filename : 파일명
     * @param out : out에 기록한다.(기록 후 close 하지 않음)
     * @param mapField : CSV에 넣을 vo의 field들, [0]:field명, [1]:Excel 파일의 타이틀 명
     * mapField 인수가 없는 경우 vo의 모든 getter를 CSV 파일로 만든다.(예외 : getClass())
     * @throws Exception
     */
    public static String createCSV(Collection<?> list, Field [] fields) throws Exception {
    	ByteArrayOutputStream stream = new ByteArrayOutputStream();
        CsvWriter csv = new CsvWriter(stream, ',', Charset.defaultCharset());
        
        //String[] fieldNames = new String[fields.length];  --> 박병훈 버그 수정 
        String[] fieldNames = null;
        if(fields!=null) fieldNames = new String[fields.length];
        
        
        // field 인수가 없는 경우  모든 getter를 title로 사용하도록 한다.
        if (fields == null || fields.length == 0) {
            Object obj = list.toArray()[0];
            Method[] arr = obj.getClass().getMethods();
            List<String> fieldList = new ArrayList<String>();
            for (Method method : arr) {
                String methodName = method.getName();
                if (!methodName.startsWith("get")) continue;    //getter만 체크하기 위함
                if ("getClass".equals(methodName)) continue;    //getter만 체크하기 위함
                
                String methodNameRemovedGet = methodName.substring(3,4).toUpperCase() + methodName.substring(4);
                fieldList.add(methodNameRemovedGet);
            }
            fieldNames = fieldList.toArray(new String[0]);
            
        }
        
        //타이틀 설정
        csv.writeRecord(fieldNames);
        
        for (Object obj : list) {
            String[] arr = new String[fieldNames.length];
            int i=0;
            for (String fieldName : fieldNames) {
                if (BrandStoreValidator.isBlankOrNull(fieldName)) continue;
                
                String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1); 
                Method getter = obj.getClass().getMethod(getterName);
                Object valueObj = getter.invoke(obj);
                arr[i] = (valueObj == null ? "" : valueObj.toString());
                i++;
            }
            
            //1줄씩 추가
            csv.writeRecord(arr);
        }
        
        csv.close();
        
        byte[] buffer = ((ByteArrayOutputStream) stream).toByteArray();
        stream.close();
        
        String data = Charset.forName("UTF-8").decode(
				ByteBuffer.wrap(buffer)).toString();

        return data;
    }
}


