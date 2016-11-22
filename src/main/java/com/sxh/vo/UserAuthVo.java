package com.sxh.vo;

/**
 * 
 * 用户权限Vo
 *
 */
public class UserAuthVo {
	private int userid;// 用户id
	private String nickname;// 用户名
	private String rolename;// 角色名称
	private String regtime;// 创建时间
	private int status;// 状态

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}



	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRegtime() {
		return regtime;
	}

	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}

}
