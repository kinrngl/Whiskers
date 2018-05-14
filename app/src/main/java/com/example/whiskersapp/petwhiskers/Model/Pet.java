package com.example.whiskersapp.petwhiskers.Model;

public class Pet {
    String id;
    String pet_name;
    String birthdate;
    String breed;
    String eyecolor;
    String furcolor;
    String desc;
    String isAdopt; //yes - adopted, no - not yet adopted
    String status; //available, deleted post
    String owner_id;

    public Pet(){

    }

    public Pet(String id, String pet_name, String birthdate, String breed, String eyecolor, String furcolor, String desc, String isAdopt, String status, String owner_id) {
        this.id = id;
        this.pet_name = pet_name;
        this.birthdate = birthdate;
        this.breed = breed;
        this.eyecolor = eyecolor;
        this.furcolor = furcolor;
        this.desc = desc;
        this.isAdopt = isAdopt;
        this.status = status;
        this.owner_id = owner_id;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }
}
