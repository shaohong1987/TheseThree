package com.shaohong.thesethree.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shaohong on 2017/5/10.
 */

public class Exam implements Parcelable{
    private String image;
    private String name;
    private String datetime;
    private String address;
    private int status;

    protected Exam(Parcel in) {
        image = in.readString();
        name = in.readString();
        datetime = in.readString();
        address = in.readString();
        status = in.readInt();
    }

    public Exam(){}

    public static final Creator<Exam> CREATOR = new Creator<Exam>() {
        @Override
        public Exam createFromParcel(Parcel in) {
            return new Exam(in);
        }

        @Override
        public Exam[] newArray(int size) {
            return new Exam[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(name);
        dest.writeString(datetime);
        dest.writeString(address);
        dest.writeInt(status);
    }
}
