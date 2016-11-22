package com.sxh.vo;

import java.util.List;

public class CircleVo {
	private int id;// 分享活动的id
	private String purl;// 头像
	private String nickname;// 昵称
	private int userid;//用户id
	private String title;// 圈子的标题
	private String fabutime;// 发布的时间
	private int zancount;// 点赞个数
	private int comcount;// 评论条数
	private int islike;//当前用户是否有点赞状态(0,未点赞;1,已点赞)
	// private HashMap<String, Object> picMap;// 图片列表
	// private HashMap<String, Object> likeMap;// 点赞列表
	// private HashMap<String, Object> comMap;// 评论列表
	private List<PictureVo> picList;// 图片列表
	private List<CommentVo> comList;// 评论列表
	private List<LikeVo> likeList;// 点赞列表

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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFabutime() {
		return fabutime;
	}

	public void setFabutime(String fabutime) {
		this.fabutime = fabutime;
	}

	public int getZancount() {
		return zancount;
	}

	public void setZancount(int zancount) {
		this.zancount = zancount;
	}

	public int getComcount() {
		return comcount;
	}

	public void setComcount(int comcount) {
		this.comcount = comcount;
	}

	public List<PictureVo> getPicList() {
		return picList;
	}

	public void setPicList(List<PictureVo> picList) {
		this.picList = picList;
	}

	public List<CommentVo> getComList() {
		return comList;
	}

	public void setComList(List<CommentVo> comList) {
		this.comList = comList;
	}

	public List<LikeVo> getLikeList() {
		return likeList;
	}

	public void setLikeList(List<LikeVo> likeList) {
		this.likeList = likeList;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getIslike() {
		return islike;
	}

	public void setIslike(int islike) {
		this.islike = islike;
	}

	// public HashMap<String, Object> getPicMap() {
	// return picMap;
	// }
	//
	// public void setPicMap(HashMap<String, Object> picMap) {
	// this.picMap = picMap;
	// }
	//
	// public HashMap<String, Object> getLikeMap() {
	// return likeMap;
	// }
	//
	// public void setLikeMap(HashMap<String, Object> likeMap) {
	// this.likeMap = likeMap;
	// }
	//
	// public HashMap<String, Object> getComMap() {
	// return comMap;
	// }
	//
	// public void setComMap(HashMap<String, Object> comMap) {
	// this.comMap = comMap;
	// }

}
