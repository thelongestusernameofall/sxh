package com.sxh.user.service.test;

import java.net.URLEncoder;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 消息推送测试类
 * 
 *
 */
public class PushMessage {

	public static void main(String[] args) {
		BasicCookieStore cookieStore = new BasicCookieStore();
		// CloseableHttpClient httpclient =
		// HttpClients.custom().setDefaultCookieStore(cookieStore).build();

		// 创建HttpClient实例
		// HttpClient httpclient = new DefaultHttpClient();
		// 创建Get方法实例
		// 将JSON进行UTF-8编码,以便传输中文
		// String json = "{\"mobilePhoneNumber\":\"18101325331\"}";

		 //String encoderJson =
		//URLEncoder.encode("{'mobilePhoneNumber':186xxxxxxxx","smsCode":"123456"}',HTTP.UTF_8);
		//System.out.println("进入验证方法");
		String result = "";
		try {
			DefaultHttpClient client = new DefaultHttpClient();

			HashMap<String, String> m = new HashMap<String, String>();
			HashMap<String, String> m1 = new HashMap<String, String>();
			HashMap<String, Object> map = new HashMap<String, Object>();

			m.put("deviceToken", "dcb04d3239d4be69821fea27100ad67eac1f9430da81c3d83bdc27c7867490b8");
			m1.put("alert", "顺心会向你问好");
			Object jsonA = JSONObject.toJSON(m);
			Object jsonB = JSONObject.toJSON(m1);
			map.put("where", jsonA);
			map.put("data", jsonB);
			String json = JSON.toJSON(map).toString();
			//json=URLEncoder.encode(json,"UTF-8");
			System.out.println("json=" + json);
			StringEntity s = new StringEntity(json, "UTF-8");
			// {
			// "where":{
			// "installationId":"57234d4c-752f-4e78-81ad-a6d14048020d"
			// },
			// "data": {
			// "alert": "LeanCloud 向您问好！"
			// }
			// }

			// s.setContentEncoding("UTF-8");
			// s.setContentType("application/json");// 发送json数据需要设置contentType
			HttpPost httpgets = new HttpPost("https://leancloud.cn/1.1/push");
			httpgets.addHeader("X-LC-Id", "OmfmIynshCoct1k9HqD5znhj-gzGzoHsz");
			httpgets.addHeader("X-LC-Key", "t20ilBPv29LoqkPEEKv8lkS2");
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
