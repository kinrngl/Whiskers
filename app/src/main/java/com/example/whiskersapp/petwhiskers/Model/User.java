package com.example.whiskersapp.petwhiskers.Model;

public class User {
    String id;
    String fname;
    String lname;
    String contact;
    String email;
    String password;
    public model_user(){

<<<<<<< HEAD:app/src/main/java/com/example/whiskersapp/petwhiskers/model_user.java
    }
    public model_user(String id, String fname, String lname, String contact_number, String email, String username, String password) {
=======
    public User(){

    }

    public User(String id, String fname, String lname, String contact, String email, String password) {
>>>>>>> master:app/src/main/java/com/example/whiskersapp/petwhiskers/Model/User.java
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.contact = contact;
        this.email = email;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
