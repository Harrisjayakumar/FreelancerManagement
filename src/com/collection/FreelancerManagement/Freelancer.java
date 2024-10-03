package com.collection.FreelancerManagement;

public class Freelancer {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String skills;
    private double hourlyRate;
    private String status;

    public Freelancer(String id, String name, String email, String phoneNumber, String skills, double hourlyRate, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.skills = skills;
        this.hourlyRate = hourlyRate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSkills() {
        return skills;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Freelancer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", skills='" + skills + '\'' +
                ", hourlyRate=" + hourlyRate +
                ", status='" + status + '\'' +
                '}';
    }
}