package com.platovco.repetitor.models;

public class cardTutor {
    private String photo;
    private String name;
    private String direction;
    private String education;
    private String experience;

    public cardTutor(String photo, String name, String direction, String education, String experience) {
        this.photo = photo;
        this.name = name;
        this.direction = direction;
        this.education = education;
        this.experience = experience;
    }

    public String getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    public String getDirection() {
        return direction;
    }

    public String getEducation() {
        return education;
    }

    public String getExperience() {
        return experience;
    }
}
