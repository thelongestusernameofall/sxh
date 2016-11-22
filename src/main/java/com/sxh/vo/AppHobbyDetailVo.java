package com.sxh.vo;

import java.util.List;

import com.sxh.model.UserTag;

public class AppHobbyDetailVo {
	private int userid;
	private String purl;
	private String username;
	private String sex;
	private String address;
	private String sign;
	private String ifshield;//当前用户是否屏蔽了该用户
	private List<UserTag> userTags;
	private List<AppHobbyVo> activities;

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getPurl() {
		return purl;
	}

	public void setPurl(String purl) {
		this.purl = purl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public List<UserTag> getUserTags() {
		return userTags;
	}

	public void setUserTags(List<UserTag> userTags) {
		this.userTags = userTags;
	}

	public List<AppHobbyVo> getActivities() {
		return activities;
	}

	public void setActivities(List<AppHobbyVo> activities) {
		this.activities = activities;
	}

	public String getIfshield() {
		return ifshield;
	}

	public void setIfshield(String ifshield) {
		this.ifshield = ifshield;
	}

	

}
