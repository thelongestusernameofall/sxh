package com.sxh.common;

import java.io.*;

import java.net.*;

public class RestUtil {

	public String load(String url, String query) throws Exception {

		URL restURL = new URL(url);

		HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();

		conn.setRequestMethod("POST");

		conn.setDoOutput(true);

		conn.setAllowUserInteraction(false);

		PrintStream ps = new PrintStream(conn.getOutputStream());
		ps.print(query);

		ps.close();

		BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		String line, resultStr = "";

		while (null != (line = bReader.readLine()))

		{

			resultStr += line;

		}

		bReader.close();

		return resultStr;

	}

	public static void main(String[] args) {

		try {

			RestUtil restUtil = new RestUtil();

			String resultString = restUtil.load(
					"https://api.leancloud.cn/1.1/requestSmsCode",
					"X-LC-Id=OmfmIynshCoct1k9HqD5znhj-gzGzoHsz&X-LC-Key=t20ilBPv29LoqkPEEKv8lkS2&Content-Type=application/json&mobilePhoneNumber=186xxxxxxxx");
			System.out.println("resultString=" + resultString);

		} catch (Exception e) {

			// TODO: handle exception

			System.out.print(e.getMessage());

		}

	}

}