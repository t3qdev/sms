package com.b5m.sms.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.SimpleTimeZone;

/**
 * Date를 정의한 Util Class <br />
 * Date의 획득,포멧변환 등의 함수를 정의한다. 
 * <p> 
 * <수정이력> <br /> 
 * 1. 수정일: 수정자: 수정사유: <br />
 * <p>
 * @since 2013. 8. 18.
 * @version 1.0
 * @author 김병찬
 */
public class DateUtil {
	
    /**
     * 현재 날짜 정보를 'yyyymmdd' 형으로 반환.
     * <pre>
     * String sReturn = DateUtil.sGetCurrentDate();  
     * sReturn 결과 : 20100305
     * </pre>
     * @since  2010. 3. 3.
     * @return 'yyyymmdd'형의 현재 날짜
     */
    public static String sGetCurrentDate(){
        return sGetCurrentDate("");
    }
	
    /**
     * 현재 날짜를 지정포멧형으로 반환.
     * <pre>
     * String sReturn1 =DateUtil.sGetCurrentDate("yyyy-MM-dd");
     * String sReturn2 =DateUtil.sGetCurrentDate("yyyy-MM-dd");
     * String sReturn3 =DateUtil.sGetCurrentDate("yyyy/MM/dd");
     * 
     * sReturn1 결과 : 20100305
     * sReturn2 결과 : 2010-03-05
     * sReturn3 결과 : 2010/03/05
     * </pre>
     * @since  2010. 3. 3.
     * @param dateFormat 지정 포멧형
     * @return  지정 포멧형의 현재 날짜 
     */
    public static String sGetCurrentDate(String dateFormat){
        Calendar aCalendar = Calendar.getInstance();

        int year = aCalendar.get(Calendar.YEAR);
        int month = aCalendar.get(Calendar.MONTH) + 1;
        int date = aCalendar.get(Calendar.DATE);
		String strDate = Integer.toString(year) +
		   ((month<10)?"0" + Integer.toString(month): Integer.toString(month)) +
		   ((date<10)?"0" + Integer.toString(date): Integer.toString(date));

		if(!"".equals(dateFormat)) strDate = sConvertDate(strDate, "yyyyMMdd", dateFormat);

        return  strDate;
    }
	
    /**
     * 현재 일시를 지정포멧형으로 반환. 
	 * <pre>
	 * String sReturn1 = DateUtil.sGetCurrentTime("yyyyMMdd");
	 * String sReturn2 = DateUtil.sGetCurrentTime("yyyyMMddHH");
	 * String sReturn3 = DateUtil.sGetCurrentTime("yyyyMMddHHmmss");
	 * String sReturn4 = DateUtil.sGetCurrentTime("yyyyMMddHHmmssSSS");
	 * 
	 * sReturn1 결과 : "20100303"
	 * sReturn2 결과 : "2010030317"
	 * sReturn3 결과 : "20100303175821"
	 * sReturn4 결과 : "20100303175821906"
	 * </pre>
     * @since  2010. 3. 3.
     * @param format 지정 포멧형
     * @return 지정 포멧형 현재 날짜
     */
    public static String sGetCurrentTime(String format){
    	// 1hour(ms) = 60s * 60m * 1000ms
        int millisPerHour = 60 * 60 * 1000;
		SimpleDateFormat fmt= new SimpleDateFormat(format);
		
        // TimeZone timeZone = TimeZone.getTimeZone( "UTC" );
		SimpleTimeZone timeZone = new SimpleTimeZone(9*millisPerHour,"KST");
		
        fmt.setTimeZone(timeZone);
        String str = fmt.format(new Date(System.currentTimeMillis()));
        return str;
    }
	
    /**
     * 입력 받은 날짜를 'yyyy-MM-dd HH:mm:ss' 형태로 반환.
     * <pre>
     * String sReturn  = DateUtil.sConvertDate("20100303175821");
     * sReturn 결과 : "2010-03-03 17:58:21"
     * </pre>
     * @since  2010. 3. 3.
     * @param strSource 'yyyyMMddHHmmss'형의 14자리의 문자열
     * @return 'yyyy-MM-dd HH:mm:ss' 날짜형
     */
    public static String sConvertDate(String strSource)
    {
        return sConvertDate(strSource, "", "");
    }

