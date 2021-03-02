package com.gather.land.models;


public class Like {

    private String postKey;

    private String userKey;

    private boolean isLike;

    private long timeStampCreated;

    private String key;

    public Like() {
    }

    public Like(String postKey, String userKey, boolean isLike, long timeStampCreated) {
        this.postKey = postKey;
        this.userKey = userKey;
        this.isLike = isLike;
        this.timeStampCreated = timeStampCreated;
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
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
}
