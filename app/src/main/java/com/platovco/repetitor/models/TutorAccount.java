package com.platovco.repetitor.models;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class TutorAccount {
    private String photoUrl;
    private String name;
    private String high;
    private String direction;
    private String experience;
    private String uuid;

    public TutorAccount(String photoUrl, String name, String high, String direction, String experience) {
        this.photoUrl = photoUrl;
        this.name = name;
        this.high = high;
        this.direction = direction;
        this.experience = experience;
    }


    public TutorAccount(@NotNull Map<?, ?> map) {
        this.photoUrl = String.valueOf(map.get("photoUrl"));
        this.name = String.valueOf(map.get("name"));
        this.high = String.valueOf(map.get("high"));
        this.direction = String.valueOf(map.get("direction"));
        this.experience = String.valueOf(map.get("experience"));
        this.uuid = String.valueOf(map.get("$id"));
    }
    public TutorAccount() {}


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

}
