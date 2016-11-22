package com.sxh.model;

public class Activities {

	private Integer activityid;// 活动id

	private String activitynum;// 活动编号

	private Integer activitytypeid;// 活动类型

	private Integer activitypic;// 活动图片

	private String activityname;// 活动名称

	private String activitydetail;// 活动详情

	private String activitystart;// 开始时间

	private String activityend;// 结束时间

	private String activityadd;// 活动地点

	private Integer peoplecount;// 限制人数

	private String activitycost;// 活动费用

	private String activityflow;// 活动流程

	private Integer activitystatus;// 活动状态

	private String createtime;// 活动创建时间

	private Integer ifmy;// 是否满员

	private Integer entercount;// 报名人数

	private Integer userid;// 创建人ID

	public Integer getActivityid() {
		return activityid;
	}

	public void setActivityid(Integer activityid) {
		this.activityid = activityid;
	}

	public String getActivitynum() {
		return activitynum;
	}

	public void setActivitynum(String activitynum) {
		this.activitynum = activitynum == null ? null : activitynum.trim();
	}

	public Integer getActivitytypeid() {
		return activitytypeid;
	}

	public void setActivitytypeid(Integer activitytypeid) {
		this.activitytypeid = activitytypeid;
	}

	public Integer getActivitypic() {
		return activitypic;
	}

	public void setActivitypic(Integer activitypic) {
		this.activitypic = activitypic;
	}

	public String getActivityname() {
		return activityname;
	}

	public void setActivityname(String activityname) {
		this.activityname = activityname == null ? null : activityname.trim();
	}

	public String getActivitydetail() {
		return activitydetail;
	}

	public void setActivitydetail(String activitydetail) {
		this.activitydetail = activitydetail == null ? null : activitydetail.trim();
	}

	public String getActivitystart() {
		return activitystart;
	}

	public void setActivitystart(String activitystart) {
		this.activitystart = activitystart == null ? null : activitystart.trim();
	}

	public String getActivityend() {
		return activityend;
	}

	public void setActivityend(String activityend) {
		this.activityend = activityend == null ? null : activityend.trim();
	}

	public String getActivityadd() {
		return activityadd;
	}

	public void setActivityadd(String activityadd) {
		this.activityadd = activityadd == null ? null : activityadd.trim();
	}

	public Integer getPeoplecount() {
		return peoplecount;
	}

	public void setPeoplecount(Integer peoplecount) {
		this.peoplecount = peoplecount;
	}

	public String getActivitycost() {
		return activitycost;
	}

	public void setActivitycost(String activitycost) {
		this.activitycost = activitycost == null ? null : activitycost.trim();
	}

	public String getActivityflow() {
		return activityflow;
	}

	public void setActivityflow(String activityflow) {
		this.activityflow = activityflow == null ? null : activityflow.trim();
	}

	public Integer getActivitystatus() {
		return activitystatus;
	}

	public void setActivitystatus(Integer activitystatus) {
		this.activitystatus = activitystatus;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public Integer getIfmy() {
		return ifmy;
	}

	public void setIfmy(Integer ifmy) {
		this.ifmy = ifmy;
	}

	public Integer getEntercount() {
		return entercount;
	}

	public void setEntercount(Integer entercount) {
		this.entercount = entercount;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "Activities [activityid=" + activityid + ", activitynum=" + activitynum + ", activitytypeid="
				+ activitytypeid + ", activitypic=" + activitypic + ", activityname=" + activityname
				+ ", activitydetail=" + activitydetail + ", activitystart=" + activitystart + ", activityend="
				+ activityend + ", activityadd=" + activityadd + ", peoplecount=" + peoplecount + ", activitycost="
				+ activitycost + ", activityflow=" + activityflow + ", activitystatus=" + activitystatus
				+ ", createtime=" + createtime + ", ifmy=" + ifmy + ", entercount=" + entercount + ", userid=" + userid
				+ "]";
	}

}