package com.sxh.vo;

import java.util.List;

import com.sxh.model.UserTag;

public class AppUserTagsSign {

	private int userid;// 用户ID
	private String sign;// 用户的签名
	private List<UserTag> userTags;// 用户的标签列表

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
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

}
