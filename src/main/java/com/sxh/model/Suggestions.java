package com.sxh.model;

public class Suggestions {
    private Integer id;

    private String sugcontent;

    private String phonemodel;

    private String version;

    private String contract;

    private String sugtime;

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

    public String getSugtime() {
        return sugtime;
    }

    public void setSugtime(String sugtime) {
        this.sugtime = sugtime == null ? null : sugtime.trim();
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
}