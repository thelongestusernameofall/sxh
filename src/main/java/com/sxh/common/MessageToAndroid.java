package com.sxh.common;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class MessageToAndroid {
	/**
	 * 给Android发推送
	 * 
	 * @param json
	 * @return
	 */
	public static String MessageAndroid(String json) {
		System.out.println("进入Android发推送方法");
		BasicCookieStore cookieStore = new BasicCookieStore();
		String result = "";
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			System.out.println("json=" + json);
			StringEntity s = new StringEntity(json, "UTF-8");
			HttpPost httpgets = new HttpPost("https://leancloud.cn/1.1/push");
			httpgets.addHeader("X-LC-Id", "8SvzjuSA142dAgf16rAFuzMh-gzGzoHsz");
			httpgets.addHeader("X-LC-Key", "lz7cgSAwkX0Ep2RAtmI3NNj4");
			httpgets.addHeader("Content-Type", "application/json");
			httpgets.setEntity(s);
			HttpResponse res;
			res = client.execute(httpgets);
			System.out.println("============" + res.getEntity().toString());
			result = EntityUtils.toString(res.getEntity());// 返回json格式：
			System.out.println("result=" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
