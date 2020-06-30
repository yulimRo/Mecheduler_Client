package com.example.mecheduler.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
public class MedicineVO implements Parcelable {
    String rcode; //회원번호
    String hpname;
    String pname;
    String mname;
    int num;
    int forday;
    int oneday;
    String eattime;
    String sectime;
    String info1;
    String info2;
    String danger;
    String replyDate;

    public MedicineVO(String rcode, String hpname, String pname, String mname, int num, int forday, int oneday, String eattime, String sectime, String info1, String info2, String danger, String replyDate) {
        this.rcode = rcode;
        this.hpname = hpname;
        this.pname = pname;
        this.mname = mname;
        this.num = num;
        this.forday = forday;
        this.oneday = oneday;
        this.eattime = eattime;
        this.sectime = sectime;
        this.info1 = info1;
        this.info2 = info2;
        this.danger = danger;
        this.replyDate = replyDate;
    }

    public MedicineVO() {
    }

    protected MedicineVO(Parcel in) {
        rcode = in.readString();
        hpname = in.readString();
        pname = in.readString();
        mname = in.readString();
        num = in.readInt();
        forday = in.readInt();
        oneday = in.readInt();
        eattime = in.readString();
        sectime = in.readString();
        info1 = in.readString();
        info2 = in.readString();
        danger = in.readString();
    }

    public static final Creator<MedicineVO> CREATOR = new Creator<MedicineVO>() {
        @Override
        public MedicineVO createFromParcel(Parcel in) {
            return new MedicineVO(in);
        }

        @Override
        public MedicineVO[] newArray(int size) {
            return new MedicineVO[size];
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

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getForday() {
        return forday;
    }

    public void setForday(int forday) {
        this.forday = forday;
    }

    public int getOneday() {
        return oneday;
    }

    public void setOneday(int oneday) {
        this.oneday = oneday;
    }

    public String getEattime() {
        return eattime;
    }

    public void setEattime(String eattime) {
        this.eattime = eattime;
    }

    public String getSectime() {
        return sectime;
    }

    public void setSectime(String sectime) {
        this.sectime = sectime;
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public String getDanger() {
        return danger;
    }

    public void setDanger(String danger) {
        this.danger = danger;
    }

    public String getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(String replyDate) {
        this.replyDate = replyDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rcode);
        dest.writeString(hpname);
        dest.writeString(pname);
        dest.writeString(mname);
        dest.writeInt(num);
        dest.writeInt(forday);
        dest.writeInt(oneday);
        dest.writeString(eattime);
        dest.writeString(sectime);
        dest.writeString(info1);
        dest.writeString(info2);
        dest.writeString(danger);
    }
}