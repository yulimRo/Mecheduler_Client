package com.example.mecheduler.DTO;

import android.os.Parcel;
import android.os.Parcelable;

public class LikeList implements Parcelable {

    String hpname;
    String hpaddress;

    protected LikeList(Parcel in) {
        hpname = in.readString();
        hpaddress = in.readString();
    }

    public static final Creator<LikeList> CREATOR = new Creator<LikeList>() {
        @Override
        public LikeList createFromParcel(Parcel in) {
            return new LikeList(in);
        }

        @Override
        public LikeList[] newArray(int size) {
            return new LikeList[size];
        }
    };

    public String getHpname() {
        return hpname;
    }

    public void setHpname(String hpname) {
        this.hpname = hpname;
    }

    public String getHpaddress() {
        return hpaddress;
    }

    public LikeList() {
    }

    public void setHpaddress(String hpaddress) {
        this.hpaddress = hpaddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hpname);
        dest.writeString(hpaddress);
    }
}
