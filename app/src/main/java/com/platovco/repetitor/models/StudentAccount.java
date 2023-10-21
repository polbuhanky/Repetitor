package com.platovco.repetitor.models;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class StudentAccount {
    private String photoUrl;
    private String name;
    private String high;
    private String direction;
    private String experience;
    private String uuid;

    public StudentAccount(String photoUrl, String name, String high, String direction, String experience) {
        this.photoUrl = photoUrl;
        this.name = name;
        this.high = high;
        this.direction = direction;
        this.experience = experience;
    }


    public StudentAccount(@NotNull Map<?, ?> map) {
        this.photoUrl = String.valueOf(map.get("Photo"));
        this.name = String.valueOf(map.get("Name"));
        this.high = String.valueOf(map.get("Education"));
        this.direction = String.valueOf(map.get("Direction"));
        this.experience = String.valueOf(map.get("Experience"));
        this.uuid = String.valueOf(map.get("$id"));
    }
    public StudentAccount() {}


    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String carModel) {
        this.high = carModel;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String carBrand) {
        this.direction = carBrand;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String carNumber) {
        this.experience = carNumber;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
