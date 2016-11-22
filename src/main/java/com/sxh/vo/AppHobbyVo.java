package com.sxh.vo;

import java.util.List;

import com.sxh.model.UserTag;

public class AppHobbyVo {
	private int userid;// 用户id
	private String username;// 用户昵称
	private String purl;// 头像
	private String sex;// 性别
	private String address;// 地点
	private String activitypul;// 近期一个活动的图片
	private String activitytitle;// 近期一个活动的名称
	private List<UserTag> userTags;// 用户的标签

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPurl() {
		return purl;
	}

	public void setPurl(String purl) {
		this.purl = purl;
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

	public String getActivitypul() {
		return activitypul;
	}

	public void setActivitypul(String activitypul) {
		this.activitypul = activitypul;
	}

	public String getActivitytitle() {
		return activitytitle;
	}

	public void setActivitytitle(String activitytitle) {
		this.activitytitle = activitytitle;
	}

	public List<UserTag> getUserTags() {
		return userTags;
	}

	public void setUserTags(List<UserTag> userTags) {
		this.userTags = userTags;
	}

}
