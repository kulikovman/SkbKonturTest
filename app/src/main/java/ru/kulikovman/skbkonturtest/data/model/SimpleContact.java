package ru.kulikovman.skbkonturtest.data.model;

public class SimpleContact {

    private String id;
    private String name;
    private String phone;
    private float height;

    public SimpleContact(String id, String name, String phone, float height) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.height = height;
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
}
