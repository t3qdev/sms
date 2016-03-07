package com.b5m.sms.common.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class AlertFromControllerUtil {
	/**
	 * 컨트롤러에서 alert을 띄우기 위한 메소드
	 * 
	 * @param response
	 * @param msg
	 * @throws IOException
	 */
	public static void alertFromController(HttpServletResponse response, String msg) throws IOException{
		response.setCharacterEncoding("UTF-8");
	    PrintWriter writer = response.getWriter();
	    writer.println("<script type='text/javascript'>");
	    writer.println("alert('" +  msg + "');");
	    writer.println("</script>");
	    writer.flush();
	}
}
