package com.collection.FreelancerManagement;
public class FreelancerNotFoundException extends Exception {
    public FreelancerNotFoundException(String message) {
        super(message);
    }
    public FreelancerNotFoundException() {
        super("Freelancer not found.");
    }
    @Override
    public String toString() {
        return "FreelancerNotFoundException: " + getMessage();
    }
}