    /**
     * 입력 받은 날짜를 지정포멧형으로 반환.
     * <pre>
     * String sReturn  = DateUtil.sConvertDate("20100303175821", "yyyy-MM-dd HH:mm:ss");
     * sReturn 결과 : "2010-03-03 17:58:21"
     * </pre>
     * @since  2010. 3. 3.
     * @param strSource 'yyyyMMddHHmmss'형의 14자리의 문자열
     * @param toDateFormat 지정포멧형 날짜 형태
     * @return 지정포멧 날짜형
     */
    public static String sConvertDate(String strSource, String toDateFormat)
    {
        return sConvertDate(strSource, "", toDateFormat);
    }
	 
    /**
     * 입력 받은 날짜를 지정포멧형으로 반환.
     * <pre>
     * String sReturn  = DateUtil.sConvertDate("19990114232121", "yyyyMMddHHmm", "yyyy-mm-dd HH:mm");
     * sReturn 결과 : "2010-03-03 17:58"
     * </pre>
     * @since  2010. 3. 3.
     * @param strSource 'yyyyMMddHHmmss'형의 14자리의 문자열 
     * @param fromDateFormat 입력받은 날짜 형태. 즉, strSource의 날짜형(예:'20090301:yyyymmdd','20090301085421:yyyymmddhhmmss')
     * @param toDateFormat 지정포멧 날짜형
     * @return String toDateFormat 날짜형
     */
    public static String sConvertDate(String strSource, String fromDateFormat, String toDateFormat)
    {
        SimpleDateFormat simpledateformat = null;
        Date date = null;
        if(StringUtil.sGetNullTrimSpace(strSource).trim().equals("")){ return ""; }
        if(StringUtil.sGetNullTrimSpace(fromDateFormat).trim().equals(""))
            fromDateFormat = "yyyyMMddHHmmss";
        if(StringUtil.sGetNullTrimSpace(toDateFormat).trim().equals(""))
            toDateFormat = "yyyy-MM-dd HH:mm:ss";
        try {
            simpledateformat = new SimpleDateFormat(fromDateFormat);
            date = simpledateformat.parse(strSource);
            simpledateformat = new SimpleDateFormat(toDateFormat,Locale.US);
        } catch(Exception exception){
            exception.printStackTrace();
        }
        return simpledateformat.format(date);

    }
	
	/**
	 * 시작, 종료일 사이의 시간 차를 분 단위 반환.
	 * <pre>
	 * int iReturn = DateUtil.iBetweenMinute("20100301", "20100303", "yyyyMMdd");
	 * iReturn 결과 : "2880"
     * </pre>
	 * @since  2010. 3. 3.
	 * @param from 시작이 되는 날짜(0~9의 숫자 값)
	 * @param to 종료가 되는 날짜(0~9의 숫자 값)
	 * @param format 반환되는 날짜형
	 * @return 시작일과 종료일 사이의 시간 차
	 * @throws java.text.ParseException
	 */
	public static int iBetweenMinute (String from, String to, String format) throws ParseException {
		// 형식 확인
		if("".equals(format)){ format = "yyyyMMdd"; }
		
		// 입력받은 날짜를 검사한다.
		Date d1 = dCheck(from, format);
		Date d2 = dCheck(to, format);

		long duration = d2.getTime() - d1.getTime();

		return (int) (duration / (1000 * 60));
	}
	
	/**
	 * 시작, 종료일 사이의 시간 차를 시간 단위로 반환.
	 * <pre>
	 * int iReturn = DateUtil.iBetweenTimes("20100301", "20100303", "yyyyMMdd");
	 * iReturn 결과 : 48
     * </pre>
	 * @since  2010. 3. 3.
	 * @param from 시작이 되는 날짜(0~9의 숫자 값)
	 * @param to 종료가 되는 날짜(0~9의 숫자 값)
	 * @param format 반환되는 날짜형
	 * @return 시작일과 종료일 사이의 시간 차
	 * @throws java.text.ParseException
	 */
	public static int iBetweenTimes(String from, String to, String format) throws ParseException {
		// 형식 확인
		if("".equals(format)){ format = "yyyyMMdd"; }
		
		// 입력받은 날짜를 검사한다.
		Date d1 = dCheck(from, format);
		Date d2 = dCheck(to, format);

		long duration = d2.getTime() - d1.getTime();

		return (int) (duration / (1000 * 60 * 60 ));
	}

