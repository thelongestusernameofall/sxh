package com.sxh.model;

public class SmsTemplate {
    private Integer id;

    private String tempname;

    private String tempcontent;

    private String tempremark;

    private Integer tempstatus;

    private Integer temptype;

    private Integer tempdelstatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTempname() {
        return tempname;
    }

    public void setTempname(String tempname) {
        this.tempname = tempname == null ? null : tempname.trim();
    }

    public String getTempcontent() {
        return tempcontent;
    }

    public void setTempcontent(String tempcontent) {
        this.tempcontent = tempcontent == null ? null : tempcontent.trim();
    }

    public String getTempremark() {
        return tempremark;
    }

    public void setTempremark(String tempremark) {
        this.tempremark = tempremark == null ? null : tempremark.trim();
    }

    public Integer getTempstatus() {
        return tempstatus;
    }

    public void setTempstatus(Integer tempstatus) {
        this.tempstatus = tempstatus;
    }

    public Integer getTemptype() {
        return temptype;
    }

    public void setTemptype(Integer temptype) {
        this.temptype = temptype;
    }

    public Integer getTempdelstatus() {
        return tempdelstatus;
    }

    public void setTempdelstatus(Integer tempdelstatus) {
        this.tempdelstatus = tempdelstatus;
    }
}