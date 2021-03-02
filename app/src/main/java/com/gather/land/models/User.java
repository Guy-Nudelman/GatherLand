package com.gather.land.models;

import java.util.List;

public class User {
    private String email;
    private String firstName;
    private String lastName;
    private String imgUrl;
//    private List<String> gameTags;
    private long birthDate;

    public User() {
    }

    public User(String email, String firstName, String lastName, String imgUrl/*, List<String> gameTags*/, long birthDate) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imgUrl = imgUrl;
//        this.gameTags = gameTags;
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

//    public List<String> getGameTags() {
//        return gameTags;
//    }
//
//    public void setGameTags(List<String> gameTags) {
//        this.gameTags = gameTags;
//    }

    public long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }
}
