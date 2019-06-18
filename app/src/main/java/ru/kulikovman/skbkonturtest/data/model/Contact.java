package ru.kulikovman.skbkonturtest.data.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import ru.kulikovman.skbkonturtest.data.Temperament;

public class Contact {

    private String id;
    private String name;
    private String phone;
    private float height;
    private String biography;
    private Temperament temperament;
    private EducationPeriod educationPeriod;

    public Contact() {
    }

    public Contact(String id, String name, String phone, float height, String biography, Temperament temperament, EducationPeriod educationPeriod) {
        this.id = id;
        this.name = name;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getTemperamentName() {
        String name = temperament.name();
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public void setTemperament(Temperament temperament) {
        this.temperament = temperament;
    }

    public EducationPeriod getEducationPeriod() {
        return educationPeriod;
    }

    public String getFormattedEducationPeriod() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return dateFormat.format(educationPeriod.getStart()) + " - " + dateFormat.format(educationPeriod.getEnd());
    }

    public void setEducationPeriod(EducationPeriod educationPeriod) {
        this.educationPeriod = educationPeriod;
    }
}
