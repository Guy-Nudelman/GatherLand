package com.gather.land.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public abstract class Post implements Serializable {

    @NonNull
    @PrimaryKey
    private String key;
    private String gameTag;
    private String userKey;
    private long timeStampCreated;
    private String title;
    private String userName;


    @Exclude
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] imagePostRAW;

    @Exclude
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] imageProfileRAW;

    @Exclude
    public byte[] getImageProfileRAW() {
        return imageProfileRAW;
    }

    @Exclude
    public void setImageProfileRAW(byte[] imageProfileRAW) {
        this.imageProfileRAW = imageProfileRAW;
    }

    public String getUserName() {
        return userName;
    }

    @Exclude
    public byte[] getImagePostRAW() {
        return imagePostRAW;
    }

    @Exclude
    public void setImagePostRAW(byte[] imagePostRAW) {
        this.imagePostRAW = imagePostRAW;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Post() {

    }



    public Post(String gameTag, long timeStampCreated, String title) {
        this.gameTag = gameTag;
        this.timeStampCreated = timeStampCreated;
        this.title = title;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getGameTag() {
        return gameTag;
    }

    public void setGameTag(String gameTag) {
        this.gameTag = gameTag;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public long getTimeStampCreated() {
        return timeStampCreated;
    }

    public void setTimeStampCreated(long timeStampCreated) {
        this.timeStampCreated = timeStampCreated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
