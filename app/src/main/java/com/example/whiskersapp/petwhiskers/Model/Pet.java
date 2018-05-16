package com.example.whiskersapp.petwhiskers.Model;

public class Pet {
    String id;
    String petBreed;
    String petCategory;
    String petPrice;
    String petBday;
    String petDesc;
    String petGender;
    String petStatus; //available, deleted post
    String owner_id;

    public Pet(){

    }
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

    public void setPetDesc(String petDesc) {
        this.petDesc = petDesc;
    }

    public String getPetGender() {
        return petGender;
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


    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }
}
