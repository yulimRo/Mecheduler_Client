package com.example.mecheduler.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HospitalItem {
    String dutyName;
    String hpid;
    String wgs84Lon;
    String wgs84Lat;
    String dutyAddr;

    public HospitalItem() {
    }

    public HospitalItem(String dutyName, String hpid, String wgs84Lon, String wgs84Lat, String dutyAddr) {
        this.dutyName = dutyName;
        this.hpid = hpid;
        this.wgs84Lon = wgs84Lon;
        this.wgs84Lat = wgs84Lat;
        this.dutyAddr = dutyAddr;
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
}
