package ru.kulikovman.skbkonturtest.data.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import ru.kulikovman.skbkonturtest.data.Temperament;
import ru.kulikovman.skbkonturtest.db.converter.DateConverter;
import ru.kulikovman.skbkonturtest.db.converter.TemperamentConverter;

@Entity
@TypeConverters({TemperamentConverter.class, DateConverter.class})
public class Contact {

    @NonNull
    @PrimaryKey
    private String id;

    private String name;
    private String phone;
    private String clearPhone;
    private float height;
    private String biography;
    private Temperament temperament;

    @Embedded
    private EducationPeriod educationPeriod;

    public Contact(@NonNull String id, String name, String phone, float height, String biography, Temperament temperament, EducationPeriod educationPeriod) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.clearPhone = phone.replaceAll("\\D+","");
        this.height = height;
        this.biography = biography;
        this.temperament = temperament;
        this.educationPeriod = educationPeriod;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
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

    public String getClearPhone() {
        return clearPhone;
    }

    public void setClearPhone(String clearPhone) {
        this.clearPhone = clearPhone;
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

    public String getTemperamentName() {
        String name = temperament.name();
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public String getFormattedEducationPeriod() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return dateFormat.format(educationPeriod.getStart()) + " - " + dateFormat.format(educationPeriod.getEnd());
    }
}
