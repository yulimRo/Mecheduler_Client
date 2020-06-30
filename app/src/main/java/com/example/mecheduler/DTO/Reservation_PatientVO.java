package com.example.mecheduler.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Reservation_PatientVO implements Parcelable {

    String rcode;
    String hpname;
    int pcode;
    String dname;
    String subject;
    String time;
    String date;
    int res_who; // 환자 측 : 1 , 병원측 :0
    Date replyDate;
    Date updateDate;
    String res_state;

    public Reservation_PatientVO() {
    }

    public Reservation_PatientVO(String rcode, String hpname, int pcode, String dname, String subject, String time, String date, int res_who) {
        this.rcode = rcode;
        this.hpname = hpname;
        this.pcode = pcode;
        this.dname = dname;
        this.subject = subject;
        this.time = time;
        this.date = date;
        this.res_who = res_who;
    }

    protected Reservation_PatientVO(Parcel in) {
        rcode = in.readString();
        hpname = in.readString();
        pcode = in.readInt();
        dname = in.readString();
        subject = in.readString();
        time = in.readString();
        date = in.readString();
        res_who = in.readInt();
        res_state = in.readString();
    }

    public static final Creator<Reservation_PatientVO> CREATOR = new Creator<Reservation_PatientVO>() {
        @Override
        public Reservation_PatientVO createFromParcel(Parcel in) {
            return new Reservation_PatientVO(in);
        }

        @Override
        public Reservation_PatientVO[] newArray(int size) {
            return new Reservation_PatientVO[size];
        }
    };

    public String getRcode() {
        return rcode;
    }

    public void setRcode(String rcode) {
        this.rcode = rcode;
    }

    public String getHpname() {
        return hpname;
    }

    public void setHpname(String hpname) {
        this.hpname = hpname;
    }

    public int getPcode() {
        return pcode;
    }

    public void setPcode(int pcode) {
        this.pcode = pcode;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRes_who() {
        return res_who;
    }

    public void setRes_who(int res_who) {
        this.res_who = res_who;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRes_state() {
        return res_state;
    }

    public void setRes_state(String res_state) {
        this.res_state = res_state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rcode);
        dest.writeString(hpname);
        dest.writeInt(pcode);
        dest.writeString(dname);
        dest.writeString(subject);
        dest.writeString(time);
        dest.writeString(date);
        dest.writeInt(res_who);
        dest.writeString(res_state);
    }
}
