package com.example.mecheduler.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class ReservationVO implements Parcelable{

    String rcode;
    String hpcode;
    int pcode;
    String dcode;
    String subject;
    String time;
    String date;
    int res_who; // 환자 측 : 1 , 병원측 :0
    Date replyDate;
    Date updateDate;
    String res_state;

    protected ReservationVO(Parcel in) {
        rcode = in.readString();
        hpcode = in.readString();
        pcode = in.readInt();
        dcode = in.readString();
        subject = in.readString();
        time = in.readString();
        date = in.readString();
        res_who = in.readInt();
        res_state = in.readString();
    }

    public static final Creator<ReservationVO> CREATOR = new Creator<ReservationVO>() {
        @Override
        public ReservationVO createFromParcel(Parcel in) {
            return new ReservationVO(in);
        }

        @Override
        public ReservationVO[] newArray(int size) {
            return new ReservationVO[size];
        }
    };

    public String getRes_state() {
        return res_state;
    }

    public void setRes_state(String res_state) {
        this.res_state = res_state;
    }

    public ReservationVO(String rcode, String hpcode, int pcode, String dcode, String subject, String time, String date, int res_who) {
        this.rcode = rcode;
        this.hpcode = hpcode;
        this.pcode = pcode;
        this.dcode = dcode;
        this.subject = subject;
        this.time = time;
        this.date = date;
        this.res_who = res_who;
    }

    public ReservationVO() {
    }

    public int getRes_who() {
        return res_who;
    }

    public void setRes_who(int res_who) {
        this.res_who = res_who;
    }

    public String getRcode() {
        return rcode;
    }

    public void setRcode(String rcode) {
        this.rcode = rcode;
    }

    public String getHpcode() {
        return hpcode;
    }

    public void setHpcode(String hpcode) {
        this.hpcode = hpcode;
    }

    public int getPcode() {
        return pcode;
    }

    public void setPcode(int pcode) {
        this.pcode = pcode;
    }

    public String getDcode() {
        return dcode;
    }

    public void setDcode(String dcode) {
        this.dcode = dcode;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rcode);
        dest.writeString(hpcode);
        dest.writeInt(pcode);
        dest.writeString(dcode);
        dest.writeString(subject);
        dest.writeString(time);
        dest.writeString(date);
        dest.writeInt(res_who);
        dest.writeString(res_state);
    }
}
