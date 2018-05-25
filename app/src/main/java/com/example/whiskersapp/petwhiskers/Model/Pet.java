package com.example.whiskersapp.petwhiskers.Model;

public class Pet {
    String id;
<<<<<<< HEAD
    String petBreed;
    String petCategory;
    String petPrice;
    String petBday;
    String petDesc;
    String petGender;
    String petStatus; //available, deleted post
=======
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
>>>>>>> temp_acain
    String owner_id;

    public Pet(){

    }
<<<<<<< HEAD
    public Pet(String id,String petBreed, String petCategory, String petPrice,String petBday,String petDesc,String petGender,String petStatus,String owner_id ){
        this.id = id;
        this.petBreed = petBreed;
        this.petCategory = petCategory;
        this.petPrice = petPrice;
        this.petBday = petBday;
        this.petDesc = petDesc;
        this.petGender = petGender;
        this.petStatus = petStatus;
        this.owner_id = owner_id;
    }

=======
>>>>>>> temp_acain

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public String getPetCategory() {
        return petCategory;
    }


    public void setPetCategory(String petCategory) {
        this.petCategory = petCategory;
    }

    public String getPetPrice() {
        return petPrice;
    }

    public void setPetPrice(String petPrice) {
        this.petPrice = petPrice;
    }

    public String getPetBday() {
        return petBday;
    }

    public void setPetBday(String petBday) {
        this.petBday = petBday;
    }

    public String getPetDesc(){
        return petDesc;
    }

<<<<<<< HEAD
    public void setPetDesc(String petDesc) {
        this.petDesc = petDesc;
    }

    public String getPetGender() {
        return petGender;
=======
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
>>>>>>> temp_acain
    }

    public void setPetGender(String petDesc) {
        this.petDesc = petDesc;
    }

    public String getPetStatus() {
        return petStatus;
    }

    public void setPetStatus(String petStatus) {
        this.petStatus = petStatus;
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