	/**
	 * 시작, 종료일 사이의 날짜 차를 일 단위로 반환.
	 * <pre>
	 * int iReturn = DateUtil.iBetweenDays("20100301", "20100303", "yyyyMMdd");
	 * iReturn 결과 : 2
     * </pre>
	 * @since  2010. 3. 3.
	 * @param from 시작이 되는 날짜(0~9의 숫자 값)
	 * @param to 종료가 되는 날짜(0~9의 숫자 값)
	 * @param format 반환되는 날짜형
	 * @return int 시작일과 종료일 사이의 날짜 차
	 * @throws java.text.ParseException
	 */
	public static int iBetweenDays(String from, String to, String format) throws ParseException {
		return (int) (iBetweenTimes(from, to, format) / 24);
	}

	/**
	 * 시작, 종료일 사이의 년도 차를 년단위 반환.
	 * <pre>
	 * int iReturn = DateUtil.iBetweenYears("20100301", "20110303", "yyyyMMdd");
	 * iReturn 결과 : 1
     * </pre>
	 * @since  2010. 3. 3.
	 * @param from 시작이 되는 날짜(0~9의 숫자 값)
	 * @param to 종료가 되는 날짜(0~9의 숫자 값)
	 * @param format 반환되는 날짜형
	 * @return int 시작일과 종료일 사이의 년도 차
	 * @throws java.text.ParseException
	 */
	public static int iBetweenYears(String from, String to, String format) throws ParseException {
		return (int) (iBetweenDays(from, to, format) / 365);
	}

	/**
	 * 시작, 현재일 사이의 년도 차를 반환.
	 * <pre>
	 * 현재년이 20100305일 경우
	 * int iReturn = DateUtil.iBetweenAge("20090305");
	 * iReturn 결과 : 1
     * </pre>
	 * @since  2010. 3. 3.
	 * @param from 종료가 되는 날짜(0~9의 숫자 값)
	 * @return int 현재일과 종료일 사이의 년도 차
	 * @throws java.text.ParseException
	 */
	public static int iBetweenAge(String from) throws ParseException {
		return iBetweenYears(from, sGetCurrentDate(), "yyyyMMdd");
	}
	
    /**
     * 입력 받은 날짜를 기준으로 가감된 날짜를 반환.
     * <pre>
     * String sReturn = DateUtil.sAddDays("20100303", 3);
	 * sReturn 결과 : 20100306
     * </pre>
     * @since  2010. 3. 3.
     * @param strDate 입력 날짜 (format:yyyyMMdd)
     * @param day 가감 하려는 날짜(0~9의 숫자 값)
     * @return 기준일에서 가감이 된 날짜
     * @throws ParseException
     */
    public static String sAddDays(String strDate, int day) throws ParseException
    {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        Date date = fmt.parse(strDate);
        date.setTime(date.getTime() + (long) day * 1000L * 60L * 60L * 24L);
        return fmt.format(date);
    }

    /**
     * 입력 받은 날짜를 기준으로 가감된 날짜를 반환.
     * <pre>
     * String sReturn = DateUtil.sAddDays("20100303", 3);
	 * sReturn 결과 : 20100306
     * </pre>
     * @since  2010. 3. 3.
     * @param date 입력 날짜 (format:yyyyMMdd)
     * @param day 가감 하려는 날짜(0~9의 숫자 값)
     * @return 기준일에서 가감이 된 날짜
     * @throws ParseException
     */
    public static String sAddDays(Date date, int day) throws ParseException
    {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        date.setTime(date.getTime() + (long) day * 1000L * 60L * 60L * 24L);
        return fmt.format(date);
    }

