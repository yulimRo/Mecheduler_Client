package com.example.mecheduler.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ListVO implements Parcelable {
    List<String[]> item;


    protected ListVO(Parcel in) {
    }

    public static final Creator<ListVO> CREATOR = new Creator<ListVO>() {
        @Override
        public ListVO createFromParcel(Parcel in) {
            return new ListVO(in);
        }

        @Override
        public ListVO[] newArray(int size) {
            return new ListVO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public List<String[]> getItem() {
        return item;
    }

    public void setItem(List<String[]> item) {
        this.item = item;
    }

    public ListVO(List<String[]> item) {
        this.item = item;
    }

    public ListVO() {
    }
}
