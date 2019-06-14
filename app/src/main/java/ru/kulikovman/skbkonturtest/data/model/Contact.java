package ru.kulikovman.skbkonturtest.data.model;

import ru.kulikovman.skbkonturtest.data.Temperament;

public class Contact {

    private String id;
    private String name;
    private float height;
    private String biography;
    private Temperament temperament;
    private EducationPeriod educationPeriod;

    public Contact() {
    }

    public Contact(String id, String name, float height, String biography, Temperament temperament, EducationPeriod educationPeriod) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.biography = biography;
        this.temperament = temperament;
        this.educationPeriod = educationPeriod;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Temperament getTemperament() {
        return temperament;
    }

    public void setTemperament(Temperament temperament) {
        this.temperament = temperament;
    }

    public EducationPeriod getEducationPeriod() {
        return educationPeriod;
    }

    public void setEducationPeriod(EducationPeriod educationPeriod) {
        this.educationPeriod = educationPeriod;
    }
}
