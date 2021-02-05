package com.obadarawashdeh.retreivedata.Model;

public class EmailModel {
    String doctor,email;

    public EmailModel() {
    }

    public EmailModel(String doctor, String email) {
        this.doctor = doctor;
        this.email = email;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