    /**
     * 입력 받은 날짜를 기준으로 가감된 날짜를 반환.
     * <pre>
     * Date dtReturn = DateUtil.dtAddDays2Date("20100303", 6);
	 * dtReturn 결과 : Tue Mar 09 00:00:00 KST 2010
     * </pre>
     * @since  2010. 3. 3.
     * @param str 입력 날짜 (format:yyyyMMdd)
     * @param day 가감 하려는 날짜(0~9의 숫자 값)
     * @return 기준일에서 가감이 된 날짜
     * @throws ParseException
     */
    public static Date dtAddDays2Date(String str, int day) throws ParseException
    {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        Date date = fmt.parse(str);
        date.setTime(date.getTime() + (long) day * 1000L * 60L * 60L * 24L);
        return date;
    }

    /**
     * 입력 받은 날짜를 기준으로 가감된 날짜를 반환.
     * <pre>
     * Date dtReturn = DateUtil.dtAddDays2Date(Date형, 6);
	 * dtReturn 결과 : Tue Mar 09 00:00:00 KST 2010
     * </pre>
     * @since  2010. 3. 3.
     * @param date 입력 날짜
     * @param day 가감 하려는 날짜(0~9의 숫자 값)
     * @return 기준일에서 가감이 된 날짜
     * @throws ParseException
     */
    public static Date dtAddDays2Date(Date date, int day) throws ParseException
    {
        date.setTime(date.getTime() + (long) day * 1000L * 60L * 60L * 24L);
        return date;
    }

    /**
     * 입력 날짜가 속한 주에서 원하는 요일의 날짜(yyyyMMdd)를 반환.
     * <pre>
     * String sReturn = DateUtil.sGetDayOfWeek("20100303", 5, 2);
     * sReturn 결과 : "20100405"
     * </pre>
     * @since  2010. 3. 3.
     * @param str 기준이 되는 입력 날짜(형식:yyyyMMdd)
     * @param week 가감 하려는 주 단위의 날짜(0~9의 숫자 값)
     * @param day 날짜 및 한주의 값의 요일(default:1), 한주시작 :: 월,화,수,목,금,토,일(2,3,4,5,6,7,1)
     * @return 입력 날짜 가 있는 주에서 가감된 주의 일정 요일 날짜(yyyyMMdd)
     * @throws ParseException
     */
    public static String sGetDayOfWeek(String str, int week, int day) throws ParseException
    {
        String conStr = null;
        int dayOfWeek = 0;

        if (week == 0){
            conStr = str;
            dayOfWeek = calGetCalendar(conStr).get(Calendar.DAY_OF_WEEK);
        }else{
            conStr = sAddDays(str, week * 7);
            dayOfWeek = calGetCalendar(conStr).get(Calendar.DAY_OF_WEEK);
        }
        int gap = 0;
        if(day == 1 || day == 2 || day == 3 || day == 4 || day == 5 || day == 6 || day == 7 ){
        	// 월,화,수,목,금,토,일
	        if(2 == day){ if (dayOfWeek != 1){ gap = 2 - dayOfWeek; } else { gap = -6; }} else 
	        if(3 == day){ if (dayOfWeek != 1){ gap = 3 - dayOfWeek; } else { gap = -5; }} else
	        if(4 == day){ if (dayOfWeek != 1){ gap = 4 - dayOfWeek; } else { gap = -4; }} else
	        if(5 == day){ if (dayOfWeek != 1){ gap = 5 - dayOfWeek; } else { gap = -3; }} else
	        if(6 == day){ if (dayOfWeek != 1){ gap = 6 - dayOfWeek; } else { gap = -2; }} else
	        if(7 == day){ if (dayOfWeek != 1){ gap = 7 - dayOfWeek; } else { gap = -1; }} else
	        if(1 == day){ if (dayOfWeek != 1){ gap = 8 - dayOfWeek; } else { gap = 0;  }}
        } else {
        	day = 1;
        }
        return sAddDays(conStr, gap);
    }

