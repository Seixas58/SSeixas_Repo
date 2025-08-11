package com.example.library2024.dto;

public class LibraryDTO {
    private String id;
    private String address;
    private String closeTime;
    private String name;
    private String openDays;
    private String openTime;

    // Constructors
    public LibraryDTO(){}

    public LibraryDTO(String id, String name, String address, String openDays, String openTime, String closeTime) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.openDays = openDays;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getters and Setters
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenDays() {
        return openDays;
    }

    public void setOpenDays(String openDays) {
        this.openDays = openDays;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    @Override
    public String toString() {
        return "LibraryDTO{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", closeTime='" + closeTime + '\'' +
                ", name='" + name + '\'' +
                ", openDays='" + openDays + '\'' +
                ", openTime='" + openTime + '\'' +
                '}';
    }
}