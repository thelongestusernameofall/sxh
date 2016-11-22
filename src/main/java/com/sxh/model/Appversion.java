package com.sxh.model;

public class Appversion {
    private Integer id;

    private String versionnum;

    private String appplatform;

    private String appurl;

    private String appsize;

    private String uploadtime;

    private String updatecontent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersionnum() {
        return versionnum;
    }

    public void setVersionnum(String versionnum) {
        this.versionnum = versionnum == null ? null : versionnum.trim();
    }

    public String getAppplatform() {
        return appplatform;
    }

    public void setAppplatform(String appplatform) {
        this.appplatform = appplatform == null ? null : appplatform.trim();
    }

    public String getAppurl() {
        return appurl;
    }

    public void setAppurl(String appurl) {
        this.appurl = appurl == null ? null : appurl.trim();
    }

    public String getAppsize() {
        return appsize;
    }

    public void setAppsize(String appsize) {
        this.appsize = appsize == null ? null : appsize.trim();
    }

    public String getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime == null ? null : uploadtime.trim();
    }

    public String getUpdatecontent() {
        return updatecontent;
    }

    public void setUpdatecontent(String updatecontent) {
        this.updatecontent = updatecontent == null ? null : updatecontent.trim();
    }
}