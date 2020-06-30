package com.example.mecheduler.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HospitalItem3 {
    String dutyName;
    String hpid;
    String wgs84Lon;
    String wgs84Lat;
    String dutyAddr;
    String dutyTel1;
    String dutyMapimg;
    String dgidIdName;

    public HospitalItem3() {
    }

    public HospitalItem3(String dutyName, String hpid, String wgs84Lon, String wgs84Lat, String dutyAddr, String dutyTel1, String dutyMapimg,String dgidIdName) {
        this.dutyName = dutyName;
        this.hpid = hpid;
        this.wgs84Lon = wgs84Lon;
        this.wgs84Lat = wgs84Lat;
        this.dutyAddr = dutyAddr;
        this.dutyTel1 = dutyTel1;
        this.dutyMapimg = dutyMapimg;
        this.dgidIdName = dgidIdName;
    }

    public String getDgidIdName() {
        return dgidIdName;
    }

    public void setDgidIdName(String dgidIdName) {
        this.dgidIdName = dgidIdName;
    }

    public String getDutyMapimg() {
        return dutyMapimg;
    }

    public void setDutyMapimg(String dutyMapimg) {
        this.dutyMapimg = dutyMapimg;
    }

    public String getDutyAddr() {
        return dutyAddr;
    }

    public void setDutyAddr(String dutyAddr) {
        this.dutyAddr = dutyAddr;
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    public String getHpid() {
        return hpid;
    }

    public void setHpid(String hpid) {
        this.hpid = hpid;
    }

    public String getWgs84Lon() {
        return wgs84Lon;
    }

    public void setWgs84Lon(String wgs84Lon) {
        this.wgs84Lon = wgs84Lon;
    }

    public String getWgs84Lat() {
        return wgs84Lat;
    }

    public void setWgs84Lat(String wgs84Lat) {
        this.wgs84Lat = wgs84Lat;
    }

    public String getDutyTel1() {
        return dutyTel1;
    }

    public void setDutyTel1(String dutyTel1) {
        this.dutyTel1 = dutyTel1;
    }
}
