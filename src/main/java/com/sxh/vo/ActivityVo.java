package com.sxh.vo;

/**
 * 
 * WEB端传入数据的Vo
 *
 */
public class ActivityVo {
	private String activityname;// 活动名称
	private int activitytypeid;// 活动类型
	private String activitycost;// 是否免费(0,就表示免费)
	private int activitystatus;// 活动状态
	private String starttime;// 发布时间的开始时间
	private String endtime;// 发布时间的结束时间
	private int userid;//用户id
	private int startitem;//开始条数
	private int enditem;//结束条数

	public String getActivityname() {
		return activityname;
	}

	public void setActivityname(String activityname) {
		this.activityname = activityname;
	}

	public int getActivitytypeid() {
		return activitytypeid;
	}

	public void setActivitytypeid(int activitytypeid) {
		this.activitytypeid = activitytypeid;
	}

	public String getActivitycost() {
		return activitycost;
	}

	public void setActivitycost(String activitycost) {
		this.activitycost = activitycost;
	}

	public int getActivitystatus() {
		return activitystatus;
	}

	public void setActivitystatus(int activitystatus) {
		this.activitystatus = activitystatus;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getStartitem() {
		return startitem;
	}

	public void setStartitem(int startitem) {
		this.startitem = startitem;
	}

	public int getEnditem() {
		return enditem;
	}

	public void setEnditem(int enditem) {
		this.enditem = enditem;
	}
}
