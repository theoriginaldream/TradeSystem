package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Item{
    private int itemid;
    private String itemname;
    private String schoolzone;
    private String type;
//    private String detail;
    private String price;
    private String host;
    private ItemPicture itempicture;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date datetime;

    private String status;

    public Item() {
    }

    public Item(int itemid, String itemname, String schoolzone, String type, String price, String host,Date datetime,String status, ItemPicture itempicture) {
        this.itemid = itemid;
        this.itemname = itemname;
        this.schoolzone = schoolzone;
        this.type = type;
//        this.detail = detail;
        this.price = price;
        this.host = host;
        this.datetime = datetime;
        this.status = status;
        this.itempicture = itempicture;
    }

    public Item(int itemid, String itemname, String schoolzone, String type, String price, String host,Date datetime,String status) {
        this.itemid = itemid;
        this.itemname = itemname;
        this.schoolzone = schoolzone;
        this.type = type;
//        this.detail = detail;
        this.price = price;
        this.host = host;
        this.datetime = datetime;
        this.status = status;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getSchoolzone() {
        return schoolzone;
    }

    public void setSchoolzone(String schoolzone) {
        this.schoolzone = schoolzone;
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

    public ItemPicture getItempicture() {
        return itempicture;
    }

    public void setItempicture(ItemPicture itempicture) {
        this.itempicture = itempicture;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
