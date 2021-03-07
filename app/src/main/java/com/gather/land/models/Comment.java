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
    private String userName;

    public Comment() {
    }

    public Comment(  String postKey, long timeStampCreated, String body) {
        this.postKey = postKey;
        this.timeStampCreated = timeStampCreated;
        this.body = body;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    @Override
    public String toString() {
        return "Comment{" +
                "key='" + key + '\'' +
                ", userKey='" + userKey + '\'' +
                ", postKey='" + postKey + '\'' +
                ", timeStampCreated=" + timeStampCreated +
                ", body='" + body + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
