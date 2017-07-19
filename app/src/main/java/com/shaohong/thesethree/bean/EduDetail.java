package com.shaohong.thesethree.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by shaohong on 2017/7/18.
 */

public class EduDetail implements Parcelable {
    private static final long serialVersionUID=1L;
    public int id;
    public String zhuti;
    public String org;
    public String adress;
    public String teacher;
    public String time;
    public String recordtime;
    public int type;
    public int score;
    public int hosid;
    public int state;

    public EduDetail(){}

    protected EduDetail(Parcel in) {
        id = in.readInt();
        zhuti = in.readString();
        org = in.readString();
        adress = in.readString();
        teacher = in.readString();
        time = in.readString();
        recordtime = in.readString();
        type = in.readInt();
        score = in.readInt();
        hosid = in.readInt();
        state = in.readInt();
    }

    public static final Creator<EduDetail> CREATOR = new Creator<EduDetail>() {
        @Override
        public EduDetail createFromParcel(Parcel in) {
            return new EduDetail(in);
        }

        @Override
        public EduDetail[] newArray(int size) {
            return new EduDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(zhuti);
        dest.writeString(org);
        dest.writeString(adress);
        dest.writeString(teacher);
        dest.writeString(time);
        dest.writeString(recordtime);
        dest.writeInt(type);
        dest.writeInt(score);
        dest.writeInt(hosid);
        dest.writeInt(state);
    }
}
