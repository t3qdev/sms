package com.b5m.sms.common.vomap;

import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * equest로 넘어온 파라메터들을 파싱하여 쉽게 사용할 수 있게 만든 유틸리티 클래스
 * <p> 
 * <수정이력> <br /> 
 * 1. 수정일: 수정자: 수정사유: <br />
 * <p>
 * @since 2013. 8. 18.
 * @version 1.0
 * @author 김병찬
 */
public class DataBox {

	private static final Log log = LogFactory.getLog(DataBox.class);

	public static final String KEY = "DATABOX";

	private String Name = "DATA_BOX";

	protected Map<String, Object> table = new HashMap<String, Object>();

	private static final boolean DEFAULT_BOOLEAN_VALUE = false;

	/**
	 * 기본 생성자
	 */
	public DataBox() {
		this.Name = "";
	}

	/**
	 * DataBox 이름을 받아 클래스에 셋팅하여 유니크하게 유지
	 *
	 * @param Name
	 *            DataBox명
	 */
	public DataBox(String Name) {
		this.Name = Name;
	}

	/**
	 * 데이터박스명과 HttpServletRequest를 파라메터로 받아 생성
	 *
	 * @param name
	 *            데이터박스명
	 * @param request
	 *            HttpServletRequest
	 * @throws ServletException
	 */
	public DataBox(String name, HttpServletRequest request)
			throws ServletException {
		this.Name = name;
		setDataBox(request);
	}

	/**
	 * HttpServletRequest를 파라메터로 받아 생성
	 *
	 * @param request
	 *            HttpServletRequest
	 * @throws ServletException
	 */
	public DataBox(HttpServletRequest request) throws ServletException {
		setDataBox(request);
	}

	/**
	 * 데이터박스명을 반환
	 *
	 * @return 데이터박스명
	 */
	public String getName() {
		return Name;
	}

	/**
	 * 데이터박스명을 셋팅
	 *
	 * @param name
	 *            데이터박스명
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * 데이터박스에서 key에 해당하는 데이터를 스트링으로 반환
	 *
	 * @param key
	 *            가져올 key명
	 * @return 스트링으로된 데이터 값
	 */
	public String getString(String key) {
		String value = null;
		if (get(key) == null)
			value = "";
		else {
			if (get(key) instanceof String[])
				value = ((String[]) get(key))[0];
			else
				value = (String) get(key);
		}

		return value;

	}

	/**
	 * 데이터박스에서 key에 해당하는 데이터를 String[] 반환
	 *
	 * @param key
	 *            가져올 key명
	 * @return 스트링배열로 된 데이터 값
	 */
	public String[] getValues(String key) {
		if (get(key) instanceof Object[]) {
			return (String[]) get(key);
		} else {
			if (get(key) == null)
				return null;
			else {
				String retS[] = new String[1];
				retS[0] = get(key).toString();
				return retS;
			}
		}
	}

	/**
	 * 데이터박스에서 key에 해당하는 데이터를 반환
	 *
	 * @param key
	 *            가져올 key명
	 * @return key에 해당하는 데이터
	 */
	public Object get(String key) {
		return table.get(key);
	}

	/**
	 * 데이터박스에 있는 데이터를 Map 형태로 반환
	 *
	 * @return Map
	 */
	public Map<String, Object> getAll() {
		return table;
	}

	/**
	 * http로 전달된 parameter의 값을 boolean 타입으로 반환한다. 파라매터의 값이 적절하지 않거나 해당 파라매터가
	 * 존재하지 않으면 defaultValue를 반환한다.
	 *
	 * @param name
	 *            파라매터의 이름
	 * @param defaultValue
	 *            파라매터가 존재하지 않을 경우에 반환될 default 값
	 * @return boolean 타입의 파라매터 값
	 */
	public boolean getBoolean(String key, boolean defaultValue) {
		boolean value = defaultValue;

		String oldValue = getString(key);

		if (oldValue == null)
			return value;
		else
			oldValue = oldValue.toUpperCase();

		if ("TRUE".equals(oldValue) || "1".equals(oldValue)
				|| "Y".equals(oldValue))
			value = true;
		else if ("FALSE".equals(oldValue) || "0".equals(oldValue)
				|| "N".equals(oldValue))
			value = false;

		return value;
	}

	/**
	 * key에 해당하는 데이터를 boolean 형으로 반환
	 *
	 * @param name
	 *            가져올 key명
	 * @return boolean
	 */
	public boolean getBoolean(String name) {
		return getBoolean(name, DEFAULT_BOOLEAN_VALUE);
	}

