package com.platovco.repetitor.models;

public class cardStudent {
    private String photo;
    private String age;
    private String name;

    public cardStudent(String photo, String age, String name) {
        this.photo = photo;
        this.age = age;
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}
