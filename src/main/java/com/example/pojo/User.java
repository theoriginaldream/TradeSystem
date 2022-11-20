package com.example.pojo;

public class User{
    private String userid;
    private String password;
    private String phone;
    private String name;
    private String schoolzone;
    private String introduce;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolzone() {
        return schoolzone;
    }

    public void setSchoolzone(String schoolzone) {
        this.schoolzone = schoolzone;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public User() {
    }

    public User(String userid, String password, String phone) {
        this.userid = userid;
        this.password = password;
        this.phone = phone;
    }

    public User(String userid, String password, String phone, String name, String schoolzone, String introduce) {
        this.userid = userid;
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.schoolzone = schoolzone;
        this.introduce = introduce;
    }
}