    /**
     * 입력 날짜가 속한 주에서 원하는 요일의 날짜(yyyyMMdd)를 반환.
     * <pre>
     * String sReturn = DateUtil.sGetDayOfWeek("20100303", 5, 2);
     * sReturn 결과 "20100405"
     * </pre>
     * @since  2010. 3. 3.
     * @param date 기준이 되는 입력 날짜(형식:yyyyMMdd)
     * @param week 가감 하려는 주 단위의 날짜(0~9의 숫자 값)
     * @param day 날짜 및 한주의 값의 요일(default:1), 한주시작 :: 월,화,수,목,금,토,일(2,3,4,5,6,7,1)
     * @return String 기준 날짜의 가감된 주의 일정 요일 날짜(yyyyMMdd)
     * @throws ParseException
     */
    public static String sGetDayOfWeek(Date date, int week, int day) throws ParseException
    {
        Date conDate = null;
        int dayOfWeek = 0;

        if (week == 0){
            conDate = date;
            dayOfWeek = calGetCalendar(conDate).get(Calendar.DAY_OF_WEEK);
        }else{
            conDate = dtAddDays2Date(date, week * 7);
            dayOfWeek = calGetCalendar(conDate).get(Calendar.DAY_OF_WEEK);
        }
        int gap = 0;
        if(day == 1 || day == 2 || day == 3 || day == 4 || day == 5 || day == 6 || day == 7 ){
        	// 월,화,수,목,금,토,일
	        if(2 == day){ if (dayOfWeek != 1){ gap = 2 - dayOfWeek; } else { gap = -6; }} else 
	        if(3 == day){ if (dayOfWeek != 1){ gap = 3 - dayOfWeek; } else { gap = -5; }} else
	        if(4 == day){ if (dayOfWeek != 1){ gap = 4 - dayOfWeek; } else { gap = -4; }} else
	        if(5 == day){ if (dayOfWeek != 1){ gap = 5 - dayOfWeek; } else { gap = -3; }} else
	        if(6 == day){ if (dayOfWeek != 1){ gap = 6 - dayOfWeek; } else { gap = -2; }} else
	        if(7 == day){ if (dayOfWeek != 1){ gap = 7 - dayOfWeek; } else { gap = -1; }} else
	        if(1 == day){ if (dayOfWeek != 1){ gap = 8 - dayOfWeek; } else { gap = 0;  }}
        } else {
        	day = 1;
        }
        return sAddDays(conDate, gap);
    }
	
    /**
     * 자신이 속한 주에서 원하는 요일의 날짜(yyyyMMdd)를 반환.
     * <pre>
     * String sReturn = DateUtil.sGetDayOfWeek("20100303", 5);
     * sReturn 결과 : [20100405, 20100406, 20100407, 20100408, 20100409, 20100410, 20100411]
     * </pre>
     * @since  2010. 3. 3.
     * @param str 기준이 되는 입력 날짜(형식:yyyyMMdd)
     * @param week 기준 날짜의 가감된 주 단위의 날짜(0~9의 숫자 값)
     * @return String[] 기준 날짜에서 가감된 주의 전체 날짜
     * @throws ParseException
     */
    public static String[] sGetDayOfWeek(String str, int week) throws ParseException
    {
        return new String[] {
			sGetDayOfWeek(str, week, 2),
			sGetDayOfWeek(str, week, 3),
			sGetDayOfWeek(str, week, 4),
			sGetDayOfWeek(str, week, 5),
			sGetDayOfWeek(str, week, 6),
			sGetDayOfWeek(str, week, 7),
			sGetDayOfWeek(str, week, 1)
		};
    }

    /**
     * 자신이 속한 주에서 원하는 요일의 날짜(yyyyMMdd)를 반환.
     * <pre>
     * Date dtReturn = DateUtil.sGetDayOfWeek(Date형, 5);
     * dtReturn 결과 : [20100405, 20100406, 20100407, 20100408, 20100409, 20100410, 20100411]
     * </pre>
     * @since  2010. 3. 3.
     * @param date 기준이 되는 입력 날짜(형식:Date형)
     * @param week 기준 날짜의 가감된 주 단위의 날짜(0~9의 숫자 값)
     * @return String[]	기준 날짜에서 가감된 주의 전체 날짜
     * @throws ParseException
     */
    public static String[] sGetDayOfWeek(Date date, int week) throws ParseException
    {
        return new String[] {
        		sGetDayOfWeek(date, week, 2),
        		sGetDayOfWeek(date, week, 3),
        		sGetDayOfWeek(date, week, 4),
        		sGetDayOfWeek(date, week, 5),
        		sGetDayOfWeek(date, week, 6),
        		sGetDayOfWeek(date, week, 7),
        		sGetDayOfWeek(date, week, 1)
		};
    }
  
