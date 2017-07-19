package com.shaohong.thesethree.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by shaohong on 2017/7/18.
 */

public class Edu implements Parcelable{
    private static final  long serialVersionUID=1L;
    public int id;
    public String content;
    public int type;
    public int educode;
    public int testcode;
    public String sendtime;
    public int hospitalcode;
    public int groupid;
    public int isvalued;


    public Edu(){}
    protected Edu(Parcel in) {
        id = in.readInt();
        content = in.readString();
        type = in.readInt();
        educode = in.readInt();
        testcode = in.readInt();
        sendtime = in.readString();
        hospitalcode = in.readInt();
        groupid = in.readInt();
        isvalued = in.readInt();
    }

    public static final Creator<Edu> CREATOR = new Creator<Edu>() {
        @Override
        public Edu createFromParcel(Parcel in) {
            return new Edu(in);
        }

        @Override
        public Edu[] newArray(int size) {
            return new Edu[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(content);
        dest.writeInt(type);
        dest.writeInt(educode);
        dest.writeInt(testcode);
        dest.writeString(sendtime);
        dest.writeInt(hospitalcode);
        dest.writeInt(groupid);
        dest.writeInt(isvalued);
    }
}
