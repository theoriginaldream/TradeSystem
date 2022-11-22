package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class RequireItem{
    private int ritemid;
    private String ritemname;
//    private String detail;
    private String price;
    private String schoolzone;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private String host;
    private ItemPicture itemPicture;

    public RequireItem() {
    }

    public RequireItem(int ritemid, String ritemname,String price,String schoolzone, Date date,String host) {
        this.ritemid = ritemid;
        this.ritemname = ritemname;
//        this.detail = detail;
        this.price = price;
        this.schoolzone = schoolzone;
        this.date = date;
    }

    public RequireItem(int ritemid, String ritemname,String price,String schoolzone, Date date,String host,ItemPicture itemPicture) {
        this.ritemid = ritemid;
        this.ritemname = ritemname;
//        this.detail = detail;
        this.price = price;
        this.schoolzone = schoolzone;
        this.date = date;
        this.itemPicture = itemPicture;
    }

    public int getRitemid() {
        return ritemid;
    }

    public void setRitemid(int ritemid) {
        this.ritemid = ritemid;
    }

    public String getRitemname() {
        return ritemname;
    }

    public void setRitemname(String ritemname) {
        this.ritemname = ritemname;
    }

//    public String getDetail() {
//        return detail;
//    }
//
//    public void setDetail(String detail) {
//        this.detail = detail;
//    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSchoolzone() {
        return schoolzone;
    }

    public void setSchoolzone(String schoolzone) {
        this.schoolzone = schoolzone;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public ItemPicture getItemPicture() {
        return itemPicture;
    }

    public void setItemPicture(ItemPicture itemPicture) {
        this.itemPicture = itemPicture;
    }
}
