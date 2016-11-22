package com.sxh.model;

public class ShareActivities {
    private Integer id;

    private Integer userid;

    private String attachs;

    private String describes;

    private String fbtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getAttachs() {
        return attachs;
    }

    public void setAttachs(String attachs) {
        this.attachs = attachs == null ? null : attachs.trim();
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes == null ? null : describes.trim();
    }

    public String getFbtime() {
        return fbtime;
    }

    public void setFbtime(String fbtime) {
        this.fbtime = fbtime == null ? null : fbtime.trim();
    }
}