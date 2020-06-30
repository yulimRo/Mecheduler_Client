package com.example.mecheduler.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class DoctorVO implements Parcelable {

    String dcode;
    String dname;
    String dgender;
    String subject1;
    String subject2;
    Date replyDate;
    Date updateDate;

    public DoctorVO(){};
    public DoctorVO(String dcode, String dname, String dgemder, String subject1, String subject2) {
        this.dcode = dcode;
        this.dname = dname;
        this.dgender = dgemder;
        this.subject1 = subject1;
        this.subject2 = subject2;
    }

    protected DoctorVO(Parcel in) {
        dcode = in.readString();
        dname = in.readString();
        dgender = in.readString();
        subject1 = in.readString();
        subject2 = in.readString();
    }

    public static final Creator<DoctorVO> CREATOR = new Creator<DoctorVO>() {
        @Override
        public DoctorVO createFromParcel(Parcel in) {
            return new DoctorVO(in);
        }

        @Override
        public DoctorVO[] newArray(int size) {
            return new DoctorVO[size];
        }
    };

    public String getDcode() {
        return dcode;
    }

    public void setDcode(String dcode) {
        this.dcode = dcode;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDgender() {
        return dgender;
    }

    public void setDgemder(String dgender) {
        this.dgender = dgender;
    }

    public String getSubject1() {
        return subject1;
    }

    public void setSubject1(String subject1) {
        this.subject1 = subject1;
    }

    public String getSubject2() {
        return subject2;
    }

    public void setSubject2(String subject2) {
        this.subject2 = subject2;
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
        dest.writeString(dcode);
        dest.writeString(dname);
        dest.writeString(dgender);
        dest.writeString(subject1);
        dest.writeString(subject2);
    }
}