    /**
     * 주어진 날짜가 속한 주가 월의 몇째주인지를 반환. <br />
	 * 일,월~토'를 한주로 보며 그달의 마지막주에서 달이 변경되면 새로운 한주로 정리
	 * <pre>
	 * int dtReturn = DateUtil.iGetWeek("20100228");
	 * dtReturn 결과 : 5
     * </pre>
     * @since  2010. 3. 3.
     * @param str 기준이 되는 입력 날짜(형식:yyyyMMdd)
     * @return int 기준 날짜가 속한 달의 몇째주 표시
     */
    public static int iGetWeek(String str)
    {
        return calGetCalendar(str).get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 주어진 날짜가 속한 주가 월의 몇째주인지를 반환.
     * <pre>
     * Date dtReturn = DateUtil.iGetWeek(Date형);
	 * dtReturn 결과 : 5
     * </pre>
     * @since  2010. 3. 3.
     * @param date 기준이 되는 입력 날짜(형식:Date형)
     * @return int 기준 날짜가 속한 달의 몇째주 표시
     */
    public static int iGetWeek(Date date)
    {
        return calGetCalendar(date).get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 주어진 문자열이 주어진 요일과 일치하는지 아닌지 여부 확인.
     * <pre>
     * boolean bReturn = DateUtil.isDayOfWeek("20100228", 1);
     * bReturn 결과 : false
     * </pre>
     * @since  2010. 3. 3.
     * @param str 입력 날짜(형식:yyyyMMdd)
     * @param dayOfWeek 요일(SUNDAY~SATURDAY중 하나)(default:1), 한주시작 :: 월,화,수,목,금,토,일(2,3,4,5,6,7,1)
     * @return boolean 날짜와 요일이 일치확인 true/false
     */
    public static boolean isDayOfWeek(String str, int dayOfWeek)
    {
        int day = calGetCalendar(str).get(Calendar.DAY_OF_WEEK);
        if (day == dayOfWeek){ return true; } else { return false; }
    }

    /**
     * 주어진 문자열이 주어진 요일과 일치하는지 아닌지 여부 확인.
     * <pre>
     * boolena bReturn = DateUtil.isDayOfWeek(Date형, 1);
     * bReturn 결과 : false
     * </pre>
     * @since  2010. 3. 3.
     * @param date 입력 날짜(형식:Date형)
     * @param dayOfWeek 요일(SUNDAY~SATURDAY중 하나)(default:1), 한주시작 :: 월,화,수,목,금,토,일(2,3,4,5,6,7,1)
     * @return boolean 날짜와 요일이 일치할시 true/false를 리턴한다
     */
    public static boolean isDayOfWeek(Date date, int dayOfWeek)
    {
        int day = calGetCalendar(date).get(Calendar.DAY_OF_WEEK);
        if (day == dayOfWeek){ return true; } else { return false; }
    }
    
    /**
     * 주어진 문자열로 날짜 셋팅한 calendar형 반환.
     * <pre>
     * Calendar return = DateUtil.calGetCalendar("20100303");
     * return 결과 : java.util.GregorianCalendar[...]
     * </pre>
     * @since  2010. 3. 3.
     * @param str 기준이 되는 입력 날짜(형식:yyyyMMdd)
     * @return Calendar형 인스턴스
     */
    public static Calendar calGetCalendar(String str)
    {
        int yy = Integer.parseInt(str.substring(0, 4));
        int mm = Integer.parseInt(str.substring(4, 6)) - 1;
        int dd = Integer.parseInt(str.substring(6, 8));

        Calendar cal = Calendar.getInstance();
        cal.set(yy, mm, dd);
        return cal;
    }

    /**
     * 주어진 문자열로 날짜셋팅한 calendar형 반환.
     * <pre>
     * Calendar return = DateUtil.calGetCalendar("20100303");
     * return 결과 : java.util.GregorianCalendar[...]
     * </pre>
     * @since  2010. 3. 3.
     * @param date 기준이 되는 입력 날짜(형식:Date형)
     * @return Calendar형 인스턴스
     */
    public static Calendar calGetCalendar(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
	
    /**
     * 입력한 년도 및 월을 통해 그 달의 마지막 날짜를 반환.
     * <pre>
     * String sReturn = DateUtil.sGetTotalDays("2012", "2");
     * sReturn 결과 "29"
     * </pre>
     * @since  2010. 3. 3.
     * @param year 년도(yyyy)
     * @param month 달(MM)
     * @return 그 달의 마지막 날짜
     */
    public static String sGetTotalDays(String year, String month)
    {
    	String[] ar = {"31","28","31","30","31","30","31","31","30","31","30","31"};
    	ar[1] = sLeapYear(Integer.parseInt(year));

    	return ar[Integer.parseInt(month)-1];
    }
	
    /**
     * 입력한 년도 및 월을 통해 그 달의 마지막 날짜를 반환.  <br />
 	 * 2월달의 경우 아래와 같이 체크한다.
 	 * <pre>
 	 * String sReturn = DateUtil.sLeapYear("2012");
     * sReturn 결과 "29"
     * </pre>
     * @since  2010. 3. 3.
     * @param year 년도(yyyy)
     * @return 2월달의 마지막 날짜
     */
    public static String sLeapYear(int year)
    {
    	if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
    		return "29";
    	}
    	return "28";
    }
    
	/**
	 * 문자형(숫자 8자리)으로 입력 받은 날짜를 검사 후 date형으로 반환.
	 * <pre>
	 * Date dtReturn = DateUtil.dCheck("20100303");
	 * dtReturn 결과 "Wed Mar 03 00:00:00 KST 2010"
     * </pre>
	 * @since  2010. 3. 3.
	 * @param s 입력 날짜(형식:yyyyMMdd)
	 * @return Date형 날짜
	 * @throws ParseException
	 */
	public static Date dCheck(String s) throws ParseException {
		return dCheck(s, "yyyyMMdd");
	}

  	/**
  	 * date 형으로 입력 받은 날짜를 검사 후 반환.
  	 * <pre>
  	 * String sReturn = DateUtil.dCheck("20100303120000","yyyyMMddHHmmss");
  	 * sReturn 결과 : "Wed Mar 03 12:00:00 KST 2010"
     * </pre>
  	 * @since  2010. 3. 3.
  	 * @param s 입력 날짜
  	 * @param format 입력받은 날짜형. 즉, s의 날짜형(예:'20090301:yyyyMMdd','20090301085421:yyyyMMddHHmmss')
  	 * @return Date형 날짜
  	 * @throws ParseException
  	 */
  	public static Date dCheck(String s, String format) throws ParseException {
  		// 파라메터 검사
  		if (s == null) {
  			throw new ParseException("date string to check is null", 0);
  		}
  		if (format == null) {
  			throw new ParseException( "format string to check date is null", 0);
  		}

  		// 날짜 형식 지정
  		SimpleDateFormat formatter = new SimpleDateFormat( format, Locale.KOREA);

  		// 검사
  		Date date = null;

  		try {
  			date = formatter.parse(s);
  		} catch (ParseException e) {
  			throw new ParseException(" wrong date:\"" + s + "\" with format \"" + format + "\"", 0);
  		}

  		// 날짜 포멧이 틀린 경우
  		if (!formatter.format(date).equals(s)) {
  			throw new ParseException("Out of bound date:\"" + s + "\" with format \"" + format + "\"", 0);
  		}
  		return date;
  	}

    /**
     * long 형으로 현재 시간을 Milliseconds 값으로 반환. <br />
 	 * 1970년 01월 01일 부터해서 해당날짜까지 milliseconds 단위로 넘겨주는 함수
 	 * 1000/60/60/24/365 초/분/시/일/년
 	 * <pre>
 	 * long lReturn = DateUtil.lGetNow();
 	 * lReturn 결과: 1267609003027
     * </pre>
     * @since  2010. 3. 3.
     * @return 현재 시간의 Milliseconds 값
     */
    public static long lGetNow(){
        return new Date().getTime();
    }
	
    /**
     * 입력받은 날짜를 string형으로 입력받은 날짜형로 반환.
     * @since  2010. 3. 3.
     * @param date 입력 날짜(형식: Date형)
     * @param format 변환할 날짜형(Default:yyyyMMdd)
     * @return 변환할 날짜형으로 변경된 입력 날짜
     */
    public static String sGetDate(Date date, String format)
    {
    	if("".equals(format)){ format = "yyyyMMdd"; }
        SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
        return simpledateformat.format(date);
    }
	
    /**
     * 주어진 날짜를 yyyy 형식으로 반환.
     * <pre>
     * SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
     * Date date = fmt.parse("20201001");
     * String sReturn = DateUtil.sGetYear(date);
     * sReturn 결과 : 2020
     * </pre>
     * @since  2010. 3. 3.
     * @param date 입력 날짜(형식: Date형)
     * @return yyyy 날짜형
     */
    public static String sGetYear(Date date)
     {
         SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy");
         return simpledateformat.format(date);
     }

	/**
	 * 주어진 날짜를 MM 형식으로 반환.
	 * <pre>
	 * SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
	 * Date date = fmt.parse("20201001");
	 * String sReturn = DateUtil.sGetMonth(date);
	 * sReturn 결과 : 10
     * </pre>
	 * @since  2010. 3. 3.
	 * @param date 입력 날짜(형식: Date형)
	 * @return MM 날짜형
	 */
	public static String sGetMonth(Date date)
	{
		SimpleDateFormat simpledateformat = new SimpleDateFormat("MM");
		return simpledateformat.format(date);
	}

	/**
	 * 주어진 날짜를 dd 형식으로 반환.
	 * <pre>     
	 * SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
     * Date date = fmt.parse("20201001");
     * String sReturn = DateUtil.sGetYear(date);
     * sReturn 결과 : 01
     * </pre>
	 * @since 2010. 3. 3.
	 * @param date 입력 날짜(형식: Date형)
	 * @return dd 날짜형
	 */
	public static String sGetDay(Date date)
	{
		SimpleDateFormat simpledateformat = new SimpleDateFormat("dd");
		return simpledateformat.format(date);
	}
	
    /**
     * yyyyMMddHHmmss 형식의 날짜를 MM/dd로 반환(구분자:/경우).
     * <pre>
     * String sReturn = DateUtil.sGetConvertDayOnly("20100303120000", "/");
     * sReturn 결과 : "03/03"
     * </pre>
     * @since 2010. 3. 3.
     * @param date 입력 날짜(형식: yyyyMMddHHmmss)
     * @param delim 구분자
     * @return String MM+구분자+dd 형식의 월일
     */
    public static String sGetConvertDayOnly(String date, String delim)
    {
        return date.substring(4, 6) + delim + date.substring(6, 8);
    }
    
	/**
	 * 기준 년월에서 개월 수를 뺀 년월을 반환.
	 * <pre>
	 * String sReturn = DateUtil.sGetMonthBefore("201003", 2);
	 * sReturn 결과 : "201001" 
     * </pre>
	 * @since 2010. 3. 3.
	 * @param yyyy 기준이 되는 입력 년
	 * @param mm 기준이 되는 입력 월
	 * @param month 기간(월)
	 * @return 년원 (형식: yyyyMM)
	 * @throws Exception 
	 */
	public static String sGetMonthBefore(int yyyy, int mm, int month){
		Calendar cal = Calendar.getInstance();
		int iYear = yyyy;
		int iMonth = mm+1;
		cal.set(iYear, iMonth, 0);
		int iDivCnt = iMonth-month;
		cal.set(iYear, iDivCnt, 0);
		
		String sYyyyMM = cal.get(Calendar.YEAR)+""+(cal.get(Calendar.MONTH)<10?"0"+cal.get(Calendar.MONTH):cal.get(Calendar.MONTH));
		
		return sYyyyMM;
	}
	
	/**
	 * Timestamp를 포멧으로 반환(업무용 등록,수정일시 포멧 변경을 위해).
	 * @since 2010. 8. 19. 
	 * @param obj TimeStamp
	 * @param sFormat 지정 포멧형
	 * @return 지정 포멧형태의 날짜
	 */
	public static String sGetTimeStampToFormat (Object obj,String sFormat){
		SimpleDateFormat formatter = new SimpleDateFormat(sFormat); 
		GregorianCalendar cal = null;
		
		cal = (GregorianCalendar) obj;
		String sDate = formatter.format(cal.getTime());
		
		return sDate;
	}
}
