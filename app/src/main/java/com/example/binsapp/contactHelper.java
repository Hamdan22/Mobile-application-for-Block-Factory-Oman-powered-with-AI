package com.example.binsapp;

public class contactHelper {
    private String fullName;
    private String phoneNumber;
    private String message;


    public contactHelper() {
    }

    public contactHelper(String fullName, String phoneNumber, String message) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMessage() {
        return message;
    }
}