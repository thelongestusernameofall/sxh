package com.sxh.vo;

public class PictureVo {
	private int id;// 图片附件的id
	private String url;// 图片的地址
	private String thumurl;//缩略图地址

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getThumurl() {
		return thumurl;
	}

	public void setThumurl(String thumurl) {
		this.thumurl = thumurl;
	}

}
