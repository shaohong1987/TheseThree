package com.shaohong.thesethree.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shaohong on 2017/7/17.
 */

public class HistoryListItemObject implements Parcelable {
    public String type;
    public String title;
    public String status;
    public String dt;
    public String educode;
    public String groupid;
    public String hospitalcode;
    public String isvalued;
    public String testcode;
    public int Id;


    protected HistoryListItemObject(Parcel in) {
        type = in.readString();
        title = in.readString();
        status = in.readString();
        dt = in.readString();
        educode = in.readString();
        groupid = in.readString();
        hospitalcode = in.readString();
        isvalued = in.readString();
        testcode = in.readString();
        Id = in.readInt();
    }

    public static final Creator<HistoryListItemObject> CREATOR = new Creator<HistoryListItemObject>() {
        @Override
        public HistoryListItemObject createFromParcel(Parcel in) {
            return new HistoryListItemObject(in);
        }

        @Override
        public HistoryListItemObject[] newArray(int size) {
            return new HistoryListItemObject[size];
        }
    };

    public String getEducode() {
        return educode;
    }

    public void setEducode(String educode) {
        this.educode = educode;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getHospitalcode() {
        return hospitalcode;
    }

    public void setHospitalcode(String hospitalcode) {
        this.hospitalcode = hospitalcode;
    }

    public String getIsvalued() {
        return isvalued;
    }

    public void setIsvalued(String isvalued) {
        this.isvalued = isvalued;
    }

    public String getTestcode() {
        return testcode;
    }

    public void setTestcode(String testcode) {
        this.testcode = testcode;
    }

    public HistoryListItemObject(){}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(title);
        dest.writeString(status);
        dest.writeString(dt);
        dest.writeString(educode);
        dest.writeString(groupid);
        dest.writeString(hospitalcode);
        dest.writeString(isvalued);
        dest.writeString(testcode);
        dest.writeInt(Id);
    }
}
