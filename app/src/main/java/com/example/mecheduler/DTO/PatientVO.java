package com.example.mecheduler.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class PatientVO implements Parcelable{
    int pcode; //회원번호
    String pname; //회원이름
    String pid; //회원아이디
    String ppwd; //회원비밀번호
    String pbirth; //회원생년월일
    String ptel; //회원 전화번호
    String pgender;
    String replyDate;
    String updateDate;

    protected PatientVO(Parcel in) {
        pcode = in.readInt();
        pname = in.readString();
        pid = in.readString();
        ppwd = in.readString();
        pbirth = in.readString();
        ptel = in.readString();
        pgender = in.readString();
        replyDate = in.readString();
        updateDate = in.readString();
    }

    public static final Creator<PatientVO> CREATOR = new Creator<PatientVO>() {
        @Override
        public PatientVO createFromParcel(Parcel in) {
            return new PatientVO(in);
        }

        @Override
        public PatientVO[] newArray(int size) {
            return new PatientVO[size];
        }
    };

    public String getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(String replyDate) {
        this.replyDate = replyDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getPgender() {
        return pgender;
    }

    public void setPgender(String pgender) {
        this.pgender = pgender;
    }


    public PatientVO(){};

    public PatientVO(String pname, String pid, String ppwd, String pbirth, String ptel,String pgender) {
        this.pname = pname;
        this.pid = pid;
        this.ppwd = ppwd;
        this.pbirth = pbirth;
        this.ptel = ptel;
        this.pgender =pgender;
    }

    public PatientVO(int pcode, String pname, String pid, String ppwd, String pbirth, String ptel, String pgender) {
        this.pcode = pcode;
        this.pname = pname;
        this.pid = pid;
        this.ppwd = ppwd;
        this.pbirth = pbirth;
        this.ptel = ptel;
        this.pgender = pgender;
    }

    public int getPcode() {
        return pcode;
    }

    public void setPcode(int pcode) {
        this.pcode = pcode;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPpwd() {
        return ppwd;
    }

    public void setPpwd(String ppwd) {
        this.ppwd = ppwd;
    }

    public String getPbirth() {
        return pbirth;
    }

    public void setPbirth(String pbirth) {
        this.pbirth = pbirth;
    }

    public String getPtel() {
        return ptel;
    }

    public void setPtel(String ptel) {
        this.ptel = ptel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pcode);
        dest.writeString(pname);
        dest.writeString(pid);
        dest.writeString(ppwd);
        dest.writeString(pbirth);
        dest.writeString(ptel);
        dest.writeString(pgender);
        dest.writeString(replyDate);
        dest.writeString(updateDate);
    }
}