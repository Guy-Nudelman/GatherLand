package com.gather.land.models;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName ="Comment" )
public class Comment {


    @NonNull
    @PrimaryKey
    private String key;

    private String userKey;
    private String postKey;
    private long timeStampCreated;
    private String body;

    public Comment() {
    }

    public Comment(String userKey, String postKey, long timeStampCreated, String body) {
        this.userKey = userKey;
        this.postKey = postKey;
        this.timeStampCreated = timeStampCreated;
        this.body = body;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public long getTimeStampCreated() {
        return timeStampCreated;
    }

    public void setTimeStampCreated(long timeStampCreated) {
        this.timeStampCreated = timeStampCreated;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