	/**
	 * key에 해당하는 데이터를 double 형으로 반환
	 *
	 * @param key
	 *            가져올 key명
	 * @return double형 데이터
	 */
	public double getDouble(String key) {
		return Double.parseDouble(getString(key));
	}

	/**
	 * key에 해당하는 데이터를 double 형으로 반환
	 *
	 * @param key
	 *            가져올 key명
	 * @param defaultValue
	 *            double 형이 아니거나 할 경우에 셋팅할 기본값
	 * @return double 형 데이터
	 */
	public double getDouble(String key, double defaultValue) {
		double result = defaultValue;
		try {
			result = Double.parseDouble(getString(key));
		} catch (Exception ex) {
		}
		return result;
	}

	/**
	 * key에 해당하는 데이터를 float 형으로 반환
	 *
	 * @param key
	 *            가져올 key명
	 * @return float 형 데이터
	 */
	public float getFloat(String key) {
		return Float.parseFloat(getString(key));
	}

	/**
	 * key에 해당하는 데이터를 float 형으로 반환
	 *
	 * @param key
	 *            가져올 key명
	 * @param defaultValue
	 *            float 형이 아니거나 할 경우에 셋팅할 기본값
	 * @return float 형 데이터
	 */
	public float getFloat(String key, float defaultValue) {
		float result = defaultValue;
		try {
			result = Float.parseFloat(getString(key));
		} catch (Exception ex) {
		}
		return result;
	}

	/**
	 * key에 해당하는 데이터를 BigDecimal형으로 반환
	 *
	 * @param key
	 *            가져올 key명
	 * @return BigDecimal 형 데이터
	 */
	public BigDecimal getBigDecimal(String key) {
		return new BigDecimal(getString(key));
	}

	/**
	 * key에 해당하는 데이터를 Int형으로 반환
	 *
	 * @param key
	 *            가져올 key명
	 * @return int 형 데이터
	 */
	public int getInt(String key) {
		return Integer.parseInt(getString(key));
	}

	/**
	 * key에 해당하는 데이터를 Int형으로 반환
	 *
	 * @param key
	 *            가져올 key명
	 * @param defaultValue
	 *            int 형이 아니거나 할 경우에 셋팅할 기본값
	 * @return int 형 데이터
	 */
	public int getInt(String key, int defaultValue) {
		int result = defaultValue;
		try {
			result = Integer.parseInt(getString(key));
		} catch (Exception ex) {
		}
		return result;
	}

	/**
	 * key에 해당하는 데이터를 Int형으로 반환
	 *
	 * @param key
	 *            가져올 key명
	 * @return
	 */
	public long getLong(String key) {
		return Long.parseLong(getString(key));
	}

