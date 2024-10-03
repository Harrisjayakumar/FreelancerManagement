package com.collection.FreelancerManagement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class FreelancerManagement {
    private Scanner scanner;
    Statement stmt;
    ResultSet rs;
    dbutil db = new dbutil();
    Connection con;

    public FreelancerManagement() {
        new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void addFreelancer() {
        try {
            System.out.print("Enter freelancer name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
                return;
            }

            System.out.print("Enter freelancer email: ");
            String email = scanner.nextLine().trim();
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                System.out.println("Invalid email format.");
                return;
            }

            System.out.print("Enter freelancer phone number: ");
            String phoneNumber = scanner.nextLine().trim();
            if (!phoneNumber.matches("\\d{10,15}")) {
                System.out.println("Phone number must be 10 to 15 digits long.");
                return;
            }

            System.out.print("Enter freelancer skills: ");
            String skills = scanner.nextLine().trim();
            if (skills.isEmpty()) {
                System.out.println("Skills cannot be empty.");
                return;
            }

            System.out.print("Enter freelancer hourly rate: ");
            double hourlyRate = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            if (hourlyRate <= 0) {
                System.out.println("Hourly rate must be greater than zero.");
                return;
            }

            Freelancer newFreelancer = new Freelancer(generateId(name, email), name, email, phoneNumber, skills, hourlyRate, "Available");

            Connection con = db.getDBConnection();
            Statement stmt = con.createStatement();
            String qry = "INSERT INTO freelancers(name, email, phoneNumber, skills, hourlyRate, status) VALUES('" +
                    newFreelancer.getName() + "', '" +
                    newFreelancer.getEmail() + "', '" +
                    newFreelancer.getPhoneNumber() + "', '" +
                    newFreelancer.getSkills() + "', " +
                    newFreelancer.getHourlyRate() + ", '" +
                    newFreelancer.getStatus() + "')";
            int count = stmt.executeUpdate(qry);
            if (count > 0) {
                System.out.println("Freelancer added successfully.");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private String generateId(String name, String email) {
        String idPart = name.length() > 3 ? name.substring(0, 3) : name;
        return idPart.toUpperCase() + email.substring(0, 3).toUpperCase();
    }

    public void updateFreelancer() throws FreelancerNotFoundException {
        try {
            System.out.print("Enter freelancer ID to update: ");
            String id = scanner.nextLine();

            Freelancer freelancer = findFreelancerById(id);
            if (freelancer == null) {
                throw new FreelancerNotFoundException("Freelancer with ID " + id + " not found.");
            }

            boolean updating = true;

            while (updating) {
                System.out.println("Current details: ");
                System.out.println("Name: " + (freelancer.getName() != null ? freelancer.getName() : "Not set"));
                System.out.println("Email: " + (freelancer.getEmail() != null ? freelancer.getEmail() : "Not set"));
                System.out.println("Phone Number: " + (freelancer.getPhoneNumber() != null ? freelancer.getPhoneNumber() : "Not set"));
                System.out.println("Skills: " + (freelancer.getSkills() != null ? freelancer.getSkills() : "Not set"));
                System.out.println("Hourly Rate: " + freelancer.getHourlyRate());
                System.out.println("Status: " + freelancer.getStatus());

                System.out.println("Which field do you want to update?");
                System.out.println("1. Name");
                System.out.println("2. Email");
                System.out.println("3. Phone Number");
                System.out.println("4. Skills");
                System.out.println("5. Hourly Rate");
                System.out.println("6. Status");
                System.out.println("7. Finish updating");

                System.out.print("Enter the number corresponding to the field: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1:
                        System.out.print("Enter new name (or press Enter to keep current): ");
                        String newName = scanner.nextLine();
                        if (!newName.isEmpty()) {
                            freelancer.setName(newName);
                        }
                        break;
                    case 2:
                        System.out.print("Enter new email (or press Enter to keep current): ");
                        String newEmail = scanner.nextLine();
                        if (!newEmail.isEmpty() && newEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                            freelancer.setEmail(newEmail);
                        } else if (!newEmail.isEmpty()) {
                            System.out.println("Invalid email format.");
                        }
                        break;
                    case 3:
                        System.out.print("Enter new phone number (or press Enter to keep current): ");
                        String newPhoneNumber = scanner.nextLine();
                        if (!newPhoneNumber.isEmpty() && newPhoneNumber.matches("\\d{10,15}")) {
                            freelancer.setPhoneNumber(newPhoneNumber);
                        } else if (!newPhoneNumber.isEmpty()) {
                            System.out.println("Phone number must be 10 to 15 digits long.");
                        }
                        break;
                    case 4:
                        System.out.print("Enter new skills (or press Enter to keep current): ");
                        String newSkills = scanner.nextLine();
                        if (!newSkills.isEmpty()) {
                            freelancer.setSkills(newSkills);
                        }
                        break;
                    case 5:
                        System.out.print("Enter new hourly rate (or press Enter to keep current): ");
                        String hourlyRateInput = scanner.nextLine();
                        if (!hourlyRateInput.isEmpty()) {
                            try {
                                double newHourlyRate = Double.parseDouble(hourlyRateInput);
                                if (newHourlyRate > 0) {
                                    freelancer.setHourlyRate(newHourlyRate);
                                } else {
                                    System.out.println("Hourly rate must be greater than zero.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input for hourly rate.");
                            }
                        }
                        break;
                    case 6:
                        System.out.print("Enter new status (or press Enter to keep current): ");
                        String newStatus = scanner.nextLine();
                        if (!newStatus.isEmpty()) {
                            freelancer.setStatus(newStatus);
                        }
                        break;
                    case 7:
                        updating = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

            // Database update logic
            try (Connection con = db.getDBConnection();
                 Statement stmt = con.createStatement()) {

                String qry = "UPDATE freelancers SET name = '" + freelancer.getName() +
                             "', email = '" + freelancer.getEmail() +
                             "', phoneNumber = '" + freelancer.getPhoneNumber() +
                             "', skills = '" + freelancer.getSkills() +
                             "', hourlyRate = " + freelancer.getHourlyRate() +
                             ", status = '" + freelancer.getStatus() + "' WHERE id = '" + id + "'";
                int count = stmt.executeUpdate(qry);
                if (count > 0) {
                    System.out.println("Freelancer updated successfully.");
                } else {
                    System.out.println("Failed to update freelancer.");
                }
            }
        } catch (InputMismatchException e) {
            handleInvalidInputException("Invalid input for hourly rate.");
        } catch (Exception e) {
            handleRuntimeException("An unexpected error occurred while updating freelancer.");
        }
    }

    public void deleteFreelancer() throws FreelancerNotFoundException {
        try {
            System.out.print("Enter freelancer ID to delete: ");
            String id = scanner.nextLine();

            // Check if the freelancer exists
            Freelancer freelancer = findFreelancerById(id);
            if (freelancer == null) {
                throw new FreelancerNotFoundException("Freelancer with ID " + id + " not found.");
            }

            try (Connection con = db.getDBConnection();
                 Statement stmt = con.createStatement()) {

                String qry = "DELETE FROM freelancers WHERE id = '" + id + "'";
                int count = stmt.executeUpdate(qry);
                if (count > 0) {
                    System.out.println("Freelancer deleted successfully.");
                } else {
                    System.out.println("Failed to delete freelancer.");
                }
            }

        } catch (Exception e) {
            handleRuntimeException("An unexpected error occurred while deleting freelancer.");
        }
    }

    public void searchFreelancer() {
        boolean continueSearching = true;

        while (continueSearching) {
            try {
                System.out.println("Select search criteria: ");
                System.out.println("1. Name");
                System.out.println("2. Email");
                System.out.println("3. Phone Number");
                System.out.println("4. Skills");
                System.out.println("5. Exit");

                System.out.print("Enter the number corresponding to the search criteria: ");
                int typeChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                String selectedType = "";
                boolean searchByName = false;

                switch (typeChoice) {
                    case 1: selectedType = "name"; break;
                    case 2: selectedType = "email"; break;
                    case 3: selectedType = "phoneNumber"; break;
                    case 4: selectedType = "skills"; break;
                    case 5:
                        System.out.println("Exiting search.");
                        continueSearching = false;
                        continue; // Exit the loop
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue; // Continue to the next iteration
                }
                System.out.print("Enter search value: ");
                String searchValue = scanner.nextLine();
                boolean typeFound = false;

                try (Connection con = db.getDBConnection();
                     Statement stmt = con.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT * FROM freelancers WHERE " + selectedType + " LIKE '%" + searchValue + "%'")) {

                    while (rs.next()) {
                        System.out.println("ID: " + rs.getString(1) +
                                " Name: " + rs.getString(2) +
                                " Email: " + rs.getString(3) +
                                " Phone Number: " + rs.getString(4) +
                                " Skills: " + rs.getString(5) +
                                " Hourly Rate: " + rs.getDouble(6) +
                                " Status: " + rs.getString(7));
                        typeFound = true;
                    }
                }

                if (!typeFound) {
                    System.out.println("No freelancers found with the specified criteria.");
                }
            } catch (Exception e) {
                handleRuntimeException("An unexpected error occurred during search.");
            }
        }
    }

    private void handleInvalidInputException(String message) {
        System.out.println(message);
        scanner.nextLine(); // Consume newline
    }

    private void handleRuntimeException(String message) {
        System.out.println(message);
    }

    public void displayFreelancers() {
        try (Connection con = db.getDBConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM freelancers")) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getString(1) +
                        " Name: " + rs.getString(2) +
                        " Email: " + rs.getString(3) +
                        " Phone Number: " + rs.getString(4) +
                        " Skills: " + rs.getString(5) +
                        " Hourly Rate: " + rs.getDouble(6) +
                        " Status: " + rs.getString(7));
            }

        } catch (Exception ex) {
            System.out.println("Error while displaying freelancers: " + ex.getMessage());
        }
    }

    private Freelancer findFreelancerById(String id) {
        try {
            con = db.getDBConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM freelancers WHERE id = '" + id + "'");
            if (rs.next()) {
                return new Freelancer(rs.getString("id"), rs.getString("name"), rs.getString("email"), rs.getString("phoneNumber"), rs.getString("skills"), rs.getDouble("hourlyRate"), rs.getString("status"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
