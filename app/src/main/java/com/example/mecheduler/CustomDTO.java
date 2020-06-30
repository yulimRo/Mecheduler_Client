package com.example.mecheduler;

public class CustomDTO {
    private String hpName;   //병원 이름
    private String hpAddr;  //병원주소
    private String hpDpid; //진료과목
    private String hpMapImg;
    private String hpTel1;

    public String getHpid() {
        return hpid;
    }

    public void setHpid(String hpid) {
        this.hpid = hpid;
    }

    private String hpid;

    public String getHpMapImg() {
        return hpMapImg;
    }

    public void setHpMapImg(String hpMapImg) {
        this.hpMapImg = hpMapImg;
    }

    public String getHpTel1() {
        return hpTel1;
    }

    public void setHpTel1(String hpTel1) {
        this.hpTel1 = hpTel1;
    }

    public String getHpName() {
        return hpName;
    }

    public void setHpName(String hpName) {
        this.hpName = hpName;
    }

    public String getHpAddr() {
        return hpAddr;
    }

    public void setHpAddr(String hpAddr) {
        this.hpAddr = hpAddr;
    }

    public String getHpDpid() {
        return hpDpid;
    }

    public void setHpDpid(String hpDpid) {
        this.hpDpid = hpDpid;
    }
}