package com.misht.shakepaws.home.models;

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

    public Pet(int id, String name, String age, String phone, int photo) {
        this.setId(id);
        this.setName(name);
        this.setAge(age);
        this.setPetOwnerPhoneNumber(phone);
        this.setPhoto(photo);
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
}
