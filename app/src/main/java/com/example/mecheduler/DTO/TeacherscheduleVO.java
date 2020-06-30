package com.example.mecheduler.DTO;

import java.util.Date;

public class TeacherscheduleVO {

    String dcode;
    String context;
    String context2;
    String startdate;
    String enddate;
    Date replyDate;
    Date updateDate;

    public TeacherscheduleVO() {
    };

    public String getDcode() {
        return dcode;
    }

    public void setDcode(String dcode) {
        this.dcode = dcode;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getContext2() {
        return context2;
    }

    public void setContext2(String context2) {
        this.context2 = context2;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
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

    public TeacherscheduleVO(String dcode, String context, String context2, String startdate, String enddate) {
        this.dcode = dcode;
        this.context = context;
        this.context2 = context2;
        this.startdate = startdate;
        this.enddate = enddate;
    }
}
