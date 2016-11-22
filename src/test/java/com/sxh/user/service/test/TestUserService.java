package com.sxh.user.service.test;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.sxh.common.SMSToAndroid;
import com.sxh.model.UserInfo;
import com.sxh.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml" })
public class TestUserService {

	private static final Logger LOGGER = Logger.getLogger(TestUserService.class);

	@Autowired
	private UserService userService;

	
	/*
	 * @Test public void testQueryById() { ApplicationContext ac = new
	 * ClassPathXmlApplicationContext( new String[] { "spring.xml",
	 * "spring-mybatis.xml" }); UserService userService = (UserService)
	 * ac.getBean("userService"); UserInfo userInfo =
	 * userService.getUserById(1); System.out.println(userInfo.getUname()); }
	 */

	@Test
	public void testQueryById1() {
		UserInfo userInfo = userService.getUserById(1);
		LOGGER.info(JSON.toJSON(userInfo));
	}
	@Test
	public void validateSMS(){
		String phone="15911084217";
		String code="816748";
		SMSToAndroid.validateSMS(phone, code);
	}
	
	public void testSMSToAndroid(){
		String mobilePhoneNumber="15911084217";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("mobilePhoneNumber", mobilePhoneNumber);
		System.out.println("======================="+JSON.toJSON(map).toString());
		SMSToAndroid.SMSAndroid(JSON.toJSON(map).toString());
	}
	
	

	@Test
	public void testQueryAll() {
		List<UserInfo> userInfos = userService.getUsers();
		LOGGER.info(JSON.toJSON(userInfos));
	}

	@Test
	public void testInsert() {
		UserInfo userInfo = new UserInfo();
		userInfo.setNickname("测试数据");
		userInfo.setSex(1);
		int result = userService.insert(userInfo);
		System.out.println(result);
	}
}
