package com.qzl.criminalintent.bean;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Qzl on 2016-09-02.
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;//日期
    private boolean mSolved;//是否已经处理
    private String mSuspect;//存储嫌疑人姓名

    public Crime() {
        this(UUID.randomUUID());
//        mId = UUID.randomUUID();
//        mDate = new Date();
    }

    public Crime(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    //获取照片文件名
    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }
}
