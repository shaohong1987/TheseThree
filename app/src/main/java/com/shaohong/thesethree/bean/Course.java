package com.shaohong.thesethree.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shaohong on 2017/5/18.
 */

public class Course implements Parcelable {
    private String name;
    private String address;
    private String date;
    private String cent;
    private String publishDate;
    private String publisher;
    private String status;

    public Course(){}
    protected Course(Parcel in) {
        name = in.readString();
        address = in.readString();
        date = in.readString();
        cent = in.readString();
        publishDate = in.readString();
        publisher = in.readString();
        status = in.readString();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCent() {
        return cent;
    }

    public void setCent(String cent) {
        this.cent = cent;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(date);
        dest.writeString(cent);
        dest.writeString(publishDate);
        dest.writeString(publisher);
        dest.writeString(status);
    }
}
