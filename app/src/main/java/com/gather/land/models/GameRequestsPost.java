package com.gather.land.models;

import androidx.room.Entity;

@Entity(tableName = "gameRequestsPost")
public class GameRequestsPost extends Post {

    private long timeStampExpired;
    private int   usersMissing;


    public GameRequestsPost() {
    }

    public GameRequestsPost(String gameTag, long timeStampCreated, String title, long timeStampExpired, int usersMissing) {
        super(gameTag, timeStampCreated, title);
        this.timeStampExpired = timeStampExpired;
        this.usersMissing = usersMissing;
    }


    public long getTimeStampExpired() {
        return timeStampExpired;
    }

    public void setTimeStampExpired(long timeStampExpired) {
        this.timeStampExpired = timeStampExpired;
    }

    public int getUsersMissing() {
        return usersMissing;
    }

    public void setUsersMissing(int usersMissing) {
        this.usersMissing = usersMissing;
    }
}
