package com.obadarawashdeh.retreivedata.Model;

public class VisitorModel {
    String name,national_number,pob,dob,sex,nationality,phone,email,secondary,average,year,school,country;

    public VisitorModel(String name, String national_number, String pob, String dob, String sex, String nationality, String phone, String email, String secondary, String average, String year, String school, String country) {
        this.name = name;
        this.national_number = national_number;
        this.pob = pob;
        this.dob = dob;
        this.sex = sex;
        this.nationality = nationality;
        this.phone = phone;
        this.email = email;
        this.secondary = secondary;
        this.average = average;
        this.year = year;
        this.school = school;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNational_number() {
        return national_number;
    }

    public void setNational_number(String national_number) {
        this.national_number = national_number;
    }

    public String getPob() {
        return pob;
    }

    public void setPob(String pob) {
        this.pob = pob;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecondary() {
        return secondary;
    }

    public void setSecondary(String secondary) {
        this.secondary = secondary;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
