package com.example.mecheduler.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class LikeHospital implements Parcelable {

    String hpid;
    String hpname;
    String hpaddress;
    String hptel;
    int pcode;
    Date replyDate;
    Date updateDate;

    public LikeHospital() {
    }

    public LikeHospital(String hpid, String hpname, String hpaddress, String hptel, int pcode) {
        this.hpid = hpid;
        this.hpname = hpname;
        this.hpaddress = hpaddress;
        this.hptel = hptel;
        this.pcode = pcode;
    }

    protected LikeHospital(Parcel in) {
        hpid = in.readString();
        hpname = in.readString();
        hpaddress = in.readString();
        hptel = in.readString();
        pcode = in.readInt();
    }

    public static final Creator<LikeHospital> CREATOR = new Creator<LikeHospital>() {
        @Override
        public LikeHospital createFromParcel(Parcel in) {
            return new LikeHospital(in);
        }

        @Override
        public LikeHospital[] newArray(int size) {
            return new LikeHospital[size];
        }
    };

    public String getHpid() {
        return hpid;
    }

    public void setHpid(String hpid) {
        this.hpid = hpid;
    }

    public String getHpname() {
        return hpname;
    }

    public void setHpname(String hpname) {
        this.hpname = hpname;
    }

    public String getHpaddress() {
        return hpaddress;
    }

    public void setHpaddress(String hpaddress) {
        this.hpaddress = hpaddress;
    }

    public String getHptel() {
        return hptel;
    }

    public void setHptel(String hptel) {
        this.hptel = hptel;
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

    public int getPcode() {
        return pcode;
    }

    public void setPcode(int pcode) {
        this.pcode = pcode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hpid);
        dest.writeString(hpname);
        dest.writeString(hpaddress);
        dest.writeString(hptel);
        dest.writeInt(pcode);
    }
}
