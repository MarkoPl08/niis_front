package com.frontend.niis_front.dto;

public class RestaurantDTO {
    private String id;
    private String name;
    private Number zipCode;
    private String location;

    public RestaurantDTO() {
    }

    public RestaurantDTO(String id, String name, Number zipCode, String location) {
        this.id = id;
        this.name = name;
        this.zipCode = zipCode;
        this.location = location;
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

    public Number getZipCode() {
        return zipCode;
    }

    public void setZipCode(Number zipCode) {
        this.zipCode = zipCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "RestaurantDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", zipCode=" + zipCode +
                ", location='" + location + '\'' +
                '}';
    }
}
