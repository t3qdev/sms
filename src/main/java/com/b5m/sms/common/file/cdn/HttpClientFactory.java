package com.b5m.sms.common.file.cdn;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;

public class HttpClientFactory {

	private final static ThreadLocal<HttpClient> threadLocal = new ThreadLocal<HttpClient>();
	private final static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();

	/**
	 * 获得httpCilent 每个请求（request）都会取得同样的httpclient,不同请求会获得不同的httpclient
	 * 다른 요청이 다른 구하려면 같은 HttpClient를 달성 할 세션 객체 각 요청 (요청)를 받기 HttpClient를
	 * 
	 * @return
	 */
	public static HttpClient getHttpClient() {
		HttpClient httpClient = (HttpClient) threadLocal.get();
		if (httpClient == null) {
			httpClient = new HttpClient(connectionManager);
			threadLocal.set(httpClient);
		}
		return httpClient;
	}
}