package com.misht.shakepaws.home.models;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Heikki Lintulahti on d/f/2018.
 */

public class Pet implements Serializable {
    private int id;
    private String name;
    private String age;
    private int photo;
    private String petOwnerPhoneNumber;
    private String petOwnerName;
    private String petOwnerEmail;
    private String gender;
    private String availability;
    private String location;
    private String description;
    private String breed;
    private String imageUrl;

    public Pet(int id, String name, String age, String phone, int photo) {
        this.setId(id);
        this.setName(name);
        this.setAge(age);
        this.setPetOwnerPhoneNumber(phone);
        this.setPhoto(photo);
    }

    public Pet() {}

    public Pet(String name, String age, String phone, String petOwnerName,
               String petOwnerEmail, String gender, String availability,
               String location, String description, String breed, String imageUrl) {
        this.setName(name);
        this.setAge(age);
        this.setPetOwnerPhoneNumber(phone);
        this.setPetOwnerName(petOwnerName);
        this.setPetOwnerEmail(petOwnerEmail);
        this.setGender(gender);
        this.setAvailability(availability);
        this.setLocation(location);
        this.setDescription(description);
        this.setBreed(breed);
        this.setImageUrl(imageUrl);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPetOwnerPhoneNumber() {
        return petOwnerPhoneNumber;
    }

    public void setPetOwnerPhoneNumber(String petOwnerPhoneNumber) {
        this.petOwnerPhoneNumber = petOwnerPhoneNumber;
    }

    public String getPetOwnerName() {
        return petOwnerName;
    }

    public void setPetOwnerName(String petOwnerName) {
        this.petOwnerName = petOwnerName;
    }

    public String getPetOwnerEmail() {
        return petOwnerEmail;
    }

    public void setPetOwnerEmail(String petOwnerEmail) {
        this.petOwnerEmail = petOwnerEmail;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
