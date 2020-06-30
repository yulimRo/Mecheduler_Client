package com.example.mecheduler.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class HpConnectDoc implements Parcelable {

    String hpcode;
    String dcode;
    Date replyDate;
    Date updateDate;

    public HpConnectDoc(){};

    protected HpConnectDoc(Parcel in) {
        hpcode = in.readString();
        dcode = in.readString();
    }

    public static final Creator<HpConnectDoc> CREATOR = new Creator<HpConnectDoc>() {
        @Override
        public HpConnectDoc createFromParcel(Parcel in) {
            return new HpConnectDoc(in);
        }

        @Override
        public HpConnectDoc[] newArray(int size) {
            return new HpConnectDoc[size];
        }
    };

    public String getHpcode() {
        return hpcode;
    }

    public void setHpcode(String hpcode) {
        this.hpcode = hpcode;
    }

    public String getDcode() {
        return dcode;
    }

    public void setDcode(String dcode) {
        this.dcode = dcode;
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

    public HpConnectDoc(String hpcode, String dcode, Date replyDate, Date updateDate) {
        this.hpcode = hpcode;
        this.dcode = dcode;
        this.replyDate = replyDate;
        this.updateDate = updateDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hpcode);
        dest.writeString(dcode);
    }
}
