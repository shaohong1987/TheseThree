package com.shaohong.thesethree.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.shaohong.thesethree.utils.ActionType;
import com.shaohong.thesethree.utils.DataType;

/**
 * Created by shaohong on 2017/5/10.
 */

public class CommonData implements Parcelable{
    private String image;
    private String title;
    private String content;
    private int dataType;
    private int actionType;

    public CommonData(){}
    protected CommonData(Parcel in) {
        image = in.readString();
        title = in.readString();
        content = in.readString();
        dataType = in.readInt();
        actionType = in.readInt();
    }

    public static final Creator<CommonData> CREATOR = new Creator<CommonData>() {
        @Override
        public CommonData createFromParcel(Parcel in) {
            return new CommonData(in);
        }

        @Override
        public CommonData[] newArray(int size) {
            return new CommonData[size];
        }
    };

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeInt(dataType);
        dest.writeInt(actionType);
    }
}
