package com.example.whiskersapp.petwhiskers;

public class model_user {
    String id;
    String fname;
    String lname;
    String contact_number;
    String email;
    String username;
    String password;
    public model_user(){

    }
    public model_user(String id, String fname, String lname, String contact_number, String email, String username, String password) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.contact_number = contact_number;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
