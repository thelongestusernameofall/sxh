package com.sxh.user.service.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;

public class TTT {
	/**
	 * @param args
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws URISyntaxException
	 */
	public static void main(String[] args) throws ClientProtocolException, IOException, URISyntaxException {
		BasicCookieStore cookieStore = new BasicCookieStore();
		// CloseableHttpClient httpclient =
		// HttpClients.custom().setDefaultCookieStore(cookieStore).build();

		// 创建HttpClient实例
		// HttpClient httpclient = new DefaultHttpClient();
		// 创建Get方法实例
		// 将JSON进行UTF-8编码,以便传输中文
		String json = "{\"mobilePhoneNumber\":\"18101325331\"}";

		// String encoderJson =
		// URLEncoder.encode("{'mobilePhoneNumber':186xxxxxxxx","smsCode":"123456"}',HTTP.UTF_8);
		System.out.println("进入验证方法");
		String result="";
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			//StringEntity s = new StringEntity("");
			//s.setContentEncoding("UTF-8");
			//s.setContentType("application/json");// 发送json数据需要设置contentType
			HttpPost httpgets = new HttpPost("https://api.leancloud.cn/1.1/verifySmsCode/966572?mobilePhoneNumber=15911084217");
			httpgets.addHeader("X-LC-Id", "8SvzjuSA142dAgf16rAFuzMh-gzGzoHsz");
			httpgets.addHeader("X-LC-Key", "lz7cgSAwkX0Ep2RAtmI3NNj4");
			httpgets.addHeader("Content-Type", "application/json");
			//httpgets.setEntity(s);
			//httpgets.setParams();
			HttpResponse res;

			res = client.execute(httpgets);
			System.out.println("============" + res.getEntity().toString());
			// JSONObject response = null;
			// if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
			// HttpEntity entity = res.getEntity();
			result = EntityUtils.toString(res.getEntity());// 返回json格式：
			System.out.println("result=" + result);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// }
		// httpgets.setParams(params);
		// httpgets.setEntity(entity);
		// HttpResponse response = httpclient.execute(httpgets);
		// HttpEntity entity = response.getEntity();
		// if (entity != null) {
		// InputStream instreams = entity.getContent();
		// String str = convertStreamToString(instreams);
		// System.out.println("Do something");
		// System.out.println(str);
		// // Do not need the rest
		// httpgets.abort();
		// }
		// try
		//
		// {
		// HttpEntity entity = response2.getEntity();
		//
		// System.out.println("Login form get: " + response2.getStatusLine());
		// EntityUtils.consume(entity);
		//
		// System.out.println("Post logon cookies:");
		// List<Cookie> cookies = cookieStore.getCookies();
		// if (cookies.isEmpty()) {
		// System.out.println("None");
		// } else {
		// for (int i = 0; i < cookies.size(); i++) {
		// System.out.println("- " + cookies.get(i).toString());
		// }
		// }
		// } finally
		//
		// {
		// response2.close();
		// }

	}

//	public static String convertStreamToString(InputStream is) {
//		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//		StringBuilder sb = new StringBuilder();
//
//		String line = null;
//		try {
//			while ((line = reader.readLine()) != null) {
//				sb.append(line + "\n");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				is.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return sb.toString();
//	}

}
