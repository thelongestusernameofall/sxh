package com.sxh.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

/**
 * 
 * 为Android提供短信服务
 *
 */
public class SMSToIOS {
	/**
	 * 给IOS发验证码短信
	 * 
	 * @param json
	 * @return
	 */
	public static String SMSIOS(String json) {
		System.out.println("进入IOS发短信方法");
		String result = "";
		try {
			System.out.println("====================json=" + json);
			DefaultHttpClient client = new DefaultHttpClient();
			StringEntity s = new StringEntity(json);
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");// 发送json数据需要设置contentType
			HttpPost httpgets = new HttpPost("https://api.leancloud.cn/1.1/requestSmsCode");
			httpgets.addHeader("X-LC-Id", "OmfmIynshCoct1k9HqD5znhj-gzGzoHsz");
			httpgets.addHeader("X-LC-Key", "t20ilBPv29LoqkPEEKv8lkS2");
			httpgets.addHeader("Content-Type", "application/json");
			httpgets.setEntity(s);
			HttpResponse res;
			res = client.execute(httpgets);
			System.out.println("============" + res.getEntity().toString());
			result = EntityUtils.toString(res.getEntity());
			System.out.println("result=" + result);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
			httpgets.addHeader("X-LC-Id", "OmfmIynshCoct1k9HqD5znhj-gzGzoHsz");
			httpgets.addHeader("X-LC-Key", "t20ilBPv29LoqkPEEKv8lkS2");
			httpgets.addHeader("Content-Type", "application/json");
			HttpResponse res;
			res = client.execute(httpgets);
			System.out.println("============" + res.getEntity().toString());
			result = EntityUtils.toString(res.getEntity());// 返回json格式：
			System.out.println("result=" + result);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 给安卓发验证码短信
	 * 
	 * @param json
	 * @return
	 */
	public static String SMSUpdateIOS(String json) {
		System.out.println("进入IOS发送修改密码短信方法");
		String result = "";
		// 封装数据
		try {
			
			System.out.println("====================json=" + json);
			DefaultHttpClient client = new DefaultHttpClient();
			StringEntity s;
			s = new StringEntity(json);
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");// 发送json数据需要设置contentType
			HttpPost httpgets = new HttpPost("https://api.leancloud.cn/1.1/requestSmsCode");
			httpgets.addHeader("X-LC-Id", "OmfmIynshCoct1k9HqD5znhj-gzGzoHsz");
			httpgets.addHeader("X-LC-Key", "t20ilBPv29LoqkPEEKv8lkS2");
			httpgets.addHeader("Content-Type", "application/json");
			httpgets.setEntity(s);
			HttpResponse res;
			res = client.execute(httpgets);
			System.out.println("============" + res.getEntity().toString());
			result = EntityUtils.toString(res.getEntity());
			System.out.println("result=" + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		// 封装数据
		try {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("mobilePhoneNumber", "18101325331");
			hashMap.put("template", "ios_find");
			String json = JSON.toJSON(hashMap).toString();
			System.out.println("====================json=" + json);
			DefaultHttpClient client = new DefaultHttpClient();
			StringEntity s;
			s = new StringEntity(json);
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");// 发送json数据需要设置contentType
			HttpPost httpgets = new HttpPost("https://api.leancloud.cn/1.1/requestSmsCode");
			httpgets.addHeader("X-LC-Id", "OmfmIynshCoct1k9HqD5znhj-gzGzoHsz");
			httpgets.addHeader("X-LC-Key", "t20ilBPv29LoqkPEEKv8lkS2");
			httpgets.addHeader("Content-Type", "application/json");
			httpgets.setEntity(s);
			HttpResponse res;
			res = client.execute(httpgets);
			System.out.println("============" + res.getEntity().toString());
			String result = EntityUtils.toString(res.getEntity());
			System.out.println("result=" + result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
