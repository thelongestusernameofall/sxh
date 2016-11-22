package com.sxh.vo;

public class AppActivityVo {
	private int id;// 活动的id
	private String activityname;//活动名称
	private String purl;// 图片的url
	private String count;// 收藏的人数
	private String ifmy;// 是否满员
	private String ifsc;// 是否收藏
	private String iffree;// 是否免费
	private String ifweek;// 是否是周末
	private String activityurl;//活动详情详细页面地址 
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPurl() {
		return purl;
	}

	public void setPurl(String purl) {
		this.purl = purl;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getIfmy() {
		return ifmy;
	}

	public void setIfmy(String ifmy) {
		this.ifmy = ifmy;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public String getIffree() {
		return iffree;
	}

	public void setIffree(String iffree) {
		this.iffree = iffree;
	}

	public String getIfweek() {
		return ifweek;
	}

	public void setIfweek(String ifweek) {
		this.ifweek = ifweek;
	}

	public String getActivityurl() {
		return activityurl;
	}

	public void setActivityurl(String activityurl) {
		this.activityurl = activityurl;
	}

	public String getActivityname() {
		return activityname;
	}

	public void setActivityname(String activityname) {
		this.activityname = activityname;
	}

}
