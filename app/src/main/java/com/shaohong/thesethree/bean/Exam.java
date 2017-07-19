package com.shaohong.thesethree.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shaohong on 2017/5/m10.
 */

public class Exam implements Parcelable{
    private String address;
    private String image;
    private String title;
    private String startTime;
    private String endTime;
    private String fanWei;
    private int fen;
    private int groupId;
    private String groupName;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFanWei() {
        return fanWei;
    }

    public void setFanWei(String fanWei) {
        this.fanWei = fanWei;
    }

    public int getFen() {
        return fen;
    }

    public void setFen(int fen) {
        this.fen = fen;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getHosId() {
        return hosId;
    }

    public void setHosId(int hosId) {
        this.hosId = hosId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public int getJiGeScore() {
        return jiGeScore;
    }

    public void setJiGeScore(int jiGeScore) {
        this.jiGeScore = jiGeScore;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getWardCode() {
        return wardCode;
    }

    public void setWardCode(int wardCode) {
        this.wardCode = wardCode;
    }

    private int hosId;
    private int Id;
    private boolean isEnd;
    private int jiGeScore;
    private int type;
    private int wardCode;
    public Exam(){}

    protected Exam(Parcel in) {
        address = in.readString();
        image = in.readString();
        title = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        fanWei = in.readString();
        fen = in.readInt();
        groupId = in.readInt();
        groupName = in.readString();
        hosId = in.readInt();
        Id = in.readInt();
        isEnd = in.readByte() != 0;
        jiGeScore = in.readInt();
        type = in.readInt();
        wardCode = in.readInt();
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(image);
        dest.writeString(title);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(fanWei);
        dest.writeInt(fen);
        dest.writeInt(groupId);
        dest.writeString(groupName);
        dest.writeInt(hosId);
        dest.writeInt(Id);
        dest.writeByte((byte) (isEnd ? 1 : 0));
        dest.writeInt(jiGeScore);
        dest.writeInt(type);
        dest.writeInt(wardCode);
    }
}
