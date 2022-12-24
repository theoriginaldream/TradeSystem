package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Consult {

    private int consultid;
    private String consult;
    private String userid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date datetime;

    public Consult() {
    }

    public Consult(int consultid, String consult, String userid, Date datetime) {
        this.consultid = consultid;
        this.consult = consult;
        this.userid = userid;
        this.datetime = datetime;
    }

    public int getConsultid() {
        return consultid;
    }

    public void setConsultid(int consultid) {
        this.consultid = consultid;
    }

    public String getConsult() {
        return consult;
    }

    public void setConsult(String consult) {
        this.consult = consult;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
