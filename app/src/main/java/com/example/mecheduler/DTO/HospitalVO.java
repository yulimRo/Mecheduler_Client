package com.example.mecheduler.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class HospitalVO implements Parcelable {

    String hpcode; //병원번호
    String hpname; //병원이름
    String hpid;  //병원아이디
    String hppwd; //병원페스워드
    String hptel;  //병원전화
    String hpemail; //병원이메일
    Date replyDate;
    Date updateDate;

    protected HospitalVO(Parcel in) {
        hpcode = in.readString();
        hpname = in.readString();
        hpid = in.readString();
        hppwd = in.readString();
        hptel = in.readString();
        hpemail = in.readString();
    }

    public static final Creator<HospitalVO> CREATOR = new Creator<HospitalVO>() {
        @Override
        public HospitalVO createFromParcel(Parcel in) {
            return new HospitalVO(in);
        }

        @Override
        public HospitalVO[] newArray(int size) {
            return new HospitalVO[size];
        }
    };

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public String getHpname() {
        return hpname;
    }

    public String getHpcode() {
        return hpcode;
    }

    public void setHpcode(String hpcode) {
        this.hpcode = hpcode;
    }

    public void setHpname(String hname) {
        this.hpname = hname;
    }

    public String getHpid() {
        return hpid;
    }

    public void setHpid(String hpid) {
        this.hpid = hpid;
    }

    public String getHppwd() {
        return hppwd;
    }

    public void setHppwd(String hppwd) {
        this.hppwd = hppwd;
    }

    public String getHptel() {
        return hptel;
    }

    public void setHptel(String hptel) {
        this.hptel = hptel;
    }

    public String getHpemail() {
        return hpemail;
    }

    public void setHpemail(String hpemail) {
        this.hpemail = hpemail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hpcode);
        dest.writeString(hpname);
        dest.writeString(hpid);
        dest.writeString(hppwd);
        dest.writeString(hptel);
        dest.writeString(hpemail);
    }
}
