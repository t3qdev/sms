package com.b5m.sms.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.b5m.sms.common.util.PropertyUtil;

/**
 * PropertyLoadListener listener Class.
 * <p> 
 * <수정이력> <br /> 
 * 1. 수정일: 수정자: 수정사유: <br />
 * <p>
 * @since 2013. 8. 18.
 * @version 1.0
 * @author 김병찬
 */
public class PropertyLoadListener implements ServletContextListener {

	/* 
	 * ServletContext Destroy.
	 * @since 2013. 8. 18.
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 * @param event ServletContextEvent
	 */
	public void contextDestroyed(ServletContextEvent event) {}

	/* 
	 * ServletContext Init.
	 * @since 2013. 8. 18.
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 * @param event ServletContextEvent
	 */
	public void contextInitialized(ServletContextEvent event) {
		PropertyUtil.Load();
	}

	
}
