package com.sxh.model;

import java.util.List;

public class UserInfo {
	private Integer userid;

	private String uid;

	private String nickname;

	private Integer photo;

	private String usertag;

	private String phone;

	private Integer provinceid;

	private Integer cityid;

	private Integer areaid;

	private Integer sex;

	private String regtime;

	private Integer ptatus;

	private String pwd;

	private String sign;

	private String tags;
	private Integer roleid;
	private Integer usertype;
	private String cid;
	private String usercode;
	private List<UserInfo> UserInfos;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid == null ? null : uid.trim();
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname == null ? null : nickname.trim();
	}

	public Integer getPhoto() {
		return photo;
	}

	public void setPhoto(Integer photo) {
		this.photo = photo;
	}

	public String getUsertag() {
		return usertag;
	}

	public void setUsertag(String usertag) {
		this.usertag = usertag == null ? null : usertag.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public Integer getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(Integer provinceid) {
		this.provinceid = provinceid;
	}

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public Integer getAreaid() {
		return areaid;
	}

	public void setAreaid(Integer areaid) {
		this.areaid = areaid;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getRegtime() {
		return regtime;
	}

	public void setRegtime(String regtime) {
		this.regtime = regtime == null ? null : regtime.trim();
	}

	public Integer getPtatus() {
		return ptatus;
	}

	public void setPtatus(Integer ptatus) {
		this.ptatus = ptatus;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd == null ? null : pwd.trim();
	}

	public List<UserInfo> getUserInfos() {
		return UserInfos;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public void setUserInfos(List<UserInfo> userInfos) {
		UserInfos = userInfos;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	@Override
	public String toString() {
		return "UserInfo [userid=" + userid + ", uid=" + uid + ", nickname=" + nickname + ", photo=" + photo
				+ ", usertag=" + usertag + ", phone=" + phone + ", provinceid=" + provinceid + ", cityid=" + cityid
				+ ", areaid=" + areaid + ", sex=" + sex + ", regtime=" + regtime + ", ptatus=" + ptatus + ", pwd=" + pwd
				+ ", sign=" + sign + ", tags=" + tags + ", roleid=" + roleid + ", usertype=" + usertype + ", cid=" + cid
				+ ", usercode=" + usercode + "]";
	}

}