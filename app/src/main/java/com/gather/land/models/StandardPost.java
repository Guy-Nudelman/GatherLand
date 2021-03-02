package com.gather.land.models;

import androidx.room.Entity;

@Entity(tableName = "standardPost")
public class StandardPost extends Post {
    private String body;

    public StandardPost() {

    }

    public StandardPost(String gameTag, String userKey, long timeStampCreated, String title, String body) {
        super(gameTag, userKey, timeStampCreated, title);
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    @Override
    public String toString() {
        return "StandardPost{" +
                "body='" + body + '\'' +
                '}';
    }
}
