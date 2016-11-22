package com.sxh.vo;

public class SuggestionsVo {
	private Integer id;

	private String sugcontent;

	private String phonemodel;

	private String version;

	private String contract;

	private String startdate;
	
	private String enddate;

	private String replycontent;

	private Integer replystatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSugcontent() {
		return sugcontent;
	}

	public void setSugcontent(String sugcontent) {
		this.sugcontent = sugcontent == null ? null : sugcontent.trim();
	}

	public String getPhonemodel() {
		return phonemodel;
	}

	public void setPhonemodel(String phonemodel) {
		this.phonemodel = phonemodel == null ? null : phonemodel.trim();
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version == null ? null : version.trim();
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract == null ? null : contract.trim();
	}

	public String getReplycontent() {
		return replycontent;
	}

	public void setReplycontent(String replycontent) {
		this.replycontent = replycontent == null ? null : replycontent.trim();
	}

	public Integer getReplystatus() {
		return replystatus;
	}

	public void setReplystatus(Integer replystatus) {
		this.replystatus = replystatus;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
}