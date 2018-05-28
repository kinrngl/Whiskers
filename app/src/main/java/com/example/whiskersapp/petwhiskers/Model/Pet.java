package com.example.whiskersapp.petwhiskers.Model;

public class Pet {
    String id;
    String pet_name;
    String birthdate;
    String breed;
    String eyecolor;
    String furcolor;
    String details;
    String isAdopt; //yes - adopted, no - not yet adopted
    String status; //available, deleted post
    String gender;
    String category;
    String imgUrl;
    String owner_id;

    public Pet(){

    }

    public Pet(String id, String pet_name, String birthdate, String breed, String eyecolor, String furcolor, String details, String isAdopt, String status, String gender, String category, String imgUrl, String owner_id) {
        this.id = id;
        this.pet_name = pet_name;
        this.birthdate = birthdate;
        this.breed = breed;
        this.eyecolor = eyecolor;
        this.furcolor = furcolor;
        this.details = details;
        this.isAdopt = isAdopt;
        this.status = status;
        this.gender = gender;
        this.category = category;
        this.imgUrl = imgUrl;
        this.owner_id = owner_id;
    }

    public Pet(String pet_name, String birthdate, String breed, String eyecolor, String furcolor, String details, String gender, String category) {
        this.pet_name = pet_name;
        this.birthdate = birthdate;
        this.breed = breed;
        this.eyecolor = eyecolor;
        this.furcolor = furcolor;
        this.details = details;
        this.gender = gender;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPet_name() {
        return pet_name;
    }

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getEyecolor() {
        return eyecolor;
    }

    public void setEyecolor(String eyecolor) {
        this.eyecolor = eyecolor;
    }

    public String getFurcolor() {
        return furcolor;
    }

    public void setFurcolor(String furcolor) {
        this.furcolor = furcolor;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getIsAdopt() {
        return isAdopt;
    }

    public void setIsAdopt(String isAdopt) {
        this.isAdopt = isAdopt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }
}