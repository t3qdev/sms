package com.b5m.sms.common.exchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 환율정보 조회
public class ExchangeUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeUtil.class);
	
	public double getExchangeUSDTo(String to) throws ClientProtocolException, IOException, ParseException {
		String url = "https://openexchangerates.org/api/latest.json?app_id=ada8da81672f4db08fffbf4f227a9ade";

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);

		LOGGER.info("\nSending 'GET' request to URL : " + url);
		LOGGER.info("Response Code : " + 
                       response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
                       new InputStreamReader(response.getEntity().getContent()));

		JSONParser jsonParser = new JSONParser();
		JSONObject jobj1 = (JSONObject)jsonParser.parse(rd);
		JSONObject jobj2 = (JSONObject) jobj1.get("rates");
		String a = jobj2.get(to).toString();
		Double result = Double.parseDouble(a);
		LOGGER.info("KRW" + result.toString());
		
		return result;

	}
}
