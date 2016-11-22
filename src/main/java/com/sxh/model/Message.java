package com.sxh.model;

public class Message {
    private Integer id;

    private String reception;

    private String title;

    private String content;

    private String sendtime;

    private Integer msgtype;

    private Integer isread;

    private Integer msgsorce;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReception() {
        return reception;
    }

    public void setReception(String reception) {
        this.reception = reception == null ? null : reception.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime == null ? null : sendtime.trim();
    }

    public Integer getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(Integer msgtype) {
        this.msgtype = msgtype;
    }

    public Integer getIsread() {
        return isread;
    }

    public void setIsread(Integer isread) {
        this.isread = isread;
    }

    public Integer getMsgsorce() {
        return msgsorce;
    }

    public void setMsgsorce(Integer msgsorce) {
        this.msgsorce = msgsorce;
    }
}