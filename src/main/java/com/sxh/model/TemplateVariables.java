package com.sxh.model;

public class TemplateVariables {
    private Integer id;

    private String tmpkey;

    private String tmpname;

    private Integer tmpstatus;

    private Integer delstatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTmpkey() {
        return tmpkey;
    }

    public void setTmpkey(String tmpkey) {
        this.tmpkey = tmpkey == null ? null : tmpkey.trim();
    }

    public String getTmpname() {
        return tmpname;
    }

    public void setTmpname(String tmpname) {
        this.tmpname = tmpname == null ? null : tmpname.trim();
    }

    public Integer getTmpstatus() {
        return tmpstatus;
    }

    public void setTmpstatus(Integer tmpstatus) {
        this.tmpstatus = tmpstatus;
    }

    public Integer getDelstatus() {
        return delstatus;
    }

    public void setDelstatus(Integer delstatus) {
        this.delstatus = delstatus;
    }
}