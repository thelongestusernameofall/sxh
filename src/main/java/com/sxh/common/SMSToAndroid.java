package com.sxh.common;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

/**
 * 
 * 为Android提供短信服务
 *
 */
public class SMSToAndroid {
	/**
	 * 给安卓发验证码短信
	 * 
	 * @param json
	 * @return
	 */
	public static String SMSAndroid(String json) {
		System.out.println("进入Android发短信方法");
		String result = "";
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			System.out.println("====================json=" + json);
			StringEntity s = new StringEntity(json);
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");// 发送json数据需要设置contentType
			HttpPost httpgets = new HttpPost("https://api.leancloud.cn/1.1/requestSmsCode");
			httpgets.addHeader("X-LC-Id", "8SvzjuSA142dAgf16rAFuzMh-gzGzoHsz");
			httpgets.addHeader("X-LC-Key", "lz7cgSAwkX0Ep2RAtmI3NNj4");
			httpgets.addHeader("Content-Type", "application/json");
			httpgets.setEntity(s);
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
		return result;
	}

	public static String validateSMS(String phone, String code) {
		System.out.println("进入验证方法");
		String result = "";
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			// StringEntity s = new StringEntity("");
			// s.setContentEncoding("UTF-8");
			// s.setContentType("application/json");// 发送json数据需要设置contentType
			HttpPost httpgets = new HttpPost(
					"https://api.leancloud.cn/1.1/verifySmsCode/" + code + "?mobilePhoneNumber=" + phone);
			System.out.println("================================" + "https://api.leancloud.cn/1.1/verifySmsCode/" + code
					+ "?mobilePhoneNumber=" + phone);
			httpgets.addHeader("X-LC-Id", "8SvzjuSA142dAgf16rAFuzMh-gzGzoHsz");
			httpgets.addHeader("X-LC-Key", "lz7cgSAwkX0Ep2RAtmI3NNj4");
			httpgets.addHeader("Content-Type", "application/json");
			// httpgets.setEntity(s);
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
		return result;

	}

	public static void main(String[] args) {
	
//		char sign = '{}';
//		if(sign=='{'||sign=='}'){
//			System.out.println("dfdfdf");
//		}
//		HashMap<String, String> hashMap = new HashMap<String, String>();
//		hashMap.put("mobilePhoneNumber", "15911084217");
//		String json = JSON.toJSON(hashMap).toString();
//		System.out.println(json.toString());
//		String results = SMSToAndroid.SMSAndroid(json.toString());
//		System.out.println(results);
//		HashMap<String, String> m = new HashMap<String, String>();
//		if (results=="{}") {
//			m.put("Scd", "1");
//			m.put("Msg", "发送成功!");
//			System.out.println("发送成功!");
//		}

	}

}