	/**
	 * key에 해당하는 데이터를 Int형으로 반환
	 *
	 * @param key
	 *            가져올 key명
	 * @param defaultValue
	 * @return
	 */
	public long getInt(String key, long defaultValue) {
		long result = defaultValue;
		try {
			result = Long.parseLong(getString(key));
		} catch (Exception ex) {
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Data Box \n Name : " + Name + "\n [" + getQueryString()
				+ "]\n ";
	}

	/**
	 * key와 double형인 데이터타입으로 이루어진 데이터를 Map에 저장
	 *
	 * @param key
	 *            저장할 key
	 * @param Data
	 *            저장할 double형 데이터타입
	 */
	public void put(String key, double Data) {
		table.put(key, Data + "");
	}

	/**
	 * key와 float형인 데이터타입으로 이루어진 데이터를 Map에 저장
	 *
	 * @param key
	 *            저장할 key
	 * @param Data
	 *            저장할 float형 데이터타입
	 */
	public void put(String key, float Data) {
		table.put(key, Data + "");
	}

	/**
	 * key와 int형인 데이터타입으로 이루어진 데이터를 Map에 저장
	 *
	 * @param key
	 *            저장할 key
	 * @param Data
	 *            저장할 int형 데이터타입
	 */
	public void put(String key, int Data) {
		table.put(key, Data + "");
	}

	/**
	 * key와 long형인 데이터타입으로 이루어진 데이터를 Map에 저장
	 *
	 * @param key
	 *            저장할 key
	 * @param Data
	 *            저장할 long형 데이터타입
	 */
	public void put(String key, long Data) {
		table.put(key, Data + "");
	}

	/**
	 * key와 Object로 이루어진 데이터를 Map에 저장
	 *
	 * @param key
	 *            저장할 key
	 * @param Data
	 *            저장할 Object data
	 */
	public void put(String key, Object Data) {
		table.put(key, Data);
	}

	/**
	 * key와 int형인 데이터타입으로 이루어진 데이터를 Map에 저장
	 *
	 * @param key
	 *            저장할 key
	 * @param Data
	 *            저장할 int형 데이터타입
	 */
	public void putInt(String key, int Data) {
		table.put(key, Data);
	}
	
	/**
	 * key와 float형인 데이터타입으로 이루어진 데이터를 Map에 저장
	 *
	 * @param key
	 *            저장할 key
	 * @param Data
	 *            저장할 float형 데이터타입
	 */
	public void putFloat(String key, float Data) {
		table.put(key, Data);
	}
	
	/**
	 * key와 double형인 데이터타입으로 이루어진 데이터를 Map에 저장
	 *
	 * @param key
	 *            저장할 key
	 * @param Data
	 *            저장할 double형 데이터타입
	 */
	public void putDouble(String key, double Data) {
		table.put(key, Data);
	}
	
	/**
	 * key와 long형인 데이터타입으로 이루어진 데이터를 Map에 저장
	 *
	 * @param key
	 *            저장할 key
	 * @param Data
	 *            저장할 long형 데이터타입
	 */
	public void putLong(String key, long Data) {
		table.put(key, Data);
	}
	
	/**
	 * key와 BigDecimal형인 데이터타입으로 이루어진 데이터를 Map에 저장
	 *
	 * @param key
	 *            저장할 key
	 * @param Data
	 *            저장할 BigDecimal형 데이터타입
	 */
	public void putBigDecimal(String key, BigDecimal Data) {
		table.put(key, Data);
	}
	
	/**
	 * key에 해당하는 속성을 삭제
	 *
	 * @param key
	 *            삭제할 key
	 */
	public void remove(String key) {
		table.remove(key);
	}

	/**
	 * Map 으로 된 데이터를 셋팅
	 *
	 * @param table
	 *            Map
	 */
	public void setAll(Map<String, Object> table) {
		this.table = table;
	}

	/**
	 * 데이터 박스에 있는 내용을 쿼리스트링으로 반환
	 *
	 * @return 키와 값으로 이루어진 쿼리스트링을 반환 ex) key=value&key2=value2
	 */
	public String getQueryString() {

		StringBuffer queryStr = new StringBuffer();

		TreeMap<String, Object> allParam = new TreeMap<String, Object>(table);
		Iterator<String> keySet = null;
		String key = null;

		keySet = allParam.keySet().iterator();
		Object obj = null;
		while (keySet.hasNext()) {
			key = (String) keySet.next();
			obj = allParam.get(key);
			if (obj != null && obj.getClass().isArray()) {
				String[] values = (String[]) obj;
				for (int i = 0; values.length > i; i++) {
					queryStr.append(key).append("=").append(values[i]).append(
							"&");
				}
			} else {
				queryStr.append(key).append("=").append(allParam.get(key));
				if (keySet.hasNext())
					queryStr.append("&");
			}

		}

		// 마지막에 '&' 가 붙을 경우 제거한다.
		if (queryStr.length() > 0
				&& queryStr.toString().substring(queryStr.length() - 1,
						queryStr.length()).equals("&")) {
			queryStr.deleteCharAt(queryStr.toString().lastIndexOf("&"));
		}

		return queryStr.toString();
	}

	/**
	 * 인코딩된 쿼리스트링으로 반환
	 *
	 * @return 키와 값으로 이루어진 쿼리스트링을 반환 ex) key=value&key2=value2
	 */
	public String getEncodeQueryString() {
		return Encoder.encode(getQueryString());
	}

	/**
	 * 리스트를 표현하기 위해 가장 기본이 되는 정보를 <form> tag 의 hidden paramer로 반환한다.
	 *
	 * @return HTML <form> tag 내부에서 사용할 수 있는 hidden parameter string.
	 */
	public String getHiddenInputParam() {
		StringBuffer hiddenParam = new StringBuffer();

		TreeMap<String, Object> allParam = new TreeMap<String, Object>(table);
		Iterator<String> keySet = null;
		String key = null;

		keySet = allParam.keySet().iterator();
		while (keySet.hasNext()) {
			key = (String) keySet.next();
			hiddenParam.append("<input type='hidden' name='").append(key)
					.append("' value='").append(allParam.get(key)).append(
							"'>\n");
		}
		return hiddenParam.toString();
	}

	/**
	 * 데이터박스에 HttpServletRequest를 파라메터로 넘겨 파싱하여 저장
	 *
	 * @param request
	 *            HttpServletRequest
	 * @throws ServletException
	 */
	public void setDataBox(HttpServletRequest request) throws ServletException {

		try {

			String sMethod = request.getMethod();
			
			Enumeration<?> names = request.getParameterNames();
			
			// Post 방식으로 넘어온 파라메터만 셋팅
			int iValuesLng = 0;
			String value = null;
			String req = request.getCharacterEncoding();
			String name = null;
			String[] values = null;
			
			if(req == null){
				req = "";
			}
			
			/*
			 * 1. url입력 후 바로 로그인 성공
			 * 2. url입력 후 로그인 실패를 한 후 로그인 성공
			 * 3. 화면에서 들어와서 한번에 로그인 성공
			 * 4. 화면에서 들어와서 로그인 실패후 다시 로그인 성공
			 * 
			 * 1번은 podo.login.forward 를 이용하여 체크(로그인전)
			 * 3번은 podo.login.forward 를 이용하여 체크(로그인전)
			 * 2, 4번은 podo.login.action.nm 를 이용하여 체크
			 * 
			 */
			/*
			if ((sMethod != null && sMethod.equals("POST")) 
					// 로그인을 커쳐서 redirect방식으로 넘어온 경우
					// url forwarding로 들어올 경우(컨테츠 나 soi뷰어등)
					|| (request.getRequestURI().indexOf(PropertyUtil.getProperty("podo.url.forward")) >= 0)
					// url쳐서 들어올 경우(로그인 필요)
					|| (request.getRequestURI().indexOf(PropertyUtil.getProperty("podo.login.direct")) >= 0)
					
					|| (request.getRequestURI().indexOf(PropertyUtil.getProperty("podo.login.action.nm")) >= 0)
					
					|| (request.getHeader("referer") != null && 
							// 로그인 실패로 재로그인으로 들어왔을 경우
							((request.getHeader("referer").indexOf(PropertyUtil.getProperty("podo.login.action.nm")) >= 0)
							// 한번의 로그인 성공으로 접속한 경우
							 || (request.getHeader("referer").indexOf(PropertyUtil.getProperty("podo.login.forward")) >= 0))
						)
				){
			*/
			if (sMethod != null) {

				while (names.hasMoreElements()) {

					name = (String) names.nextElement();
					
					values = request.getParameterValues(name);
					if (values != null && values.length > 1) {
						//utf-8이 아닐경우 utf-8로 변환
						if(!req.toUpperCase().equals("UTF-8")){
							iValuesLng = values.length;
							for(int i=0;i<iValuesLng;i++){
								values[i] = getCharSet(values[i], "8859_1", "UTF-8");
							}
						}
						table.put(name, values);
					} else {
						
						//utf-8이 아닐경우 utf-8로 변환
						if(!req.toUpperCase().equals("UTF-8")){
							value = getCharSet(request.getParameter(name), "8859_1", "UTF-8");
						}else{
							value = request.getParameter(name);
						}
						
						table.put(name, value);
					}

					
				}

				if (log.isDebugEnabled())
					log.debug("[setDataBox] POST:" + request.getRequestURI()
							+ "?" + getQueryString());

			} 
			/*
			else if (sMethod != null && sMethod.equals("GET")
					&& names.hasMoreElements()) {

				// GET 방식으로 넘어오는 데이터중 menucode로 넘어오는 데이터가 있는 경우만 제외하고 에러 출력
				int iCount = 0;
				boolean bCheck = true;
				while (names.hasMoreElements()) {
					iCount++;
					name = (String) names.nextElement();
					
				}
				if (iCount != 1 || bCheck) {
					log.info("[DataBox Info] Get 방식으로 데이터가 넘어왔습니다. \n"
							+ "  	GET:" + request.getRequestURI() + "?"
							+ request.getQueryString());
				}

			}
			*/

		} catch (Exception e) {
			log.error("[DataBox Error]\n", e);
		}

	}

	/**
	 * charset변경
	 * @param sText : 변형될 값
	 * @param fromChar : 변형전 charset
	 * @param toChar : 변형 후 charset
	 * @return
	 */
	public static String getCharSet(String sText, String fromCharSet, String toCharSet) {
		String sResult = null;
		try {
			if(sText == null) return sResult;
			sResult = new String(sText.getBytes(fromCharSet),toCharSet);
			return sResult;
		}catch(Exception e) {
			return "";
		}
	}
}
