package com.gather.land.models;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

public abstract class Post {

    @NonNull
    @PrimaryKey
    private String key;
    private String gameTag;
    private String userKey;
    private long timeStampCreated;
    private String title;


    public Post() {

    }


    public Post(String gameTag, String userKey, long timeStampCreated, String title) {
        this.gameTag = gameTag;
        this.userKey = userKey;
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
