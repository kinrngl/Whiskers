package com.example.whiskersapp.petwhiskers.Model;

public class UserChat {
    String id;
    String user_one_id;
    String user_two_id;
    String isReadUserOne; //0 - read, 1 - unread
    String isReadUserTwo; //0 - read, 1 - unread

    public UserChat(){}

    public UserChat(String id, String user_one_id, String user_two_id, String isReadUserOne, String isReadUserTwo) {
        this.id = id;
        this.user_one_id = user_one_id;
        this.user_two_id = user_two_id;
        this.isReadUserOne = isReadUserOne;
        this.isReadUserTwo = isReadUserTwo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_one_id() {
        return user_one_id;
    }

    public void setUser_one_id(String user_one_id) {
        this.user_one_id = user_one_id;
    }

    public String getUser_two_id() {
        return user_two_id;
    }

    public void setUser_two_id(String user_two_id) {
        this.user_two_id = user_two_id;
    }

    public String getIsReadUserOne() {
        return isReadUserOne;
    }

    public void setIsReadUserOne(String isReadUserOne) {
        this.isReadUserOne = isReadUserOne;
    }

    public String getIsReadUserTwo() {
        return isReadUserTwo;
    }

    public void setIsReadUserTwo(String isReadUserTwo) {
        this.isReadUserTwo = isReadUserTwo;
    }
}