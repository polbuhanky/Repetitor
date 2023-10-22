package com.platovco.repetitor.models;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class StudentAccount {
    private String photoUrl;
    private String name;
    private String uuid;
    private Integer age;



    public StudentAccount(@NotNull Map<?, ?> map) {
        this.photoUrl = String.valueOf(map.get("photo"));
        this.name = String.valueOf(map.get("name"));
        this.age = Integer.valueOf(String.valueOf(map.get("age")));
        this.uuid = String.valueOf(map.get("$id"));
    }
    public StudentAccount() {}

    public StudentAccount(String photoUrl, String name, Integer age) {
        this.photoUrl = photoUrl;
        this.name = name;
        this.age = age;
    }

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
