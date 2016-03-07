package com.b5m.sms.common.file.cdn;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.b5m.sms.common.util.DateUtil;
import com.b5m.sms.common.util.RandomUtil;



/**
 * Tfs-image 图片上传工具类 /사진 업로드 도구
 *
 * Created by wenda on 15-1-28.
 */
public class ImageUploadUtil {

    private static Logger logger = LoggerFactory.getLogger(ImageUploadUtil.class);

//    private static final String bsDomain = "http://10.30.99.38:8999/imageUpload";
//    private static final String tfsUrl = "http://upm01.b5m.com/";
//    private static final String userName = "duanyi";
//    private static final String topicName = "goods";
//
//    private static final String singleFileName = "file";
//    private static final String multiFileName = "files";

    
    
    private static String bsDomain ;
    private static String tfsUrl ;
    private static String userName;
    private static String topicName;

    private static String singleFileName = "file";
    private static String multiFileName = "files";
    
    
    /**
     * Static 변수 초기화를 위한 메소드. (esources/spring/context-properties.xml 에서 호출)
     * @param bsDomain
     * @param tfsUrl
     * @param userName
     * @param topicName
     */
    public static void  setConfig(String bsDomain, String tfsUrl ,String userName,String topicName){
    	ImageUploadUtil.bsDomain = bsDomain;
    	ImageUploadUtil.tfsUrl = tfsUrl;
    	ImageUploadUtil.userName = userName;
    	ImageUploadUtil.topicName = topicName;
    	
    }
  

	public static String getBsDomain() {
		return bsDomain;
	}


	public static void setBsDomain(String bsDomain) {
		ImageUploadUtil.bsDomain = bsDomain;
	}


	public static String getTfsUrl() {
		return tfsUrl;
	}


	public static void setTfsUrl(String tfsUrl) {
		ImageUploadUtil.tfsUrl = tfsUrl;
	}


	public static String getUserName() {
		return userName;
	}


	public static void setUserName(String userName) {
		ImageUploadUtil.userName = userName;
	}


	public static String getTopicName() {
		return topicName;
	}


	public static void setTopicName(String topicName) {
		ImageUploadUtil.topicName = topicName;
	}


	public static String getSingleFileName() {
		return singleFileName;
	}


	public static void setSingleFileName(String singleFileName) {
		ImageUploadUtil.singleFileName = singleFileName;
	}


	public static String getMultiFileName() {
		return multiFileName;
	}


	public static void setMultiFileName(String multiFileName) {
		ImageUploadUtil.multiFileName = multiFileName;
	}


	/**
     * 단일 쓰레드 접근만 보장해야.
     * @param imageData
     * @return
     * @throws IOException
     */
    public static  String createImageName(byte[] imageData) throws IOException {
        HttpClient httpClient = HttpClientFactory.getHttpClient();

        //HttpClient httpClient = HttpClientFactory.

        PostMethod postMethod = new PostMethod(bsDomain);
        // 使用UUID作为图片名称 //사진 이름으로 UUID를 사용하여
        /*
         	BR_SYS_FILE_YYYYMMDD24Hmmss_NNNN (Random number)
			예 : BR_SYS_FILE_2015 12 04 16 04 23_3728.jpg
			년월일시분초 에 random 4자리 입니다.
         */
        StringBuffer fileName = new StringBuffer("BR_SYS_FILE_");
        fileName.append(DateUtil.sGetCurrentTime("yyyyMMddHHmmss"));
        fileName.append("_");
        fileName.append(RandomUtil.random(10000, 1)[0]);
        
        //String fileName = "BR_SYS_FILE_"+DateUtil.getDateString()+"_"+RandomUtil.random(10000, 1)[0];;//PWCode.getUUID();

        Part[] parts = {
                new StringPart("userName", userName),
                new StringPart("topic", topicName),
                new FilePart(fileName.toString(), new ByteArrayPartSource("", imageData))
        };

        MultipartRequestEntity mpre = new MultipartRequestEntity(parts, postMethod.getParams());
        postMethod.setRequestEntity(mpre);

        httpClient.executeMethod(postMethod);

        if(logger.isInfoEnabled()) {
            logger.info("create tfs image by uuid: {}", fileName);
        }

        String body = postMethod.getResponseBodyAsString();
        
        //링크될 URL.
        return tfsUrl + extractImageName(fileName.toString(), body);
    }

    
    /**
     * 解析返回数据结果 /반환 된 데이터의 결과를 구문 분석
     */
    private static String extractImageName(String key, String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        if(jsonObject.getIntValue("code") == HttpStatus.OK.value()) {
            return  jsonObject.getJSONArray("data").getJSONObject(0).getJSONObject("data").getString(key);
        }
        return null;
    }

    /**
     * 单图上传 / 단일 이미지 업로드
     *
     * @param fileName 图片名称，不填写使用默认值 / 기본 값으로 이미지의 이름을 기입하지 않습니다
     */
    public static String singleImage(HttpServletRequest request, String fileName) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        if (null == fileName) {
            fileName = singleFileName;
        }
        MultipartFile multipartFile = multipartRequest.getFile(fileName);
        if (multipartFile.getSize() == 0 || multipartFile.isEmpty()) {
            return null;
        }
        return createImageName(multipartFile.getBytes());
    }

    /**
     * 多图上传 / 다중 이미지 업로드
     */
    public static List<String> multiImages(HttpServletRequest request, String fileNames) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        if (null == fileNames) {
            fileNames = multiFileName;
        }
        List<MultipartFile> multipartFileList = multipartRequest.getFiles(fileNames);

        List<String> list = new ArrayList<String>(multipartFileList.size());

        for (MultipartFile multipartFile : multipartFileList) {
            if (multipartFile.isEmpty()) {
                return null;
            }
            list.add(createImageName(multipartFile.getBytes()));
        }

        return list;
    }

    /**
     * 获取逗号分隔的图片地址组合 / 쉼표로 구분 된 이미지 주소 조합을 가져옵니다
     */
    public static String getCommaImages(HttpServletRequest request, String fileNames) throws Exception {
        List<String> list = multiImages(request, fileNames);
        StringBuilder builder = new StringBuilder();

        if (list == null) {
            return null;
        }
        for (String imageName : list) {
            builder.append(imageName).append(",");
        }
        return builder.substring(0, builder.length() - 1);
    }
}