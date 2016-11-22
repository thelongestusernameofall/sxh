package com.sxh.model;

public class ActivityType {
    private Integer activitytypeid;

    private String activityname;

    private Integer status;

    public Integer getActivitytypeid() {
        return activitytypeid;
    }

    public void setActivitytypeid(Integer activitytypeid) {
        this.activitytypeid = activitytypeid;
    }

    public String getActivityname() {
        return activityname;
    }

    public void setActivityname(String activityname) {
        this.activityname = activityname == null ? null : activityname.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}