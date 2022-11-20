package com.example.pojo;

import java.util.Date;

public class RequireItem{
    private int ritemid;
    private String ritemname;
    private String detail;
    private String price;
    private Date date;
    private String host;
    private ItemPicture itemPicture;

    public RequireItem() {
    }

    public RequireItem(int ritemid, String ritemname, String detail, String price, Date date,String host) {
        this.ritemid = ritemid;
        this.ritemname = ritemname;
        this.detail = detail;
        this.price = price;
        this.date = date;
    }

    public RequireItem(int ritemid, String ritemname, String detail, String price, Date date,String host,ItemPicture itemPicture) {
        this.ritemid = ritemid;
        this.ritemname = ritemname;
        this.detail = detail;
        this.price = price;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
